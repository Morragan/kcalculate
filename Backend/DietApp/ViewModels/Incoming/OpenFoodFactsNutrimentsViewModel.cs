
using Newtonsoft.Json;

namespace DietApp.ViewModels.Incoming
{
    public class OpenFoodFactsNutrimentsViewModel
    {
        [JsonProperty("carbohydrates_100g")]
        public float Carbs { get; set; }
        [JsonProperty("fat_100g")]
        public float Fat { get; set; }
        [JsonProperty("proteins_100g")]
        public float Protein { get; set; }
        [JsonProperty("fiber_100g")]
        public float Fiber { get; set; }
        [JsonProperty("energy-kcal_100g")]
        public float Kcal { get; set; }
    }
}
