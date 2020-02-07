using DietApp.Domain.Helpers;
using DietApp.Domain.Repositories;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using DietApp.Domain.Tokens;
using Microsoft.IdentityModel.Tokens;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class AuthenticationService : IAuthenticationService
    {
        readonly IUserService userService;
        readonly IPasswordHasher passwordHasher;
        readonly ITokenService tokenService;
        readonly IRefreshTokenRepository refreshTokenRepository;
        readonly IUnitOfWork unitOfWork;
        readonly SigningConfigurations configurations;

        public AuthenticationService(IUserService userService, IPasswordHasher passwordHasher, ITokenService tokenService, IRefreshTokenRepository refreshTokenRepository, IUnitOfWork unitOfWork, SigningConfigurations configurations)
        {
            this.userService = userService;
            this.passwordHasher = passwordHasher;
            this.tokenService = tokenService;
            this.refreshTokenRepository = refreshTokenRepository;
            this.unitOfWork = unitOfWork;
            this.configurations = configurations;
        }
        public async Task<TokenResponse> CreateAccessToken(string nickname, string password)
        {
            var user = await userService.FindByNickname(nickname).ConfigureAwait(false);
            if (user == null || !passwordHasher.CheckPasswordMatching(password, user.Password))
                return new TokenResponse(false, "Invalid email and/or password", null);

            var token = await tokenService.CreateAccessToken(user).ConfigureAwait(false);
            return new TokenResponse(true, null, token);
        }

        public async Task<TokenResponse> RefreshToken(string refreshToken)
        {
            var token = await refreshTokenRepository.GetRefreshToken(refreshToken).ConfigureAwait(false);
            if (token == null) return new TokenResponse(false, "Invalid refresh token", null);

            refreshTokenRepository.Remove(token);
            await unitOfWork.Complete().ConfigureAwait(false);

            if (token.Expiration < DateTimeOffset.UtcNow.ToUnixTimeMilliseconds()) return new TokenResponse(false, "Expired refresh token", null);
            if (token.User == null) return new TokenResponse(false, "Invalid refresh token", null);

            var accessToken = await tokenService.CreateAccessToken(token.User).ConfigureAwait(false);
            if (accessToken == null) return new TokenResponse(false, "Token is already being refreshed", null);
            return new TokenResponse(true, null, accessToken);
        }

        public void RevokeRefreshToken(string refreshToken)
        {
            tokenService.RevokeRefreshToken(refreshToken);
        }


        public ClaimsPrincipal GetPrincipalFromToken(string token, bool validateLifetime)
        {
            var tokenValidationParameters = new TokenValidationParameters
            {
                ValidateAudience = false,
                ValidateIssuer = false,
                ValidateIssuerSigningKey = true,
                ValidateLifetime = validateLifetime,
                IssuerSigningKey = configurations.Key
            };

            var tokenHandler = new JwtSecurityTokenHandler();
            var principal = tokenHandler.ValidateToken(token, tokenValidationParameters, out SecurityToken securityToken);

            if (!(securityToken is JwtSecurityToken jwtSecurityToken)
                || !jwtSecurityToken.Header.Alg.Equals(SecurityAlgorithms.RsaSha256Signature, StringComparison.InvariantCultureIgnoreCase))
                throw new SecurityTokenException("Invalid token");

            return principal;
        }

    }
}
