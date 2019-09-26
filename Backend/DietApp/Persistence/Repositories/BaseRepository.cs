using DietApp.Persistence.Contexts;

namespace DietApp.Persistence.Repositories
{
    public abstract class BaseRepository
    {
        protected readonly ApplicationDbContext context;

        public BaseRepository(ApplicationDbContext context)
        {
            this.context = context;
        }
    }
}
