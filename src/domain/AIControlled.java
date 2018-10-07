package domain;

import domain.Collisions.CollisionDirection;
import domain.Collisions.CollisionType;
import domain.interfaces.Action;
import domain.interfaces.Actionable;
import domain.interfaces.Collisionable;
import domain.interfaces.Reactable;
import domain.interfaces.ReactedAction;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import persistence.State;

/**
 * This class represents a game entity wich is controlled by it's artificial
 * intelligence implementation.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract class AIControlled extends Animated implements Reactable {

    //
    // Fields
    //
    /**
     * The ArrayList containing all the ReactedAction instances for this AIControlled
     */
    protected ArrayList<ReactedAction> reactedActions;

    //
    // Constructors.
    //
    /**
     * Default constructor. calls super() and initializes the reactedActions ArrayList.
     */
    protected AIControlled() {
        super();
        reactedActions = new ArrayList<ReactedAction>();
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex);</code>
     * and initializes the reactedActions field.
     *
     * @param relativeToScreen The point at wich the upper-left AIControlled sprite corner will be screen allocated.
     * @param width The width of the AIControlled sprite
     * @param height The height of the AIControlled sprite
     * @param zIndex The zIndex of the AIControlled sprite
     */
    protected AIControlled(Point relativeToScreen, int width, int height, int zIndex) {
        super(relativeToScreen, width, height, zIndex);
        reactedActions = new ArrayList<ReactedAction>();
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, int width, int height, int zIndex,
     * Rectangle[] collisionableAreas);</code>
     * and initializes the reactedActions field.
     *
     * @param relativeToScreen The point at wich the upper-left AIControlled sprite corner will be screen allocated.
     * @param width The width of the AIControlled sprite
     * @param height The height of the AIControlled sprite
     * @param zIndex The zIndex of the AIControlled sprite
     * @param collisionableAreas The AIControlled sprite collisionableAreas
     */
    protected AIControlled(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, width, height, zIndex, collisionableAreas);
        reactedActions = new ArrayList<ReactedAction>();
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, Point relativeToEnvironment, int width, int height,
     * int zIndex);</code>
     * and initializes the reactedActions field.
     *
     * @param relativeToScreen The point at wich the upper-left AIControlled sprite corner will be screen allocated.
     * @param relativeToScenario The point at wich the upper-left AIControlled sprite corner will be scenario allocated.
     * @param width The width of the AIControlled sprite
     * @param height The height of the AIControlled sprite
     * @param zIndex The zIndex of the AIControlled sprite
     */
    protected AIControlled(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex) {
        super(relativeToScreen, relativeToScenario, width, height, zIndex);
        reactedActions = new ArrayList<ReactedAction>();
    }

    /**
     * Constructor. It calls <code>super(Point relativeToScreen, Point relativeToEnvironment, int width, int height,
     * int zIndex);</code>
     * and initializes the reactedActions field.
     *
     * @param relativeToScreen The point at wich the upper-left AIControlled sprite corner will be screen allocated.
     * @param relativeToScenario The point at wich the upper-left AIControlled sprite corner will be scenario allocated.
     * @param width The width of the AIControlled sprite
     * @param height The height of the AIControlled sprite
     * @param zIndex The zIndex of the AIControlled sprite
     * @param collisionableAreas The AIControlled sprite collisionableAreas
     *
     */
    protected AIControlled(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex, Rectangle[] collisionableAreas) {
        super(relativeToScreen, relativeToScenario, width, height, zIndex, collisionableAreas);
        reactedActions = new ArrayList<ReactedAction>();
    }

    /**
     *
     * @return Simple name of the class
     *
     * @see Actionable#getActionableName()
     */
    public String getActionableName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Getter.
     *
     * @return The Action's ArrayList
     *
     * @see Actionable#getActions()
     */
    public ArrayList<Action> getActions() {

        ArrayList<Action> actions = new ArrayList<Action>();

        for (Action reactedAction : reactedActions) {
            actions.add((Action) reactedAction);
        }

        if (actions.isEmpty()) {
            System.out.println("Warning! The action array from " + getActionableName() + "seems to be empty!");
        }

        return actions;
    }

    /**
     * Process an action.
     *
     * @param action The action to execute.
     *
     * @see Actionable#processAction(domain.interfaces.Action)
     */
    public void processAction(Action action) {

        if (action instanceof ReactedAction) {
            action.executeAction();
            action.finishAction();
        }
    }

    /**
     * Process a reaction.
     *
     * @param collisionType The collision type to wich to react
     * @param collisionDirection The direction from wich to react
     *
     * @see Reactable#processReaction(domain.Collisions.CollisionType, domain.Collisions.CollisionDirection)
     */
    public void processReaction(CollisionType collisionType, domain.Collisions.CollisionDirection collisionDirection) {
        for (ReactedAction reactedAction : reactedActions) {
            for (CollisionType type : reactedAction.getCollisionTypesReactionable()) {
                if (type.equals(collisionType) && collisionDirection.equals(reactedAction.getDirectionReactionable())) {
                    reactedAction.executeAction();
                    break;
                }
            }
        }
    }

    /**
     * Add an action into reactedActions. It will only added if its instance of a
     * ReactedAction
     *
     * @param action The Action to add.
     *
     * @see Actionable#addAction(domain.interfaces.Action) 
     */
    public void addAction(Action action) {
        if (action instanceof ReactedAction) {
            reactedActions.add((ReactedAction) action);
        }
    }

    /**
     * Execute and finish an action caused by collision.
     *
     * @param sprite The Sprite instance wich caused the collision
     * @param type The collision type received
     *
     * @see Collisionable#receiveCollisionFromSprite(domain.Sprite, domain.Collisions.CollisionType)
     */
    public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
        for(ReactedAction reactedAction : reactedActions)
            for(CollisionType colType : reactedAction.getCollisionTypesReactionable())
                if(colType.equals(type)){
                    reactedAction.executeAction();
                    reactedAction.finishAction();
                }
    }

    /**
     * xecute and finish an action caused by collision.
     *
     * @param sprite The Sprite instance wich caused the collision
     * @param type The collision type received
     * @param direction The direction from wich to react
     *
     * @see Collisionable#receiveCollisionFromSprite(domain.Sprite, domain.Collisions.CollisionType, domain.Collisions.CollisionDirection) 
     */
    public void receiveCollisionFromSprite(Sprite sprite, CollisionType type, CollisionDirection direction) {
        processReaction(type, direction);
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
}
