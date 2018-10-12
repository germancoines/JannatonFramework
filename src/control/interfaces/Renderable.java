
package control.interfaces;

import java.awt.Graphics;

/**
 * This interface should be implemented by any Class that have to be rendered by
 * a GameEngine instance. The operations implemented in the render method will
 * be repeteadly invoked while the GameEngine is running.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface Renderable {

    /**
     * NEVER IMPLEMENT INFINITE LOOPS OR INDEPENDENT THREADS IN THIS METHOD.
     *
     * Doing that will cause the GameEngine entering in a non finite loop and
     * that may cause several problems.
     *
     * It's not recomended to implement logical operations in the render method.
     * @param gr Graphics object
     */
    public void render(Graphics gr);

}
