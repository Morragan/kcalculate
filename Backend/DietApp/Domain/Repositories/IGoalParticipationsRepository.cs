using DietApp.Domain.Models;
using System;

namespace DietApp.Domain.Repositories
{
    public interface IGoalParticipationsRepository
    {
        void Update(GoalParticipation goalParticipation);
        void Delete(GoalParticipation goalParticipation);
    }
}
