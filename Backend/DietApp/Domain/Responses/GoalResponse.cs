using DietApp.Domain.Models;

namespace DietApp.Domain.Responses
{
    public class GoalResponse : BaseResponse
    {
        public Goal Goal { get; set; }

        public GoalResponse(bool isSuccess, string message, Goal goal) : base(isSuccess, message)
        {
            Goal = goal;
        }
    }
}
