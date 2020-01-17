using DietApp.Domain.Models;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IScoreLogRepository
    {
        Task Add(ScoreLog scoreLog);
    }
}
