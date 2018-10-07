
package domain.interfaces;

/**
 * This is a subinterface of Action. It represents an Action instance wich can be
 * fired from a user event. In order to permit the GameClient and Controls
 * instances managing user fired actions, they need to be initially mapped to a
 * event code.
 *
 * A FiredAction is an Action wich can be executed by user.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface FiredAction extends Action{

    /**
     *
     * @return The default event code that the FiredAction will be mapped to
     * in the Controls. This event code will be setted at game instantiation
     * time. It can be setted latter.
     * 
     */
    public int getDefaultEventCode();
}
