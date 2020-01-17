using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using System.Threading.Tasks;

namespace DietApp.Persistence.Repositories
{
    public class ScoreLogRepository : BaseRepository, IScoreLogRepository
    {
        public ScoreLogRepository(ApplicationDbContext context) : base(context) { }

        public async Task Add(ScoreLog scoreLog)
        {
            await context.AddAsync(scoreLog);
        }
    }
}
