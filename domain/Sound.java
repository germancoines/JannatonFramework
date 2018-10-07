package domain;

/**
 * This class will act as the Sound configuration manager in a further release.
 * It's currently on developement.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class Sound {
    //
    // Fields
    //

    /**
     * The Sound instance itself.
     */
    private static Sound sound;

    //
    // Constructors
    //
    /**
     * The default constructor
     */
    private Sound() {
    }

    /**
     * This method is used to get a Sound instance following the Single-Town model
     *
     * @return a Sound instance.
     */
    protected static Sound instantiate() {

        if (sound == null) {
            sound = new Sound();
        }

        return sound;
    }
}
