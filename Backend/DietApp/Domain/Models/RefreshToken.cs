using DietApp.Domain.Tokens;

namespace DietApp.Domain.Models
{
    public class RefreshToken
    {
        public int ID { get; set; }
        public string Token { get; set; }
        public long Expiration { get; set; }
        public int UserID { get; set; }
        public User User { get; set; }

        public RefreshToken(JwtRefreshToken token, int userID)
        {
            Token = token.Token;
            Expiration = token.Expiration;
            UserID = userID;
        }
        public RefreshToken() { }
    }
}
