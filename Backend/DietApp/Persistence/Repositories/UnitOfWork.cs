using System.Threading.Tasks;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;

namespace DietApp.Persistence.Repositories
{
    public class UnitOfWork : IUnitOfWork
    {
        readonly ApplicationDbContext context;

        public UnitOfWork(ApplicationDbContext context)
        {
            this.context = context;
        }

        public async Task Complete()
        {
            await context.SaveChangesAsync().ConfigureAwait(false);
        }
    }
}
