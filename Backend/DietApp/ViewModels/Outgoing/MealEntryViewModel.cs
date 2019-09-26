using DietApp.Domain.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

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
