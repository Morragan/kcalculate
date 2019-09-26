using System;

namespace DietApp.Domain.Tokens
{
    public class JwtAccessToken : JsonWebToken
    {
        public JwtRefreshToken RefreshToken { get; }

        public JwtAccessToken(string token, long expiration, JwtRefreshToken refreshToken) : base(token, expiration)
        {
            RefreshToken = refreshToken ?? throw new ArgumentException("Invalid refresh token");
        }
    }
}
