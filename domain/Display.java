package domain;

/**
 * This class will act as the Display configuration manager in a further release.
 * It's still on developement.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class Display {

    //
    // Fields
    //
    /**
     * The Display instance itself
     */
    private static Display display;

    //
    // Constructors
    //
    private Display() {
    }

    /**
     * This method is used to get a Display instance following the Single-Town model
     *
     * @return a Display instance.
     */
    protected static Display instantiate() {

        if (display == null) {
            display = new Display();
        }

        return display;
    }
}
