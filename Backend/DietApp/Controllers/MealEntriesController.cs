using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Services;
using DietApp.ViewModels;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace DietApp.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class MealEntriesController : ControllerBase
    {
        readonly IUserService userService;
        readonly IMealEntryService mealEntryService;
        readonly IMapper mapper;

        public MealEntriesController(IUserService userService, IMealEntryService mealEntryService, IMapper mapper)
        {
            this.userService = userService;
            this.mealEntryService = mealEntryService;
            this.mapper = mapper;
        }

        [HttpPost]
        public async Task<IActionResult> Record([FromBody]CreateMealEntryViewModel viewModel)
        {
            if (!ModelState.IsValid) return BadRequest();

            var mealEntry = mapper.Map<CreateMealEntryViewModel, MealEntry>(viewModel);
            mealEntry.UserID = userService.GetCurrentUserId(HttpContext);
            var response = await mealEntryService.RecordMeal(mealEntry);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var mealEntriesResponse = await mealEntryService.GetMealEntries(mealEntry.UserID);
            if (!mealEntriesResponse.IsSuccess) return BadRequest(response.Message);
            var mealEntriesViewModel = mapper.Map<IEnumerable<MealEntry>, IEnumerable<MealEntryViewModel>>(mealEntriesResponse.MealEntries);
            return Ok(mealEntriesViewModel);
        }

        [HttpGet]
        [Route("today")]
        public async Task<IActionResult> GetTodaysEntries()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await mealEntryService.GetMealEntriesToday(userId);
            if (!response.IsSuccess) return BadRequest(response.Message);
            // Dać view modele
            return Ok(response.MealEntries);
        }

        [HttpGet]
        [Route("history")]
        public async Task<IActionResult> GetEntries()
        {
            var userId = userService.GetCurrentUserId(HttpContext);
            var response = await mealEntryService.GetMealEntries(userId);
            if (!response.IsSuccess) return BadRequest(response.Message);

            var responseViewModel = mapper.Map<IEnumerable<MealEntry>, IEnumerable<MealEntryViewModel>>(response.MealEntries);
            return Ok(responseViewModel);
        }
    }
}