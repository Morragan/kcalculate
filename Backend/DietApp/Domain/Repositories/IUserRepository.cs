using DietApp.Domain.Models;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace DietApp.Domain.Repositories
{
    public interface IUserRepository
    {
        Task<IEnumerable<User>> List();
        Task Add(User user);
        Task Update(User user);
        Task<User> FindById(int id);
        IEnumerable<User> FindByIdRange(IEnumerable<int> ids);
        Task<User> FindByIdIncludeFriendships(int id);
        Task<User> FindByEmail(string email);
        Task<User> FindByNickname(string nickname);
        IEnumerable<User> FindByNicknameContains(string nickname);
        void UpdatePrivacy(int userId, bool isPrivate);
    }
}
