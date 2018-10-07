
package domain.interfaces;

import domain.Collisions;
import domain.Sprite;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This interface defines the model that a collisionable game entity should have
 * to get its collisions managed by the Collisions class.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface Collisionable {

    /**
     * This method should return the 'world' collisionable upper-left corner coordinates.
     *
     * @return The Scenario relative Point.
     */
    public Point getScenarioCoordinates();
    /**
     * This method should return the visible area for this collisionable instance.
     *
     * @return A Rectnalge instance representing the current visible area for a Collisionable
     * instance.
     */
    public Rectangle getVisibleArea();
    /**
     * The zIndex indicates wich is the superposition value between collisionables.
     * A smaller value indicates it's behind, while a bigger one indicates it's in front.
     *
     * @return a int value indicating the superposition.
     */
    public int getZIndex();
    /**
     * This method should return the bounds of a Collisionable instance.
     *
     * @return a Rectangle instance representing the Collisionable bounds.
     */
    public Rectangle getCollisionableArea();
    /**
     * This method should return other collisionable areas of the Collisionable
     * instance wich are not its bounding box (getCollisionableArea()).
     *
     * @return Other Rectangle instances wich are not the bounding box.
     */
    public Rectangle[] getCollisionableAreas();
    /**
     * This method should implement the Collision processing.
     *
     * @param sprite The causable Sprite instance wich caused the collision
     * @param type The CollisionType caused.
     */
    public void receiveCollisionFromSprite(Sprite sprite, Collisions.CollisionType type);

    /**
     * This method should implement the Collision processing.
     *
     * @param sprite The causable Sprite instance wich caused the collision
     * @param type The CollisionType caused.
     * @param direction The CollisionDirection from wich the collision has been received.
     */
    public void receiveCollisionFromSprite(Sprite sprite, Collisions.CollisionType type, Collisions.CollisionDirection direction);

}
