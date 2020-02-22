using DietApp.Domain.Models;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IGoalsRepository
    {
        Task Add(Goal goal);
    }
}
