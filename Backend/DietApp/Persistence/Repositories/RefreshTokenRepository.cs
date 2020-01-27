using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using Microsoft.EntityFrameworkCore;
using System.Threading.Tasks;

namespace DietApp.Persistence.Repositories
{
    public class RefreshTokenRepository : BaseRepository, IRefreshTokenRepository
    {
        public RefreshTokenRepository(ApplicationDbContext context) : base(context) { }

        public async Task Add(RefreshToken token)
        {
            await context.AddAsync(token);
        }

        public async Task<RefreshToken> GetRefreshToken(string token)
        {
            return await context.RefreshTokens.SingleOrDefaultAsync(refreshToken => refreshToken.Token == token).ConfigureAwait(false);
        }

        public void Remove(RefreshToken token)
        {
            context.Remove(token);
        }
    }
}
