using DietApp.Domain.Helpers;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using DietApp.Domain.Tokens;
using Microsoft.Extensions.Configuration;
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
        readonly SigningConfigurations configurations;

        public AuthenticationService(IUserService userService, IPasswordHasher passwordHasher, ITokenService tokenService, SigningConfigurations configurations)
        {
            this.userService = userService;
            this.passwordHasher = passwordHasher;
            this.tokenService = tokenService;
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

        public async Task<TokenResponse> RefreshToken(string refreshToken, string userEmail)
        {
            var token = await tokenService.TakeRefreshToken(refreshToken).ConfigureAwait(false);
            if (token == null) return new TokenResponse(false, "Invalid refresh token", null);
            if (token.IsExpired()) return new TokenResponse(false, "Expired refresh token", null);

            var user = await userService.FindByEmail(userEmail).ConfigureAwait(false);
            if (user == null) return new TokenResponse(false, "Invalid refresh token", null);

            var accessToken = await tokenService.CreateAccessToken(user).ConfigureAwait(false);
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
