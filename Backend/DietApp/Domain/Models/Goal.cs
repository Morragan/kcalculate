using System.Collections.Generic;

namespace DietApp.Domain.Models
{
    public class Goal
    {
        public int ID { get; set; }
        public IEnumerable<GoalParticipation> GoalParticipations { get; set; }
        public int WeightGoal { get; set; }
    }
}
