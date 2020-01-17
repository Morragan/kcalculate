using DietApp.Domain.Models;
using DietApp.Domain.Tokens;
using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface ITokenService
    {
        Task<JwtAccessToken> CreateAccessToken(User user);
        Task<JwtRefreshToken> TakeRefreshToken(string token);
        Task RevokeRefreshToken(string token);
    }
}
