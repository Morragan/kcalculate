using DietApp.Domain.Models;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System.IO;

namespace DietApp.Persistence.Contexts
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext() { }
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options) { }
        public DbSet<MealEntry> MealEntries { get; set; }
        public DbSet<RefreshToken> RefreshTokens { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Friendship> Friendships { get; set; }
        public DbSet<Meal> Meals { get; set; }
        public DbSet<PublicMeal> PublicMeals { get; set; }
        public DbSet<ScoreLog> ScoreLogs { get; set; }
        public DbSet<Goal> Goals { get; set; }
        public DbSet<GoalInvitation> GoalInvitations { get; set; }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            #region User
            modelBuilder.Entity<User>().HasMany(m => m.MealsHistory).WithOne(m => m.User).HasForeignKey(m => m.UserID);
            modelBuilder.Entity<User>().HasMany(m => m.RefreshTokens).WithOne(m => m.User).HasForeignKey(m => m.UserID);
            modelBuilder.Entity<User>().HasMany(m => m.SavedMeals).WithOne(m => m.User).HasForeignKey(m => m.UserID);
            modelBuilder.Entity<User>().HasMany(m => m.ScoreLogs).WithOne(m => m.User).HasForeignKey(m => m.UserID);
            modelBuilder.Entity<User>().HasMany(m => m.GoalInvitations).WithOne(m => m.InvitedUser).HasForeignKey(m => m.InvitedUserID);
            modelBuilder.Entity<User>().Property(m => m.ID).IsRequired().ValueGeneratedOnAdd();
            modelBuilder.Entity<User>().Property(m => m.Nickname).IsRequired().HasMaxLength(50);
            modelBuilder.Entity<User>().Property(m => m.Email).IsRequired();
            modelBuilder.Entity<User>().Property(m => m.IsEmailConfirmed).HasDefaultValue(false);
            modelBuilder.Entity<User>().Property(m => m.Password).IsRequired();
            modelBuilder.Entity<User>().Property(m => m.Role).IsRequired().HasDefaultValue(UserRole.User);
            modelBuilder.Entity<User>().Property(m => m.IsPrivate).HasDefaultValue(false);
            modelBuilder.Entity<User>().Ignore(m => m.Friendships);
            #endregion

            #region RefreshToken
            modelBuilder.Entity<RefreshToken>().Property(m => m.ID).ValueGeneratedOnAdd();
            modelBuilder.Entity<RefreshToken>().Property(m => m.Expiration).IsRequired();
            modelBuilder.Entity<RefreshToken>().Property(m => m.Token).IsRequired();
            #endregion

            #region MealEntry
            modelBuilder.Entity<MealEntry>().Property(m => m.Date).IsRequired();
            modelBuilder.Entity<MealEntry>().Property(m => m.WeightGram).IsRequired();
            modelBuilder.Entity<MealEntry>().Ignore(m => m.Kcal);
            modelBuilder.Entity<MealEntry>().Ignore(m => m.Carbs);
            modelBuilder.Entity<MealEntry>().Ignore(m => m.Fat);
            modelBuilder.Entity<MealEntry>().Ignore(m => m.Protein);
            #endregion

            #region Nutrients
            modelBuilder.Entity<Meal>().OwnsOne(m => m.Nutrients);
            modelBuilder.Entity<MealEntry>().OwnsOne(m => m.Nutrients);
            modelBuilder.Entity<PublicMeal>().OwnsOne(m => m.Nutrients);
            #endregion

            #region Meal
            modelBuilder.Entity<Meal>().Property(m => m.Name).IsRequired().HasMaxLength(128);
            modelBuilder.Entity<Meal>().Property(m => m.UserID).ValueGeneratedNever();
            #endregion

            #region PublicMeal
            modelBuilder.Entity<PublicMeal>().Property(m => m.Name).IsRequired().HasMaxLength(128);
            #endregion

            #region Friendship
            modelBuilder.Entity<Friendship>().HasKey(m => new { m.SrcUserID, m.DestUserID });
            modelBuilder.Entity<Friendship>().HasOne(m => m.SrcUser).WithMany(m => m.RequestedFriendships).HasForeignKey(m => m.SrcUserID).OnDelete(DeleteBehavior.Restrict);
            modelBuilder.Entity<Friendship>().HasOne(m => m.DestUser).WithMany(m => m.ReceivedFriendships).HasForeignKey(m => m.DestUserID);
            modelBuilder.Entity<Friendship>().Property(m => m.Status).IsRequired();
            #endregion

            #region ScoreLog
            modelBuilder.Entity<ScoreLog>().Property(m => m.Date).IsRequired();
            modelBuilder.Entity<ScoreLog>().Property(m => m.ScoredPointsKcal).IsRequired();
            modelBuilder.Entity<ScoreLog>().Property(m => m.ScoredPointsCarbs).IsRequired();
            modelBuilder.Entity<ScoreLog>().Property(m => m.ScoredPointsFat).IsRequired();
            modelBuilder.Entity<ScoreLog>().Property(m => m.ScoredPointsProtein).IsRequired();
            #endregion

            #region Goal
            modelBuilder.Entity<Goal>().HasMany(m => m.GoalInvitations).WithOne(m => m.Goal).HasForeignKey(m => m.GoalID);
            #endregion

            #region GoalInvitation
            modelBuilder.Entity<GoalInvitation>().Property(m => m.Status).IsRequired().HasDefaultValue(GoalInvitationStatus.Pending);
            #endregion
        }
        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                IConfigurationRoot configuration = new ConfigurationBuilder()
                   .SetBasePath(Directory.GetCurrentDirectory())
                   .AddJsonFile("appsettings.json")
                   .Build();
                var connectionString = configuration.GetConnectionString("DefaultConnection");
                optionsBuilder.UseSqlServer(connectionString);
                optionsBuilder.EnableSensitiveDataLogging();
            }
        }
    }
}
