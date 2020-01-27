using System.Collections.Generic;
using System.Threading.Tasks;
using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.ViewModels.Outgoing;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
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
        [Route("friends/{friend_id}")]
        public async Task<IActionResult> RequestFriend([FromRoute(Name = "friend_id")] int friendId)
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var friendship = new Friendship()
            {
                SrcUserID = userId,
                DestUserID = friendId,
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
        [Route("accept-friend/{friend_id}")]
        public async Task<IActionResult> AcceptFriend([FromRoute(Name = "friend_id")] int friendId)
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await friendshipService.AcceptFriend(userId, friendId).ConfigureAwait(false);
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
        [Route("friends/{friend_id}")]
        public async Task<IActionResult> DeleteFriend([FromRoute(Name = "friend_id")] int friendId)
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await friendshipService.Delete(userId, friendId).ConfigureAwait(false);
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
        [Route("block-user/{user_id}")]
        public async Task<IActionResult> BlockUser([FromRoute(Name = "user_id")] int blockedUserId)
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await friendshipService.BlockUser(userId, blockedUserId).ConfigureAwait(false);
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
        public async Task<IActionResult> SearchPeople(string nickname)
        {
            if (nickname == null || nickname.Length == 0) return BadRequest(ModelState);

            var userId = userService.GetCurrentUserId(HttpContext);

            var users = await userService.SearchUsers(nickname, userId).ConfigureAwait(false);
            var usersViewModel = mapper.Map<IEnumerable<User>, IEnumerable<SearchUserViewModel>>(users);

            return Ok(usersViewModel);
        }
    }
}