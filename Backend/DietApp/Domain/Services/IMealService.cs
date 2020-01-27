using DietApp.Domain.Models;
using DietApp.Domain.Responses;
using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface IMealService
    {
        Task<FindMealsResponse> GetUserMeals(int userID);
        Task<MealResponse> Create(Meal meal);
        Task<MealResponse> Delete(Meal meal);
        Task<MealResponse> Update(Meal meal);
        Task<FindPublicMealsResponse> FindByBarcode(string barcode);
        Task<FindPublicMealsResponse> FindByName(string name);
        Task<MealResponse> CreatePublic(PublicMeal meal, int userId);
    }
}
