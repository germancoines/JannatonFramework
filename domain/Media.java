/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class acts as the media manager for all the Sprite instances of the game.
 * It's the responsible of carrying the image and sound petitions from a Sprite.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class Media {

    //
    // Fields
    //
    /**
     * The Media instance itself.
     *
     * @see #instantiate()
     */
    private static Media media;
    /**
     * The ImagesManager instance associated to the Media instance, from wich it
     * will get any image resources or an ImagesAnimator object.
     */
    private static ImagesManager imagesManager;
    /**
     * The SoundsManager instance associated to the Media instance, from wich it
     * will get any AudioClipPlayer or ClipPlayer instances.
     */
    private static SoundsManager soundManager;

    //
    // Constructors.
    //
    /**
     * The default constructor. It initializes the imagesManager and sounsManager
     * fields.
     *
     * @see ImagesManager#instantiate()
     * @see SoundsManager#instantiate()
     */
    private Media() {

        if (imagesManager == null) {
            imagesManager = ImagesManager.instantiate();
        }

        if (soundManager == null) {
            soundManager = SoundsManager.instantiate();
        }

    }

    /**
     * This method is used to get a Media instance following the Single-Town model
     *
     * @return a Media instance.
     */
    public static Media instantiate() {

        if (media == null) {
            media = new Media();
        }

        return media;
    }

    /**
     * This method gets a simple BufferedImage from the ImagesManager instance.
     *
     * @param name the name of the stored image requested
     *
     * @return The BufferedImage instance associated to the name. null if the
     * imagesManger is null or if the ImagesManager haven't found the specified image.
     *
     * @see ImagesManager#getImage(java.lang.String)
     */
    protected BufferedImage getImage(String name) {
        if (imagesManager != null) {
            return imagesManager.getImage(name);
        } else {
            return null;
        }
    }

    /**
     * This method gets an ArrayList of BufferedImage from the ImagesManager instance.
     *
     * @param name the name of the stored images requested
     *
     * @return The ArrayList of BufferedImage instance associated to the name. null if the
     * imagesManger is null or if the ImagesManager haven't found the specified ArrayList.
     *
     * @see ImagesManager#getImages(java.lang.String) 
     */
    protected ArrayList<BufferedImage> getImages(String name) {
        if (imagesManager != null) {
            return imagesManager.getImages(name);
        } else {
            return null;
        }
    }

    /**
     * This method is used to get an ImageAnimator with a sequence of images
     *
     * @param sprite The Sprite asking for the ImagesAnimator instance
     * @param imagesName The name specifying the desired set of images
     * @param animationPeriod The period that indicates how often the ImagesAnimator
     * setImage() method will be invoked.
     * @param sequenceDuration The total amount of time that a sequence should take
     * @param isRepeating A boolean value indicating if the ImagesAnimator should restart
     * the sequence when it reaches or passes the end of it.
     *
     * @return an ImagesAnimator instance with a sequence of images loaded. Or null if the images
     * were not found or the imagesManager is null.
     *
     * @see ImagesManager#getAnimator(domain.Sprite, java.lang.String, int, double, boolean) 
     */
    protected ImagesManager.ImagesAnimator getAnimator(Sprite sprite, String imagesName, int animationPeriod,
            double sequenceDuration, boolean isRepeating) {
        if (imagesManager != null) {
            return imagesManager.getAnimator(sprite, imagesName, animationPeriod, sequenceDuration, isRepeating);
        } else {
            return null;
        }
    }

    /**
     * This method is used to get an AudioClipPlayer instance
     *
     * @param soundName The name associated to the AudioClip instance that is wanted
     * to be managed by the AudioClipPlayer instance.
     *
     * @return an AudioClipPlayer with an AudioClip instance loaded. null if the sound
     * is not found or if the soundManager is null.
     *
     * @see SoundsManager#getAudioClipPlayer(java.lang.String)
     */
    protected SoundsManager.AudioClipPlayer getAudioClipPlayer(String soundName) {
        if (soundManager != null) {
            return soundManager.getAudioClipPlayer(soundName);
        } else {
            return null;
        }
    }

    /**
     * This method is used to get an ClipPlayer instance
     *
     * @param soundName The name associated to the Clip instance that is wanted
     * to be managed by the ClipPlayer instance.
     *
     * @return an ClipPlayer with a Clip instance loaded. null if the sound
     * is not found or if the soundManager is null.
     *
     * @see SoundsManager#getClipPlayer(java.lang.String) 
     */
    protected SoundsManager.ClipPlayer getClipPlayer(String soundName) {
        if(soundManager != null)
            return soundManager.getClipPlayer(soundName);
        else
            return null;
    }
}
