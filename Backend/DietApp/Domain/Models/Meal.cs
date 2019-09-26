using System.ComponentModel.DataAnnotations.Schema;

namespace DietApp.Domain.Models
{
    public class Meal
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public Nutrients Nutrients { get; set; }
        public string ImageLink { get; set; }
        public int UserID { get; set; }
        public User User { get; set; }
    }
}
