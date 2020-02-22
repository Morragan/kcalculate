using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace DietApp.Migrations
{
    public partial class Goals2 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_GoalInvitations_InvitedUserID",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "MaxCaloriesKcal",
                table: "Goals");

            migrationBuilder.DropColumn(
                name: "MinCaloriesKcal",
                table: "Goals");

            migrationBuilder.DropColumn(
                name: "Reward",
                table: "Goals");

            migrationBuilder.DropColumn(
                name: "Type",
                table: "Goals");

            migrationBuilder.AddColumn<int>(
                name: "WeightGoal",
                table: "Goals",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "CalorieLimit",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "CalorieLimitLower",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "CalorieLimitUpper",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<float>(
                name: "CarbsLimit",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "CarbsLimitLower",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "CarbsLimitUpper",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "FatLimit",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "FatLimitLower",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "FatLimitUpper",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "ProteinLimit",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "ProteinLimitLower",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<float>(
                name: "ProteinLimitUpper",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.AddColumn<DateTime>(
                name: "StartDate",
                table: "GoalInvitations",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.CreateIndex(
                name: "IX_GoalInvitations_InvitedUserID",
                table: "GoalInvitations",
                column: "InvitedUserID",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_GoalInvitations_InvitedUserID",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "WeightGoal",
                table: "Goals");

            migrationBuilder.DropColumn(
                name: "CalorieLimit",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "CalorieLimitLower",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "CalorieLimitUpper",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "CarbsLimit",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "CarbsLimitLower",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "CarbsLimitUpper",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "FatLimit",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "FatLimitLower",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "FatLimitUpper",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "ProteinLimit",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "ProteinLimitLower",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "ProteinLimitUpper",
                table: "GoalInvitations");

            migrationBuilder.DropColumn(
                name: "StartDate",
                table: "GoalInvitations");

            migrationBuilder.AddColumn<int>(
                name: "MaxCaloriesKcal",
                table: "Goals",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "MinCaloriesKcal",
                table: "Goals",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "Reward",
                table: "Goals",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "Type",
                table: "Goals",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_GoalInvitations_InvitedUserID",
                table: "GoalInvitations",
                column: "InvitedUserID");
        }
    }
}
