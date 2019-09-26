using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.Persistence.Repositories
{
    public class MealEntryRepository : BaseRepository, IMealEntryRepository
    {
        public MealEntryRepository(ApplicationDbContext context) : base(context) { }

        public async Task Add(MealEntry mealEntry)
        {
            await context.AddAsync(mealEntry);
        }

        public async Task<IEnumerable<MealEntry>> List(int userId)
        {
            return await context.MealEntries.Where(m => m.UserID == userId).ToListAsync();
        }

        public async Task<IEnumerable<MealEntry>> ListToday(int userId)
        {
            return await context.MealEntries.Where(m => m.UserID == userId && m.Date.ToUniversalTime().Date == DateTime.Today).ToListAsync();
        }
    }
}
