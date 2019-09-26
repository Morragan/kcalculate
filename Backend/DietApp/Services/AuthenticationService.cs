using DietApp.Domain.Helpers;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class AuthenticationService : IAuthenticationService
    {
        readonly IUserService userService;
        readonly IPasswordHasher passwordHasher;
        readonly ITokenService tokenService;

        public AuthenticationService(IUserService userService, IPasswordHasher passwordHasherService, ITokenService tokenService)
        {
            this.userService = userService;
            this.passwordHasher = passwordHasherService;
            this.tokenService = tokenService;
        }
        public async Task<TokenResponse> CreateAccessToken(string nickname, string password)
        {
            var user = await userService.FindByNickname(nickname);
            if (user == null || !passwordHasher.CheckPasswordMatching(password, user.Password))
                return new TokenResponse(false, "Invalid email and/or password", null);

            var token = tokenService.CreateAccessToken(user);
            return new TokenResponse(true, null, token);
        }

        public async Task<TokenResponse> RefreshToken(string refreshToken, string userEmail)
        {
            var token = tokenService.TakeRefreshToken(refreshToken);
            if (token == null) return new TokenResponse(false, "Invalid refresh token", null);
            if (token.IsExpired()) return new TokenResponse(false, "Expired refresh token", null);
            var user = await userService.FindByEmail(userEmail);
            if (user == null) return new TokenResponse(false, "Invalid refresh token", null);

            var accessToken = tokenService.CreateAccessToken(user);
            return new TokenResponse(true, null, accessToken);
        }

        public void RevokeRefreshToken(string refreshToken)
        {
            tokenService.RevokeRefreshToken(refreshToken);
        }
    }
}
