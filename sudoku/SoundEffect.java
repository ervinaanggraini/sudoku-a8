package sudoku;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundEffect {

    public static void playSound(String soundFileName) {
        try {
            // Akses file suara dari folder 'resources' yang terletak di luar 'sudoku'
            URL soundFileURL = SoundEffect.class.getClassLoader().getResource("resources/" + soundFileName);
            
            if (soundFileURL == null) {
                System.out.println("Sound file not found: " + soundFileName);
                return;
            }

            // Membuka dan memutar file suara
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFileURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
