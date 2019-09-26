using DietApp.Domain.Models;

namespace DietApp.Domain.Responses
{
    public class MealResponse : BaseResponse
    {
        public MealResponse(bool isSuccess, string message) : base(isSuccess, message) { }
    }
}
