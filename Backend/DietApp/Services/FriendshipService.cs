using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class FriendshipService : IFriendshipService
    {
        readonly IFriendshipRepository friendshipRepository;
        readonly IUserRepository userRepository;
        readonly IUnitOfWork unitOfWork;
        public FriendshipService(IFriendshipRepository friendshipRepository, IUserRepository userRepository, IUnitOfWork unitOfWork)
        {
            this.friendshipRepository = friendshipRepository;
            this.userRepository = userRepository;
            this.unitOfWork = unitOfWork;
        }

        public async Task<FriendshipResponse> Create(Friendship friendship)
        {
            var existingFriendship = await friendshipRepository.Find(friendship.SrcUserID, friendship.DestUserID);
            if (existingFriendship != null) return new FriendshipResponse(false, "Friendship already exists");
            if (existingFriendship.Status == FriendshipStatus.Blocked) return new FriendshipResponse(false, "User doesn't exist");
            var friend = userRepository.FindById(friendship.DestUserID);
            if (friend == null) return new FriendshipResponse(false, "User doesn't exist");

            await friendshipRepository.Add(friendship);
            await unitOfWork.Complete();
            return new FriendshipResponse(true, null);
        }

        public async Task<FriendshipResponse> Delete(int userId, int friendId)
        {
            var existingFriendship = await friendshipRepository.Find(userId, friendId);
            if (existingFriendship == null) return new FriendshipResponse(false, "Friendship doesn't exist");
            if (existingFriendship.DestUserID == userId && existingFriendship.Status == FriendshipStatus.Blocked)
                return new FriendshipResponse(false, "You can't unblock yourself");

            friendshipRepository.Delete(existingFriendship);
            await unitOfWork.Complete();

            return new FriendshipResponse(true, null);
        }

        public async Task<IEnumerable<Friendship>> ListRequested(int userId)
        {
            return await friendshipRepository.ListRequested(userId);
        }

        public async Task<IEnumerable<Friendship>> ListReceived(int userId)
        {
            return (await friendshipRepository.ListReceived(userId)).Where(f => f.Status != FriendshipStatus.Blocked);
        }

        public async Task<FriendshipResponse> AcceptFriend(int userId, int friendId)
        {
            var existingFriendship = await friendshipRepository.Find(userId, friendId);
            if (existingFriendship == null || existingFriendship.Status == FriendshipStatus.Blocked)
                return new FriendshipResponse(false, "Friend request doesn't exist");

            if (existingFriendship.Status != FriendshipStatus.Pending) return new FriendshipResponse(false, "Friend request status is not pending");
            if (existingFriendship.SrcUserID == userId) return new FriendshipResponse(false, "Friend request already exists");

            existingFriendship.Status = FriendshipStatus.Accepted;
            friendshipRepository.Update(existingFriendship);
            await unitOfWork.Complete();

            return new FriendshipResponse(true, null);
        }

        public async Task<FriendshipResponse> BlockUser(int userId, int blockedUserId)
        {
            var existingFriendship = await friendshipRepository.Find(userId, blockedUserId);
            if (existingFriendship == null)
            {
                var friendship = new Friendship()
                {
                    SrcUserID = userId,
                    DestUserID = blockedUserId,
                    Status = FriendshipStatus.Blocked,
                    StartDate = DateTime.Now
                };
                await friendshipRepository.Add(friendship);
            }
            else
            {
                existingFriendship.Status = FriendshipStatus.Accepted;
                friendshipRepository.Update(existingFriendship);
            }
            await unitOfWork.Complete();
            return new FriendshipResponse(true, null);
        }

        public async Task<(IEnumerable<(User, FriendshipStatus)> requestedFriends, IEnumerable<(User, FriendshipStatus)> receivedFriends)> GetUserFriends(int userId)
        {
            var userIncludeFriendships = await userRepository.FindByIdIncludeFriendships(userId);
            var requestedFriends = (IEnumerable<(User, FriendshipStatus)>)userIncludeFriendships.RequestedFriendships.Select(f => (f.DestUser, f.Status));
            var receivedFriends = (IEnumerable<(User, FriendshipStatus)>)userIncludeFriendships.ReceivedFriendships
                .Where(f => f.Status != FriendshipStatus.Blocked).Select(f => (f.SrcUser, f.Status));

            return (requestedFriends, receivedFriends);
        }
    }
}
