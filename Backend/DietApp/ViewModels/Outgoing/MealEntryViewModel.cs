using DietApp.Domain.Models;
using System;

namespace DietApp.ViewModels
{
    public class MealEntryViewModel
    {
        public string Name { get; set; }
        public DateTime Date { get; set; }
        public string ImageLink { get; set; }
        public Nutrients Nutrients { get; set; }
        public int WeightGram { get; set; }
        public int Kcal { get; set; }
    }
}
