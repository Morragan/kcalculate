using DietApp.Domain.Models;

namespace DietApp.ViewModels
{
    public class CreateMealViewModel
    {
        public string Name { get; set; }
        public string ImageLink { get; set; }
        public Nutrients Nutrients { get; set; }
    }
}
