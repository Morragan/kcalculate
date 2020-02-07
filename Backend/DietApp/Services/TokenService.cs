using DietApp.Domain.Helpers;
using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Services;
using DietApp.Domain.Tokens;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class TokenService : ITokenService
    {
        readonly IRefreshTokenRepository refreshTokenRepository;
        readonly IUnitOfWork unitOfWork;
        readonly SigningConfigurations signingConfigurations;
        readonly TokenClaims tokenClaims;
        readonly IPasswordHasher passwordHasher;

        public TokenService(IRefreshTokenRepository refreshTokenRepository, IUnitOfWork unitOfWork, IOptions<TokenClaims> tokenOptionsSnapshot, SigningConfigurations signingConfigurations, IPasswordHasher passwordHasher)
        {
            this.refreshTokenRepository = refreshTokenRepository;
            this.unitOfWork = unitOfWork;
            this.signingConfigurations = signingConfigurations;
            this.tokenClaims = tokenOptionsSnapshot.Value;
            this.passwordHasher = passwordHasher;
        }
        public async Task<JwtAccessToken> CreateAccessToken(User user)
        {
            //Refresh token
            var refreshToken = new JwtRefreshToken(
                token: passwordHasher.HashPassword(Guid.NewGuid().ToString()),
                expiration: DateTimeOffset.UtcNow.AddSeconds(tokenClaims.RefreshTokenExpiration).ToUnixTimeMilliseconds());
            //Access token
            var accessTokenExpiration = DateTimeOffset.UtcNow.AddSeconds(tokenClaims.AccessTokenExpiration).UtcDateTime;
            var securityToken = new JwtSecurityToken(
                issuer: tokenClaims.Issuer,
                audience: tokenClaims.Audience,
                claims: GetClaims(user),
                expires: accessTokenExpiration,
                notBefore: DateTime.UtcNow,
                signingCredentials: signingConfigurations.SigningCredentials);
            var jwtHandler = new JwtSecurityTokenHandler();
            var accessToken = jwtHandler.WriteToken(securityToken);

            try
            {
                await SaveRefreshTokenToDatabase(refreshToken, user.ID).ConfigureAwait(false);
            }
            catch (DbUpdateConcurrencyException)
            {
                return null;
            }
            return new JwtAccessToken(accessToken, new DateTimeOffset(accessTokenExpiration).ToUnixTimeMilliseconds(), refreshToken);
        }

        public async Task RevokeRefreshToken(string token)
        {
            await TakeRefreshToken(token).ConfigureAwait(false);
        }

        public async Task<JwtRefreshToken> TakeRefreshToken(string token)
        {
            if (string.IsNullOrWhiteSpace(token)) return null;
            var refreshToken = await refreshTokenRepository.GetRefreshToken(token).ConfigureAwait(false);
            if (refreshToken != null) refreshTokenRepository.Remove(refreshToken);
            return new JwtRefreshToken(refreshToken);
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

        private async Task SaveRefreshTokenToDatabase(JwtRefreshToken token, int userID)
        {
            var refreshToken = new RefreshToken(token, userID);
            await refreshTokenRepository.Add(refreshToken).ConfigureAwait(false);
            await unitOfWork.Complete().ConfigureAwait(false);
        }
    }
}
