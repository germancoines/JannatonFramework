
package domain.interfaces;

import domain.Collisions;

/**
 * This is a subinterface of Action. It represents an Action instance wich it's
 * execution will be the consequence of a Collision or other event kind.
 *
 * A ReactedAction is an Action wich will be executed as a consequence of another
 * event kind (A Collision, for example).
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface ReactedAction extends Action{

    /**
     *
     * @return the CollisionDirection at wich the implemented ReactedAction will react.
     */
    public Collisions.CollisionDirection getDirectionReactionable();

    /**
     *
     * @return the CollisionType types at wich the ReactedAction will react.
     */
    public Collisions.CollisionType[] getCollisionTypesReactionable();

}
