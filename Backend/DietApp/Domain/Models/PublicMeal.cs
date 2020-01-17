namespace DietApp.Domain.Models
{
    public class PublicMeal
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public Nutrients Nutrients { get; set; }
        public string Barcode { get; set; }

        public PublicMeal(string name, Nutrients nutrients, string barcode)
        {
            Name = name;
            Nutrients = nutrients;
            Barcode = barcode;
        }
        public PublicMeal(string name, Nutrients nutrients)
        {
            Name = name;
            Nutrients = nutrients;
        }
        public PublicMeal() { }
    }
}
