using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using System.Threading.Tasks;

namespace DietApp.Persistence.Repositories
{
    public class GoalsRepository : BaseRepository, IGoalsRepository
    {
        public GoalsRepository(ApplicationDbContext context) : base(context) { }

        public async Task Add(Goal goal)
        {
            await context.AddAsync(goal).ConfigureAwait(false);
        }
    }
}
