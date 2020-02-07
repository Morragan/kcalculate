using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using AutoMapper;
using DietApp.Domain.Helpers;
using DietApp.Domain.Repositories;
using DietApp.Domain.Services;
using DietApp.Domain.Tokens;
using DietApp.Helpers;
using DietApp.Persistence.Contexts;
using DietApp.Persistence.Repositories;
using DietApp.Services;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.IdentityModel.Tokens;

namespace DietApp
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            //services.AddControllers().AddNewtonsoftJson();
            services.AddCors();
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Latest)
                .AddNewtonsoftJson(options => options.SerializerSettings.Converters.Add(new DecimalJsonConverter()));
            services.AddControllers();
            services.AddDbContext<ApplicationDbContext>(options =>
            {
                options.UseSqlServer(Configuration.GetConnectionString("DefaultConnection"));
                //options.UseInMemoryDatabase("testDB");
            });
            services.AddScoped<IUnitOfWork, UnitOfWork>();
            services.AddScoped<IUserRepository, UserRepository>();
            services.AddScoped<IMealRepository, MealRepository>();
            services.AddScoped<IMealEntryRepository, MealEntryRepository>();
            services.AddScoped<IPublicMealRepository, PublicMealRepository>();
            services.AddScoped<IFriendshipRepository, FriendshipRepository>();
            services.AddScoped<IScoreLogRepository, ScoreLogRepository>();
            services.AddScoped<ITokenService, TokenService>();

            services.AddScoped<Domain.Services.IAuthenticationService, Services.AuthenticationService>();
            services.AddScoped<IUserService, UserService>();
            services.AddScoped<ITokenService, TokenService>();
            services.AddScoped<IMealService, MealService>();
            services.AddScoped<IMealEntryService, MealEntryService>();
            services.AddScoped<IFriendshipService, FriendshipService>();
            services.AddScoped<IScheduledActionsService, ScheduledActionsService>();
            services.AddScoped<IRefreshTokenRepository, RefreshTokenRepository>();

            services.AddSingleton<IPasswordHasher, PasswordHasher>();
            services.AddSingleton(typeof(FatSecretAPITokenCache));
            var signingConfigurations = new SigningConfigurations();
            services.AddSingleton(signingConfigurations);

            services.AddAutoMapper(typeof(Startup));



            services.Configure<TokenClaims>(Configuration.GetSection("TokenClaims"));
            var tokenClaims = Configuration.GetSection("TokenClaims").Get<TokenClaims>();

            services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
                .AddJwtBearer(jwtBearerOptions =>
                {
                    jwtBearerOptions.TokenValidationParameters = new TokenValidationParameters()
                    {
                        ValidateAudience = true,
                        ValidateLifetime = true,
                        ValidateIssuerSigningKey = true,
                        ValidIssuer = tokenClaims.Issuer,
                        ValidAudience = tokenClaims.Audience,
                        IssuerSigningKey = signingConfigurations.Key,
                        ClockSkew = TimeSpan.Zero,
                        LifetimeValidator =
                            (DateTime? notBefore, DateTime? expires, SecurityToken securityToken, TokenValidationParameters validationParameters) =>
                                notBefore <= DateTime.UtcNow && expires >= DateTime.UtcNow
                    };
                })
            .AddGoogle(options =>
            {
                IConfigurationSection googleAuthNSection =
                    Configuration.GetSection("Authentication:Google");

                options.ClientId = googleAuthNSection["ClientId"];
                options.ClientSecret = googleAuthNSection["ClientSecret"];
                options.CallbackPath = "/api/signin-google";
            })
            .AddFacebook(facebookOptions =>
            {
                facebookOptions.AppId = Configuration["Authentication:Facebook:AppId"];
                facebookOptions.AppSecret = Configuration["Authentication:Facebook:AppSecret"];
                facebookOptions.CallbackPath = "/api/signin-facebook";
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }
            app.UseCors(options =>
            {
                options.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader();
                //options.WithOrigins("https://localhost:3000").AllowAnyMethod().AllowAnyHeader();
                //options.WithOrigins("https://192.168.8.105").AllowAnyMethod().AllowAnyHeader();
            });
            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthentication();
            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapDefaultControllerRoute();
            });
        }
    }
}
