package domain;

import domain.interfaces.FiredAction;
import domain.interfaces.FiredActionable;
import java.security.KeyException;
import java.util.ArrayList;

/**
 * This class holds all the configuration managers for the GameClient instance.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class Configuration {

    //
    // Fields
    //
    /**
     * The Configuration instance itself.
     */
    private static Configuration configuration;
    /**
     * The Controls instance, wich manage the mapping of FiredActions with the
     * event codes.
     */
    private Controls controls;
    /**
     * The Display instance (At developement stage yet). It will manage the screen
     * configuration.
     */
    private Display display;
    /**
     * The Sound instance (At developemente stage yet). It will manage the game sound configuration.
     */
    private Sound sound;

    //
    // Constructors.
    //
    /**
     * The constructor first checks if the controls, display and sound fields are null.
     * If they are, the Configuration instance calls their instantiation methods.
     *
     * @see Controls#instantiate()
     * @see Display#instantiate()
     * @see Sound#instantiate()
     */
    private Configuration() {

        if (controls == null) {
            controls = Controls.instantiate();
        }

        if (display == null) {
            display = Display.instantiate();
        }

        if (sound == null) {
            sound = Sound.instantiate();
        }
    }

    /**
     * This method is used to get a Configuration instance following the Single-Town model
     *
     * @return a Configuration instance.
     */
    protected static Configuration instantiate() {

        if (configuration == null) {
            configuration = new Configuration();
        }

        return configuration;
    }

    /**
     * This method returns the stablished FiredAction for an event code.
     *
     * @param k The event code to look for it's mapped FiredAction
     *
     * @return The FiredAction assigned to this event code, or null if there are
     * no FiredAction mapped for the given event code.
     */
    protected FiredAction getFiredAction(int k) {

        return controls.getFiredAction(k);
    }

    /**
     * This method is called by the GameClient to initialize the Control hashmap.
     *
     * If a KeyException is raised due to a duplicity key in the Controls controlmap
     * HashMap, the System will be shutdown. (We will perform that bad praxis in a next
     * framework release)
     *
     * @param firedActionables An ArrayList containing all the firedActionables
     * instances from the game.
     *
     * @see Controls#initialize(java.util.ArrayList)
     */
    protected void initializeControls(ArrayList<FiredActionable> firedActionables) {

        try {
            controls.initialize(firedActionables);
        } catch (KeyException keyEx) {
            System.out.println(keyEx.getMessage());
            
        }
    }

    /**
     *
     * @return The Controls instance assigned to the Configuration
     *
     */
    public Controls getControls() {
        return controls;
    }

    /**
     *
     * @return The Display instance assigned to the Configuration
     *
     */
    public Display getDisplay() {
        return display;
    }

    /**
     *
     * @return The Sound instance assigned to the Configuration
     *
     */
    public Sound getSound() {
        return sound;
    }
}
