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
        readonly IUnitOfWork unitOfWork;
        readonly IPasswordHasher passwordHasher;

        public UserService(IUserRepository userRepository, IUnitOfWork unitOfWork, IPasswordHasher passwordHasher)
        {
            this.userRepository = userRepository;
            this.unitOfWork = unitOfWork;
            this.passwordHasher = passwordHasher;
        }
        public async Task<CreateUserResponse> Create(User user)
        {
            var existingUser = await userRepository.FindByEmail(user.Email);
            if(existingUser != null)
            {
                return new CreateUserResponse(false, "Email already in use", null);
            }

            user.Password = passwordHasher.HashPassword(user.Password);
            user.JoinDate = DateTime.UtcNow;
            user.CalorieLimit = 2000;

            await userRepository.Add(user);
            await unitOfWork.Complete();

            return new CreateUserResponse(true, null, user);
        }

        public async Task<UpdateUserResponse> Update(User user)
        {
            var existingUser = await userRepository.FindById(user.ID);
            if (existingUser == null) return new UpdateUserResponse(false, "User does not exist", null);

            await userRepository.Update(user);
            await unitOfWork.Complete();

            return new UpdateUserResponse(true, null, user);
        }
        
        public async Task<User> FindById(int id)
        {
            return await userRepository.FindById(id);
        }

        public async Task<User> FindByNickname(string nickname)
        {
            return await userRepository.FindByNickname(nickname);
        }

        public async Task<User> FindByEmail(string email)
        {
            return await userRepository.FindByEmail(email);
        }

        public int GetCurrentUserId(HttpContext httpContext)
        {
            return int.Parse(httpContext.User.FindFirst(ClaimTypes.GivenName).Value);
        }

        public async Task<IEnumerable<User>> List()
        {
            return await userRepository.List();
        }

        public IEnumerable<User> FindByIdRange(IEnumerable<int> ids)
        {
            return userRepository.FindByIdRange(ids);
        }

        public async Task<User> FindByIdIncludeFriendships(int id)
        {
            return await userRepository.FindByIdIncludeFriendships(id);
        }
    }
}
