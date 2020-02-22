using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Persistence.Contexts;
using System;

namespace DietApp.Persistence.Repositories
{
    public class GoalParticipationsRepository : BaseRepository, IGoalParticipationsRepository
    {
        public GoalParticipationsRepository(ApplicationDbContext context) : base(context) { }

        public void Update(int goalId, GoalInvitationStatus status, DateTime startDate)
        {
            var participation = new GoalParticipation() { ID = goalId, Status = status, StartDate = startDate };
            context.GoalInvitations.Attach(participation);
            context.Entry(participation).Property(p => p.Status).IsModified = true;
            context.Entry(participation).Property(p => p.StartDate).IsModified = true;
        }
        public void Delete(int goalParticipationId)
        {
            var participation = new GoalParticipation() { ID = goalParticipationId };
            context.GoalInvitations.Attach(participation);
            context.GoalInvitations.Remove(participation);
        }

    }
}
