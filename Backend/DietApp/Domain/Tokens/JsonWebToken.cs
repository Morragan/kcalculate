using System;

namespace DietApp.Domain.Tokens
{
    public abstract class JsonWebToken
    {
        public string Token { get; protected set; }
        public long Expiration { get; protected set; }

        public JsonWebToken(string token, long expiration)
        {
            if (string.IsNullOrWhiteSpace(token)) throw new ArgumentException("Invalid token value");
            if (expiration <= 0) throw new ArgumentException("Invalid expiration value");

            Token = token;
            Expiration = expiration;
        }
    }
}
