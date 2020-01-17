using DietApp.Domain.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IPublicMealRepository
    {
        Task<IEnumerable<PublicMeal>> FetchByName(string name, int quantity);
        Task<PublicMeal> FetchByBarcode(string barcode);
        Task<IEnumerable<PublicMeal>> GetCachedByName(string name);
        Task<PublicMeal> GetCachedByBarcode(string barcode);
        Task Cache(PublicMeal publicMeal);
        Task Cache(IEnumerable<PublicMeal> publicMeals);
    }
}
