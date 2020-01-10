using DietApp.Domain.Repositories;
using DietApp.Domain.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class ScheduledActionsService : IScheduledActionsService
    {
        readonly IScoreLogRepository scoreLogRepository;
        readonly IUserRepository userRepository;
        readonly IMealEntryRepository mealEntryRepository;
        readonly IUnitOfWork unitOfWork;

        public ScheduledActionsService(IScoreLogRepository scoreLogRepository, IUserRepository userRepository, IMealEntryRepository mealEntryRepository, IUnitOfWork unitOfWork)
        {
            this.scoreLogRepository = scoreLogRepository;
            this.userRepository = userRepository;
            this.mealEntryRepository = mealEntryRepository;
            this.unitOfWork = unitOfWork;
        }

        public async Task CreateDailyUserScoreSummaries()
        {
            val users = await userRepository.List().ConfigureAwait(false);
        }
    }
}
