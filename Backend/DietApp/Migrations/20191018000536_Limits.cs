using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class Limits : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ExerciseReadings");

            migrationBuilder.DropColumn(
                name: "CalorieLimit",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "Gender",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "HeightCm",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "TelephoneNumber",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "WeightKg",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "ImageLink",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_WaterMilli",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "ImageLink",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_WaterMilli",
                table: "MealEntries");

            migrationBuilder.AddColumn<int>(
                name: "CalorieLimitLower",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "CalorieLimitUpper",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "CarbsLimitLower",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "CarbsLimitUpper",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "FatLimitLower",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "FatLimitUpper",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "ProteinLimitLower",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "ProteinLimitUpper",
                table: "Users",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "CalorieLimitLower",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "CalorieLimitUpper",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "CarbsLimitLower",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "CarbsLimitUpper",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "FatLimitLower",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "FatLimitUpper",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "ProteinLimitLower",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "ProteinLimitUpper",
                table: "Users");

            migrationBuilder.AddColumn<int>(
                name: "CalorieLimit",
                table: "Users",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "Gender",
                table: "Users",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<decimal>(
                name: "HeightCm",
                table: "Users",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<string>(
                name: "TelephoneNumber",
                table: "Users",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "WeightKg",
                table: "Users",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<string>(
                name: "ImageLink",
                table: "Meals",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_WaterMilli",
                table: "Meals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "ImageLink",
                table: "MealEntries",
                type: "nvarchar(max)",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_WaterMilli",
                table: "MealEntries",
                type: "int",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "ExerciseReadings",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Date = table.Column<DateTime>(type: "datetime2", nullable: false),
                    KcalBurned = table.Column<int>(type: "int", nullable: false),
                    UserID = table.Column<int>(type: "int", nullable: false)
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

            migrationBuilder.CreateIndex(
                name: "IX_ExerciseReadings_UserID",
                table: "ExerciseReadings",
                column: "UserID");
        }
    }
}
