using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.Services;
using DietApp.ViewModels.Incoming;
using DietApp.ViewModels.Outgoing;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
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
            var user = await userService.FindById(userId);

            var goalViewModel = mapper.Map<GoalParticipation, GoalViewModel>(user.Goal);
            return Ok(goalViewModel);
        }

        [HttpPost]
        public async Task<IActionResult> CreateGoal([FromBody] CreateGoalViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var userId = userService.GetCurrentUserId(HttpContext);
            var result = await goalsService.Create(userId, viewModel.WeightGoal, viewModel.InvitedUsers);
            if (!result.IsSuccess) return BadRequest(result.Message);

            throw new NotImplementedException();

        }
    }
}