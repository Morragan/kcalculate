namespace DietApp.Domain.Models
{
    public class RefreshToken
    {
        public string ID { get; set; }
        public string Token { get; set; }
        public int Expiration { get; set; }
        public int UserID { get; set; }
        public User User { get; set; }
    }
}
