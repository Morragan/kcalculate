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

        public async Task<GoalResponse> Create(int userId, int weightGoal, int[] invitedUsers)
        {
            var goal = new Goal() { WeightGoal = weightGoal };
            var user = await userRepository.FindById(userId);

            var users = userRepository.FindByIdRange(invitedUsers);

            var goalParticipations = new List<GoalParticipation>();

            // add invited users
            foreach (User invitedUser in users)
            {
                if (invitedUser.Goal != null) continue;

                var calorieLimit = invitedUser.CalorieLimit * (100 + weightGoal) / 100;
                var calorieLimitLower = invitedUser.CalorieLimitLower * (100 + weightGoal) / 100;
                var calorieLimitUpper = invitedUser.CalorieLimitUpper * (100 + weightGoal) / 100;
                var carbsLimit = invitedUser.CarbsLimit * (100 + weightGoal) / 100;
                var carbsLimitLower = invitedUser.CarbsLimitLower * (100 + weightGoal) / 100;
                var carbsLimitUpper = invitedUser.CarbsLimitUpper * (100 + weightGoal) / 100;
                var fatLimit = invitedUser.FatLimit * (100 + weightGoal) / 100;
                var fatLimitLower = invitedUser.FatLimitLower * (100 + weightGoal) / 100;
                var fatLimitUpper = invitedUser.FatLimitUpper * (100 + weightGoal) / 100;
                var proteinLimit = invitedUser.ProteinLimit * (100 + weightGoal) / 100;
                var proteinLimitLower = invitedUser.ProteinLimitLower * (100 + weightGoal) / 100;
                var proteinLimitUpper = invitedUser.ProteinLimitUpper * (100 + weightGoal) / 100;


                var goalParticipation = new GoalParticipation()
                {
                    Goal = goal,
                    InvitedUser = invitedUser,
                    Status = GoalInvitationStatus.Pending,
                    CalorieLimit = calorieLimit,
                    CalorieLimitLower = calorieLimitLower,
                    CalorieLimitUpper = calorieLimitUpper,
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

            var _calorieLimit = user.CalorieLimit * (100 + weightGoal) / 100;
            var _calorieLimitLower = user.CalorieLimitLower * (100 + weightGoal) / 100;
            var _calorieLimitUpper = user.CalorieLimitUpper * (100 + weightGoal) / 100;
            var _carbsLimit = user.CarbsLimit * (100 + weightGoal) / 100;
            var _carbsLimitLower = user.CarbsLimitLower * (100 + weightGoal) / 100;
            var _carbsLimitUpper = user.CarbsLimitUpper * (100 + weightGoal) / 100;
            var _fatLimit = user.FatLimit * (100 + weightGoal) / 100;
            var _fatLimitLower = user.FatLimitLower * (100 + weightGoal) / 100;
            var _fatLimitUpper = user.FatLimitUpper * (100 + weightGoal) / 100;
            var _proteinLimit = user.ProteinLimit * (100 + weightGoal) / 100;
            var _proteinLimitLower = user.ProteinLimitLower * (100 + weightGoal) / 100;
            var _proteinLimitUpper = user.ProteinLimitUpper * (100 + weightGoal) / 100;

            goalParticipations.Add(new GoalParticipation()
            {
                Goal = goal,
                InvitedUserID = userId,
                Status = GoalInvitationStatus.Accepted,
                StartDate = DateTime.UtcNow,
                CalorieLimit = _calorieLimit,
                CalorieLimitLower = _calorieLimitLower,
                CalorieLimitUpper = _calorieLimitUpper,
                CarbsLimit = _carbsLimit,
                CarbsLimitLower = _carbsLimitLower,
                CarbsLimitUpper = _carbsLimitUpper,
                FatLimit = _fatLimit,
                FatLimitLower = _fatLimitLower,
                FatLimitUpper = _fatLimitUpper,
                ProteinLimit = _proteinLimit,
                ProteinLimitLower = _proteinLimitLower,
                ProteinLimitUpper = _proteinLimitUpper
            });

            goal.GoalParticipations = goalParticipations;

            await goalsRepository.Add(goal);
            await unitOfWork.Complete();

            return new GoalResponse(true, null, goal);
        }

        public async Task AcceptInvitation(int userId)
        {
            var user = await userRepository.FindById(userId);
            goalParticipationsRepository.Update(user.Goal.GoalID, GoalInvitationStatus.Accepted, DateTime.UtcNow);
            await unitOfWork.Complete();
        }

        public async Task Remove(int userId)
        {
            var user = await userRepository.FindById(userId);
            goalParticipationsRepository.Delete(user.Goal.ID);
            await unitOfWork.Complete();
        }
    }
}
