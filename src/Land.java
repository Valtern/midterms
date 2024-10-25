import java.util.LinkedList;
import java.util.Scanner;

public class Land {
    char[][] grid;
    LinkedList<Crop> plants = new LinkedList<>();
    Player plyr = new Player();
    Scanner sc = new Scanner(System.in);
    Weather weather = new Weather();

    public Land(int rows, int cols) {
        grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = 'O';
            }
        }
    }

    public void displayGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void plant(int row, int col) {
        System.out.println("What seed would you like to plant?");
        System.out.println("1. Carrot\n2. Potatoes\n3. Cabbage\n-1. Exit");
        int select = sc.nextInt();

        if (select != -1) {
            if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
                if (grid[row][col] == 'O') {
                    Crop crop;
                    switch (select) {
                        case 1: crop = new Carrot();
                        break;
                        case 2: crop = new Potatoes();
                        break;
                        case 3: crop = new Cabbage();
                        break;
                        default: return;
                    }
                    grid[row][col] = 'P';
                    plants.add(crop);
                    System.out.println(crop.name + " planted!");
                } else {
                    System.out.println("Cannot plant here. Spot is unavailable.");
                }
            } else {
                System.out.println("Invalid coordinates.");
            }
        } else {
            System.out.println("Exited!");
        }
    }

    public void dailyGrowth() {
        String currentSeason = weather.currentSeason();
        String currentWeather = weather.currentWeather();

        for (Crop crop : plants) {
            crop.plantGrowInc(currentWeather, currentSeason);
            if (crop.isFullyGrown()) {
                System.out.println(crop.name + " is ready to harvest!");
            }
        }
    }

    public void harvest(int row, int col) {
        if (grid[row][col] == 'P') {
            Crop crop = plants.removeFirst();
            if (crop.isFullyGrown()) {
                plyr.inv.add(crop);
                grid[row][col] = 'O';
                System.out.println(crop.name + " harvested and added to inventory!");
            } else {
                System.out.println("This crop is not ready to harvest.");
            }
        } else {
            System.out.println("No plant to harvest here.");
        }
    }
}
