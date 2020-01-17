using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Services;
using System;
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
            var users = await userRepository.List().ConfigureAwait(false);
            var yesterday = DateTime.Now.AddDays(-1);
            foreach (var user in users)
            {
                var meals = await mealEntryRepository.ListFromDay(user.ID, yesterday).ConfigureAwait(false);

                int scoredPointsKcal = 0;
                int scoredPointsCarbs = 0;
                int scoredPointsFat = 0;
                int scoredPointsProtein = 0;

                var kcalSum = meals.Sum(meal => meal.Kcal);
                var carbsSum = meals.Sum(meal => meal.Nutrients.Carbs);
                var fatSum = meals.Sum(meal => meal.Nutrients.Fat);
                var proteinSum = meals.Sum(meal => meal.Nutrients.Protein);

                if (kcalSum >= user.CalorieLimitLower && kcalSum <= user.CalorieLimitUpper) scoredPointsKcal = 50;
                if (carbsSum >= user.CarbsLimitLower && carbsSum <= user.CarbsLimitUpper) scoredPointsCarbs = 15;
                if (fatSum >= user.FatLimitLower && fatSum <= user.FatLimitUpper) scoredPointsFat = 15;
                if (proteinSum >= user.ProteinLimitLower && proteinSum <= user.ProteinLimitUpper) scoredPointsProtein = 15;

                var userScore = new ScoreLog(user.ID, yesterday, scoredPointsKcal, scoredPointsCarbs, scoredPointsFat, scoredPointsProtein);


                await scoreLogRepository.Add(userScore).ConfigureAwait(false);
            }

            await unitOfWork.Complete().ConfigureAwait(false);
        }
    }
}
