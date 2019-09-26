using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class mealentry : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_MealReadings_Users_UserID",
                table: "MealReadings");

            migrationBuilder.DropPrimaryKey(
                name: "PK_MealReadings",
                table: "MealReadings");

            migrationBuilder.RenameTable(
                name: "MealReadings",
                newName: "MealEntries");

            migrationBuilder.RenameIndex(
                name: "IX_MealReadings_UserID",
                table: "MealEntries",
                newName: "IX_MealEntries_UserID");

            migrationBuilder.AddPrimaryKey(
                name: "PK_MealEntries",
                table: "MealEntries",
                column: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_MealEntries_Users_UserID",
                table: "MealEntries",
                column: "UserID",
                principalTable: "Users",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_MealEntries_Users_UserID",
                table: "MealEntries");

            migrationBuilder.DropPrimaryKey(
                name: "PK_MealEntries",
                table: "MealEntries");

            migrationBuilder.RenameTable(
                name: "MealEntries",
                newName: "MealReadings");

            migrationBuilder.RenameIndex(
                name: "IX_MealEntries_UserID",
                table: "MealReadings",
                newName: "IX_MealReadings_UserID");

            migrationBuilder.AddPrimaryKey(
                name: "PK_MealReadings",
                table: "MealReadings",
                column: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK_MealReadings_Users_UserID",
                table: "MealReadings",
                column: "UserID",
                principalTable: "Users",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
