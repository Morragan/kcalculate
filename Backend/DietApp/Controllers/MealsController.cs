using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.ViewModels;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace DietApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class MealsController : ControllerBase
    {
        readonly IMealService mealService;
        readonly IMealEntryService mealEntryService;
        readonly IUserService userService;
        readonly IMapper mapper;

        public MealsController(IMealService mealService, IMealEntryService mealEntryService, IUserService userService, IMapper mapper)
        {
            this.mapper = mapper;
            this.userService = userService;
            this.mealEntryService = mealEntryService;
            this.mealService = mealService;
        }
        [HttpGet]
        public async Task<IActionResult> GetUserMeals()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await mealService.GetUserMeals(userId).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var mealViewModels = mapper.Map<IEnumerable<Meal>, IEnumerable<MealViewModel>>(response.MealsFound);
            return Ok(mealViewModels);
        }

        [HttpPost]
        public async Task<IActionResult> AddMeal([FromBody] CreateMealViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest();

            var meal = mapper.Map<CreateMealViewModel, Meal>(viewModel);
            var userId = userService.GetCurrentUserId(HttpContext);
            meal.UserID = userId;

            var response = await mealService.Create(meal).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var userMealsResponse = await mealService.GetUserMeals(userId).ConfigureAwait(false);
            if (!userMealsResponse.IsSuccess) return BadRequest(userMealsResponse.Message);

            var userMealsViewModel = mapper.Map<IEnumerable<Meal>, IEnumerable<MealViewModel>>(userMealsResponse.MealsFound);
            return Ok(userMealsViewModel);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteMeal(int id)
        {
            if (!ModelState.IsValid) return BadRequest();

            var userId = userService.GetCurrentUserId(HttpContext);
            var findMealsResponse = await mealService.GetUserMeals(userId).ConfigureAwait(false);
            if (!findMealsResponse.IsSuccess) return BadRequest(findMealsResponse.Message);

            var meal = findMealsResponse.MealsFound.Where(m => m.ID == id).SingleOrDefault();
            if (meal == null) return BadRequest("Meal doesn't exist");

            var response = await mealService.Delete(meal).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            return NoContent();
        }

        [HttpPut]
        public async Task<IActionResult> UpdateMeal([FromBody]MealViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest();

            var meal = mapper.Map<MealViewModel, Meal>(viewModel);
            var userId = userService.GetCurrentUserId(HttpContext);
            var userMealsResponse = await mealService.GetUserMeals(userId).ConfigureAwait(false);
            if (!userMealsResponse.IsSuccess) return BadRequest(userMealsResponse.Message);

            var userMealsIds = userMealsResponse.MealsFound.Select(m => m.ID);
            if (!userMealsIds.Contains(meal.ID)) return BadRequest("Meal doesn't exist");

            var response = await mealService.Update(meal).ConfigureAwait(false);
            if (!response.IsSuccess) return BadRequest(response.Message);

            return NoContent();
        }
    }
}