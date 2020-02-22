using DietApp.Domain.Responses;
using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface IGoalsService
    {
        Task<GoalResponse> Create(int userId, int weightGoal, int[] invitedUsers);
        Task AcceptInvitation(int userId);
        Task Remove(int userId);
    }
}
