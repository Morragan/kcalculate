using DietApp.Domain.Models;

namespace DietApp.ViewModels.Outgoing
{
    public class FriendViewModel
    {
        public int ID { get; set; }
        public string Nickname { get; set; }
        public string AvatarLink { get; set; }
        public int Points { get; set; }
        public Gender Gender { get; set; }
        public bool IsPrivate { get; set; }
        public int FriendshipID { get; set; }
        public FriendshipStatus Status { get; set; }
    }
}
