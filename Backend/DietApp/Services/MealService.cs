using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using System;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class MealService : IMealService
    {
        readonly IMealRepository mealRepository;
        readonly IUnitOfWork unitOfWork;

        public MealService(IMealRepository mealRepository, IUnitOfWork unitOfWork)
        {
            this.mealRepository = mealRepository;
            this.unitOfWork = unitOfWork;
        }

        public async Task<MealResponse> Create(Meal meal)
        {
            await mealRepository.Add(meal);
            await unitOfWork.Complete();

            return new MealResponse(true, null);
        }

        public async Task<MealResponse> Delete(Meal meal)
        {
            mealRepository.Delete(meal);
            await unitOfWork.Complete();

            return new MealResponse(true, null);
        }

        //TODO: 3-party food lib
        public Task<FindMealsResponse> FindByBarcode(string barcode)
        {
            throw new NotImplementedException();
        }

        public async Task<FindMealsResponse> GetUserMeals(int userID)
        {
            var meals = await mealRepository.List(userID);

            return new FindMealsResponse(true, null, meals);
        }

        public async Task<MealResponse> Update(Meal meal)
        {
            var existingMeal = await mealRepository.FindById(meal.ID);
            if (existingMeal == null)
            {
                return new MealResponse(false, "Meal doesn't exist");
            }

            mealRepository.Update(meal);
            await unitOfWork.Complete();

            return new MealResponse(true, null);
        }
    }
}
