using DietApp.Domain.Models;

namespace DietApp.ViewModels.Incoming
{
    public class CreatePublicMealViewModel
    {
        public string Name { get; set; }
        public Nutrients Nutrients { get; set; }
        public string Barcode { get; set; }
    }
}
