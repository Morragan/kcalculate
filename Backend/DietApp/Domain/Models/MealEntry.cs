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
        public string ImageLink { get; set; }
        public Nutrients Nutrients { get; set; } //TODO: Bardzo możliwe że trzeba będzie zamienić na meal
        public int WeightGram { get; set; }
        //TODO: ogarnąć dlaczego kcal daje 0
        public int Kcal { get => WeightGram * Nutrients.KcalPer100Gram / 100; } //TODO: Rozważyć czy tylko gram, czy dodać np sztuki
    }
}
