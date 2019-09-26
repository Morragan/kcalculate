using DietApp.Domain.Models;
using DietApp.Domain.Tokens;

namespace DietApp.Domain.Services
{
    public interface ITokenService
    {
        JwtAccessToken CreateAccessToken(User user);
        JwtRefreshToken TakeRefreshToken(string token);
        void RevokeRefreshToken(string token);
    }
}
