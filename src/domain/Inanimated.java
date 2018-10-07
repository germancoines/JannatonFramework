
package domain;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * This Specialitzation class represents a Sprite wich will never have the ability
 * of moving. Not moving doesn't imply that the Inanimated can't set its position,
 * change it's image, playing sounds...
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract class Inanimated extends Sprite {

    //
    // All Fields are inherited
    //

    //
    // Constructors.
    //
    /**
     * Default Constructor. calls to <code>super()</code>
     */
    protected Inanimated() {
        super();
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex);</code>.
     *
     * @param relativeToScreen The point at wich the upper-left Inanimated sprite corner will be screen allocated.
     * @param width The width of the Inanimated sprite
     * @param height The height of the Inanimated sprite
     * @param zIndex The zIndex of the Inanimated sprite
     */
    protected Inanimated(Point relativeToScreen, int width, int height, int zIndex) {
        super(relativeToScreen, width, height, zIndex);
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex,
     * Rectangle[] collisionableAreas);</code>.
     *
     * @param relativeToScreen The point at wich the upper-left Inanimated sprite corner will be screen allocated.
     * @param width The width of the Inanimated sprite
     * @param height The height of the Inanimated sprite
     * @param zIndex The zIndex of the Inanimated sprite
     * @param collisionableAreas The Inanimated sprite collisionableAreas
     */
    protected Inanimated(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, width, height, zIndex, collisionableAreas);
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, Point relativeToEnvironment, int width, int height,
     * int zIndex);</code>.
     *
     * @param relativeToScreen The point at wich the upper-left Inanimated sprite corner will be screen allocated.
     * @param relativeToScenario The point at wich the upper-left Inanimated sprite corner will be scenario allocated.
     * @param width The width of the Inanimated sprite
     * @param height The height of the Inanimated sprite
     * @param zIndex The zIndex of the Inanimated sprite
     */
    protected Inanimated(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex) {
        super(relativeToScreen, relativeToScenario, width, height, zIndex);
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, Point relativeToEnvironment, int width, int height,
     * int zIndex);</code>.
     *
     * @param relativeToScreen The point at wich the upper-left Inanimated sprite corner will be screen allocated.
     * @param relativeToScenario The point at wich the upper-left Inanimated sprite corner will be scenario allocated.
     * @param width The width of the Inanimated sprite
     * @param height The height of the Inanimated sprite
     * @param zIndex The zIndex of the Inanimated sprite
     * @param collisionableAreas The Inanimated sprite collisionableAreas
     *
     */
    protected Inanimated(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, relativeToScenario, width, height, zIndex, collisionableAreas);
    }
}
