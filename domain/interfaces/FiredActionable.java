
package domain.interfaces;

/**
 * This subinterface inherits the Actionable methods. The FiredActionable type represents
 * a game entity wich will be able to process a FiredAction given a user event.
 * This means that the FiredActionable instance should have a collection
 * of FiredAction instances implemented.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface FiredActionable extends Actionable {

    /**
     *
     * @return true if this FiredActionable instance is currently responding to user
     * events. false otherwise.
     */
    public boolean hasActionFocus();
    /**
     * This method should be used to change the focusable state on the FiredActionable
     * instance.
     *
     * @param actionFocus a boolean value indicating if the FiredActionable is currently
     * listening at the user events. true if it's listening, or false if not.
     */
    public void setActionFocus(boolean actionFocus);

}
