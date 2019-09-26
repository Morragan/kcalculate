using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class UserAvatarLink : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "AvatarLink",
                table: "Users",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "AvatarLink",
                table: "Users");
        }
    }
}
