using DietApp.Domain.Models;

namespace DietApp.Domain.Responses
{
    public class CreateUserResponse : BaseResponse
    {
        public User User { get; set; }

        public CreateUserResponse(bool isSuccess, string message, User user) : base(isSuccess, message)
        {
            User = user;
        }
    }
}
