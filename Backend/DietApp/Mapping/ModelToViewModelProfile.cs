﻿using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Tokens;
using DietApp.ViewModels;
using DietApp.ViewModels.Outgoing;

namespace DietApp.Mapping
{
    public class ModelToViewModelProfile : Profile
    {
        public ModelToViewModelProfile()
        {
            CreateMap<User, RegisterViewModel>();
            CreateMap<User, LoginViewModel>();
            CreateMap<User, UserViewModel>();
            CreateMap<JwtAccessToken, AccessTokenViewModel>()
                .ForMember(dest => dest.AccessToken, opt => opt.MapFrom(src => src.Token))
                .ForMember(dest => dest.RefreshToken, opt => opt.MapFrom(src => src.RefreshToken.Token))
                .ForMember(dest => dest.Expiration, opt => opt.MapFrom(src => src.Expiration));
            CreateMap<Meal, MealViewModel>();
            CreateMap<MealEntry, MealEntryViewModel>();
            CreateMap<(User user, FriendshipStatus status), FriendViewModel>()
                .ForMember(dest => dest.ID, opt => opt.MapFrom(src => src.user.ID))
                .ForMember(dest => dest.Nickname, opt => opt.MapFrom(src => src.user.Nickname))
                .ForMember(dest => dest.AvatarLink, opt => opt.MapFrom(src => src.user.AvatarLink))
                .ForMember(dest => dest.Status, opt => opt.MapFrom(src => src.status));
            //TODO: dodać punkty
            CreateMap<User, SearchUserViewModel>();
        }
    }
}
