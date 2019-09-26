using DietApp.Domain.Helpers;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.Domain.Tokens;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;

namespace DietApp.Services
{
    public class TokenService : ITokenService
    {
        readonly ISet<JwtRefreshToken> refreshTokens = new HashSet<JwtRefreshToken>(); //TODO: przenieść do bazy

        readonly SigningConfigurations signingConfigurations;
        readonly TokenClaims tokenClaims;
        readonly IPasswordHasher passwordHasher;

        public TokenService(IOptions<TokenClaims> tokenOptionsSnapshot, SigningConfigurations signingConfigurations, IPasswordHasher passwordHasher)
        {
            this.signingConfigurations = signingConfigurations;
            this.tokenClaims = tokenOptionsSnapshot.Value;
            this.passwordHasher = passwordHasher;
        }
        public JwtAccessToken CreateAccessToken(User user)
        {
            //Refresh token
            var refreshToken = new JwtRefreshToken(
                token: passwordHasher.HashPassword(Guid.NewGuid().ToString()),
                expiration: DateTime.UtcNow.AddSeconds(tokenClaims.RefreshTokenExpiration).Ticks);
            //Access token
            var accessTokenExpiration = DateTime.UtcNow.AddSeconds(tokenClaims.AccessTokenExpiration);
            var securityToken = new JwtSecurityToken(
                issuer: tokenClaims.Issuer,
                audience: tokenClaims.Audience,
                claims: GetClaims(user),
                expires: accessTokenExpiration,
                notBefore: DateTime.UtcNow,
                signingCredentials: signingConfigurations.SigningCredentials);
            var jwtHandler = new JwtSecurityTokenHandler();
            var accessToken = jwtHandler.WriteToken(securityToken);

            refreshTokens.Add(refreshToken); //TODO: nie trzymać tokenów w pamięci

            return new JwtAccessToken(accessToken, accessTokenExpiration.Ticks, refreshToken);
        }

        public void RevokeRefreshToken(string token)
        {
            TakeRefreshToken(token);
        }

        public JwtRefreshToken TakeRefreshToken(string token)
        {
            if (string.IsNullOrWhiteSpace(token)) return null;
            var refreshToken = refreshTokens.SingleOrDefault(t => t.Token == token);
            if (refreshToken != null)
                refreshTokens.Remove(refreshToken);
            return refreshToken;
        }

        private IEnumerable<Claim> GetClaims(User user)
        {
            var claims = new List<Claim>
            {
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString()),
                new Claim(JwtRegisteredClaimNames.Sub, user.Email),
                new Claim(ClaimTypes.Role, user.Role.ToString()),
                new Claim(ClaimTypes.GivenName, user.ID.ToString())
            };
            return claims;
        }
    }
}
