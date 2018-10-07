
package domain.interfaces;

import domain.Collisions.CollisionDirection;
import domain.Collisions.CollisionType;

/**
 * This subinterface inherits the Actionable methods. The Reactionable type represents
 * a game entity wich will be able to process a reaction given a CollisionType and it's
 * CollisionDirection. This means that the Reactable instance should have a collection
 * of ReactedAction instances implemented.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface Reactable extends Actionable{

    /**
     * Both of the CollisionType and CollisionDirection should match by the ReactedAction
     * causable parameters to be executed.
     *
     * @param collisionType The CollisionType from wich the ReactedAction will check
     * before being executed (if it finally has to do)
     * @param collisionDirection The CollisionDirection from wich the ReactedAction will check
     * before being executed (if it finally has to do)
     */
    public void processReaction(CollisionType collisionType, CollisionDirection collisionDirection);
}
