import java.util.Random;

public class Weather extends Time {
    public String currentSeason() {
        int adjustedMonth = (month - 1) % 12 + 1;
        switch (adjustedMonth / 3) {
            case 0:
                return "Winter";
            case 1:
                return "Spring";
            case 2:
                return "Summer";
            case 3:
                return "Autumn";
            default:
                return "Invalid month";
        }
    }

    public String currentWeather() {
        int rng = new Random().nextInt(6);
        if (month == 12) {
            return "Snowy";
        } else {
            switch (rng) {
                case 0: return "Sunny";
                case 1: return "Cloudy";
                case 2: return "Rainy";
                case 3: return "Heatwave";
                case 4: return "Storm";
                case 5: return "Windy";
                default: return "Unknown";
            }
        }
    }
}
