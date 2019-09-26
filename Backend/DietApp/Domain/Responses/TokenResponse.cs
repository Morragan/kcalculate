using DietApp.Domain.Tokens;

namespace DietApp.Domain.Responses
{
    public class TokenResponse : BaseResponse
    {
        public JwtAccessToken Token { get; set; }
        public TokenResponse(bool isSuccess, string message, JwtAccessToken token) : base(isSuccess, message)
        {
            Token = token;
        }
    }
}
