using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class NutrientsNamesSimplified : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Nutrients_CarbsGram",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_FatGram",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_FiberGram",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_KcalPer100Gram",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_ProteinGram",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_CarbsGram",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_FatGram",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_FiberGram",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_KcalPer100Gram",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_ProteinGram",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_CarbsGram",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_FatGram",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_FiberGram",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_KcalPer100Gram",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_ProteinGram",
                table: "MealEntries");

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Carbs",
                table: "PublicMeals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Fat",
                table: "PublicMeals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Fiber",
                table: "PublicMeals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Kcal",
                table: "PublicMeals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Protein",
                table: "PublicMeals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Carbs",
                table: "Meals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Fat",
                table: "Meals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Fiber",
                table: "Meals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Kcal",
                table: "Meals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Protein",
                table: "Meals",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Carbs",
                table: "MealEntries",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Fat",
                table: "MealEntries",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Fiber",
                table: "MealEntries",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Kcal",
                table: "MealEntries",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "Nutrients_Protein",
                table: "MealEntries",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Nutrients_Carbs",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Fat",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Fiber",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Kcal",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Protein",
                table: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Carbs",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Fat",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Fiber",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Kcal",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Protein",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_Carbs",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_Fat",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_Fiber",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_Kcal",
                table: "MealEntries");

            migrationBuilder.DropColumn(
                name: "Nutrients_Protein",
                table: "MealEntries");

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_CarbsGram",
                table: "PublicMeals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FatGram",
                table: "PublicMeals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FiberGram",
                table: "PublicMeals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_KcalPer100Gram",
                table: "PublicMeals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_ProteinGram",
                table: "PublicMeals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_CarbsGram",
                table: "Meals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FatGram",
                table: "Meals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FiberGram",
                table: "Meals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_KcalPer100Gram",
                table: "Meals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_ProteinGram",
                table: "Meals",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_CarbsGram",
                table: "MealEntries",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FatGram",
                table: "MealEntries",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FiberGram",
                table: "MealEntries",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_KcalPer100Gram",
                table: "MealEntries",
                type: "int",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_ProteinGram",
                table: "MealEntries",
                type: "int",
                nullable: true);
        }
    }
}
