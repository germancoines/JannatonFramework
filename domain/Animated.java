
package domain;

import domain.Collisions.CollisionDirection;
import domain.interfaces.Movable;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * This Specialitzation class represents a Sprite wich has the ability of moving.
 * That's why it implements the Movable interface and it's methods.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract class Animated extends Sprite implements Movable {

    //
    // Fields
    //
    /**
     * The increment that should be added in each update to the x relativeToScenario
     * coordinate.
     */
    protected int xIncrement;
    /**
     * The increment that should be added in each update to the y relativeToScenario
     * coordinate.
     */
    protected int yIncrement;
    /**
     * This field holds the last time the Animated sprite was or has moved.
     */
    private long lastMovementTime;
    /**
     * This field holds a move interval in milliseconds. A movement will be performed
     * at each moveInterval. It can't have a negative value.
     */
    private int moveInterval;

    //
    // Constructors
    //
    /**
     * Default constructor. It calls <code>super();</code> and initializes the
     * lastMovementTime field.
     */
    protected Animated() {
        super();
        this.lastMovementTime = System.currentTimeMillis();
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex);</code>
     * and initializes the lastMovementTime field.
     *
     * @param relativeToScreen The point at wich the upper-left animated sprite corner will be screen allocated.
     * @param width The width of the Animated sprite
     * @param height The height of the Animated sprite
     * @param zIndex The zIndex of the Animated sprite
     */
    protected Animated(Point relativeToScreen, int width, int height, int zIndex) {
        super(relativeToScreen, width, height, zIndex);
        this.lastMovementTime = System.currentTimeMillis();
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex,
     * Rectangle[] collisionableAreas);</code>
     * and initializes the lastMovementTime field.
     *
     * @param relativeToScreen The point at wich the upper-left animated sprite corner will be screen allocated.
     * @param width The width of the Animated sprite
     * @param height The height of the Animated sprite
     * @param zIndex The zIndex of the Animated sprite
     * @param collisionableAreas The Animated sprite collisionableAreas
     */
    protected Animated(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, width, height, zIndex, collisionableAreas);
        this.lastMovementTime = System.currentTimeMillis();
        //this.moveInterval = moveInterval;
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, Point relativeToEnvironment, int width, int height,
     * int zIndex);</code>
     * and initializes the lastMovementTime field.
     *
     * @param relativeToScreen The point at wich the upper-left animated sprite corner will be screen allocated.
     * @param relativeToScenario The point at wich the upper-left animated sprite corner will be scenario allocated.
     * @param width The width of the Animated sprite
     * @param height The height of the Animated sprite
     * @param zIndex The zIndex of the Animated sprite
     */
    protected Animated(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex) {
        super(relativeToScreen, relativeToScenario, width, height, zIndex);
        this.lastMovementTime = System.currentTimeMillis();
        //this.moveInterval = moveInterval;
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, Point relativeToEnvironment, int width, int height,
     * int zIndex);</code>
     * and initializes the lastMovementTime field.
     *
     * @param relativeToScreen The point at wich the upper-left animated sprite corner will be screen allocated.
     * @param relativeToScenario The point at wich the upper-left animated sprite corner will be scenario allocated.
     * @param width The width of the Animated sprite
     * @param height The height of the Animated sprite
     * @param zIndex The zIndex of the Animated sprite
     * @param collisionableAreas The Animated sprite collisionableAreas
     *
     */
    protected Animated(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, relativeToScenario, width, height, zIndex, collisionableAreas);
        this.lastMovementTime = System.currentTimeMillis();
        //this.moveInterval = moveInterval;
    }

    /**
     * This method sets the xIncrement value.
     *
     * @param xIncrement the new xIncrement that the Animated sprite will move
     */
    public void setXIncrement(int xIncrement) {
        this.xIncrement = xIncrement;
    }

    /**
     * This method sets the yIncrement value.
     *
     * @param yIncrement the new yIncrement that the Animated sprite will move
     */
    public void setYIncrement(int yIncrement) {
        this.yIncrement = yIncrement;
    }

    /**
     * Stop animated sprite. This method sets the xIncrement and yIncrement fields
     * to 0, causing the Animated sprite suddenly being stopped.
     *
     */
    public void stop() {
        this.xIncrement = 0;
        this.yIncrement = 0;
    }

    /**
     * This method sets the moveInterval field value. That's useful when you want
     * to make movements go slowly.
     *
     * @param speed The time interval in milliseconds at wich each move process
     * should be performed.
     */
    public void setSpeed(int speed) {
        this.moveInterval = speed;
    }

    /**
     * This method calculate the current Animated sprite direction using it's x and
     * y increments.
     *
     * @return The CollisionDirection that this Animated sprite
     * is currently producing.
     *
     * @see CollisionDirection
     */
    protected CollisionDirection examineCurrentDirection() {

        CollisionDirection direction = CollisionDirection.STANDING;

        if (xIncrement == 0 && yIncrement < 0) {
            direction = CollisionDirection.NORTH;
        } else if (xIncrement > 0 && yIncrement < 0) {
            direction = CollisionDirection.NORTH_EAST;
        } else if (xIncrement > 0 && yIncrement == 0) {
            direction = CollisionDirection.EAST;
        } else if (xIncrement > 0 && yIncrement > 0) {
            direction = CollisionDirection.SOUTH_EAST;
        } else if (xIncrement == 0 && yIncrement > 0) {
            direction = CollisionDirection.SOUTH;
        } else if (xIncrement < 0 && yIncrement > 0) {
            direction = CollisionDirection.SOUTH_WEST;
        } else if (xIncrement < 0 && yIncrement == 0) {
            direction = CollisionDirection.WEST;
        } else if (xIncrement < 0 && yIncrement < 0) {
            direction = CollisionDirection.NORTH_WEST;
        }

        return direction;
    }

    /**
     * This method is used to make the Animated instance moving taking care of the
     * Scenario Limits and collisionable areas. Depending on it's current direction,
     * both, x, y, or any increments will be applied. All the collisionable areas that
     * this Animated instance have will be translated to the new location too.
     *
     * @param direction The current direction of the Animated sprite.
     *
     * @see CollisionDirection
     * @see Collisions#checkScenarioCollisionableAreasCollision(domain.interfaces.Collisionable, domain.Collisions.CollisionDirection, domain.Scenario)
     * @see Collisions#checkScenarioLimitsCollision(domain.interfaces.Collisionable, domain.Collisions.CollisionDirection, domain.Scenario)
     */
    @SuppressWarnings("static-access")
    protected void moveCheckingScenarioCollisions(CollisionDirection direction) {

        if (direction.equals(CollisionDirection.NORTH) ||
                direction.equals(CollisionDirection.SOUTH)) {

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, direction, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, direction, environment.getScenario())) {
                this.pointRelativeToScenario.x += this.xIncrement;
                this.pointRelativeToScenario.y += this.yIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(this.xIncrement, this.yIncrement);
                }
            } else {
                this.pointRelativeToScenario.x += this.xIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(this.xIncrement, 0);
                }
            }
        } else if (direction.equals(CollisionDirection.WEST) ||
                direction.equals(CollisionDirection.EAST)) {

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, direction, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, direction, environment.getScenario())) {
                this.pointRelativeToScenario.x += this.xIncrement;
                this.pointRelativeToScenario.y += this.yIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(this.xIncrement, this.yIncrement);
                }
            } else {
                this.pointRelativeToScenario.y += this.yIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(0, this.yIncrement);
                }
            }
        } else if (direction.equals(CollisionDirection.NORTH_EAST)) {

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.NORTH, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.NORTH, environment.getScenario())) {
                this.pointRelativeToScenario.y += this.yIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(0, this.yIncrement);
                }
            }

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.EAST, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.EAST, environment.getScenario())) {
                this.pointRelativeToScenario.x += this.xIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(this.xIncrement, 0);
                }
            }

        } else if (direction.equals(CollisionDirection.NORTH_WEST)) {

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.NORTH, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.NORTH, environment.getScenario())) {
                this.pointRelativeToScenario.y += this.yIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(0, this.yIncrement);
                }
            }

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.WEST, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.WEST, environment.getScenario())) {
                this.pointRelativeToScenario.x += this.xIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(this.xIncrement, 0);
                }
            }

        } else if (direction.equals(CollisionDirection.SOUTH_EAST)) {

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.SOUTH, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.SOUTH, environment.getScenario())) {
                this.pointRelativeToScenario.y += this.yIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(0, this.yIncrement);
                }
            }

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.EAST, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.EAST, environment.getScenario())) {
                this.pointRelativeToScenario.x += this.xIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(this.xIncrement, 0);
                }
            }

        } else if (direction.equals(CollisionDirection.SOUTH_WEST)) {

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.SOUTH, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.SOUTH, environment.getScenario())) {                    //this.pointRelativeToScenario.x += this.xIncrement;
                this.pointRelativeToScenario.y += this.yIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(0, this.yIncrement);
                }
            }

            if (!environment.getPhisics().getCollisions().checkScenarioLimitsCollision(this, CollisionDirection.WEST, environment.getScenario()) &&
                    !environment.getPhisics().getCollisions().checkScenarioCollisionableAreasCollision(this, CollisionDirection.WEST, environment.getScenario())) {
                this.pointRelativeToScenario.x += this.xIncrement;
                for (Rectangle collisionableArea : collisionableAreas) {
                    collisionableArea.translate(this.xIncrement, 0);
                }
            }
        }
    }

    /**
     * This method makes the Animated instance moving. It checks if the moveInterval
     * has been passed since the last movement. If it has been, then the Animated
     * will move taking care of the Scenario limits and collisionable Areas.
     *
     * @see #moveCheckingScenarioCollisions(domain.Collisions.CollisionDirection)
     * @see #examineCurrentDirection()
     *
     */
    public void move() {

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastMovementTime > moveInterval) {

            CollisionDirection direction = examineCurrentDirection();

            moveCheckingScenarioCollisions(direction);

            lastMovementTime = currentTime;
        }
    }

    /**
     * This method overrides the <code>Sprite.update()</code>. It first
     * invoke <code>super.update()</code> to avoid the loss of super update processes
     * and then make a call to the move() method.
     *
     * @see Sprite#update()
     * @see #move() 
     *
     */
    @Override
    public void update() {

        super.update();
        move();

    }
}
