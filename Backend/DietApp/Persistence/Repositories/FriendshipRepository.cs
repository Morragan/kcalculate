using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.Persistence.Repositories
{
    public class FriendshipRepository : BaseRepository, IFriendshipRepository
    {
        public FriendshipRepository(ApplicationDbContext context) : base(context) { }

        public async Task Add(Friendship friendship)
        {
            await context.AddAsync(friendship);
        }

        public async Task<IEnumerable<Friendship>> ListRequested(int userId)
        {
            return await context.Friendships.Where(_friendship => _friendship.SrcUserID == userId || _friendship.DestUserID == userId).ToListAsync().ConfigureAwait(false);
        }

        public async Task<IEnumerable<Friendship>> ListReceived(int userId)
        {
            return await context.Friendships.Where(_friendship => _friendship.SrcUserID == userId || _friendship.DestUserID == userId).ToListAsync().ConfigureAwait(false);
        }

        public void Delete(Friendship friendship)
        {
            context.Remove(friendship);
        }

        public void Update(Friendship friendship)
        {
            context.Update(friendship);
        }

        public async Task<Friendship> Find(int srcUserId, int destUserId)
        {
            return await context.Friendships.SingleOrDefaultAsync(friendship => friendship.SrcUserID == srcUserId && friendship.DestUserID == destUserId ||
            friendship.DestUserID == srcUserId && friendship.SrcUserID == destUserId);
        }
    }
}
