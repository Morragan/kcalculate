using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class MealEntryKcal : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "Nutrients_KcalPer100Gram",
                table: "Meals",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "WeightGram",
                table: "MealEntries",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_KcalPer100Gram",
                table: "MealEntries",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Nutrients_KcalPer100Gram",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "WeightGram",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_KcalPer100Gram",
                table: "MealEntries");
        }
    }
}
