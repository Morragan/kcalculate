using DietApp.Domain.Responses;
using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface IGoalsService
    {
        Task<GoalParticipationResponse> Create(int userId, float weightGoal, int[] invitedUsers);
        Task AcceptInvitation(int userId);
        Task Remove(int userId);
    }
}
