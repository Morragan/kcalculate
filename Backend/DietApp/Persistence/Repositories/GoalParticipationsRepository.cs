using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using System;

namespace DietApp.Persistence.Repositories
{
    public class GoalParticipationsRepository : BaseRepository, IGoalParticipationsRepository
    {
        public GoalParticipationsRepository(ApplicationDbContext context) : base(context) { }

        public void Update(GoalParticipation goalParticipation)
        {
            context.Entry(goalParticipation).Property(p => p.Status).IsModified = true;
            context.Entry(goalParticipation).Property(p => p.StartDate).IsModified = true;
        }
        public void Delete(GoalParticipation goalParticipation)
        {
            context.GoalInvitations.Remove(goalParticipation);
        }

    }
}
