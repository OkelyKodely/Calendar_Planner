package music;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

    public class Player {
AudioInputStream audioStream = null;        private Clip audioClip = null;
        private boolean playOn;
        public Player(boolean playOn) {
            this.playOn = playOn;
        }
        public void start() {
            try { //P
                       File audioFile = new File("theme.wav");
                       audioStream = AudioSystem.getAudioInputStream(audioFile);

                       AudioFormat format = audioStream.getFormat();

                       DataLine.Info info = new DataLine.Info(Clip.class, format);
                       audioClip = (Clip) AudioSystem.getLine(info);
                       audioClip.open(audioStream);
            } catch(Exception e) { }
        }
        public void toggleMusic() {
        if(playOn)
                            try {

                   audioClip.loop(Clip.LOOP_CONTINUOUSLY);
                   audioClip.start();
               } catch (Exception e) {

               }
        else

                            try {

                                audioClip.stop();
               } catch (Exception e) {

               }
                    playOn = !playOn;
        }
    }
