using DietApp.Domain.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IMealEntryRepository
    {
        Task Add(MealEntry mealEntry);
        Task<IEnumerable<MealEntry>> List(int userId);
        Task<IEnumerable<MealEntry>> ListFromDay(int userId, DateTime day);
    }
}
