namespace DietApp.Domain.Responses
{
    public abstract class BaseResponse
    {
        public bool IsSuccess { get; set; }
        public string Message { get; set; }
        public BaseResponse(bool isSuccess, string message)
        {
            IsSuccess = isSuccess;
            Message = message;
        }
    }
}
