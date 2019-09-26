using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DietApp.Domain.Models
{
    public class ScoreLog
    {
        public int ID { get; set; }
        public int UserID { get; set; }
        public User User { get; set; }
        public DateTime Date { get; set; }
        public int ScoredPoints { get; set; }
    }
}
