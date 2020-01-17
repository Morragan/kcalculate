using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DietApp.Domain.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace DietApp.Controllers
{
    [Route("api/[controller]")]
    [Authorize(Roles = "Admin")]
    public class ScheduledActionsController : Controller
    {
        readonly IScheduledActionsService scheduledActionsService;
        readonly IUserService userService;

        public ScheduledActionsController(IScheduledActionsService scheduledActionsService)
        {
            this.scheduledActionsService = scheduledActionsService;
        }


        [HttpPost]
        [Route("")]
        public async Task CreateDailyUserScoreSummaries()
        {
            await scheduledActionsService.CreateDailyUserScoreSummaries().ConfigureAwait(false);
        }

    }
}
