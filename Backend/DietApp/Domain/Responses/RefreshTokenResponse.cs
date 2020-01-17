using DietApp.Domain.Models;

namespace DietApp.Domain.Responses
{
    public class RefreshTokenResponse : BaseResponse
    {
        public RefreshToken RefreshToken { get; }
        public RefreshTokenResponse(bool isSuccess, string message, RefreshToken refreshToken) : base(isSuccess, message)
        {
            RefreshToken = refreshToken;
        }
    }
}
