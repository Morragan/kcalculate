﻿using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using Microsoft.EntityFrameworkCore;

namespace DietApp.Persistence.Repositories
{
    public class UserRepository : BaseRepository, IUserRepository
    {
        public UserRepository(ApplicationDbContext context) : base(context) { }
        public async Task<IEnumerable<User>> List()
        {
            return await context.Users.ToListAsync();
        }
        public async Task Add(User user)
        {
            await context.AddAsync(user);
        }

        public async Task Update(User user)
        {
            var _user = await context.Users.FirstOrDefaultAsync(_user => _user.ID == user.ID);
            if (_user == null) return;

            _user.CalorieLimitLower = user.CalorieLimitLower;
            _user.CalorieLimitUpper = user.CalorieLimitUpper;
            _user.CarbsLimitLower = user.CarbsLimitLower;
            _user.CarbsLimitUpper = user.CarbsLimitUpper;
            _user.FatLimitLower = user.FatLimitLower;
            _user.FatLimitUpper = user.FatLimitUpper;
            _user.ProteinLimitLower = user.ProteinLimitLower;
            _user.ProteinLimitUpper = user.ProteinLimitUpper;
            _user.IsEmailConfirmed = user.IsEmailConfirmed;
            _user.IsPrivate = user.IsPrivate;
            _user.Password = user.Password;

            context.Users.Update(_user);
        }

        public async Task<User> FindById(int id)
        {
            return await context.Users.SingleOrDefaultAsync(u => u.ID == id);
        }

        public async Task<User> FindByIdIncludeFriendships(int id)
        {
            return await context.Users
                .Include(u => u.RequestedFriendships).ThenInclude(f => f.DestUser)
                .Include(u => u.ReceivedFriendships).ThenInclude(f => f.SrcUser)
                .SingleOrDefaultAsync(u => u.ID == id);
        }

        public async Task<User> FindByEmail(string email)
        {
            return await context.Users.SingleOrDefaultAsync(u => u.Email == email);
        }

        public async Task<User> FindByNickname(string nickname)
        {
            return await context.Users.SingleOrDefaultAsync(u => u.Nickname == nickname);
        }

        public IEnumerable<User> FindByIdRange(IEnumerable<int> ids)
        {
            return from user in context.Users
                   where ids.Contains(user.ID)
                   select user;
        }

        public IEnumerable<User> FindByNicknameContains(string nickname)
        {
            return context.Users.Where(user => user.Nickname.Contains(nickname));
        }
    }
}
