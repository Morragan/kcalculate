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
        public string AvatarLink { get; set; }
        public UserRole Role { get; set; }
        public string Password { get; set; }
        public bool IsEmailConfirmed { get; set; } //TODO: potwierdzenie emaila
        public DateTime JoinDate { get; set; }
        public int CalorieLimit { get; set; }
        public int CalorieLimitLower { get; set; }
        public int CalorieLimitUpper { get; set; }
        public float CarbsLimit { get; set; }
        public float CarbsLimitLower { get; set; }
        public float CarbsLimitUpper { get; set; }
        public float FatLimit { get; set; }
        public float FatLimitLower { get; set; }
        public float FatLimitUpper { get; set; }
        public float ProteinLimit { get; set; }
        public float ProteinLimitLower { get; set; }
        public float ProteinLimitUpper { get; set; }
        public bool IsPrivate { get; set; }
        public int Streak { get; set; }
        public int Points => ScoreLogs.Sum(score => score.ScoredPointsKcal + score.ScoredPointsCarbs + score.ScoredPointsFat + score.ScoredPointsProtein);
        public IEnumerable<MealEntry> MealsHistory { get; set; }
        public IEnumerable<Meal> SavedMeals { get; set; }
        public IEnumerable<RefreshToken> RefreshTokens { get; set; }
        public IEnumerable<ScoreLog> ScoreLogs { get; set; }
        public IEnumerable<Friendship> RequestedFriendships { get; set; }
        public IEnumerable<Friendship> ReceivedFriendships { get; set; }
        public IEnumerable<Friendship> Friendships => RequestedFriendships.Concat(ReceivedFriendships);
        public GoalParticipation Goal { get; set; }
        public int GoalPoints => ScoreLogs.Where(log => log.Date > Goal?.StartDate)
            .Sum(score => score.ScoredPointsKcal + score.ScoredPointsCarbs + score.ScoredPointsFat + score.ScoredPointsProtein);
    }
}
