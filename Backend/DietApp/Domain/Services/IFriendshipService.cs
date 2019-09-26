using DietApp.Domain.Models;
using DietApp.Domain.Responses;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DietApp.Domain.Services
{
    public interface IFriendshipService
    {
        Task<IEnumerable<Friendship>> ListRequested(int userId);
        Task<IEnumerable<Friendship>> ListReceived(int userId);
        Task<(IEnumerable<(User, FriendshipStatus)> requestedFriends, IEnumerable<(User, FriendshipStatus)> receivedFriends)> GetUserFriends(int userId);
        Task<FriendshipResponse> Create(Friendship friendship);
        Task<FriendshipResponse> AcceptFriend(int userId, int friendId);
        Task<FriendshipResponse> BlockUser(int userId, int blockedUserId);
        Task<FriendshipResponse> Delete(int userId, int friendId);
    }
}
