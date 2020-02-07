using System.Threading.Tasks;
using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.Domain.Tokens;
using DietApp.ViewModels;
using DietApp.ViewModels.Incoming;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace DietApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AccountController : ControllerBase
    {
        readonly IMapper mapper;
        readonly IUserService userService;
        readonly IAuthenticationService authenticationService;

        public AccountController(IMapper mapper, IUserService userService, IAuthenticationService authenticationService)
        {
            this.mapper = mapper;
            this.userService = userService;
            this.authenticationService = authenticationService;
        }

        [HttpPost]
        [Route("register")]
        public async Task<IActionResult> Register([FromBody] RegisterViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var user = mapper.Map<RegisterViewModel, User>(viewModel);
            var response = await userService.Create(user).ConfigureAwait(false);

            if (!response.IsSuccess) return BadRequest(response.Message);

            return Ok();
        }
        [HttpPost]
        [Route("login")]
        public async Task<IActionResult> Login([FromBody] LoginViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var response = await authenticationService.CreateAccessToken(viewModel.Nickname, viewModel.Password).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var accesTokenResponse = mapper.Map<JwtAccessToken, AccessTokenViewModel>(response.Token);
            return Ok(accesTokenResponse);
        }
        [HttpPost]
        [Route("refresh-token")]
        public async Task<IActionResult> RefreshToken([FromBody] RefreshTokenViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var response = await authenticationService.RefreshToken(viewModel.RefreshToken).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var tokenResponse = mapper.Map<JwtAccessToken, AccessTokenViewModel>(response.Token);
            return Ok(tokenResponse);
        }
        [HttpPost]
        [Route("revoke-token")]
        [Authorize]
        public IActionResult RevokeToken([FromBody] RevokeTokenViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            authenticationService.RevokeRefreshToken(viewModel.Token);
            return Ok();
        }
        [HttpGet]
        [Authorize]
        public async Task<IActionResult> GetCurrentUserData()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var userData = await userService.FindById(userId).ConfigureAwait(false);
            if (userData == null) return BadRequest();

            var userDataResponse = mapper.Map<User, UserViewModel>(userData);
            return Ok(userDataResponse);
        }

        [HttpPost]
        [Route("check-nickname-taken")]
        public async Task<IActionResult> IsNicknameTaken([FromBody] CheckNicknameAccessibilityViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var user = await userService.FindByNickname(viewModel.Nickname).ConfigureAwait(false);

            var response = new ViewModels.Outgoing.CheckNicknameAccessibilityViewModel();
            if (user == null) response.Taken = false;
            else response.Taken = true;

            return Ok(response);
        }

        [HttpPut]
        [Route("privacy")]
        [Authorize]
        public async Task<IActionResult> ChangeAccountPrivacy([FromBody] ChangeAccountPrivacyViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var userId = userService.GetCurrentUserId(HttpContext);

            var response = await userService.UpdatePrivacy(userId, viewModel.IsPrivate).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var userViewModel = mapper.Map<User, UserViewModel>(response.User);
            return Ok(userViewModel);
        }

        [HttpPut]
        [Route("nutrient-goals")]
        [Authorize]
        public async Task<IActionResult> ChangeUserNutrientGoals([FromBody] ChangeUserNutrientGoalsViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var userId = userService.GetCurrentUserId(HttpContext);

            var response = await userService.UpdateNutrientGoals(
                userId,
                viewModel.CalorieLimitLower,
                viewModel.CalorieLimit,
                viewModel.CalorieLimitUpper,
                viewModel.CarbsLimitLower,
                viewModel.CarbsLimit,
                viewModel.CarbsLimitUpper,
                viewModel.FatLimitLower,
                viewModel.FatLimit,
                viewModel.FatLimitUpper,
                viewModel.ProteinLimitLower,
                viewModel.ProteinLimit,
                viewModel.ProteinLimitUpper
            ).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var userViewModel = mapper.Map<User, UserViewModel>(response.User);
            return Ok(userViewModel);
        }

        [HttpGet]
        [Route("check-logged-in")]
        [Authorize]
        public IActionResult CheckUserLoggedIn()
        {
            return Ok();
        }
    }
}