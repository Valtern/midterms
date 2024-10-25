import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;

public class Enemies {
    String name;
    int hp;
    int def;
    int att;

    Enemies(String name, int hp, int def, int att) {
        this.name = name;
        this.hp = hp;
        this.def = def;
        this.att = att;
    }
    public boolean isAlive() {
        return hp > 0;
    }

    public void performAction(Player player) {
        int attChoose;
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



}
