
package domain;

import control.GameControler;
import control.interfaces.Renderable;
import control.interfaces.Updatable;
import domain.interfaces.FiredAction;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * This class acts as the GameClient. It's subClasses will be responsible of
 * passing the needed domain information from the Environment to the GameControler.
 * Both should be assigned to it once a GameClient subclass has been instantiated.
 * First the GameControler shoud be added, and then the GameClient should be initialized
 * passing to it the Environment instance.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract class GameClient {

    //
    // Fields
    //
    /**
     * The Configuration instance used by this GameClient.
     */
    private Configuration configuration;
    /**
     * The GameControler added to this GameClient.
     */
    private GameControler controler;
    /**
     * The GameClient subclass instance itself.
     *
     * @see OffLine#instantiate()
     *
     */
    protected static GameClient gameClient;
    /**
     * The Environment instance assigned to a GameClient subclass.
     */
    private Environment environment;
    //
    // Constructors
    //
    /**
     * The default GameClient constructor. When it's invoked by one of it's subclasses
     * the configuration field will be checked and instantiated if necessary.
     *
     * @see Configuration#instantiate()
     */
    protected GameClient(){

        if(configuration == null)
            configuration = Configuration.instantiate();        
    }

    /**
     * This method will be called by the GameControler when it needs to register
     * the permitted controls for this game.
     *
     * @return an Array with all the eventCodes allowed for this game.
     *
     * @see Configuration#getControls()
     * @see Controls#getEventCodes()
     */
    public int[] getPermittedControls() {

        Integer[] eventCodes = configuration.getControls().getEventCodes();
        int length = eventCodes.length;

        int[] permittedControls = new int[length];
        for (int i = 0; i < length; i++) {
            permittedControls[i] = eventCodes[i].intValue();
        }
        
        return permittedControls;
    }

    /**
     * This method will be called by the GameControler when it needs to register
     * the Updatable instances for this game.
     *
     * @return an ArrayList containing the current Updatable instances from the
     * Environment.
     *
     * @see Environment#getUpdatables()
     */
    public ArrayList<Updatable> getUpdatables() {

        return environment.getUpdatables();
    }
    /**
     * This method will be called by the GameControler when it needs to register
     * the Renderable instances for this game.
     *
     * @return an ArrayList containing the current Renderable instances from the
     * Environment.
     *
     * @see Environment#getRenderables()
     */
    public ArrayList<Renderable> getRenderables() {

        return environment.getRenderables();
    }
    /**
     * This method is called by the GameControler each time it receives an Event
     * wich it's key event code is in the permittedControls array.
     * When the GameClient receives the event it's prepared to be processed.
     *
     * @param ke The KeyEvent to process.
     *
     * @see Configuration#getFiredAction(int)
     */
    public void executeKeyEvent(KeyEvent ke) {

        FiredAction action = configuration.getFiredAction(ke.getKeyCode());
        if(action != null){
            int eventID = ke.getID();
            if( eventID == KeyEvent.KEY_PRESSED)
                action.executeAction();
            else if(eventID == KeyEvent.KEY_RELEASED)
                action.finishAction();
        }
    }

    /**
     * This method is called by the GameControler each time it receives an Event
     * wich it's mouse event code is in the permittedControls array.
     * When the GameClient receives the event it's prepared to be processed.
     *
     * @param me The Mouse Event to be processed.
     */
    public void executeMouseEvent(MouseEvent me) {

        FiredAction action = configuration.getFiredAction(me.getButton());
        if(action != null){
            int eventID = me.getID();
            if(eventID == MouseEvent.MOUSE_PRESSED ||
                    eventID == MouseEvent.MOUSE_ENTERED )
                action.executeAction();
            else if(eventID == MouseEvent.MOUSE_RELEASED ||
                    eventID == MouseEvent.MOUSE_EXITED)
                action.finishAction();
        }
    }
    /**
     * This method is used to add to the GameClient instance a GameControler instance.
     * Only one GameClient can be added and can't be changed.
     *
     * @param gameControler The GameControler for this GameClient.
     */
    public void addGameController(GameControler gameControler){

        if(gameControler != null && controler == null)
            controler = gameControler;
        else
            System.out.println("Another controller was previously added. Aborting.");
    }

    /**
     * This method is called by the initialize() method. It adds the Environment
     * to the current GameClient instance.
     *
     * @param gameEnvironment The Environment instance to add.
     *
     * @see #initialize(environment)
     */
    private void addEnvironment(Environment gameEnvironment){
        
        if(gameEnvironment != null)
            environment = gameEnvironment;
    }

    /**
     * This method initializes the game Controls passing to it the FiredActionable
     * game instances.
     *
     * @see Configuration#initializeControls(java.util.ArrayList)
     */
    private void setUpConfiguration(){

        configuration.initializeControls(environment.getFiredActionables());
        
    }

    public void setEnvironmentClientDimension(GameControler controler, Dimension dimension){
        if(controler == this.controler){
            setEnvironmentClientDimension(dimension);
        }
    }

    protected void setEnvironmentClientDimension(Dimension dimension){
        environment.setDimension(dimension);
    }
    /**
     * This method adds the passed Environment instance to the current GameClient.
     * Only one Environment can be assigned and never changed.
     *
     * When the Environment is properly added, then a call to setUpConfiguration is made.
     *
     * @param environment The Environment instance to add.
     *
     * @see #addEnvironment(domain.Environment)
     * @see #setUpConfiguration()
     */
    public void initialize(Environment environment) {
        if (this.environment == null){
            addEnvironment(environment);
        }
        else
            System.out.println("An Environment instance was already assigned. Aborting.");

        setUpConfiguration();

    }
}
