using DietApp.Domain.Models;

namespace DietApp.ViewModels
{
    public class MealViewModel
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public Nutrients Nutrients { get; set; }
    }
}
