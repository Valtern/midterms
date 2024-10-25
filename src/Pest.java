import java.util.*;
public class Pest extends Enemies{
    Random rng = new Random();
    Player plyr = new Player();

    public Pest() {
        super("Mutant Ants", 10, 0, 1);
    }

    @Override
    public void performAction(Player player) {
        int attChoose = rng.nextInt(5);
        if (attChoose < 2) {
            player.health -= attackSmall(att);
            System.out.println(name + " took a nibble from " + player.name + "!");
            soundPlayer("eneAtt1.wav");
        } else if (attChoose == 3) {
            System.out.println(name + " bite " + player.name + "!");
            player.health -= attackMed(att);
            soundPlayer("eneAtt2.wav");
        } else if (attChoose == 4) {
            System.out.println(name + " poisoned " + player.name + "!");
            player.health -= poison(att);
            soundPlayer("eneBite.wav");
        }
    }


    public double attackSmall(int att) {
        return att * 0.8;
    }

    public double attackMed(int att) {
        System.out.println();
        return att;
    }

    public double attackHeavy(int att) {
        return att * 1.2;
    }

    public double defense(int def) {
        return def * 2;
    }

    public double defenseHeavy(int def) {
        return def * 4;
    }

    public double poison (int att) {
        double totalDmg = 0;
        for (int i = 0; i <= 3; i++) {
             totalDmg = att * 0.7;
        }
        return totalDmg;

    }

}
