using DietApp.Domain.Models;
using DietApp.Domain.Responses;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IRefreshTokenRepository
    {
        Task<RefreshToken> GetRefreshToken(string token);
        Task Add(RefreshToken token);
        void Remove(RefreshToken token);
    }
}
