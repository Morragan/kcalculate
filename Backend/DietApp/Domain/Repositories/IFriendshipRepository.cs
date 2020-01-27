using DietApp.Domain.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IFriendshipRepository
    {
        void Add(Friendship friendship);
        void Delete(Friendship friendship);
        void Update(Friendship friendship);
        Task<IEnumerable<Friendship>> ListRequested(int userId);
        Task<IEnumerable<Friendship>> ListReceived(int userId);
        Task<Friendship> Find(int srcUserId, int destUserId);
    }
}
