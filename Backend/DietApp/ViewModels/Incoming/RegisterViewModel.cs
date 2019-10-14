using DietApp.Domain.Models;

namespace DietApp.ViewModels
{
    public class RegisterViewModel
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string Nickname { get; set; }
        public string AvatarLink { get; set; }
        public int CalorieLimit { get; set; }
    }
}
