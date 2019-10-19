namespace DietApp.ViewModels
{
    public class RegisterViewModel
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string Nickname { get; set; }
        public string AvatarLink { get; set; }
        public int CalorieLimitLower { get; set; }
        public int CalorieLimitUpper { get; set; }
        public int CarbsLimitLower { get; set; }
        public int CarbsLimitUpper { get; set; }
        public int FatLimitLower { get; set; }
        public int FatLimitUpper { get; set; }
        public int ProteinLimitLower { get; set; }
        public int ProteinLimitUpper { get; set; }
    }
}
