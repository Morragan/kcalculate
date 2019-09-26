using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Hosting;

namespace DietApp
{
    public class Program
    {
        public static void Main(string[] args)
        {
            CreateHostBuilder(args).Build().Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseStartup<Startup>();
                });

        //public static void Main(string[] args)
        //{
        //    var host = CreateHostBuilder(args).Build();

        //    using (var scope = host.Services.CreateScope())
        //    using (var context = scope.ServiceProvider.GetService<ApplicationDbContext>())
        //    {
        //        context.Database.EnsureCreated();
        //    }

        //    host.Run();
        //}
    }
}
