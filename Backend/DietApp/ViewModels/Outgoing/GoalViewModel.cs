using DietApp.Domain.Models;
using System;

namespace DietApp.ViewModels.Outgoing
{
    public class GoalViewModel
    {
        public GoalInvitationStatus Status { get; set; }
        public int GoalID { get; set; }
        public DateTime StartDate { get; set; }
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
        public int WeightGoal { get; set; }
    }
}
