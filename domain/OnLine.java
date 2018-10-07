package domain;

/**
 * This class will act as an Online GameClient instance in further releases.
 * It's currently at developement.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class OnLine extends GameClient {

    //
    // Constructors
    //
    /**
     * This currently calls the super constructor.
     * Connection Listeners and Requesters and Methods will be implemented in further
     * releases of the Framework.
     */
    private OnLine() {
        super();
    }

    /**
     * This method is used to get a OnLine instance following the Single-Town model
     *
     * @return a Online instance (wich is a specialitzation of GameClient)
     */
    public static GameClient instantiate() {

        if (gameClient == null) {
            gameClient = new OnLine();
        }

        return gameClient;
    }
    /**
     * Implement connection Methods.
     */
}
