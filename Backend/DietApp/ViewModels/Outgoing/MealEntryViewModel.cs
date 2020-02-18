using DietApp.Domain.Models;
using System;

namespace DietApp.ViewModels
{
    public class MealEntryViewModel
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public DateTime Date { get; set; }
        public string ImageLink { get; set; }
        public Nutrients Nutrients { get; set; }
        public int WeightGram { get; set; }
        public float Kcal { get; set; }
        public float Carbs { get; set; }
        public float Fat { get; set; }
        public float Protein { get; set; }
    }
}
