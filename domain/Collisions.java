package domain;

import domain.interfaces.Action;
import domain.interfaces.Collisionable;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * This utility class implements methods for detecting collisions, detecting 
 * Collisionable instances in determinated areas, detecting visible Collisionable
 * instances for a determinated Collisionable, etc.
 *
 * @author Alberto Languiz Polo
 * @author German Coines Laguna
 */
public class Collisions {

    //
    //Fields
    //
    /**
     * The Collisions instance itself.
     * @see #instantiate(domain.Phisics)
     */
    private static Collisions collisions;

    /**
     * Enumeration containing various generic collision types
     */
    public static enum CollisionType {

        MOVING, JUMPING, FALLING, LANDING, ATTACKING, DEFENDING, CATCHING, DROPPING, USING, TALKING, LIMIT_REACHED,
        SCENARIO_COLLISIONABLE_AREA_REACHED, UNDEFINED
    };

    /**
     * Enumeration containing possible collision directions, where the ANY value can be used
     * to specify all of them.
     */
    public static enum CollisionDirection {

        NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, STANDING, ANY
    };
    /**
     * ArrayList containing all the collisionables from the Environment. Before
     * any collision check operation is made, an outdate test will be executed
     * to ensure that there are no empty or null references.
     *
     * @see #checkIfCollisionablesOutDated()
     */
    private static ArrayList<Collisionable> collisionables;
    /**
     * This field holds a boolean value wich indicates wether if the collisionables
     * ArrayList is outdated or not. This field is only changed by the method
     *
     * @see #checkIfCollisionablesOutDated()
     */
    private static boolean collisionablesOutdated = true;
    /**
     * A field holding the Phisics instance passed by parameter to the constructor.
     *
     * @see Collisions#Collisions(domain.Phisics)
     * @see Collisions#instantiate(domain.Phisics)
     */
    private static Phisics phisics;

    /**
     * Constructor. The phisics field will be initialized with the Phisics instance
     * passed by parameter. The collisionables field is initialized too.
     *
     * @param phisics The Phisics instance wich is invoking the Collisions object.
     *
     * @see #instantiate(domain.Phisics)
     */
    private Collisions(Phisics phisics) {

        this.phisics = phisics;

        collisionables = new ArrayList<Collisionable>();

    }

    /**
     * This method is used to get a Collisions instance following the Single-Town model
     *
     * @param phisics The Phisics instance wich is invoking the Collisions object.
     *
     * @return a Collisions instance.
     */
    protected static Collisions instantiate(Phisics phisics) {

        if (collisions == null) {
            collisions = new Collisions(phisics);
        }


        return collisions;
    }

    //
    //Collisions between Collisionables
    //
    /**
     * This method checks if two Collisionable objects intersects.
     *
     * First their zIndex are checked, and if they are equals their collisionable
     * areas are asked for intersection.
     *
     * @param collisionable1 the first Collisionable instance
     * @param collisionable2 the second Collisionable instance
     *
     * @return true if the Collisionables intersects. false otherwise.
     */
    public static boolean checkCollisionableIntersectionCollision(Collisionable collisionable1, Collisionable collisionable2) {

        if (collisionable1.getZIndex() == collisionable2.getZIndex()) {
            if (collisionable1.getCollisionableArea().intersects(collisionable2.getCollisionableArea())) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method checks if an Action executed by a Collisionable has collisioned with
     * any Collisionable instance from the collisionables ArrayList. If a collision is
     * produced, then the collisioned instance is told that it has received a collision.
     * This method first checks if the collisionables ArrayList is out dated.
     *
     * @param causable The Collisionable instance wich executed the action
     * @param action The Action executed
     *
     * @return true if the Action executed has produced an intersection collision
     * with any Collisionable instance from the collisionables ArrayList. false otherwise.
     *
     * @see #checkIfCollisionablesOutDated()
     * @see #tellCollisionToCollisionableCollisioned(domain.interfaces.Collisionable, domain.interfaces.Action, domain.interfaces.Collisionable) 
     */
    public static boolean checkCollisionableIntersectionCollision(Collisionable causable, Action action) {

        checkIfCollisionablesOutDated();

        Rectangle collisionableArea = causable.getCollisionableArea();
        int zIndex = causable.getZIndex();

        ArrayList<Collisionable> collisionCandidates = getVisibleCollisionables(causable);

        boolean collisioned = false;

        for (Collisionable col : collisionCandidates) {
            if (zIndex == col.getZIndex()) {
                if (collisionableArea.intersects(col.getCollisionableArea())) {
                    collisioned = true;
                    tellCollisionToCollisionableCollisioned(causable, action, col);
                }
            }
        }

        return collisioned;
    }

    /**
     * This method checks if a Collisionable wich is moving in a direction collisionate
     * with another.
     *
     * @param collisionable The Collisionable instance wich is moving
     * @param direction The current CollisionDirection of the collisionable
     * @param collisioned The Collisionable instance to examine for collision
     *
     * @return true if the collisionable wich is moving in the current direction intersects with the collisioned.
     * false otherwise.
     *
     * @see #checkCollisionableIntersectionCollision(domain.interfaces.Collisionable, domain.interfaces.Collisionable) 
     */
    public static boolean checkCollisionableIntersectionCollision(Collisionable collisionable, CollisionDirection direction, Collisionable collisioned) {

        Rectangle collisionableArea = collisionable.getCollisionableArea();
        Rectangle collisionedArea = collisioned.getCollisionableArea();


        switch (direction) {

            case NORTH:
                if (collisionableArea.y <= collisionedArea.y + collisionedArea.height) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
            case NORTH_EAST:
                if (collisionableArea.y <= collisionedArea.y + collisionedArea.height ||
                        collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
            case EAST:
                if (collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
            case SOUTH_EAST:
                if (collisionableArea.y + collisionableArea.height >= collisionedArea.y ||
                        collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
            case SOUTH:
                if (collisionableArea.y + collisionableArea.height >= collisionedArea.y) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
            case SOUTH_WEST:
                if (collisionableArea.y + collisionableArea.height >= collisionedArea.y ||
                        collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
            case WEST:
                if (collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
            case NORTH_WEST:
                if (collisionableArea.y <= collisionedArea.y + collisionedArea.height ||
                        collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                    if (checkCollisionableIntersectionCollision(collisionable, collisioned)) {
                        return true;
                    }
                }
                break;
        }

        return false;
    }

    /**
     * This method checks if any of the collisionable1 collisionableAreas intersects any of the collisionable2
     * collisionableAreas.
     *
     * @param collisionable1 the first Collisionable instance
     * @param collisionable2 the second Collisionable instance
     *
     * @return true if any of the collisionable1 collisionableAreas intersects any of the collisionable2.
     * false otherwise.
     */
    public static boolean checkCollisionableIntersectionPrecisionCollision(Collisionable collisionable1, Collisionable collisionable2) {

        if (collisionable1.getZIndex() == collisionable2.getZIndex()) {
            Rectangle[] rectangles1 = collisionable1.getCollisionableAreas();
            Rectangle[] rectangles2 = collisionable2.getCollisionableAreas();
            for (Rectangle rectangle1 : rectangles1) {
                for (Rectangle rectangle2 : rectangles2) {
                    if (rectangle1.intersects(rectangle2)) {
                        return true;
                    }
                }
            }

        }


        return false;
    }

    /**
     * This method checks if any of the collisionable1 collisionableAreas intersects any of the collisionable2
     * collisionableAreas at a specified direction.
     *
     * @param collisionable The Collisionable instance wich is moving
     * @param direction The current CollisionDirection of the collisionable
     * @param collisioned The Collisionable instance to examine for collision
     *
     * @return true if any of the collisionable collisionableAreas wich is moving in the current direction intersects
     * with the collisioned. false otherwise.
     */
    public static boolean checkCollisionableIntersectionPrecisionCollision(Collisionable collisionable, CollisionDirection direction, Collisionable collisioned) {

        Rectangle collisionableArea = collisionable.getCollisionableArea();
        Rectangle[] collisionedAreas = collisioned.getCollisionableAreas();

        for (Rectangle collisionedArea : collisionedAreas) {
            switch (direction) {

                case NORTH:
                    if (collisionableArea.y <= collisionedArea.y + collisionedArea.height) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
                case NORTH_EAST:
                    if (collisionableArea.y <= collisionedArea.y + collisionedArea.height ||
                            collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
                case EAST:
                    if (collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
                case SOUTH_EAST:
                    if (collisionableArea.y + collisionableArea.height >= collisionedArea.y ||
                            collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
                case SOUTH:
                    if (collisionableArea.y + collisionableArea.height >= collisionedArea.y) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
                case SOUTH_WEST:
                    if (collisionableArea.y + collisionableArea.height >= collisionedArea.y ||
                            collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
                case WEST:
                    if (collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
                case NORTH_WEST:
                    if (collisionableArea.y <= collisionedArea.y + collisionedArea.height ||
                            collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                        if (collisionableArea.intersects(collisionedArea)) {
                            return true;
                        }
                    }
                    break;
            }
        }

        return false;
    }

    /**
     * This method checks if an Action executed by a Collisionable has collisioned with
     * any Collisionable instance within the Tile tile. If a collision is
     * produced, then the collisioned instance is told that it has received a collision.
     *
     * @param causable The Collisionable instance wich executed the action
     * @param action The Action executed
     * @param tile The Tile instance wich is asked for an occupier
     *
     * @return true if the tile instance has an occupier. false otherwise.
     *
     * @see #checkTileCollision(domain.Tile)
     * @see #tellCollisionToCollisionableCollisioned(domain.interfaces.Collisionable, domain.interfaces.Action, domain.interfaces.Collisionable) 
     */
    public static boolean checkCollisionableTileCollision(Collisionable causable, Action action, Tile tile) {

        if (!checkTileCollision(tile)) {
            return false;
        }

        tellCollisionToCollisionableCollisioned(causable, action, (Collisionable) tile.getOcuppier());
        return true;
    }

    //
    //Collisions between a Collisionable and other Objects
    //
    /**
     * This method checks if a Collisionable contains a Point at his collisionableArea.
     *
     * @param c The collisionable instance to be asked for containment
     * @param p The Point instace requested for it's containment
     *
     * @return true if the Collisionable instance contains the Point.
     */
    public static boolean checkPointCollision(Collisionable c, Point p) {
        if (c.getCollisionableArea().contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used to check if a Collisionable instance is collisionating with
     * a Scenario instance limits. If it's at, or passes the limits it's told.
     *
     * @param collisionable The collisionable instance to check if it's out of the scenario limits.
     * @param direction The direction in wich the collisionable is moving.
     * @param scenario The Scenario instance from wich to get the limits
     *
     * @return true if the collisionable instance has reached or passes the Scenario limits
     * for the specified direction. false otherwise.
     */
    public static boolean checkScenarioLimitsCollision(Collisionable collisionable, CollisionDirection direction, Scenario scenario) {

        Rectangle collisionableArea = collisionable.getCollisionableArea();

        boolean collision = false;

        switch (direction) {

            case NORTH:
                if (collisionableArea.y <= scenario.getNorthLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
            case NORTH_EAST:
                if (collisionableArea.y <= scenario.getNorthLimit() ||
                        collisionableArea.x + collisionableArea.width >= scenario.getEastLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
            case EAST:
                if (collisionableArea.x + collisionableArea.width >= scenario.getEastLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
            case SOUTH_EAST:
                if (collisionableArea.y + collisionableArea.height >= scenario.getSouthLimit() ||
                        collisionableArea.x + collisionableArea.width >= scenario.getEastLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
            case SOUTH:
                if (collisionableArea.y + collisionableArea.height >= scenario.getSouthLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
            case SOUTH_WEST:
                if (collisionableArea.y + collisionableArea.height >= scenario.getSouthLimit() ||
                        collisionableArea.x <= scenario.getWestLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
            case WEST:
                if (collisionableArea.x <= scenario.getWestLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
            case NORTH_WEST:
                if (collisionableArea.y <= scenario.getNorthLimit() ||
                        collisionableArea.x <= scenario.getWestLimit()) {
                    tellCollisionToCollisionableCollisioned(scenario, CollisionType.LIMIT_REACHED, direction, collisionable);
                    collision = true;
                }
                break;
        }

        return collision;
    }

    /**
     * This method is used to check if a Collisionable instance is collisioning with
     * any Scenario instance collisionableAreas. 
     *
     * @param collisionable The collisionable instance to check if it's out of the scenario limits.
     * @param direction The direction in wich the collisionable is moving.
     * @param scenario The Scenario instance from wich to get the collisionable areas
     *
     * @return true if the collisionable instance intersects any of the scenario collisionable areas
     * in the specified direction. false otherwise.
     */
    public static boolean checkScenarioCollisionableAreasCollision(Collisionable collisionable, CollisionDirection direction, Scenario scenario) {

        boolean collision = false;

        Rectangle collisionableArea = collisionable.getCollisionableArea();
        Rectangle[] collisionedAreas = scenario.getCollisionableAreas();

        for (Rectangle collisionedArea : collisionedAreas) {
            if (collision) {
                break;
            }

            collision = checkSurfaceCollisionablesCollision(collisionableArea, direction, collisionedArea);

            if (collision) {
                tellCollisionToCollisionableCollisioned(scenario, CollisionType.SCENARIO_COLLISIONABLE_AREA_REACHED, direction, collisionable);
            }


        }

        return collision;
    }

    /**
     * This method is used to detect if there's ground down the collisionable.
     *
     * @param collisionable The collisionable instance to check if it's falling in a PlatForm game.
     * @param scenario The Scenario instance from wich to get the collisionable areas
     *
     * @return true if the collisionable instance intersects any of the scenario collisionable areas
     * in the SOUTH direction. false otherwise.
     */
    public static boolean checkScenarioFallingCollisionPlatformGame(Collisionable collisionable, Scenario scenario) {

        Rectangle collisionableArea = collisionable.getCollisionableArea();

        Point bottomLeft = new Point(collisionableArea.x, collisionableArea.y + collisionableArea.height);
        Point bottomRight = new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y + collisionableArea.height);

        Point[] basePoints = Paths.generateLinearPathFromPointToPoint(bottomLeft, bottomRight);
        Rectangle[] collisionableAreas = scenario.getCollisionableAreas();


        boolean falling = true;

        for (Point basePoint : basePoints) {
            basePoint.translate(0, 1);
            for (Rectangle rectangle : collisionableAreas) {
                if (rectangle.contains(basePoint)) {
                    falling = false;
                    return falling;
                }
            }
        }

        if (scenario.getSouthLimit() > basePoints[0].y) {
            tellCollisionToCollisionableCollisioned(scenario, CollisionType.FALLING, CollisionDirection.SOUTH, collisionable);
        }

        return falling;
    }

    //
    //Collision preventing
    //
    /**
     * This method checks if any of the collisionable instances contains the passed Point.
     *
     * @param point The point to check for being contained by a collisionable.
     *
     * @return true if any of the collisionable instaces conatins this Point. false otherwise.
     */
    public static boolean checkPointCollision(Point point) {

        checkIfCollisionablesOutDated();

        for (Collisionable collisionable : collisionables) {
            if (collisionable.getCollisionableArea().contains(point)) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method checks if an end point is going away at a specified direction.
     * This method doesn't really check if two Point collisionate.
     *
     * @param initial The initial Point
     * @param direction The direction to check if the end Point is going on.
     * @param end The end Point
     *
     * @return true if the end Point is at the specified direction from the initial.
     * false otherwise.
     */
    public static boolean checkPointCollision(Point initial, CollisionDirection direction, Point end) {

        switch (direction) {

            case NORTH:
                if (initial.y >= end.y) {
                    return true;
                }
                break;
            case NORTH_EAST:
                if (initial.y >= end.y || initial.x <= end.x) {
                    return true;
                }
                break;
            case EAST:
                if (initial.x <= end.x) {
                    return true;
                }
                break;
            case SOUTH_EAST:
                if (initial.y <= end.y || initial.x <= end.x) {
                    return true;
                }
                break;
            case SOUTH:
                if (initial.y <= end.y) {
                    return true;
                }
                break;
            case SOUTH_WEST:
                if (initial.y <= end.y || initial.x >= end.x) {
                    return true;
                }
                break;
            case WEST:
                if (initial.x >= end.x) {
                    return true;
                }
                break;
            case NORTH_WEST:
                if (initial.y >= end.y || initial.x >= end.x) {
                    return true;
                }
                break;
            case STANDING:
                if (initial.equals(end)) {
                    return true;
                }
                break;
        }

        return false;
    }

    /**
     * This method checks if a Tile object is occupied or not by a Collisionable.
     *
     * @param tile The Tile instance to check for a Collisionable containment.
     *
     * @return true if tile has an occupier. false otherwise.
     */
    public static boolean checkTileCollision(Tile tile) {

        if (tile.isOcuppied()) {
            return true;
        }

        return false;
    }

    /**
     * This method is used to check if a Rectangle surface collisionates with with the opposite
     * from another Rectangle.
     *
     * @param collisionableArea The first Rectangle instance.
     * @param direction The direction in wich the first Rectangle should be checked for intersection with the
     * opposite from the second. (f.e.: if direction is NORTH, the upper Rectangle1 surface will be checked with the bottom
     * Rectangle2 surface)
     * @param collisionedArea The second Rectangle instance.
     *
     * @return true if the first Rectangle surface collisionates with the opposite from the second. false otherwise.
     */
    public static boolean checkSurfaceCollisionablesCollision(Rectangle collisionableArea, CollisionDirection direction, Rectangle collisionedArea) {

        boolean collision = false;

        switch (direction) {

            case NORTH:
                if (collisionableArea.y <= collisionedArea.y + collisionedArea.height) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] northSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.getLocation()),
                                new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedArea.x + collisionedArea.width;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;


                        for (Point north : northSide) {
                            if (north.x >= collisionedX && north.x <= collisionedXEnd) {
                                if (north.y <= collisionedYEnd && north.y >= collisionedY) {

                                    collision = true;
                                    break;
                                }
                            }
                        }

                    }
                }
                break;

            case NORTH_EAST:

                if (collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] eastSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y),
                                new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y + collisionableArea.height - 5));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedX;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;

                        for (Point point : eastSide) {
                            if (point.y >= collisionedY && point.y <= collisionedYEnd) {
                                if (point.x >= collisionedX && point.x >= collisionedXEnd) {

                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!collision) {

                    if (collisionableArea.y <= collisionedArea.y + collisionedArea.height) {
                        if (collisionableArea.intersects(collisionedArea)) {

                            Point[] northSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.getLocation()),
                                    new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y));

                            int collisionedX = collisionedArea.x;
                            int collisionedXEnd = collisionedArea.x + collisionedArea.width;
                            int collisionedY = collisionedArea.y;
                            int collisionedYEnd = collisionedArea.y + collisionedArea.height;


                            for (Point north : northSide) {
                                if (north.x >= collisionedX && north.x <= collisionedXEnd) {
                                    if (north.y <= collisionedYEnd && north.y >= collisionedY) {

                                        collision = true;
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }

                break;

            case EAST:
                if (collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] eastSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y),
                                new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y + collisionableArea.height - 5));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedX;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;

                        for (Point point : eastSide) {
                            if (point.y >= collisionedY && point.y <= collisionedYEnd) {
                                if (point.x >= collisionedX && point.x >= collisionedXEnd) {

                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;

            case SOUTH_EAST:

                if (collisionableArea.x + collisionableArea.width >= collisionedArea.x) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] eastSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y),
                                new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y + collisionableArea.height - 5));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedX;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;

                        for (Point point : eastSide) {
                            if (point.y >= collisionedY && point.y <= collisionedYEnd) {
                                if (point.x >= collisionedX && point.x >= collisionedXEnd) {

                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!collision) {

                    if (collisionableArea.y + collisionableArea.height >= collisionedArea.y) {
                        if (collisionableArea.intersects(collisionedArea)) {

                            Point[] southSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.x, collisionableArea.y + collisionableArea.height),
                                    new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y + collisionableArea.height));

                            int collisionedX = collisionedArea.x;
                            int collisionedXEnd = collisionedArea.x + collisionedArea.width;
                            int collisionedY = collisionedArea.y;
                            int collisionedYEnd = collisionedArea.y + collisionedArea.height;


                            for (Point south : southSide) {
                                if (south.x >= collisionedX && south.x <= collisionedXEnd) {
                                    if (south.y <= collisionedYEnd && south.y >= collisionedY) {

                                        collision = true;
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }
                break;

            case SOUTH:
                if (collisionableArea.y + collisionableArea.height >= collisionedArea.y) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] southSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.x, collisionableArea.y + collisionableArea.height),
                                new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y + collisionableArea.height));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedArea.x + collisionedArea.width;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;


                        for (Point south : southSide) {
                            if (south.x >= collisionedX && south.x <= collisionedXEnd) {
                                if (south.y <= collisionedYEnd && south.y >= collisionedY) {

                                    collision = true;
                                    break;
                                }
                            }
                        }

                    }
                }
                break;

            case SOUTH_WEST:

                if (collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] westSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.getLocation()),
                                new Point(collisionableArea.x, collisionableArea.y + collisionableArea.height - 5));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedX;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;

                        for (Point west : westSide) {
                            if (west.y >= collisionedY && west.y <= collisionedYEnd) {
                                if (west.x >= collisionedX && west.x >= collisionedXEnd) {

                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!collision) {

                    if (collisionableArea.y + collisionableArea.height >= collisionedArea.y) {
                        if (collisionableArea.intersects(collisionedArea)) {

                            Point[] southSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.x, collisionableArea.y + collisionableArea.height),
                                    new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y + collisionableArea.height));

                            int collisionedX = collisionedArea.x;
                            int collisionedXEnd = collisionedArea.x + collisionedArea.width;
                            int collisionedY = collisionedArea.y;
                            int collisionedYEnd = collisionedArea.y + collisionedArea.height;


                            for (Point south : southSide) {
                                if (south.x >= collisionedX && south.x <= collisionedXEnd) {
                                    if (south.y <= collisionedYEnd && south.y >= collisionedY) {

                                        collision = true;
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }
                break;

            case WEST:
                if (collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] westSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.getLocation()),
                                new Point(collisionableArea.x, collisionableArea.y + collisionableArea.height - 5));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedX;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;

                        for (Point west : westSide) {
                            if (west.y >= collisionedY && west.y <= collisionedYEnd) {
                                if (west.x >= collisionedX && west.x >= collisionedXEnd) {

                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;

            case NORTH_WEST:
                if (collisionableArea.x <= collisionedArea.x + collisionedArea.width) {
                    if (collisionableArea.intersects(collisionedArea)) {

                        Point[] westSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.getLocation()),
                                new Point(collisionableArea.x, collisionableArea.y + collisionableArea.height - 5));

                        int collisionedX = collisionedArea.x;
                        int collisionedXEnd = collisionedX;
                        int collisionedY = collisionedArea.y;
                        int collisionedYEnd = collisionedArea.y + collisionedArea.height;

                        for (Point west : westSide) {
                            if (west.y >= collisionedY && west.y <= collisionedYEnd) {
                                if (west.x >= collisionedX && west.x >= collisionedXEnd) {

                                    collision = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!collision) {

                    if (collisionableArea.y <= collisionedArea.y + collisionedArea.height) {
                        if (collisionableArea.intersects(collisionedArea)) {

                            Point[] northSide = Paths.generateLinearPathFromPointToPoint(new Point(collisionableArea.getLocation()),
                                    new Point(collisionableArea.x + collisionableArea.width, collisionableArea.y));

                            int collisionedX = collisionedArea.x;
                            int collisionedXEnd = collisionedArea.x + collisionedArea.width;
                            int collisionedY = collisionedArea.y;
                            int collisionedYEnd = collisionedArea.y + collisionedArea.height;


                            for (Point north : northSide) {
                                if (north.x >= collisionedX && north.x <= collisionedXEnd) {
                                    if (north.y <= collisionedYEnd && north.y >= collisionedY) {

                                        collision = true;
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }
                break;

        }

        return collision;


    }
    //
    //Collisionables operations
    //
    /**
     * This method tells a collisioned Collisionable intance that it has been collisioned.
     *
     * @param causable The causable Collisioned instace that caused the collision.
     * @param collisionType The collision type produced by the causable Collisionable.
     * @param direction The direction in wich the collision type was produced.
     * @param collisioned The target Collisionable, which is told for the collision.
     */
    private static void tellCollisionToCollisionableCollisioned(Collisionable causable, CollisionType collisionType, CollisionDirection direction, Collisionable collisioned) {

        collisioned.receiveCollisionFromSprite((Sprite) causable, collisionType, direction);

    }

    /**
     * This method tells a collisioned Collisionable intance that it has been collisioned.
     *
     * @param causable The Collisionable instace that caused the collision.
     * @param actionCaused The action raised from the causable Collisionable.
     * @param collisioned The target Collisionable, which is told for the collision.
     */
    private static void tellCollisionToCollisionableCollisioned(Collisionable causable, Action actionCaused, Collisionable collisioned) {

        CollisionType collisionTypeCaused = actionCaused.getCollisionTypeCausable();

        collisioned.receiveCollisionFromSprite((Sprite) causable, collisionTypeCaused);

    }

    /**
     * This method is used to obtain all the Collisionable instances that are inside a Collisionable
     * visible area.
     *
     * @param visionable The Collisionable instace to ask for it's visible area
     *
     * @return a Collisionable ArrayList containing the visible Collisionable instances for
     * the visionable.
     */
    public static ArrayList<Collisionable> getVisibleCollisionables(Collisionable visionable) {

        checkIfCollisionablesOutDated();

        Rectangle visibleArea = visionable.getVisibleArea();

        ArrayList<Collisionable> visibleCollisionables = new ArrayList<Collisionable>();

        for (Collisionable col : collisionables) {
            if (visibleArea.contains(col.getCollisionableArea()) ||
                    visibleArea.intersects(col.getCollisionableArea())) {
                visibleCollisionables.add(col);
            }
        }

        visibleCollisionables.remove(visionable);

        return visibleCollisionables;
    }

    /**
     * This method is used to obtain all the Collisionable instances that are inside a Rectangle.
     *
     * @param area The Rectangle instance to check for it's contained Collisionable instances.
     *
     * @return a Collisionable ArrayList containing the Collisionable instances in this Rectangle.
     */
    public static ArrayList<Collisionable> getCollisionablesInArea(Rectangle area) {

        checkIfCollisionablesOutDated();

        ArrayList<Collisionable> collisionablesInArea = new ArrayList<Collisionable>();

        for (Collisionable col : collisionables) {
            if (area.contains(col.getCollisionableArea())) {
                collisionablesInArea.add(col);
            }
        }

        return collisionablesInArea;
    }

    /**
     * This method look for a Collisionable intance at the given Point.
     *
     * @param point The Point at wich a Collisionable instance is asked to be.
     *
     * @return The Collisionable instance from any of the the collisionables ArrayList
     * wich contains that Point. null if any Collisionable was found at the given Point.
     */
    public static Collisionable getCollisionableAtPoint(Point point) {

        checkIfCollisionablesOutDated();

        for (Collisionable collisionable : collisionables) {
            if (checkPointCollision(collisionable, point)) {
                return collisionable;
            }
        }

        return null;
    }

    //
    //Integrity checkings
    //
    /**
     * This method tells the Collisions instance that it should update it's collisionables
     * when it has a chance.
     */
    protected void setCollisionablesToOutdated() {
        collisionablesOutdated = true;
    }

    /**
     * This method is used to check if the collisionables field is outdated. If it is,
     * The Environment will be asked for the updated Collsionable ArrayList.
     */
    private static void checkIfCollisionablesOutDated() {

        if (collisionablesOutdated) {
            collisionables = phisics.getEnvironment().getCollisionables();
        }

        collisionablesOutdated = false;
    }
}
