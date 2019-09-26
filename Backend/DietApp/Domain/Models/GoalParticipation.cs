namespace DietApp.Domain.Models
{
    public class GoalInvitation
    {
        public int ID { get; set; }
        public User InvitedUser { get; set; }
        public int InvitedUserID { get; set; }
        public GoalInvitationStatus Status { get; set; }
        public int GoalID { get; set; }
        public Goal Goal { get; set; }
    }
}
