using DietApp.Domain.Models;
using System;

namespace DietApp.ViewModels
{
    public class UserViewModel
    {
        public int ID { get; set; }
        public string Nickname { get; set; }
        public string Email { get; set; }
        public string AvatarLink { get; set; }
        public DateTime JoinDate { get; set; }
        public int Points { get; set; }
        public int GoalPoints { get; set; }
        public bool IsEmailConfirmed { get; set; }
        public int CalorieLimit { get; set; }
        public int CalorieLimitLower { get; set; }
        public int CalorieLimitUpper { get; set; }
        public int CarbsLimit { get; set; }
        public int CarbsLimitLower { get; set; }
        public int CarbsLimitUpper { get; set; }
        public int FatLimit { get; set; }
        public int FatLimitLower { get; set; }
        public int FatLimitUpper { get; set; }
        public int ProteinLimit { get; set; }
        public int ProteinLimitLower { get; set; }
        public int ProteinLimitUpper { get; set; }
        public bool IsPrivate { get; set; }
    }
}
