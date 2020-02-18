using System;

namespace DietApp.Domain.Models
{
    public class MealEntry
    {
        public int ID { get; set; }
        public DateTime Date { get; set; }
        public int UserID { get; set; }
        public User User { get; set; }
        public string Name { get; set; }
        public Nutrients Nutrients { get; set; }
        public int WeightGram { get; set; }
        public float Kcal { get => WeightGram * Nutrients.Kcal / 100; }
        public float Carbs { get => WeightGram * Nutrients.Carbs / 100; }
        public float Fat { get => WeightGram * Nutrients.Fat / 100; }
        public float Protein { get => WeightGram * Nutrients.Protein / 100; }
    }
}
