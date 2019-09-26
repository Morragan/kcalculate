using System;

namespace DietApp.Domain.Models
{
    public class Friendship
    {
        public int SrcUserID { get; set; }
        public User SrcUser { get; set; }
        public int DestUserID { get; set; }
        public User DestUser { get; set; }
        public FriendshipStatus Status { get; set; }
        public DateTime StartDate { get; set; }
    }
}
