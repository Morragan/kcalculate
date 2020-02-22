using DietApp.Domain.Models;

namespace DietApp.Domain.Responses
{
    public class GoalParticipationResponse : BaseResponse
    {
        public GoalParticipation GoalParticipation { get; set; }

        public GoalParticipationResponse(bool isSuccessful, string message, GoalParticipation goalParticipation) : base(isSuccessful, message)
        {
            GoalParticipation = goalParticipation;
        }
    }
}
