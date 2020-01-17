using DietApp.Domain.Models;

namespace DietApp.Domain.Tokens
{
    public class JwtRefreshToken : JsonWebToken
    {
        public JwtRefreshToken(string token, long expiration) : base(token, expiration) { }
        public JwtRefreshToken(RefreshToken refreshToken) : base(refreshToken.Token, refreshToken.Expiration) { }
    }
}
