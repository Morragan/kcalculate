using DietApp.Domain.Models;
using DietApp.Domain.Responses;
using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface IMealEntryService
    {
        Task<RecordMealResponse> RecordMeal(MealEntry mealEntry); //TODO: Dodać usuwanie xD
        Task<FindMealEntriesResponse> GetMealEntries(int userId);
        Task<FindMealEntriesResponse> GetMealEntriesToday(int userId);
    }
}
