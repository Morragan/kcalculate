using System.Threading.Tasks;
using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.ViewModels.Incoming;
using DietApp.ViewModels.Outgoing;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace DietApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class GoalsController : ControllerBase
    {
        readonly IGoalsService goalsService;
        readonly IUserService userService;
        readonly IMapper mapper;

        public GoalsController(IGoalsService goalsService, IUserService userService, IMapper mapper)
        {
            this.goalsService = goalsService;
            this.userService = userService;
            this.mapper = mapper;
        }

        [HttpGet]
        public async Task<IActionResult> GetUserGoal()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var user = await userService.FindById(userId).ConfigureAwait(false);

            var goalViewModel = mapper.Map<GoalParticipation, GoalViewModel>(user.Goal);
            return Ok(goalViewModel);
        }

        [HttpPost]
        public async Task<IActionResult> CreateGoal([FromBody] CreateGoalViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var userId = userService.GetCurrentUserId(HttpContext);
            var result = await goalsService.Create(userId, viewModel.WeightGoal, viewModel.InvitedUsers).ConfigureAwait(false);
            if (!result.IsSuccess) return BadRequest(result.Message);

            var goalViewModel = mapper.Map<GoalParticipation, GoalViewModel>(result.GoalParticipation);
            return Ok(goalViewModel);
        }

        [HttpPut]
        [Route("accept")]
        public async Task<IActionResult> AcceptGoal()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            await goalsService.AcceptInvitation(userId).ConfigureAwait(false);
            var user = await userService.FindById(userId).ConfigureAwait(false);

            var goalViewModel = mapper.Map<GoalParticipation, GoalViewModel>(user.Goal);
            return Ok(goalViewModel);
        }

        [HttpDelete]
        [Route("remove")]
        public async Task<IActionResult> RemoveGoal()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            await goalsService.Remove(userId).ConfigureAwait(false);
            return Ok();
        }
    }
}