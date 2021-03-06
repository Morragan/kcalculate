﻿using AutoMapper;
using DietApp.Domain.Models;
using DietApp.ViewModels;
using System.Collections.Generic;

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
        }
    }
}
