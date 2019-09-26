using DietApp.Domain.Responses;
using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface IAuthenticationService
    {
        Task<TokenResponse> CreateAccessToken(string email, string password);
        Task<TokenResponse> RefreshToken(string refreshToken, string userEmail);
        void RevokeRefreshToken(string refreshToken);
    }
}
