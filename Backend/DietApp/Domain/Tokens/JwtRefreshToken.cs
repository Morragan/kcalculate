namespace DietApp.Domain.Tokens
{
    public class JwtRefreshToken : JsonWebToken
    {
        public JwtRefreshToken(string token, long expiration) : base(token, expiration) { }
    }
}
