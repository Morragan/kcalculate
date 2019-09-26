using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class adventure : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "NutrientsReadings");

            migrationBuilder.RenameColumn(
                name: "Points",
                table: "Users",
                newName: "HeightCm");

            migrationBuilder.AlterColumn<int>(
                name: "Role",
                table: "Users",
                nullable: false,
                defaultValue: 1,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<string>(
                name: "Password",
                table: "Users",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.AddColumn<int>(
                name: "CaloriesGoalKcal",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "Gender",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<bool>(
                name: "IsEmailConfirmed",
                table: "Users",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "IsPrivate",
                table: "Users",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "TelephoneNumber",
                table: "Users",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "WeightKg",
                table: "Users",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AlterColumn<string>(
                name: "Token",
                table: "RefreshTokens",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateTable(
                name: "ExerciseReadings",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Date = table.Column<DateTime>(nullable: false),
                    UserID = table.Column<int>(nullable: false),
                    KcalBurned = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ExerciseReadings", x => x.ID);
                    table.ForeignKey(
                        name: "FK_ExerciseReadings_Users_UserID",
                        column: x => x.UserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Friendships",
                columns: table => new
                {
                    SrcUserID = table.Column<int>(nullable: false),
                    DestUserID = table.Column<int>(nullable: false),
                    Status = table.Column<int>(nullable: false),
                    StartDate = table.Column<DateTime>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Friendships", x => new { x.SrcUserID, x.DestUserID });
                    table.ForeignKey(
                        name: "FK_Friendships_Users_DestUserID",
                        column: x => x.DestUserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Friendships_Users_SrcUserID",
                        column: x => x.SrcUserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Restrict);
                });

            migrationBuilder.CreateTable(
                name: "MealReadings",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Date = table.Column<DateTime>(nullable: false),
                    UserID = table.Column<int>(nullable: false),
                    Nutrients_WaterMilli = table.Column<int>(nullable: false),
                    Nutrients_CarbsGram = table.Column<int>(nullable: false),
                    Nutrients_FatGram = table.Column<int>(nullable: false),
                    Nutrients_ProteinGram = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_MealReadings", x => x.ID);
                    table.ForeignKey(
                        name: "FK_MealReadings_Users_UserID",
                        column: x => x.UserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Meals",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    Name = table.Column<string>(maxLength: 50, nullable: false),
                    Nutrients_WaterMilli = table.Column<int>(nullable: false),
                    Nutrients_CarbsGram = table.Column<int>(nullable: false),
                    Nutrients_FatGram = table.Column<int>(nullable: false),
                    Nutrients_ProteinGram = table.Column<int>(nullable: false),
                    UserID = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Meals", x => x.ID);
                    table.ForeignKey(
                        name: "FK_Meals_Users_UserID",
                        column: x => x.UserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "ScoreLogs",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    UserID = table.Column<int>(nullable: false),
                    Date = table.Column<DateTime>(nullable: false),
                    ScoredPoints = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ScoreLogs", x => x.ID);
                    table.ForeignKey(
                        name: "FK_ScoreLogs_Users_UserID",
                        column: x => x.UserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ExerciseReadings_UserID",
                table: "ExerciseReadings",
                column: "UserID");

            migrationBuilder.CreateIndex(
                name: "IX_Friendships_DestUserID",
                table: "Friendships",
                column: "DestUserID");

            migrationBuilder.CreateIndex(
                name: "IX_MealReadings_UserID",
                table: "MealReadings",
                column: "UserID");

            migrationBuilder.CreateIndex(
                name: "IX_Meals_UserID",
                table: "Meals",
                column: "UserID");

            migrationBuilder.CreateIndex(
                name: "IX_ScoreLogs_UserID",
                table: "ScoreLogs",
                column: "UserID");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ExerciseReadings");

            migrationBuilder.DropTable(
                name: "Friendships");

            migrationBuilder.DropTable(
                name: "MealReadings");

            migrationBuilder.DropTable(
                name: "Meals");

            migrationBuilder.DropTable(
                name: "ScoreLogs");

            migrationBuilder.DropColumn(
                name: "CaloriesGoalKcal",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "Gender",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "IsEmailConfirmed",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "IsPrivate",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "TelephoneNumber",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "WeightKg",
                table: "Users");

            migrationBuilder.RenameColumn(
                name: "HeightCm",
                table: "Users",
                newName: "Points");

            migrationBuilder.AlterColumn<int>(
                name: "Role",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldDefaultValue: 1);

            migrationBuilder.AlterColumn<string>(
                name: "Password",
                table: "Users",
                nullable: true,
                oldClrType: typeof(string));

            migrationBuilder.AlterColumn<string>(
                name: "Token",
                table: "RefreshTokens",
                nullable: true,
                oldClrType: typeof(string));

            migrationBuilder.CreateTable(
                name: "NutrientsReadings",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn),
                    CarbsGram = table.Column<int>(nullable: false),
                    Date = table.Column<DateTime>(nullable: false),
                    FatGram = table.Column<int>(nullable: false),
                    ProteinGram = table.Column<int>(nullable: false),
                    UserID = table.Column<int>(nullable: false),
                    WaterMilli = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_NutrientsReadings", x => x.ID);
                    table.ForeignKey(
                        name: "FK_NutrientsReadings_Users_UserID",
                        column: x => x.UserID,
                        principalTable: "Users",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_NutrientsReadings_UserID",
                table: "NutrientsReadings",
                column: "UserID");
        }
    }
}
