namespace DietApp.Domain.Helpers
{
    public interface IPasswordHasher
    {
        string HashPassword(string password);
        bool CheckPasswordMatching(string providedPassword, string passwordHash);
    }
}
