using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class MealEntryName : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Name",
                table: "MealEntries",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Name",
                table: "MealEntries");
        }
    }
}
