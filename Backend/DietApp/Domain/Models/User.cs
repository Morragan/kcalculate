using System;
using System.Collections.Generic;
using System.Linq;

namespace DietApp.Domain.Models
{
    public class User
    {
        public int ID { get; set; }
        public string Nickname { get; set; }
        public string Email { get; set; }
        public string AvatarLink { get; set; } //TODO: dodać do rejestracji
        public UserRole Role { get; set; }
        public string Password { get; set; }
        public bool IsEmailConfirmed { get; set; } //TODO: potwierdzenie emaila
        public DateTime JoinDate { get; set; }
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
        public int Streak { get; set; }
        public int Points => ScoreLogs.Sum(score => score.ScoredPointsKcal + score.ScoredPointsCarbs + score.ScoredPointsFat + score.ScoredPointsProtein);
        public IEnumerable<MealEntry> MealsHistory { get; set; }
        public IEnumerable<Meal> SavedMeals { get; set; }
        public IEnumerable<RefreshToken> RefreshTokens { get; set; }
        public IEnumerable<ScoreLog> ScoreLogs { get; set; }
        public IEnumerable<GoalInvitation> GoalInvitations { get; set; }
        public IEnumerable<Friendship> RequestedFriendships { get; set; }
        public IEnumerable<Friendship> ReceivedFriendships { get; set; }
        public IEnumerable<Friendship> Friendships => RequestedFriendships.Concat(ReceivedFriendships);
    }
}
