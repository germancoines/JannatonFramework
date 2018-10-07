
package domain.interfaces;

import domain.Collisions;

/**
 * This abstract interface define the generic model for a Action in the game.
 * 
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract interface Action {

    /**
     *
     * @return The name for that Action instance.
     */
    public String getActionName();

    /**
     *
     * @return The CollisionType that this action can produce
     *
     */
    public Collisions.CollisionType getCollisionTypeCausable();

    /**
     * This method will have the action execution instructions.
     */
    public void executeAction();

    /**
     * This method will be invoked after the executeAction method has been executed
     * by the GameClient.
     */
    public void finishAction();
    
}
