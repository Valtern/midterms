import java.util.HashSet;
import java.util.LinkedList;

public class Player {
    String name;
    int money = 0, energy = 100;
    double health = 30;
    Weapon stick = new Weapon("Wooden stick", 4);
    LinkedList<Object> inv = new LinkedList<>();
    HashSet<Weapon> weapon = new HashSet<>();

    public void PlayerSetName(String name) {
        this.name = name;
        System.out.println("Welcome, " + name + "!");
        inv.addFirst(new Carrot());
        weapon.add(stick);
    }

    public boolean death() {
        return health > 0;
    }

    public void checkStats() {
        System.out.println("\n--- Player Stats ---");
        System.out.println("Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Money: " + money);

        System.out.println("\n--- Inventory ---");
        if (inv.size() > 0) {
            int i = 1;
            for (Object item : inv) {
                if (item instanceof Crop) {
                    Crop crop = (Crop) item;
                    System.out.println(i + ". " + crop.name + " (Ready: " + (crop.isFullyGrown() ? "Yes" : "No") + ")");
                }
                i++;
            }
        } else {
            System.out.println("Inventory is empty.");
        }

        System.out.println("\n--- Weapons ---");
        if (weapon.size() > 0) {
            int i = 1;
            for (Weapon w : weapon) {
                System.out.println(i + ". " + w.name + " (Damage: " + w.damage + ")");
                i++;
            }
        } else {
            System.out.println("No weapons.");
        }
    }
}
