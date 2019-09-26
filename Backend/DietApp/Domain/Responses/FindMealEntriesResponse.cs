using DietApp.Domain.Models;
using System.Collections.Generic;

namespace DietApp.Domain.Responses
{
    public class FindMealEntriesResponse : BaseResponse
    {
        public IEnumerable<MealEntry> MealEntries { get; set; }
        public FindMealEntriesResponse(bool isSuccess, string message, IEnumerable<MealEntry> mealEntries) : base(isSuccess, message)
        {
            MealEntries = mealEntries;
        }
    }
}
