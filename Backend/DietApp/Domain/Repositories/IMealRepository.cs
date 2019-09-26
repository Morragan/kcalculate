using DietApp.Domain.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IMealRepository
    {
        Task<IEnumerable<Meal>> List(int userID);
        Task Add(Meal meal);
        void Delete(Meal meal);
        void Update(Meal meal);
        Task<Meal> FindById(int id);
    }
}
