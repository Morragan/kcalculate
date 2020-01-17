using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using Microsoft.Extensions.Configuration;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class MealService : IMealService
    {
        readonly IConfiguration configuration;
        readonly IMealRepository mealRepository;
        readonly IPublicMealRepository publicMealRepository;
        readonly IUnitOfWork unitOfWork;

        public MealService(IConfiguration configuration, IMealRepository mealRepository, IPublicMealRepository publicMealRepository, IUnitOfWork unitOfWork)
        {
            this.configuration = configuration;
            this.mealRepository = mealRepository;
            this.publicMealRepository = publicMealRepository;
            this.unitOfWork = unitOfWork;
        }

        public async Task<MealResponse> Create(Meal meal)
        {
            await mealRepository.Add(meal).ConfigureAwait(false);
            await unitOfWork.Complete().ConfigureAwait(false);

            return new MealResponse(true, null);
        }

        public async Task<MealResponse> Delete(Meal meal)
        {
            mealRepository.Delete(meal);
            await unitOfWork.Complete().ConfigureAwait(false);

            return new MealResponse(true, null);
        }

        public async Task<FindPublicMealsResponse> FindByBarcode(string barcode)
        {
            var cacheResponse = await publicMealRepository.GetCachedByBarcode(barcode).ConfigureAwait(false);
            if (cacheResponse != null) return new FindPublicMealsResponse(true, null, cacheResponse);

            try
            {
                var apiResponse = await publicMealRepository.FetchByBarcode(barcode).ConfigureAwait(false);
                if (apiResponse == null) return new FindPublicMealsResponse(false, "Barcode not found", (PublicMeal)null);

                await publicMealRepository.Cache(apiResponse).ConfigureAwait(false);
                await unitOfWork.Complete().ConfigureAwait(false);
                return new FindPublicMealsResponse(true, null, apiResponse);
            }
            catch (HttpRequestException)
            {
                return new FindPublicMealsResponse(false, "Error connecting to Nutrition API", (PublicMeal)null);
            }
        }

        public async Task<FindPublicMealsResponse> FindByName(string name)
        {
            var cacheResponse = await publicMealRepository.GetCachedByName(name).ConfigureAwait(false);
            if (cacheResponse.Count() > 0) return new FindPublicMealsResponse(true, null, cacheResponse);

            try
            {
                int quantity = configuration.GetValue<int>("FatSecretMaxMeals");
                var apiResponse = await publicMealRepository.FetchByName(name, quantity).ConfigureAwait(false);

                await publicMealRepository.Cache(apiResponse).ConfigureAwait(false);
                await unitOfWork.Complete().ConfigureAwait(false);
                return new FindPublicMealsResponse(true, null, apiResponse);
            }
            catch (HttpRequestException)
            {
                return new FindPublicMealsResponse(false, "Error connecting to Nutrition API", (PublicMeal)null);
            }
        }

        public async Task<FindMealsResponse> GetUserMeals(int userID)
        {
            var meals = await mealRepository.List(userID).ConfigureAwait(false);

            return new FindMealsResponse(true, null, meals);
        }

        public async Task<MealResponse> Update(Meal meal)
        {
            var existingMeal = await mealRepository.FindById(meal.ID).ConfigureAwait(false);
            if (existingMeal == null)
            {
                return new MealResponse(false, "Meal doesn't exist");
            }

            mealRepository.Update(meal);
            await unitOfWork.Complete().ConfigureAwait(false);

            return new MealResponse(true, null);
        }
    }
}
