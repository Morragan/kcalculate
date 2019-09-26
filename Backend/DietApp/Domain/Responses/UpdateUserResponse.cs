using DietApp.Domain.Models;

namespace DietApp.Domain.Responses
{
    public class UpdateUserResponse: BaseResponse
    {
        public User User { get; set; }
        public UpdateUserResponse(bool isSuccess, string message, User user): base(isSuccess, message)
        {
            User = user;
        }
    }
}
