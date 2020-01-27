using DietApp.Domain.Helpers;
using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class UserService : IUserService
    {
        readonly IUserRepository userRepository;
        readonly IFriendshipService friendshipService;
        readonly IUnitOfWork unitOfWork;
        readonly IPasswordHasher passwordHasher;

        public UserService(IUserRepository userRepository, IFriendshipService friendshipService, IUnitOfWork unitOfWork, IPasswordHasher passwordHasher)
        {
            this.userRepository = userRepository;
            this.friendshipService = friendshipService;
            this.unitOfWork = unitOfWork;
            this.passwordHasher = passwordHasher;
        }
        public async Task<CreateUserResponse> Create(User user)
        {
            var existingUser = await userRepository.FindByEmail(user.Email).ConfigureAwait(false);
            if (existingUser != null)
                return new CreateUserResponse(false, "Email already in use", null);

            existingUser = await userRepository.FindByNickname(user.Nickname).ConfigureAwait(false);
            if (existingUser != null)
                return new CreateUserResponse(false, "Nickname already in use", null);

            user.Password = passwordHasher.HashPassword(user.Password);
            user.JoinDate = DateTime.UtcNow;

            await userRepository.Add(user).ConfigureAwait(false);
            await unitOfWork.Complete().ConfigureAwait(false);

            return new CreateUserResponse(true, null, user);
        }

        public async Task<UpdateUserResponse> Update(User user)
        {
            var existingUser = await userRepository.FindById(user.ID).ConfigureAwait(false);
            if (existingUser == null) return new UpdateUserResponse(false, "User does not exist", null);

            await userRepository.Update(user).ConfigureAwait(false);
            await unitOfWork.Complete().ConfigureAwait(false);

            return new UpdateUserResponse(true, null, user);
        }

        public async Task<User> FindById(int id)
        {
            return await userRepository.FindById(id).ConfigureAwait(false);
        }

        public async Task<User> FindByNickname(string nickname)
        {
            return await userRepository.FindByNickname(nickname).ConfigureAwait(false);
        }

        public async Task<User> FindByEmail(string email)
        {
            return await userRepository.FindByEmail(email).ConfigureAwait(false);
        }

        public int GetCurrentUserId(HttpContext httpContext)
        {
            return int.Parse(httpContext.User.FindFirst(ClaimTypes.GivenName).Value);
        }

        public async Task<IEnumerable<User>> List()
        {
            return await userRepository.List().ConfigureAwait(false);
        }

        public IEnumerable<User> FindByIdRange(IEnumerable<int> ids)
        {
            return userRepository.FindByIdRange(ids);
        }

        public async Task<User> FindByIdIncludeFriendships(int id)
        {
            return await userRepository.FindByIdIncludeFriendships(id).ConfigureAwait(false);
        }

        public async Task<IEnumerable<User>> SearchUsers(string nickname, int userId)
        {
            var (requestedFriends, receivedFriends) = await friendshipService.GetUserFriends(userId).ConfigureAwait(false);
            var friends = new List<(User, FriendshipStatus)>(requestedFriends);
            friends.AddRange(receivedFriends);

            return userRepository.FindByNicknameContains(nickname).Where(user => !user.IsPrivate && !friends.Any(friend => friend.Item1.ID == user.ID));
        }
    }
}
