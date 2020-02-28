using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class GoalsService : IGoalsService
    {
        readonly IGoalsRepository goalsRepository;
        readonly IGoalParticipationsRepository goalParticipationsRepository;
        readonly IUserRepository userRepository;
        readonly IUnitOfWork unitOfWork;

        public GoalsService(IGoalsRepository goalsRepository, IGoalParticipationsRepository goalParticipationsRepository, IUserRepository userRepository, IUnitOfWork unitOfWork)
        {
            this.goalsRepository = goalsRepository;
            this.goalParticipationsRepository = goalParticipationsRepository;
            this.userRepository = userRepository;
            this.unitOfWork = unitOfWork;
        }

        public async Task<GoalParticipationResponse> Create(int userId, float weightGoal, int[] invitedUsers)
        {
            var goal = new Goal() { WeightGoal = weightGoal };
            var user = await userRepository.FindById(userId);

            var users = userRepository.FindByIdRange(invitedUsers);

            var goalParticipations = new List<GoalParticipation>();

            // add invited users
            foreach (User invitedUser in users)
            {
                if (invitedUser.Goal != null) continue;

                var calorieLimit = invitedUser.CalorieLimit + 257 * weightGoal;
                var calorieLimitLower = invitedUser.CalorieLimitLower + 257 * weightGoal;
                var calorieLimitUpper = invitedUser.CalorieLimitUpper + 257 * weightGoal;
                var carbsLimit = invitedUser.CarbsLimit + 257 * weightGoal;
                var carbsLimitLower = invitedUser.CarbsLimitLower + 257 * weightGoal;
                var carbsLimitUpper = invitedUser.CarbsLimitUpper + 257 * weightGoal;
                var fatLimit = invitedUser.FatLimit + 257 * weightGoal;
                var fatLimitLower = invitedUser.FatLimitLower + 257 * weightGoal;
                var fatLimitUpper = invitedUser.FatLimitUpper + 257 * weightGoal;
                var proteinLimit = invitedUser.ProteinLimit + 257 * weightGoal;
                var proteinLimitLower = invitedUser.ProteinLimitLower + 257 * weightGoal;
                var proteinLimitUpper = invitedUser.ProteinLimitUpper + 257 * weightGoal;


                var goalParticipation = new GoalParticipation()
                {
                    Goal = goal,
                    InvitedUser = invitedUser,
                    Status = GoalInvitationStatus.Pending,
                    CalorieLimit = Convert.ToInt32(calorieLimit),
                    CalorieLimitLower = Convert.ToInt32(calorieLimitLower),
                    CalorieLimitUpper = Convert.ToInt32(calorieLimitUpper),
                    CarbsLimit = carbsLimit,
                    CarbsLimitLower = carbsLimitLower,
                    CarbsLimitUpper = carbsLimitUpper,
                    FatLimit = fatLimit,
                    FatLimitLower = fatLimitLower,
                    FatLimitUpper = fatLimitUpper,
                    ProteinLimit = proteinLimit,
                    ProteinLimitLower = proteinLimitLower,
                    ProteinLimitUpper = proteinLimitUpper
                };

                goalParticipations.Add(goalParticipation);
            }

            var _calorieLimit = user.CalorieLimit + 257 * weightGoal;
            var _calorieLimitLower = user.CalorieLimitLower + 257 * weightGoal;
            var _calorieLimitUpper = user.CalorieLimitUpper + 257 * weightGoal;
            var _carbsLimit = user.CarbsLimit + 257 * weightGoal;
            var _carbsLimitLower = user.CarbsLimitLower + 257 * weightGoal;
            var _carbsLimitUpper = user.CarbsLimitUpper + 257 * weightGoal;
            var _fatLimit = user.FatLimit + 257 * weightGoal;
            var _fatLimitLower = user.FatLimitLower + 257 * weightGoal;
            var _fatLimitUpper = user.FatLimitUpper + 257 * weightGoal;
            var _proteinLimit = user.ProteinLimit + 257 * weightGoal;
            var _proteinLimitLower = user.ProteinLimitLower + 257 * weightGoal;
            var _proteinLimitUpper = user.ProteinLimitUpper + 257 * weightGoal;

            var creatorParticipation = new GoalParticipation()
            {
                Goal = goal,
                InvitedUserID = userId,
                Status = GoalInvitationStatus.Accepted,
                StartDate = DateTime.UtcNow,
                CalorieLimit = Convert.ToInt32(_calorieLimit),
                CalorieLimitLower = Convert.ToInt32(_calorieLimitLower),
                CalorieLimitUpper = Convert.ToInt32(_calorieLimitUpper),
                CarbsLimit = _carbsLimit,
                CarbsLimitLower = _carbsLimitLower,
                CarbsLimitUpper = _carbsLimitUpper,
                FatLimit = _fatLimit,
                FatLimitLower = _fatLimitLower,
                FatLimitUpper = _fatLimitUpper,
                ProteinLimit = _proteinLimit,
                ProteinLimitLower = _proteinLimitLower,
                ProteinLimitUpper = _proteinLimitUpper
            };

            goalParticipations.Add(creatorParticipation);

            goal.GoalParticipations = goalParticipations;

            await goalsRepository.Add(goal);
            await unitOfWork.Complete();

            return new GoalParticipationResponse(true, null, creatorParticipation);
        }

        public async Task AcceptInvitation(int userId)
        {
            var user = await userRepository.FindById(userId);
            user.Goal.Status = GoalInvitationStatus.Accepted;
            user.Goal.StartDate = DateTime.UtcNow;
            goalParticipationsRepository.Update(user.Goal);
            await unitOfWork.Complete();
        }

        public async Task Remove(int userId)
        {
            var user = await userRepository.FindById(userId);
            goalParticipationsRepository.Delete(user.Goal);
            await unitOfWork.Complete();
        }
    }
}
