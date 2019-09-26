using DietApp.Domain.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.ViewModels
{
    public class CreateMealEntryViewModel
    {
        public string Name { get; set; }
        public string ImageLink { get; set; }
        public Nutrients Nutrients { get; set; }
    }
}
