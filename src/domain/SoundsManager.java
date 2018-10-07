
package domain;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class is the responsible of managing the sounds of the game.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class SoundsManager {

    //
    // Fields
    //
    /**
     * The SoundsManager instance itself.
     */
    private static SoundsManager soundsManager;
    /**
     * A HashMap containing all the AudioClips referenced by its name.
     */
    private HashMap<String, AudioClip> audioClips;
    /**
     * A HashMap containing all the Clips referenced by its name.
     */
    private HashMap<String, Clip> clips;

    //
    // Constructors
    //
    /**
     * The default constructor. Initializes the audioClips and the clips maps.
     */
    private SoundsManager() {

        audioClips = new HashMap<String, AudioClip>();
        clips = new HashMap<String, Clip>();

    }

    /**
     * This method is used to get an SoundsManager instance following the Single-Town model
     *
     * @return an SoundsManager instance.
     */
    public static SoundsManager instantiate() {

        if (soundsManager == null) {
            soundsManager = new SoundsManager();
        }

        return soundsManager;
    }

    /**
     * This method loads an AudioClip instance. If the AudioClip can't be loaded,
     * then the audioClips HashMap is unchanged.
     *
     * @param soundName The name that will be assigned to the sound at the audioClips
     * hashmap.
     * @param url The Url instance from wich to get the new AudioClip
     */
    public void loadAudioClip(String soundName, URL url) {

        AudioClip clip = Applet.newAudioClip(url);

        if (clip == null) {
            System.out.println("Warnign! The clip " + url.getFile() + "was not found! Not loaded.");
        } else {
            audioClips.put(soundName, clip);
        }

    }

    /**
     * This method loads a Clip instance from a given File.
     *
     * @param soundName The key value that the sound will have in the HashMap
     * @param file The File containing audio.
     */
    public void loadClip(String soundName, File file) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            AudioFormat format = audioStream.getFormat();
            
            if ((format.getEncoding() == AudioFormat.Encoding.ULAW) ||
                    (format.getEncoding() == AudioFormat.Encoding.ALAW)) {
                AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                        format.getSampleRate(),
                        format.getSampleSizeInBits() * 2,
                        format.getChannels(),
                        format.getFrameSize() * 2,
                        format.getFrameRate(), true);

                audioStream = AudioSystem.getAudioInputStream(newFormat, audioStream);
                System.out.println("Converted Audio format: " + newFormat);
                format = newFormat;
            }


            DataLine.Info audioInfo = new DataLine.Info(Clip.class, format);

            if (!AudioSystem.isLineSupported(audioInfo)) {
                System.out.println("Unsupported Clip File: " + file.getName());
            }


            final Clip clip = (Clip) AudioSystem.getLine(audioInfo);

            LineListener lineListener = new LineListener() {

                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        System.out.println("Line stopped");
                        System.out.println(clip.getFramePosition());
                        clip.stop();
                        clip.setFramePosition(0);
                        System.out.println(clip.getFramePosition());
//                        try {
//                            clip.wait();
//                        } catch (InterruptedException ex) {
//                            System.out.println("Cannot make Line waiting");
//                        }
                    }
                    if (event.getType() == LineEvent.Type.START) {
                        System.out.println("Line started");

                        try {
                            clip.start();
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                }
            };

            clip.addLineListener(lineListener);

            clip.open(audioStream);
            audioStream.close();

            double duration = clip.getMicrosecondLength() / 1000000.0;
            System.out.println("Duration: " + duration + " secs");

            clips.put(soundName, clip);

        } catch (UnsupportedAudioFileException unsEx) {
            System.out.println("The audio format for " + file.getName() + " is not supported. Not loading.");
        } catch (IOException ioEx) {
            System.out.println("Error reading file " + file.getName() + ". Aborting. Not Loading.");
        } catch (LineUnavailableException linEx) {
            System.out.println("The Line was unavaible or it's in use by another process.");            
        }
    }

    /**
     * This method plays an AudioClip.
     *
     * @param soundName The key in the audioClips HashMap.
     * @param looping A boolean value determining wether if the sound should be
     * looping.
     */
    public void playAudioClip(String soundName, boolean looping) {

        AudioClip clip = audioClips.get(soundName);

        if (clip == null) {
            System.out.println("AudioClip " + soundName + " not found.");
        } else if (looping) {
            clip.loop();
        } else {
            clip.play();
        }
    }

    /**
     * This method is used to get an AudioClipPlayer instance.
     *
     * @param soundName The key for the desired sound to be managed by the AudioClipPlayer instance.
     *
     * @return an AudioClipPlayer instance managing the desired AudioClip.
     */
    protected AudioClipPlayer getAudioClipPlayer(String soundName) {

        AudioClip clip = audioClips.get(soundName);

        if (clip == null) {
            System.out.println("Warning! " + soundName + "Not found. clip is null");
        }

        return new AudioClipPlayer(clip);
    }

    /**
     * This method is used to get an ClipPlayer instance.
     *
     * @param soundName The key for the desired Clip to be managed by the ClipPlayer instance.
     *
     * @return a ClipPlayer instance managing the desired Clip.
     */
    protected ClipPlayer getClipPlayer(String soundName) {

        Clip clip = clips.get(soundName);

        if (clip == null) {
            System.out.println("Warning! " + soundName + "Not found. clip is null");
        }

        return new ClipPlayer(clip);
    }

    /**
     * This nested class is the responsible of managing the AudioClip instances.
     */
    protected class AudioClipPlayer {

        //
        // Fields
        //
        /**
         * The AudioClip instance that this AudioClipPlayer will manage.
         */
        private AudioClip audioClip;

        /**
         * Constructor. It initializes the audioClip field.
         *
         * @param clip an AudioClip instance.
         */
        protected AudioClipPlayer(AudioClip clip) {

            this.audioClip = clip;
        }

        /**
         * This method plays the AudioClip. It does nothing if audioClip is null.
         */
        public void playAudioClip() {
            if (audioClip != null) {
                audioClip.play();
            }
        }

        /**
         * This method loops the AudioClip. It does nothing if audioClip is null.
         */
        public void loopAudioClip() {
            if (audioClip != null) {
                audioClip.loop();
            }
        }

        /**
         * This method stops the AudioClip. It does nothing if audioClip is null.
         */
        public void stopAudioClip() {
            if (audioClip != null) {
                audioClip.stop();
            }
        }
    }

    /**
     * This nested class is the responsible of managing the Clip instances.
     */
    protected class ClipPlayer {

        //
        // Fields
        //
        private Clip clip;

        /**
         * Constructor.  It initializes the clip field.
         *
         * @param clip
         */
        protected ClipPlayer(Clip clip) {

            this.clip = clip;
        }

        /**
         * This method plays the Clip. It does nothing if clip is null.
         */
        public void playClip() {
            if (clip != null) {
                
                clip.start();

            }
        }

        /**
         * This method loops the Clip. It does nothing if clip is null.
         */
        public void loopClip() {
            if (clip != null) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }

        /**
         * This method loops the Clip for a determinated times. It does nothing if clip is null.
         */
        public void loopClip(int loopTimes) {
            if (clip != null) {
                clip.loop(loopTimes);
            }
        }

        /**
         * This method stops the Clip. It does nothing if clip is null.
         */
        public void stopClip() {
            if (clip != null) {
                clip.stop();
            }
        }
    }
}
