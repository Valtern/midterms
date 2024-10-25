import java.util.*;

public class Store {
    Player player;
    Scanner input = new Scanner(System.in);

    public Store(Player player) {
        this.player = player;
    }

    public void enterStore() {
        boolean shopping = true;
        while (shopping) {
            System.out.println("\nWelcome to the store! What would you like to do?");
            System.out.println("1. Buy Items");
            System.out.println("2. Sell Items");
            System.out.println("3. Exit Store");

            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    buyItems();
                    break;
                case 2:
                    sellItems();
                    break;
                case 3:
                    shopping = false;
                    System.out.println("Thanks for visiting the store!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void buyItems() {
        System.out.println("\nWhat would you like to buy?");
        System.out.println("1. Weapons");
        System.out.println("2. Seeds");
        System.out.println("3. Exit");

        int choice = input.nextInt();
        switch (choice) {
            case 1:
                buyWeapons();
                break;
            case 2:
                buySeeds();
                break;
            case 3:
                System.out.println("Exiting Buy Menu.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void buyWeapons() {
        System.out.println("\nAvailable Weapons:");
        System.out.println("1. Wooden sword (Cost: 50 coins, Damage: 6)");
        System.out.println("2. Copper sword (Cost: 100 coins, Damage: 8)");
        System.out.println("3. Iron sword (Cost: 200 coins, Damage: 10)");
        System.out.println("4. Exit");

        int choice = input.nextInt();
        Weapon weapon = null;
        int cost = 0;

        switch (choice) {
            case 1:
                weapon = new Weapon("Wooden sword", 6);
                cost = 50;
                break;
            case 2:
                weapon = new Weapon("Copper sword", 8);
                cost = 100;
                break;
            case 3:
                weapon = new Weapon("Iron sword", 10);
                cost = 200;
                break;
            case 4:
                System.out.println("Exiting weapon menu.");
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (checkMoney(cost)) {
            player.weapon.add(weapon);
            System.out.println("You bought a " + weapon.name + "!");
        } else {
            System.out.println("You don't have enough money.");
        }
    }

    private void buySeeds() {
        System.out.println("\nAvailable Seeds:");
        System.out.println("1. Carrot seed (Cost: 5 coins)");
        System.out.println("2. Potato seed (Cost: 10 coins)");
        System.out.println("3. Cabbage seed (Cost: 15 coins)");
        System.out.println("4. Exit");

        int choice = input.nextInt();
        int cost = 0;
        Object seed = null;

        switch (choice) {
            case 1:
                seed = new Carrot();
                cost = 5;
                break;
            case 2:
                seed = new Potatoes();
                cost = 10;
                break;
            case 3:
                seed = new Cabbage();
                cost = 15;
                break;
            case 4:
                System.out.println("Exiting seed menu.");
                return;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (checkMoney(cost)) {
            player.inv.add(seed);
            System.out.println("You bought a " + ((Crop) seed).name + " seed!");
        } else {
            System.out.println("You don't have enough money.");
        }
    }

    // Method to handle selling items
    private void sellItems() {
        System.out.println("\nAvailable items to sell from your inventory:");
        int i = 1;
        LinkedList<Crop> sellableItems = new LinkedList<>();

        for (Object item : player.inv) {
            if (item instanceof Crop) {
                System.out.println(i + ". " + ((Crop) item).name + " (Sell Price: " + ((Crop) item).sellingPrice + " coins)");
                sellableItems.add((Crop) item);
                i++;
            }
        }

        if (sellableItems.size() == 0) {
            System.out.println("You have no crops to sell.");
            return;
        }

        System.out.println(i + ". Exit");
        int choice = input.nextInt();

        if (choice > 0 && choice <= sellableItems.size()) {
            Crop crop = sellableItems.get(choice - 1);
            player.money += crop.sellingPrice;
            player.inv.remove(crop);
            System.out.println("You sold " + crop.name + " for " + crop.sellingPrice + " coins.");
        } else if (choice == i) {
            System.out.println("Exiting sell menu.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private boolean checkMoney(int price) {
        if (player.money >= price) {
            player.money -= price;
            return true;
        }
        return false;
    }
}
