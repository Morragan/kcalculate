using System.Collections.Generic;
using System.Threading.Tasks;
using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.ViewModels.Incoming;
using DietApp.ViewModels.Outgoing;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using InFriendViewModel = DietApp.ViewModels.Incoming.FriendViewModel;
using OutFriendViewModel = DietApp.ViewModels.Outgoing.FriendViewModel;

namespace DietApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class SocialController : ControllerBase
    {
        readonly IFriendshipService friendshipService;
        readonly IUserService userService;
        readonly IMapper mapper;
        public SocialController(IFriendshipService friendshipService, IUserService userService, IMapper mapper)
        {
            this.friendshipService = friendshipService;
            this.userService = userService;
            this.mapper = mapper;
        }

        [HttpGet]
        [Route("friends")]
        public async Task<IActionResult> GetFriendsList()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var (requestedFriends, receivedFriends) = await friendshipService.GetUserFriends(userId).ConfigureAwait(false);

            var requestedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(requestedFriends);
            var receivedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(receivedFriends);

            return Ok(new
            {
                requestedFriends = requestedFriendsViewModel,
                receivedFriends = receivedFriendsViewModel
            });
        }

        [HttpPost]
        [Route("friends")]
        public async Task<IActionResult> RequestFriend(InFriendViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);
            var userId = userService.GetCurrentUserId(HttpContext);
            var friendship = new Friendship()
            {
                SrcUserID = userId,
                DestUserID = viewModel.FriendID,
                Status = FriendshipStatus.Pending
            };
            var response = await friendshipService.Create(friendship).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var (requestedFriends, receivedFriends) = await friendshipService.GetUserFriends(userId).ConfigureAwait(false);

            var requestedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(requestedFriends);
            var receivedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(receivedFriends);

            return Ok(new
            {
                requestedFriends = requestedFriendsViewModel,
                receivedFriends = receivedFriendsViewModel
            });//TODO: signalr czy coś do zapraszanego
        }

        [HttpPut]
        [Route("accept-friend")]
        public async Task<IActionResult> AcceptFriend(InFriendViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await friendshipService.AcceptFriend(userId, viewModel.FriendID).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var (requestedFriends, receivedFriends) = await friendshipService.GetUserFriends(userId).ConfigureAwait(false);

            var requestedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(requestedFriends);
            var receivedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(receivedFriends);

            return Ok(new
            {
                requestedFriends = requestedFriendsViewModel,
                receivedFriends = receivedFriendsViewModel
            });//TODO: signalr
        }

        [HttpDelete]
        [Route("friends")]
        public async Task<IActionResult> DeleteFriend(InFriendViewModel viewModel)
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await friendshipService.Delete(userId, viewModel.FriendID).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var (requestedFriends, receivedFriends) = await friendshipService.GetUserFriends(userId).ConfigureAwait(false);

            var requestedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(requestedFriends);
            var receivedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(receivedFriends);

            return Ok(new
            {
                requestedFriends = requestedFriendsViewModel,
                receivedFriends = receivedFriendsViewModel
            });//TODO: signalr?
        }

        [HttpPost]
        [Route("block-user")]
        public async Task<IActionResult> BlockUser(BlockUserViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest(ModelState);

            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await friendshipService.BlockUser(userId, viewModel.UserID).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var (requestedFriends, receivedFriends) = await friendshipService.GetUserFriends(userId).ConfigureAwait(false);

            var requestedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(requestedFriends);
            var receivedFriendsViewModel = mapper.Map<IEnumerable<(User, FriendshipStatus)>, IEnumerable<OutFriendViewModel>>(receivedFriends);

            return Ok(new
            {
                requestedFriends = requestedFriendsViewModel,
                receivedFriends = receivedFriendsViewModel
            });
        }

        [HttpGet]
        [Route("search")]
        public IActionResult SearchPeople(string nickname)
        {
            if (nickname == null || nickname.Length == 0) return BadRequest(ModelState);

            var users = userService.FindByNicknameContains(nickname);
            var usersViewModel = mapper.Map<IEnumerable<User>, IEnumerable<SearchUserViewModel>>(users);

            return Ok(usersViewModel);
        }
    }
}