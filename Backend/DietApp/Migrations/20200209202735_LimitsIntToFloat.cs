using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class LimitsIntToFloat : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<float>(
                name: "ProteinLimitUpper",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "ProteinLimitLower",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "ProteinLimit",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "FatLimitUpper",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "FatLimitLower",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "FatLimit",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "CarbsLimitUpper",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "CarbsLimitLower",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<float>(
                name: "CarbsLimit",
                table: "Users",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "ProteinLimitUpper",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "ProteinLimitLower",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "ProteinLimit",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "FatLimitUpper",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "FatLimitLower",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "FatLimit",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "CarbsLimitUpper",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "CarbsLimitLower",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));

            migrationBuilder.AlterColumn<int>(
                name: "CarbsLimit",
                table: "Users",
                type: "int",
                nullable: false,
                oldClrType: typeof(float));
        }
    }
}
