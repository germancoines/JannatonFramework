
package domain.interfaces;

/**
 * Any game entity wich implements this interface will represent something wich is able
 * to move around the Scenario.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface Movable {
    /**
     *
     * @param xIncrement the x increment value that will be applied on each update.
     */
    public void setXIncrement(int xIncrement);
    /**
     *
     * @param yIncrement the y increment value that will be applied on each update.
     */
    public void setYIncrement(int yIncrement);
    /**
     * This method will set the increment values to 0.
     */
    public void stop();
    /**
     *
     * @param speed an optional value wich is used to make the movement time
     * controlled. 0 if a time control is not needed (the movement will depend on
     * the update calls).
     */
    public void setSpeed(int speed);
    /**
     * The movement operation should be implemented here. 
     */
    public void move();
}
