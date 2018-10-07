package domain;

import domain.Collisions.CollisionDirection;
import domain.Collisions.CollisionType;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import persistence.State;

/**
 * This class represents the game Scenario. It will be the "background image".
 * Be care when implementing collisions between other Collisionable instances and
 * the Scenario. Since the <code>Sprite.getCollisionableArea()</code> returns a
 * Rectangle wich represents the whole dimension of a Sprite, if an intersection
 * collision check is made with any Collisionable instance wich is inside the
 * Scenario the result will be true. To keep Scenario collision checkings going on
 * as well as it's needed you will find special methods in the Collisions class.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 *
 * @see Collisions#checkScenarioLimitsCollision(domain.interfaces.Collisionable, domain.Collisions.CollisionDirection, domain.Scenario)
 * @see Collisions#checkSurfaceCollisionablesCollision(java.awt.Rectangle, domain.Collisions.CollisionDirection, java.awt.Rectangle)
 * @see Collisions#checkScenarioCollisionableAreasCollision(domain.interfaces.Collisionable, domain.Collisions.CollisionDirection, domain.Scenario)
 * @see Collisions#checkScenarioFallingCollisionPlatformGame(domain.interfaces.Collisionable, domain.Scenario)
 */
public class Scenario extends Inanimated {

    //
    // Fields
    //
    /**
     * The north limit
     */
    private int northLimit;
    /**
     * The west limit
     */
    private int westLimit;
    /**
     * The south limit
     */
    private int southLimit;
    /**
     * The east limit
     */
    private int eastLimit;

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex,
     * Rectangle[] collisionableAreas);</code>.
     * The limits are initialized using the given Point (upper-left corner, so the north and west limits are given),
     * the width (the east limit is given) and the height (the south limit is given)
     *
     * @param relativeToScreen The point at wich the upper-left Scenario sprite corner will be screen allocated.
     * @param width The width of the Scenario sprite
     * @param height The height of the Scenario sprite
     * @param zIndex The zIndex of the Scenario sprite
     * 
     */
    public Scenario(Point relativeToScreen, int width, int height, int zIndex) {
        super(relativeToScreen, width, height, zIndex, new Rectangle[0]);
        northLimit = relativeToScreen.x;
        eastLimit = relativeToScreen.x + width;
        southLimit = relativeToScreen.y + height;
        westLimit = relativeToScreen.x;
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex,
     * Rectangle[] collisionableAreas);</code>.
     * The limits are initialized using the given Point (upper-left corner, so the north and west limits are given),
     * the width (the east limit is given) and the height (the south limit is given)
     *
     * @param relativeToScreen The point at wich the upper-left Scenario sprite corner will be screen allocated.
     * @param width The width of the Scenario sprite
     * @param height The height of the Scenario sprite
     * @param zIndex The zIndex of the Scenario sprite
     * @param north The north limit
     * @param east The east limit
     * @param south The south limit
     * @param west The west limit
     */
    public Scenario(Point relativeToScreen, int width, int height, int zIndex,
            int north, int east, int south, int west) {
        super(relativeToScreen, width, height, zIndex, new Rectangle[0]);
        northLimit = north;
        eastLimit = east;
        southLimit = south;
        westLimit = west;
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex,
     * Rectangle[] collisionableAreas);</code>.
     * The limits are initialized using the given Point (upper-left corner, so the north and west limits are given),
     * the width (the east limit is given) and the height (the south limit is given)
     *
     * @param relativeToScreen The point at wich the upper-left Scenario sprite corner will be screen allocated.
     * @param width The width of the Scenario sprite
     * @param height The height of the Scenario sprite
     * @param zIndex The zIndex of the Scenario sprite
     * @param collisionableAreas The Scenario sprite collisionableAreas
     */
    public Scenario(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, width, height, zIndex, collisionableAreas);
        northLimit = relativeToScreen.x;
        eastLimit = relativeToScreen.x + width;
        southLimit = relativeToScreen.y + height;
        westLimit = relativeToScreen.x;
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex,
     * Rectangle[] collisionableAreas);</code>.
     * The limits are initialized using the given Point (upper-left corner, so the north and west limits are given),
     * the width (the east limit is given) and the height (the south limit is given)
     *
     * @param relativeToScreen The point at wich the upper-left Scenario sprite corner will be screen allocated.
     * @param width The width of the Scenario sprite
     * @param height The height of the Scenario sprite
     * @param zIndex The zIndex of the Scenario sprite
     * @param collisionableAreas The Scenario sprite collisionableAreas
     * @param north The north limit
     * @param east The east limit
     * @param south The south limit
     * @param west The west limit
     */
    public Scenario(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas,
            int north, int east, int south, int west) {
        super(relativeToScreen, width, height, zIndex, collisionableAreas);
        northLimit = relativeToScreen.x;
        eastLimit = relativeToScreen.x + width;
        southLimit = relativeToScreen.y + height;
        westLimit = relativeToScreen.x;
    }

    /**
     * The persistence will be implemented in a further release.
     */
    public void loadFromState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * The persistence will be implemented in a further release.
     */
    public void saveToState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * The persistence will be implemented in a further release.
     */
    public void updateFromState(State state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * The persistence will be implemented in a further release.
     */
    public State getState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @return The current northLimit for the Scenario
     */
    public int getNorthLimit() {
        return northLimit;
    }

    /**
     *
     * @return The current eastLimit for the Scenario
     */
    public int getEastLimit() {
        return eastLimit;
    }

    /**
     *
     * @return The current southLimit for the Scenario
     */
    public int getSouthLimit() {
        return southLimit;
    }

    /**
     *
     * @return The current westLimit for the Scenario
     */
    public int getWestLimit() {
        return westLimit;
    }

    /**
     * This method sets the northLimit
     *
     * @param newNorthLimit the new northLimit
     */
    public void setNorthLimit(int newNorthLimit) {
        northLimit = newNorthLimit;
    }

    /**
     * This method sets the eastLimit
     *
     * @param newEastLimit the new eastLimit
     */
    public void setEastLimit(int newEastLimit) {
        eastLimit = newEastLimit;
    }

    /**
     * This method sets the southLimit
     *
     * @param newSouthLimit the new southLimit
     */
    public void setSouthLimit(int newSouthLimit) {
        southLimit = newSouthLimit;
    }

    /**
     * This method sets the westLimit
     *
     * @param newWestLimit the new westLimit
     */
    public void setWestLimit(int newWestLimit) {
        westLimit = newWestLimit;
    }

    /**
     * This method actually does nothing. If an operation needs to be performed
     * if the scenario receives a collision, the class should be inherited an the
     * method overriden.
     *
     * @param sprite The Sprite instace causing the collision.
     * @param type The CollisionType caused
     */
    public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
    }

    /**
     * This method actually does nothing. If an operation needs to be performed
     * if the scenario receives a collision, the class should be inherited an the
     * method overriden.
     *
     * @param sprite The Sprite instace causing the collision.
     * @param type The CollisionType caused
     * @param direction  The CollisionDirection in wich the collision is caused.
     */
    public void receiveCollisionFromSprite(Sprite sprite, CollisionType type, CollisionDirection direction) {
    }

    /**
     * The render method is overriden since a special Scenario rendering is needed
     * if the current Environment is an instance of a DinamicEnvironment.
     * That means the Scenario image is bigger than the game window and so on a special
     * rendering is required to get only a subimage fitting the window in order to
     * avoid the ineficiency caused by rendering the whole Scenario if it's too big.
     *
     * @param g The Graphics object wich will perform the rendering.
     */
    @Override
    public void render(Graphics g) {

        if (environment instanceof StaticEnvironment) {
            super.render(g);
        } else if (environment instanceof DinamicEnvironment) {

            Dimension client = environment.clientScreen;

            Point playerPosition = environment.getPlayer().getScenarioCoordinates();
            Point playerScreenPosition = environment.getPlayer().getScreenCoordinates();
            Point clientWindowPosition = new Point(playerPosition.x - playerScreenPosition.x,
                    playerPosition.y - playerScreenPosition.y);

//            for(Animated sprite : environment.getOthers()){
//                sprite.xIncrement = -environment.getPlayer().xIncrement;
//                sprite.yIncrement = -environment.getPlayer().yIncrement;
//
//                //sprite.pointRelativeToScreen = sprite.pointRelativeToScenario;
//            }

            g.drawImage(image, 0, 0, client.width, client.height,
                    clientWindowPosition.x, clientWindowPosition.y, 
                    (clientWindowPosition.x + client.width),
                    (clientWindowPosition.y + client.height), null);
        }

    }
}
