using DietApp.Domain.Models;
using System.Collections.Generic;

namespace DietApp.Domain.Responses
{
    public class FindMealsResponse : BaseResponse
    {
        public IEnumerable<Meal> MealsFound { get; set; }
        public FindMealsResponse(bool isSuccess, string message, IEnumerable<Meal> mealsFound) : base(isSuccess, message)
        {
            MealsFound = mealsFound;
        }
    }
}
