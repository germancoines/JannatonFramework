package domain;

/**
 * This class will act as an Offline GameClient instance.
 * It's State management processes are currently at developement.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class OffLine extends GameClient {

    //
    // Constructors
    //
    /**
     * This currently calls the super constructor.
     * State saving and loading will be implemented in the next release.
     */
    private OffLine() {
        super();
    }

    /**
     * This method is used to get a OffLine instance following the Single-Town model
     *
     * @return a Offline instance (wich is a specialitzation of GameClient)
     */
    public static GameClient instantiate() {

        if (gameClient == null) {
            gameClient = new OffLine();
        }

        return gameClient;
    }
    /**
     * Implement State management methods here.
     */
}
