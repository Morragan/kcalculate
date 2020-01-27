using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using System;
using Newtonsoft.Json.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Extensions.Configuration;
using System.Text;
using DietApp.Helpers;
using System.Net.Http.Headers;
using System.Web;
using DietApp.ViewModels.Incoming;
using AutoMapper;

namespace DietApp.Persistence.Repositories
{
    public class PublicMealRepository : BaseRepository, IPublicMealRepository
    {
        readonly IConfiguration configuration;
        readonly FatSecretAPITokenCache fatSecretAPITokenCache;
        readonly IMapper mapper;

        public PublicMealRepository(ApplicationDbContext context, IConfiguration configuration, FatSecretAPITokenCache fatSecretAPITokenCache, IMapper mapper) : base(context)
        {
            this.configuration = configuration;
            this.fatSecretAPITokenCache = fatSecretAPITokenCache;
            this.mapper = mapper;
        }

        public async Task<IEnumerable<PublicMeal>> FetchByName(string name, int quantity)
        {
            if (fatSecretAPITokenCache.Expiration <= DateTime.UtcNow.Ticks)
                await FetchFatSecretAccessToken().ConfigureAwait(false);

            using HttpClient httpClient = new HttpClient();
            httpClient.BaseAddress = new Uri(configuration.GetValue<string>("FatSecretBaseAddress"));
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", fatSecretAPITokenCache.Token);

            var query = HttpUtility.ParseQueryString(string.Empty);
            query["method"] = "foods.search";
            query["format"] = "json";
            query["search_expression"] = name;
            query["max_results"] = quantity.ToString();
            var response = await httpClient.GetAsync($"?{query.ToString()}").ConfigureAwait(false);
            response.EnsureSuccessStatusCode();

            var responseBody = await response.Content.ReadAsStringAsync().ConfigureAwait(false);
            var mealsIds = JObject.Parse(responseBody)["foods"]["food"].Children()
                .Select(mealData => mealData["food_id"].ToString());

            var meals = (await Task.WhenAll(mealsIds
                .Select(mealId => FetchMealNutrientsData(mealId, httpClient))
                .ToArray()
            ).ConfigureAwait(false)).ToList();
            meals.RemoveAll(meal => meal == null);

            return meals;
        }

        public async Task<PublicMeal> FetchByBarcode(string barcode)
        {
            using var httpClient = new HttpClient
            {
                BaseAddress = new Uri(configuration.GetValue<string>("OpenFoodFactsBaseAddress"))
            };
            var response = await httpClient.GetAsync($"/api/v0/product/{barcode}.json").ConfigureAwait(false);
            response.EnsureSuccessStatusCode();

            var responseBody = await response.Content.ReadAsStringAsync().ConfigureAwait(false);
            var productData = JObject.Parse(responseBody)["product"];
            if (productData == null) return null;

            string mealName = productData["product_name"].ToObject<string>();
            var nutrientsViewModel = productData["nutriments"].ToObject<OpenFoodFactsNutrimentsViewModel>();
            var nutrients = mapper.Map<OpenFoodFactsNutrimentsViewModel, Nutrients>(nutrientsViewModel);

            return new PublicMeal(mealName, nutrients, barcode);
        }

        public async Task<IEnumerable<PublicMeal>> GetCachedByName(string name)
        {
            return await context.PublicMeals.Where(m => m.Name.ToLower().Contains(name.ToLower())).ToListAsync().ConfigureAwait(false);
        }

        public async Task<PublicMeal> GetCachedByBarcode(string barcode)
        {
            return await context.PublicMeals.SingleOrDefaultAsync(m => m.Barcode == barcode).ConfigureAwait(false);
        }

        public async Task Cache(PublicMeal publicMeal)
        {
            if (context.PublicMeals.Any(cachedMeal => cachedMeal.Barcode == publicMeal.Barcode)) return;
            await context.AddAsync(publicMeal).ConfigureAwait(false);
        }

        public async Task Cache(IEnumerable<PublicMeal> publicMeals)
        {
            var nonCachedMeals = publicMeals
                .Where(meal => context.PublicMeals.All(cachedMeal => cachedMeal.Name != meal.Name));
            await context.AddRangeAsync(nonCachedMeals).ConfigureAwait(false);
        }

        private async Task<PublicMeal> FetchMealNutrientsData(string mealId, HttpClient httpClient)
        {
            var query = HttpUtility.ParseQueryString(string.Empty);
            query["method"] = "food.get";
            query["food_id"] = mealId;
            query["format"] = "json";
            var mealResponse = await httpClient.GetAsync($"?{query.ToString()}").ConfigureAwait(false);
            mealResponse.EnsureSuccessStatusCode();

            var mealResponseBody = await mealResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
            var mealResponseData = JObject.Parse(mealResponseBody)["food"];
            var foodName = mealResponseData["food_name"].ToString();
            var servingsResponseData = mealResponseData["servings"]["serving"].Children();
            var servingData = servingsResponseData.Where(
                serving =>
                {
                    if (serving.Children().Count() < 20) return false;
                    var numberOfUnits = serving["number_of_units"];
                    var measurement = serving["measurement_description"];
                    return numberOfUnits.ToObject<float>() == 100.0 && measurement.ToString() == "g";
                }).SingleOrDefault();

            if (servingData == null) return null;

            var nutrientsViewModel = servingData.ToObject<FatSecretNutrientsViewModel>();
            var nutrients = mapper.Map<FatSecretNutrientsViewModel, Nutrients>(nutrientsViewModel);

            return new PublicMeal(foodName, nutrients);
        }

        private async Task FetchFatSecretAccessToken()
        {
            using HttpClient httpClient = new HttpClient();
            var byteArray = Encoding.ASCII.GetBytes(
                $"{configuration.GetValue<string>("FatSecretClientID")}:{configuration.GetValue<string>("FatSecretClientSecret")}"
            );
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Basic", Convert.ToBase64String(byteArray));

            var values = new Dictionary<string, string>
            {
               { "scope", "basic" },
               { "grant_type", "client_credentials" }
            };
            using var content = new FormUrlEncodedContent(values);
            var tokenResponse = await httpClient.PostAsync(configuration.GetValue<string>("FatSecretOAuthBaseAddress"), content).ConfigureAwait(false);

            var tokenResponseBody = await tokenResponse.Content.ReadAsStringAsync().ConfigureAwait(false);
            var tokenData = JObject.Parse(tokenResponseBody);
            var accessToken = tokenData["access_token"].ToString();
            var expiration = tokenData["expires_in"].ToObject<long>();
            fatSecretAPITokenCache.Save(accessToken, expiration);
        }

        public async Task<bool> ContainsBarcode(string barcode)
        {
            return await context.PublicMeals.AnyAsync(meal => meal.Barcode == barcode).ConfigureAwait(false);
        }
    }
}
