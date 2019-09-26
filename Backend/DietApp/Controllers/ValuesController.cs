using Microsoft.AspNetCore.Mvc;

namespace DietApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ValuesController : ControllerBase
    {
        [HttpGet]
        public string Get()
        {
            return "value";
        }
    }
}