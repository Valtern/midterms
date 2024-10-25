public class Crop {
    String name;
    double growthRate;
    double growthMax;
    double growthProgress;
    int sellingPrice;
    String favoredWeather, favoredSeason, unfavorableWeather, unfavorableSeason;

    public Crop() {
        growthProgress = 0;
    }

    public void plantGrowInc(String currentWeather, String currentSeason) {
        if (currentWeather.equals(favoredWeather) || currentSeason.equals(favoredSeason)) {
            growthProgress += growthRate * 1.5;
        } else if (currentWeather.equals(unfavorableWeather) || currentSeason.equals(unfavorableSeason)) {
            growthProgress += growthRate * 0.5;
        } else {
            growthProgress += growthRate;
        }

        if (growthProgress >= growthMax) {
            System.out.println(name + " has fully grown.");
        } else {
            System.out.println("Current growth progress of " + name + ": " + growthProgress);
        }
    }

    public boolean isFullyGrown() {
        return growthProgress >= growthMax;
    }
}
