using System;

namespace DietApp.Helpers
{
    public class FatSecretAPITokenCache
    {
        public string Token { get; private set; } = "";
        public long Expiration { get; private set; } = -1;
        public void Save(string token, long expiration)
        {
            Token = token;
            Expiration = DateTime.UtcNow.Ticks + expiration;
        }
    }
}
