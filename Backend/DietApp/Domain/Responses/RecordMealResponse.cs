namespace DietApp.Domain.Responses
{
    public class RecordMealResponse : BaseResponse
    {
        public RecordMealResponse(bool isSuccess, string message) : base(isSuccess, message) { }
    }
}
