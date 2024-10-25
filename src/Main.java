import java.util.Scanner;

public class Main {
    private static Player player;
    private static Land land;
    private static Store store;
    private static Time time;
    private static BattleSystem battleSystem;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the farming and battle simulator!");
        initializeGame();
        gameLoop();
    }

    private static void initializeGame() {
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        player = new Player();
        player.PlayerSetName(playerName);

        land = new Land(3, 3);
        store = new Store(player);
        time = new Time();

        System.out.println("Hello, " + playerName + "! Let's get started.");
    }

    private static void gameLoop() {
        boolean gameRunning = true;
        while (gameRunning && player.money < 500) {
            System.out.println("\n--- Day " + time.days + ", Week " + time.weeks + ", Month " + time.month + " ---");
            displayMenu();

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    land.displayGrid();
                    plantCrop();
                    break;
                case 2:
                    land.displayGrid();
                    System.out.print("Enter row: ");
                    int row = scanner.nextInt();
                    System.out.print("Enter column: ");
                    int column = scanner.nextInt();
                    land.harvest(row, column);
                case 3:
                    progressTime();
                    break;
                case 4:
                    enterStore();
                    break;
                case 5:
                    player.checkStats();
                    break;
                case 6:
                    gameRunning = false;
                    System.out.println("Thanks for playing!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        if (player.money >= 500) {
            System.out.println("Congratulations! You've reached 500 gold and won the game!");
        }
    }

    private static void displayMenu() {
        System.out.println("\nChoose an action:");
        System.out.println("1. Plant crops on land");
        System.out.println("2. Harvest crops on land");
        System.out.println("3. Progress time");
        System.out.println("4. Enter the store (buy/sell items)");
        System.out.println("5. Check inventory and stats");
        System.out.println("6. Exit game");
        System.out.print("Your choice: ");
    }

    private static void plantCrop() {
        System.out.println("Enter row and column to plant crop (0-2 for both):");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        scanner.nextLine();
        land.plant(row, col);
    }

    private static void progressTime() {
        System.out.println("\nProgressing time...");
        time.timeChange();
        land.dailyGrowth();

        if (time.days % 3 == 0) {
            System.out.println("\nA battle is approaching...");
            battleSystem = new BattleSystem(player);
            battleSystem.startBattle();

            if (time.weeks > 1) {
                System.out.println("Enemies have grown stronger with each week.");
                for (Enemies enemy : battleSystem.enemies) {
                    enemy.hp += time.weeks * 5;
                    enemy.att += time.weeks * 2;
                }
            }
        }
    }

    private static void enterStore() {
        System.out.println("Entering the store...");
        store.enterStore();
    }
}
