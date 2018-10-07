
package domain.interfaces;

import java.util.ArrayList;

/**
 * This abstract interface define the generic model that an Actionable instance
 * will have.
 * A class wich implements one of it's subinterfaces will be able to manage Action
 * instances.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract interface Actionable {

    /**
     *
     * @return The Actionable name.
     */
    public String getActionableName();
    /**
     *
     * @return An ArrayList containing the Action instances that an Actionable
     * have implemented.
     */
    public ArrayList<Action> getActions();
    /**
     *
     * @param action The action to be executed. The easiest way of implementing this
     * contract method is calling the <code>action.executeAction()</code>
     *
     * @see Action#executeAction()
     */
    public void processAction(Action action);
    /**
     *
     * @param action The action to be added to an ArrayList containing the multiple
     * Action instances that an Actionable implements.
     */
    public void addAction(Action action);

}
