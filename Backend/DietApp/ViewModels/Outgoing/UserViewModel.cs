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
        public bool IsEmailConfirmed { get; set; }
        public decimal WeightKg { get; set; }
        public int HeightCm { get; set; }
        public Gender Gender { get; set; }
        public int CalorieLimit { get; set; }
        public bool IsPrivate { get; set; }
    }
}
