using DietApp.Domain.Models;

namespace DietApp.ViewModels
{
    public class CreateMealEntryViewModel
    {
        public string Name { get; set; }
        public Nutrients Nutrients { get; set; }
        public int WeightGram { get; set; }
    }
}
