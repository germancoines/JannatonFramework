/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author wida36528678
 */
public class Player extends UserControlled{
    public Player() {
        super();
    }

    public Player(Point relativeToScreen, int width, int height, int zIndex) {
        super(relativeToScreen, width, height, zIndex);
    }

    public Player(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, width, height, zIndex, collisionableAreas);
    }

    public Player(Point relativeToScreen, Point relativeToEnvironment, int width, int height, int zIndex) {
        super(relativeToScreen, relativeToEnvironment, width, height, zIndex);
    }

    public Player(Point relativeToScreen, Point relativeToEnvironment, int width, int height, int zIndex, Rectangle[] collisionableAreas){
        super(relativeToScreen, relativeToEnvironment, width, height, zIndex, collisionableAreas);
    }

    
}
