using DietApp.Domain.Models;
using System.Collections.Generic;

namespace DietApp.Domain.Responses
{
    public class FindPublicMealsResponse : BaseResponse
    {
        public IEnumerable<PublicMeal> PublicMealsFound { get; }
        public FindPublicMealsResponse(bool isSuccess, string message, IEnumerable<PublicMeal> publicMealsFound) : base(isSuccess, message)
        {
            PublicMealsFound = publicMealsFound;
        }
        public FindPublicMealsResponse(bool isSuccess, string message, PublicMeal publicMealFound) : base(isSuccess, message)
        {
            PublicMealsFound = new List<PublicMeal> { publicMealFound };
        }
    }
}
