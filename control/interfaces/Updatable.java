

package control.interfaces;

/**
 * This interface should be implemented by any Class that have to be updated by
 * a GameEngine instance. The operations implemented in the update method will
 * be repeteadly invoked while the GameEngine is running.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface Updatable {

    /**
     * NEVER IMPLEMENT INFINITE LOOPS OR INDEPENDENT THREADS IN THIS METHOD.
     *
     * Doing that will cause the GameEngine entering in a non finite loop and
     * that may cause several problems.
     */
    public void update();

}
