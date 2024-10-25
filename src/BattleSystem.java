import java.util.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class BattleSystem {
    LinkedList<Object> arena = new LinkedList<>();
    Random rand = new Random();
    int turns;
    Player player;
    LinkedList<Enemies> enemies = new LinkedList<>();
    Clip bgmClip;

    public BattleSystem(Player player) {
        this.player = player;
        arena.add(player);
        System.out.println("Battle initiated!");
        playBGM();
    }

    public void spawnEnemies() {
        double chance = rand.nextDouble();
        if (chance <= 0.7) {
            enemies.add(new Pest());
        } else if (chance <= 0.9) {
            enemies.add(new Pest());
            enemies.add(new Pest2());
        } else {
            enemies.add(new Pest());
            enemies.add(new Pest2());
            enemies.add(new Pest());
        }
        for (Enemies enemy : enemies) {
            arena.add(enemy);
        }
        System.out.println(enemies.size() + " enemies spawned!");
    }

    public void startBattle() {
        spawnEnemies();
        while (player.death() && enemies.stream().anyMatch(Enemies::isAlive)) {
            turns++;
            System.out.println("\nTurn " + turns);

            if (turns % 2 == 1) {
                System.out.println("Player's turn!");
                playerTurn();
            } else {
                System.out.println("Enemy's turn!");
                enemyTurn();
            }
            displayStatus();
        }

        if (!player.death()) {
            System.out.println("Player lost the battle!");
            System.out.println("Game over!");
            System.exit(0);
        } else if (enemies.stream().noneMatch(Enemies::isAlive)) {
            System.out.println("Player wins the battle!");
            rewardPlayer();
        }

        stopBGM();
    }


    public void playBGM() {
        String[] bgmFiles = { "bgm.wav", "bgm2.wav", "bgm3.wav" };

        String chosenBGM = bgmFiles[rand.nextInt(bgmFiles.length)];

        URL bgmURL = getClass().getClassLoader().getResource("SE/" + chosenBGM);
        if (bgmURL != null) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bgmURL);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(audioInputStream);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
                bgmClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing BGM: " + e.getMessage());
            }
        } else {
            System.err.println("BGM file not found: " + chosenBGM);
        }
    }

    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    public void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an action:");
        System.out.println("1. Attack");
        System.out.println("2. Use Item");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Choose a weapon:");
                int i = 1;
                for (Weapon weapon : player.weapon) {
                    System.out.println(i + ". " + weapon.name + " (Damage: " + weapon.damage + ")");
                    i++;
                }
                int weaponChoice = scanner.nextInt();
                scanner.nextLine();
                if (weaponChoice > 0 && weaponChoice <= player.weapon.size()) {
                    Weapon chosenWeapon = (Weapon) player.weapon.toArray()[weaponChoice - 1];
                    attackEnemy(chosenWeapon);
                } else {
                    System.out.println("Invalid weapon choice.");
                }
                break;

            case 2:
                useItem();
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    public void soundPlayer(String fileName) {
        URL soundURL = getClass().getClassLoader().getResource("SE/" + fileName);
        if (soundURL != null) {
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL)) {
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        } else {
            System.err.println("Sound file not found: " + fileName);
        }
    }

    public void attackEnemy(Weapon weapon) {
        System.out.println("Choose an enemy to attack:");
        int i = 1;
        for (Enemies enemy : enemies) {
            if (enemy.isAlive()) {
                System.out.println(i + ". " + enemy.name + " (HP: " + enemy.hp + ")");
                i++;
            }
        }
        Scanner scanner = new Scanner(System.in);
        int enemyChoice = scanner.nextInt();
        scanner.nextLine();
        if (enemyChoice > 0 && enemyChoice <= enemies.size() && enemies.get(enemyChoice - 1).isAlive()) {
            Enemies chosenEnemy = enemies.get(enemyChoice - 1);
            chosenEnemy.hp -= weapon.damage;
            System.out.println(player.name + " attacks " + chosenEnemy.name + " with " + weapon.name + " for " + weapon.damage + " damage!");
            if (weapon.name.equalsIgnoreCase("Wooden stick")) {
                soundPlayer("att1Plyr.wav");
            } else if (weapon.name.equalsIgnoreCase("Wooden sword")) {
                soundPlayer("att2Plyr.wav");
            } else if (weapon.name.equalsIgnoreCase("Copper sword")) {
                soundPlayer("att3Plyr.wav");
            } else if (weapon.name.equalsIgnoreCase("Iron sword")) {
                soundPlayer("att4Plyr.wav");
            }
        } else {
            System.out.println("Invalid enemy choice.");
        }
    }

    public void useItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an item from your inventory to use:");

        int i = 1;
        for (Object item : player.inv) {
            if (item instanceof Crop) {
                System.out.println(i + ". " + ((Crop) item).name);
            }
            i++;
        }

        int itemChoice = scanner.nextInt();
        scanner.nextLine();
        if (itemChoice > 0 && itemChoice <= player.inv.size()) {
            Object item = player.inv.get(itemChoice - 1);
            if (item instanceof Potatoes) {
                player.health += 10;
                System.out.println("Used Potatoes! Healed 10 HP.");
            } else if (item instanceof Carrot) {
                player.health += 5;
                System.out.println("Used Carrot! Healed 5 HP.");
            } else if (item instanceof Cabbage) {
                player.health += player.health * 0.1;
                System.out.println("Used Cabbage! Healed 10% of max HP.");
            }
            player.inv.remove(item);
        } else {
            System.out.println("Invalid item choice.");
        }
    }

    public void enemyTurn() {
        for (Enemies enemy : enemies) {
            if (enemy.isAlive()) {
                if (enemy instanceof Pest) {
                    ((Pest) enemy).performAction(player);
                } else if (enemy instanceof Pest2) {
                    ((Pest2) enemy).performAction(player);
                } else {
                    System.out.println(enemy.name + " attacks " + player.name + " for " + enemy.att + " damage!");
                    player.health -= enemy.att;
                }
            }
        }
    }

    public void displayStatus() {
        System.out.println("\nPlayer health: " + player.health);
        for (Enemies enemy : enemies) {
            if (enemy.isAlive()) {
                System.out.println(enemy.name + " health: " + enemy.hp);
            }
        }
    }

    public void rewardPlayer() {
        int reward = rand.nextInt(30) + 20;
        player.money += reward;
        System.out.println("Player received " + reward + " coins for defeating the enemies!");
    }
}
