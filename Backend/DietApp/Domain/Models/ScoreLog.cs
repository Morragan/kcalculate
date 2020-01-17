using System;

namespace DietApp.Domain.Models
{
    public class ScoreLog
    {
        public int ID { get; set; }
        public int UserID { get; set; }
        public User User { get; set; }
        public DateTime Date { get; set; }
        public int ScoredPointsKcal { get; set; }
        public int ScoredPointsCarbs { get; set; }
        public int ScoredPointsFat { get; set; }
        public int ScoredPointsProtein { get; set; }

        public ScoreLog(int userID, DateTime date, int scoredPointsKcal, int scoredPointsCarbs, int scoredPointsFat, int scoredPointsProtein)
        {
            UserID = userID;
            Date = date;
            ScoredPointsKcal = scoredPointsKcal;
            ScoredPointsCarbs = scoredPointsCarbs;
            ScoredPointsFat = scoredPointsFat;
            ScoredPointsProtein = scoredPointsProtein;
        }
        public ScoreLog() { }
    }
}
