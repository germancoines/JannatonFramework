package domain;

import domain.interfaces.Action;
import domain.interfaces.FiredAction;
import domain.interfaces.FiredActionable;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * This class acts as the controls manager. it's the responsible of mapping the
 * FiredAction from the FiredActionable instances of the Environment to the
 * Events received from the GameEngine.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class Controls {

    //
    // Fields
    //
    /**
     * The Controls instance itself.
     */
    private static Controls controls;
    /**
     * An ArrayList containing all the FiredActionable instances from the game.
     */
    private ArrayList<FiredActionable> firedActionables;
    /**
     * The Hashmap where the FiredAction instances from the FiredActionable
     * are mapped to the event codes.
     */
    private static HashMap<Integer, FiredAction> controlMap;

    //
    // Constructors
    //
    /**
     * The constructor checks inf the controlMap and firedActionables field are null
     * and if they are it initializes them.
     */
    private Controls() {

        if (controlMap == null) {
            controlMap = new HashMap<Integer, FiredAction>();
        }

        if (firedActionables == null) {
            firedActionables = new ArrayList<FiredActionable>();
        }
    }

    /**
     * This method returns the FiredAction assigned to this event code.
     *
     * @param eventCode The key value in the controlMap HashMap field
     *
     * @return The FiredAction mapped or null if there is no value for the key
     */
    protected FiredAction getFiredAction(int eventCode) {
        return controlMap.get(eventCode);
    }

    /**
     * This method returns the event code wich is the key for a FiredAction value
     * at the controlMap HashMap.
     *
     * @param firedAction The FiredAction instance for wich its key is requested
     *
     * @return An Integer object containing the key value, or null if the FiredAction
     * was not found in the HashMap.
     */
    protected Integer getEventCode(FiredAction firedAction) {
        Integer kec = null;
        Set<Integer> keys = controlMap.keySet();
        for (Integer eventCode : keys) {
            FiredAction actualFA = controlMap.get(eventCode);
            if (actualFA.getDefaultEventCode() == firedAction.getDefaultEventCode()) {
                kec = eventCode;
                break;
            }
        }
        return kec;

    }

    /**
     * This method is used to set the current eventcode wich is mapping a FiredAction
     * for another. This can be used to set controls.
     *
     * @param oldKeyCode The current key value at the controlMap
     * @param newKeyCode The new key value at the controlMap
     */
    protected void setKeyCodeForFiredAction(int oldKeyCode, int newKeyCode) {
        if ((!controlMap.containsKey(newKeyCode)) && (controlMap.containsKey(oldKeyCode))) {
            FiredAction firedAction = controlMap.get(oldKeyCode);
            controlMap.remove(oldKeyCode);
            controlMap.put(newKeyCode, firedAction);
        }

    }

    /**
     *
     * @return a FiredAction array containing all the FiredAction instances hold by
     * the controlMap HashMap.
     */
    protected FiredAction[] getFiredActions() {

        Object[] values = controlMap.values().toArray();
        FiredAction[] firedActions = new FiredAction[values.length];

        int count = 0;
        for (Object value : values) {
            firedActions[count] = (FiredAction) value;
            count++;
        }

        return firedActions;

    }

    /**
     *
     * @return an Integer array containing all the Integer instances representing the
     * key values at the controlMap HashMap.
     */
    protected Integer[] getEventCodes() {
        Object[] keys = controlMap.keySet().toArray();
        Integer[] eventCodes = new Integer[keys.length];

        int count = 0;
        for (Object eventCode : keys) {
            eventCodes[count] = (Integer) eventCode;
            count++;
        }

        return eventCodes;
    }

    /**
     *
     * @return The entire controlMap HashMap, containing all the Integer keys and the
     * FiredActions mapped.
     */
    protected HashMap<Integer, FiredAction> getControls() {
        return controlMap;
    }

    /**
     * This method is used to get a Controls instance following the Single-Town model
     *
     * @return a Controls instance.
     */
    public static Controls instantiate() {

        if (controls == null) {
            controls = new Controls();
        }

        return controls;
    }

    /**
     * This method is used to initialize the controlMap HashMap.
     * From each FiredActionable contained in the passed ArrayList, it's actions
     * are taken. From each FiredAction instance in the Action ArrayList taken, it's
     * default event code is taken.
     *
     * Once the keys an the values are obtained, then they are put into the controlMap
     * HashMap.
     *
     * @param firedActionables The game FiredActionable instances.
     *
     * @throws KeyException if any of the FiredActionable instances have a duplied default
     * event code.
     */
    protected void initialize(ArrayList<FiredActionable> firedActionables) throws KeyException {

        for (FiredActionable firedActionable : firedActionables) {

            ArrayList<Action> actions = firedActionable.getActions();
            ArrayList<FiredAction> firedActions = new ArrayList<FiredAction>();

            for (Action action : actions) {
                if (action instanceof FiredAction) {
                    firedActions.add((FiredAction) action);
                }
            }

            for (FiredAction firedAction : firedActions) {
                int defaultEventCode = firedAction.getDefaultEventCode();
                if (!controlMap.containsKey(defaultEventCode)) {
                    controlMap.put(defaultEventCode, firedAction);
                } else {

                    FiredAction existentFiredAction = controlMap.get(defaultEventCode);
                    FiredActionable existentFiredActionable = null;

                    for (FiredActionable firedActionOwner : firedActionables) {
                        if (firedActions.contains(existentFiredAction)) {
                            existentFiredActionable = firedActionable;
                            break;
                        }
                    }

                    if (existentFiredActionable != null) {
                        throw new KeyException("The default event code for the action <<" +
                                firedAction.getActionName() + ">> in " +
                                firedActionable.getActionableName() + " is used by <<" +
                                existentFiredAction.getActionName() + ">> in " +
                                existentFiredActionable.getActionableName() + ". You should establish a diferent " +
                                "default event code in 'getDefaultEventCode()' in one of them.");
                    } else {
                        throw new KeyException("The default event code for the action <<" +
                                firedAction.getActionName() + ">> in " +
                                firedActionable.getActionableName() + " is used by <<" +
                                existentFiredAction.getActionName() + ">> in an unknown FiredActionable." +
                                " You should establish a diferent " +
                                "default event code in 'getDefaultEventCode()' in one of them.");
                    }
                }
            }
        }

        this.firedActionables = firedActionables;
    }
}
