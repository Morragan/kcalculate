using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class PublicMeal : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "ScoredPoints",
                table: "ScoreLogs");

            migrationBuilder.AddColumn<int>(
                name: "ScoredPointsCarbs",
                table: "ScoreLogs",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "ScoredPointsFat",
                table: "ScoreLogs",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "ScoredPointsKcal",
                table: "ScoreLogs",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "ScoredPointsProtein",
                table: "ScoreLogs",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<long>(
                name: "Expiration",
                table: "RefreshTokens",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FiberGram",
                table: "Meals",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "Nutrients_FiberGram",
                table: "MealEntries",
                nullable: true);

            migrationBuilder.CreateTable(
                name: "PublicMeals",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(maxLength: 50, nullable: false),
                    Nutrients_CarbsGram = table.Column<int>(nullable: true),
                    Nutrients_FatGram = table.Column<int>(nullable: true),
                    Nutrients_ProteinGram = table.Column<int>(nullable: true),
                    Nutrients_FiberGram = table.Column<int>(nullable: true),
                    Nutrients_KcalPer100Gram = table.Column<int>(nullable: true),
                    Barcode = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_PublicMeals", x => x.ID);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "PublicMeals");

            migrationBuilder.DropColumn(
                name: "ScoredPointsCarbs",
                table: "ScoreLogs");

            migrationBuilder.DropColumn(
                name: "ScoredPointsFat",
                table: "ScoreLogs");

            migrationBuilder.DropColumn(
                name: "ScoredPointsKcal",
                table: "ScoreLogs");

            migrationBuilder.DropColumn(
                name: "ScoredPointsProtein",
                table: "ScoreLogs");

            migrationBuilder.DropColumn(
                name: "Nutrients_FiberGram",
                table: "Meals");

            migrationBuilder.DropColumn(
                name: "Nutrients_FiberGram",
                table: "MealEntries");

            migrationBuilder.AddColumn<int>(
                name: "ScoredPoints",
                table: "ScoreLogs",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<int>(
                name: "Expiration",
                table: "RefreshTokens",
                type: "int",
                nullable: false,
                oldClrType: typeof(long));
        }
    }
}
