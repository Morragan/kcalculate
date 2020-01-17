using DietApp.Domain.Models;
using DietApp.Domain.Repositories;
using DietApp.Domain.Responses;
using DietApp.Domain.Services;
using System;
using System.Threading.Tasks;

namespace DietApp.Services
{
    public class MealEntryService : IMealEntryService
    {
        readonly IMealEntryRepository mealEntryRepository;
        readonly IUnitOfWork unitOfWork;

        public MealEntryService(IMealEntryRepository mealEntryRepository, IUnitOfWork unitOfWork)
        {
            this.mealEntryRepository = mealEntryRepository;
            this.unitOfWork = unitOfWork;
        }

        public async Task<FindMealEntriesResponse> GetMealEntries(int userId)
        {
            var mealEntriesFound = await mealEntryRepository.List(userId);
            return new FindMealEntriesResponse(true, null, mealEntriesFound);
        }

        public async Task<FindMealEntriesResponse> GetMealEntriesToday(int userId)
        {
            var mealEntriesFound = await mealEntryRepository.ListFromDay(userId, DateTime.Now.AddDays(-1)).ConfigureAwait(false);
            return new FindMealEntriesResponse(true, null, mealEntriesFound);
        }

        public async Task<RecordMealResponse> RecordMeal(MealEntry mealEntry)
        {
            mealEntry.Date = DateTime.UtcNow;

            await mealEntryRepository.Add(mealEntry);
            await unitOfWork.Complete();

            return new RecordMealResponse(true, null);
        }
    }
}
