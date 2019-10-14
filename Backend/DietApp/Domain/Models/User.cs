using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
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
        public bool IsPrivate { get; set; }
        public IEnumerable<MealEntry> MealsHistory { get; set; }
        public IEnumerable<ExerciseReading> ExercisesHistory { get; set; }
        public IEnumerable<Meal> SavedMeals { get; set; }
        public IEnumerable<RefreshToken> RefreshTokens { get; set; }
        public IEnumerable<ScoreLog> ScoreLogs { get; set; }
        public IEnumerable<GoalInvitation> GoalInvitations { get; set; }
        public IEnumerable<Friendship> RequestedFriendships { get; set; }
        public IEnumerable<Friendship> ReceivedFriendships { get; set; }
        public IEnumerable<Friendship> Friendships => RequestedFriendships.Concat(ReceivedFriendships);
    }
}
