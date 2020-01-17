using Newtonsoft.Json;

namespace DietApp.ViewModels.Incoming
{
    public class FatSecretNutrientsViewModel
    {
        [JsonProperty("serving_description")]
        public string ServingDescription { get; set; }
        [JsonProperty("number_of_units")]
        public string NumberOfUnits { get; set; }
        [JsonProperty("calories")]
        public float Kcal { get; set; }
        [JsonProperty("carbohydrate")]
        public float Carbs { get; set; }
        [JsonProperty("fat")]
        public float Fat { get; set; }
        [JsonProperty("protein")]
        public float Protein { get; set; }
        [JsonProperty("fiber")]
        public float Fiber { get; set; }
    }
}
