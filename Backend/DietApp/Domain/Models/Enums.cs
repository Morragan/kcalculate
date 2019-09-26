namespace DietApp.Domain.Models
{
    public enum UserRole
    {
        User = 1,
        Admin = 2
    }

    public enum Gender
    {
        Male = 1,
        Female = 2
    }

    public enum FriendshipStatus
    {
        Pending = 1,
        Accepted = 2,
        Blocked = 3
    }

    public enum GoalType
    {
        Personal = 1,
        Group = 2
    }

    public enum GoalInvitationStatus
    {
        Pending = 1,
        Accepted = 2,
        RejectedOrExpired = 3
    }
}
