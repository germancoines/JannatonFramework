
package control;

import control.interfaces.Renderable;
import control.interfaces.Updatable;
import domain.GameClient;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import userInterface.GameEngine;

/**
 * This class acts as the control layer between the userInterface (GameEngine, 
 * GameFrame, and GameApplet) and the domain layers (GameClient, Environment ...)
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 *
 */
public class GameControler {

    //
    // Fiels.
    //
    /**
     * The GameEngine instance to wich GameController refers
     */
    private GameEngine engine;

    /**
     * The GameControler itself
     */
    private static GameControler controller;

    /**
     * The GameClient instance to wich GameControler refers
     */
    private GameClient client;

    /**
     * This array will contain the permitted event codes that any of the events
     * received from the GameEngine should have to be passed to the GameClient
     * to process the action mapped to that event.
     */
    private int[] permittedControls;

    /**
     * The updatables from the GameClient
     */
    private ArrayList<Updatable> updatables;

    /**
     * The renderables from the GameClient
     */
    private ArrayList<Renderable> drawables;

    //
    // Constructors
    //
    /**
     * When a GameController is instanciated using its <code>instantiate()</code>
     * method, it first checks if its client and engine fields are null.
     * If they are, then the passed values are assigned.
     * Furthermore, the GameControler adds itself to the GameEngine and GameClient
     * instances.
     * If the permmitedControls field is null, then the GameClient will be asked for
     * them and assigned to the field.
     * At last, the updatables and renderables are setted.
     *
     * @param gameEngine The GameEngine instance that the GameControler will refer.
     * @param gameClient The GameClient instance that the GameControler will refer.
     *
     * @see GameEngine#addGameController(control.GameControler)
     * @see GameClient#addGameController(control.GameControler)
     * @see #setUpdatables()
     * @see #setRenderables()
     */
    private GameControler(GameEngine gameEngine, GameClient gameClient) {

        if(client == null)
            client = gameClient;

        if(engine == null)
            engine = gameEngine;

        engine.addGameController(this);
        client.addGameController(this);

        permittedControls = client.getPermittedControls();

        setUpdatables();
        setRenderables();

        client.setEnvironmentClientDimension(this, getEngineDimension());
    }

    /**
     *
     * @return A Dimension object that represents the GameEngine's current dimension.
     */
    public Dimension getEngineDimension(){
        return engine.getPreferredSize();
    }

    /**
     *
     * @return The updatables ArrayList
     */
    public ArrayList<Updatable> getUpdatables() {

        return updatables;
    }

    /**
     *
     * @return The renderables ArrayList
     */
    public ArrayList<Renderable> getRenderables() {

        return drawables;
    }

    /**
     * This method forces the GameControler to ask the GameClient for it's current
     * updatables and then, the GameEngine is told that it should update it's updatables.
     *
     * @see GameClient#getUpdatables()
     * @see #tellEngineUpdatablesOutDated()
     */
    public void setUpdatables(){
        updatables = client.getUpdatables();
        tellEngineUpdatablesOutDated();
    }

    /**
     * This method tells the GameEngine that it's updatables instances are outDated.
     * That will make the GameEngine updating them whenever it has a chance.
     *
     * @see GameEngine#setUpdatablesToOutDated()
     */
    private void tellEngineUpdatablesOutDated(){
        engine.setUpdatablesToOutDated();
    }

    /**
     * This method forces the GameControler to ask the GameClient for it's current
     * renderables and then, the GameEngine is told that it should update it's renderables.
     *
     * @see GameClient#getRenderables()
     * @see #tellEngineRenderablesOutDated()
     */
    public void setRenderables(){
        drawables = client.getRenderables();
        tellEngineRenderablesOutDated();
    }

    /**
     * This method tells the GameEngine that it's renderables instances are outDated.
     * That will make the GameEngine updating them whenever it has a chance.
     *
     * @see GameEngine#setRenderablesToOutDated()
     */
    private void tellEngineRenderablesOutDated(){
        engine.setRenderablesToOutDated();
    }

    /**
     * This method is invoked by the GameEngine instance every time it receives
     * a KeyEvent. The GameControler then checks if the event code of this KeyEvent
     * is in the permitted controls array, and if it is, the event is passed to
     * the GameClient instance to be processed.
     *
     * @param ke The KeyEvent that should be checked for permission before being
     * executed by the GameClient.
     */
    public void processKeyEvent(KeyEvent ke) {
        
        int keyCode = ke.getKeyCode();
        for (int p : permittedControls) {
            if (keyCode == p) {
                client.executeKeyEvent(ke);
                break;
            }
        }
    }

    /**
     * This method is invoked by the GameEngine instance every time it receives
     * a MouseEvent. The GameControler then checks if the event code of this MouseEvent
     * is in the permitted controls array, and if it is, the event is passed to
     * the GameClient instance to be processed.
     *
     * @param me The MouseEvent that should be checked for permission before being
     * executed by the GameClient.
     */
    public void processMouseEvent(MouseEvent me) {
        int mouseCode = me.getButton();
        for (int p : permittedControls) {
            if (mouseCode == p) {
                client.executeMouseEvent(me);
                break;
            }
        }
    }

    /**
     * This method is used to get a GameControler instance following the Single-Town model
     *
     * @param gameEngine The GameEngine instance that the GameControler will refer.
     * @param gameClient The GameClient instance that the GameControler will refer.
     *
     * @return a GameControler instance
     */
    public static GameControler instanciate(GameEngine gameEngine, GameClient gameClient) {

        if (controller == null )
            controller = new GameControler(gameEngine, gameClient);

        return controller;
    }
}
