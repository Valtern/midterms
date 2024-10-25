import java.util.*;
public class Pest2 extends Enemies{
    Random rng = new Random();
    Player plyr = new Player();

    public Pest2() {
        super("Mutant Rat", 20, 2, 3);
    }
    @Override
    public void performAction(Player player) {
        int attChoose = rng.nextInt(5);
        if (attChoose <= 2) {
            System.out.println(name + " took a nibble from " + player.name + "!");
            player.health -= attackSmall(att);
            soundPlayer("eneAtt1.wav");
        } else if (attChoose > 2 && attChoose <= 4) {
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
