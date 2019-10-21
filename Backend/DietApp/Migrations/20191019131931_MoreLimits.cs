using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class MoreLimits : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "CalorieLimit",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "CarbsLimit",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "FatLimit",
                table: "Users",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "ProteinLimit",
                table: "Users",
                nullable: false,
                defaultValue: 0);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "CalorieLimit",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "CarbsLimit",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "FatLimit",
                table: "Users");

            migrationBuilder.DropColumn(
                name: "ProteinLimit",
                table: "Users");
        }
    }
}
