using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IUnitOfWork
    {
        Task Complete();
    }
}
