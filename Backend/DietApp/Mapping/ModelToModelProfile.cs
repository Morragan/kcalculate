using AutoMapper;
using DietApp.Domain.Models;
using DietApp.Domain.Tokens;

namespace DietApp.Mapping
{
    public class ModelToModelProfile : Profile
    {
        public ModelToModelProfile()
        {
            CreateMap<JwtRefreshToken, RefreshToken>()
                .ForMember(dest => dest.Token, opt => opt.MapFrom(src => src.Token))
                .ForMember(dest => dest.Expiration, opt => opt.MapFrom(src => src.Expiration));

            CreateMap<RefreshToken, JwtRefreshToken>()
                .ForMember(dest => dest.Token, opt => opt.MapFrom(src => src.Token))
                .ForMember(dest => dest.Expiration, opt => opt.MapFrom(src => src.Expiration));
        }
    }
}
