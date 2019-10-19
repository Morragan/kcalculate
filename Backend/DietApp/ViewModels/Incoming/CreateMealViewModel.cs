using DietApp.Domain.Models;

namespace DietApp.ViewModels
{
    public class CreateMealViewModel
    {
        public string Name { get; set; }
        public Nutrients Nutrients { get; set; }
    }
}
