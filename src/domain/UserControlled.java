/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Collisions.CollisionDirection;
import domain.Collisions.CollisionType;
import domain.interfaces.Action;
import domain.interfaces.FiredAction;
import domain.interfaces.FiredActionable;
import domain.interfaces.Reactable;
import domain.interfaces.ReactedAction;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import persistence.State;

/**
 *
 * @author wida36528678
 */
public abstract class UserControlled extends Animated implements FiredActionable, Reactable {

    protected ArrayList<FiredAction> firedActions;
    protected ArrayList<ReactedAction> reactedActions;
    private boolean isActionFocused;

    public UserControlled() {

        super();
        firedActions = new ArrayList<FiredAction>();
        reactedActions = new ArrayList<ReactedAction>();

    }

    protected UserControlled(Point relativeToScreen, int width, int height, int zIndex) {
        super(relativeToScreen, width, height, zIndex);
        firedActions = new ArrayList<FiredAction>();
        reactedActions = new ArrayList<ReactedAction>();
    }

    protected UserControlled(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, width, height, zIndex, collisionableAreas);
        firedActions = new ArrayList<FiredAction>();
        reactedActions = new ArrayList<ReactedAction>();
    }

    protected UserControlled(Point relativeToScreen, Point relativeToEnvironment, int width, int height, int zIndex) {
        super(relativeToScreen, relativeToEnvironment, width, height, zIndex);
        firedActions = new ArrayList<FiredAction>();
        reactedActions = new ArrayList<ReactedAction>();
    }

    protected UserControlled(Point relativeToScreen, Point relativeToEnvironment, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, relativeToEnvironment, width, height, zIndex, collisionableAreas);
        firedActions = new ArrayList<FiredAction>();
        reactedActions = new ArrayList<ReactedAction>();
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

    public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
        for(ReactedAction reactedAction : reactedActions)
            for(CollisionType colType : reactedAction.getCollisionTypesReactionable())
                if(colType.equals(type)){
                    reactedAction.executeAction();
                    reactedAction.finishAction();
                }
    }

    public void receiveCollisionFromSprite(Sprite sprite, CollisionType type, CollisionDirection direction) {

        processReaction(type, direction);
    }

    public boolean hasActionFocus() {
        return this.isActionFocused;
    }

    public void setActionFocus(boolean actionFocus) {
        this.isActionFocused = actionFocus;
    }

    public String getActionableName() {
        return this.getClass().getSimpleName();
    }

    public ArrayList<Action> getActions() {

        ArrayList<Action> actions = new ArrayList<Action>();

        for (Action firedAction : firedActions) {
            actions.add((Action) firedAction);
        }

        for (Action reactedAction : reactedActions) {
            actions.add((Action) reactedAction);
        }

        if (actions.isEmpty()) {
            System.out.println("Warning! The action array from " + getActionableName() + "seems to be empty!");
        }

        return actions;
    }

    public void processAction(Action action) {

        if (action instanceof FiredAction) {
            if (isActionFocused) {
                action.executeAction();
            }
        } else if (action instanceof ReactedAction) {
            action.executeAction();
        }

    }

    public void processReaction(CollisionType collisionType, CollisionDirection collisionDirection) {
        for (ReactedAction reactedAction : reactedActions) {
            for(CollisionType type : reactedAction.getCollisionTypesReactionable())
                if (type.equals(collisionType) && collisionDirection.equals(reactedAction.getDirectionReactionable())) {
                    reactedAction.executeAction();
                    break;
                }
        }
    }

    public void addAction(Action action) {

        if (action instanceof FiredAction) {
            firedActions.add((FiredAction) action);
        }

        if (action instanceof ReactedAction) {
            reactedActions.add((ReactedAction) action);
        }
    }
}
