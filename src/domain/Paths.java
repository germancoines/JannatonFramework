/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author wida36528678
 */
public class Paths {

    private static Paths paths;

    public static enum Direction {

        NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, STANDING
    };

    public static Paths instantiate(){

        if(paths == null)
            paths = new Paths();

        return paths;
    }

    public static Point[] generateWayPathToPoint(int x, int y, Animated sprite) {

        Point initialPoint = sprite.getScenarioCoordinates();
        Point finalPoint = new Point(x, y);

        Point[] wayPath = generateLinearPathFromPointToPoint(initialPoint, finalPoint);

        return wayPath;
    }

    public static Point[] generateParabolicPathToPoint(int width, int height, Animated sprite) {
        //not implemented yet
        return new Point[0];
    }

    public static Point[] generateRandomPath(int steps, Animated sprite) {
        //not implemented yet
        return new Point[0];
    }

    public static Point[] generateOvalPath(int width, int height, Animated sprite) {
        //not implemented yet
        return new Point[0];
    }

    private static Direction checkDirection(Point initial, Point end) {

        int initialX = initial.x;
        int initialY = initial.y;

        int endX = end.x;
        int endY = end.y;
        

        if(initialX == endX && initialY > endY)
            return Direction.NORTH;
        if(initialX == endX && initialY < endY)
            return Direction.SOUTH;
        if(initialX < endX && initialY == endY)
            return Direction.EAST;
        if(initialX > endX && initialY == endY)
            return Direction.WEST;
        if(initialX > endX && initialY < endY)
            return Direction.NORTH_WEST;
        if(initialX < endX && initialY < endY)
            return Direction.NORTH_EAST;
        if(initialX > endX && initialY > endY)
            return Direction.SOUTH_WEST;
        if(initialX < endX && initialY > endY)
            return Direction.SOUTH_EAST;

        return Direction.STANDING;
    }

    protected static Point[] generateLinearPathFromPointToPoint(Point initial, Point end) {

        Direction wayDirection = checkDirection(initial, end);

        Point start = new Point(initial);
        Point finish = new Point(end);


        ArrayList<Point> linearPath = new ArrayList<Point>();

        while (!start.equals(finish)) {

            switch (wayDirection) {

                case NORTH:
                    linearPath.add(new Point(start.x, --start.y));
                    break;
                case NORTH_EAST:
                    linearPath.add(new Point(++start.x, --start.y));
                    if (start.x == finish.x) {
                        wayDirection = Direction.EAST;
                    }
                    if (start.y == finish.y) {
                        wayDirection = Direction.NORTH;
                    }
                    break;
                case EAST:
                    linearPath.add(new Point(++start.x, start.y));
                    break;
                case SOUTH_EAST:
                    linearPath.add(new Point(++start.x, ++start.y));
                    if (start.x == finish.x) {
                        wayDirection = Direction.EAST;
                    }
                    if (start.y == finish.y) {
                        wayDirection = Direction.SOUTH;
                    }
                    break;
                case SOUTH:
                    linearPath.add(new Point(start.x, ++start.y));
                    break;
                case SOUTH_WEST:
                    linearPath.add(new Point(--start.x, ++start.y));
                    if (start.x == finish.x) {
                        wayDirection = Direction.WEST;
                    }
                    if (start.y == finish.y) {
                        wayDirection = Direction.SOUTH;
                    }
                    break;
                case WEST:
                    linearPath.add(new Point(--start.x, start.y));
                    break;
                case NORTH_WEST:
                    linearPath.add(new Point(--start.x, --start.y));
                    if (start.x == finish.x) {
                        wayDirection = Direction.WEST;
                    }
                    if (start.y == finish.y) {
                        wayDirection = Direction.NORTH;
                    }
                    break;

            }
        }

        Object[] result = linearPath.toArray();
        Point[] resultPath = new Point[result.length];

        int count = 0;
        for (Object obj : result) {
            resultPath[count] = (Point) obj;
            count++;
        }

        return resultPath;
    }
}
