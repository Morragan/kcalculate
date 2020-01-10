using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface IScheduledActionsService
    {
        Task CreateDailyUserScoreSummaries();
    }
}
