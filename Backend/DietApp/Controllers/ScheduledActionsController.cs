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

        public ScheduledActionsController(IScheduledActionsService scheduledActionsService)
        {
            this.scheduledActionsService = scheduledActionsService;
        }


        [HttpPost]
        [Route("scores")]
        public async Task<IActionResult> CreateDailyUserScoreSummaries()
        {
            await scheduledActionsService.CreateDailyUserScoreSummaries().ConfigureAwait(false);
            return Ok();
        }

    }
}
