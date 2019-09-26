using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class goals : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "CaloriesGoalKcal",
                table: "Users");

            migrationBuilder.CreateTable(
                name: "Goals",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Type = table.Column<int>(nullable: false),
                    Reward = table.Column<int>(nullable: false),
                    MinCaloriesKcal = table.Column<int>(nullable: false),
                    MaxCaloriesKcal = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Goals", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "GoalInvitations",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    InvitedUserID = table.Column<int>(nullable: false),
                    Status = table.Column<int>(nullable: false, defaultValue: 1),
                    GoalID = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_GoalInvitations", x => x.ID);
                    table.ForeignKey(
                        name: "FK_GoalInvitations_Goals_GoalID",
                        column: x => x.GoalID,
                        principalTable: "Goals",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_GoalInvitations_Users_InvitedUserID",
                        column: x => x.InvitedUserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_GoalInvitations_GoalID",
                table: "GoalInvitations",
                column: "GoalID");

            migrationBuilder.CreateIndex(
                name: "IX_GoalInvitations_InvitedUserID",
                table: "GoalInvitations",
                column: "InvitedUserID");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "GoalInvitations");

            migrationBuilder.DropTable(
                name: "Goals");

            migrationBuilder.AddColumn<int>(
                name: "CaloriesGoalKcal",
                table: "Users",
                nullable: false,
                defaultValue: 0);
        }
    }
}
