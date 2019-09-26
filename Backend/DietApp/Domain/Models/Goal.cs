using System.Collections.Generic;

namespace DietApp.Domain.Models
{
    public class Goal
    {
        public int ID { get; set; }
        public GoalType Type { get; set; } //możliwe że trzeba będzie zmienić na dziedziczenie
        public IEnumerable<GoalInvitation> GoalInvitations { get; set; } //TODO: uczestnictwo danego użytkownika w zorganizowanym celu - many x many czy dodatkowy model?
        public int Reward { get; set; }
        public int MinCaloriesKcal { get; set; } //TODO: zmienić na min nutrients i max nutrients
        public int MaxCaloriesKcal { get; set; }
    }
}
