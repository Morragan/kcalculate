using DietApp.Domain.Models;
using System;

namespace DietApp.Domain.Repositories
{
    public interface IGoalParticipationsRepository
    {
        void Update(int goalId, GoalInvitationStatus status, DateTime startDate);
        void Delete(int goalParticipationId);
    }
}
