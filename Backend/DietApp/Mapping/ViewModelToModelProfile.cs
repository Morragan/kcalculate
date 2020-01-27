using AutoMapper;
using DietApp.Domain.Models;
using DietApp.ViewModels;
using DietApp.ViewModels.Incoming;

namespace DietApp.Mapping
{
    public class ViewModelToModelProfile : Profile
    {
        public ViewModelToModelProfile()
        {
            CreateMap<RegisterViewModel, User>();
            CreateMap<LoginViewModel, User>();
            CreateMap<MealViewModel, Meal>();
            CreateMap<CreateMealViewModel, Meal>();
            CreateMap<CreateMealEntryViewModel, MealEntry>();
            CreateMap<OpenFoodFactsNutrimentsViewModel, Nutrients>();
            CreateMap<FatSecretNutrientsViewModel, Nutrients>();
            CreateMap<CreatePublicMealViewModel, PublicMeal>();
        }
    }
}
