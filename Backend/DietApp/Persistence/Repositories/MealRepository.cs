using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.Persistence.Repositories
{
    public class MealRepository : BaseRepository, IMealRepository
    {
        public MealRepository(ApplicationDbContext context) : base(context) { }
        public async Task Add(Meal meal)
        {
            await context.AddAsync(meal);
        }

        public void Delete(Meal meal)
        {
            context.Remove(meal);
        }

        public async Task<IEnumerable<Meal>> List(int userID)
        {
            return await context.Meals.Where(m => m.UserID == userID).ToListAsync().ConfigureAwait(false);
        }

        public void Update(Meal meal)
        {
            context.Update(meal);
        }

        public async Task<Meal> FindById(int id)
        {
            return await context.Meals.SingleOrDefaultAsync(m => m.ID == id).ConfigureAwait(false);
        }
    }
}
