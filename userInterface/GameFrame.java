package userInterface;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * This class acts as the game window. It can't be resized by the user by
 * mouse clicking and dragging, it should be resized from the GameClient display
 * configuration options (if they are enabled).
 *
 * It uses to listen window events since it has a WindowListener. Whenever the
 * GameFrame listens for a WindowEvent, it passes the event to the GameEngine
 * for processing it.
 *
 * @author Alberto Languiz Polo
 * @author Germán Coines Laguna
 * 
 */
public class GameFrame extends JFrame {

    //
    //Fields
    //
    /**
     * The GameFrame instance itself. 
     * 
     * @see #instantiate()
     */
    private static GameFrame gameFrame;
    /**
     * The GameEngine instance that will be added as a child to this GameFrame
     */
    private GameEngine engine;
    /**
     * This field contains the Dimension that the GameFrame will handle when it's
     * in windowed mode.
     */
    private Dimension gameFrameDimension;
    /**
     * The GraphicsDevice object extracted from the GameFrame GraphicsConfiguration
     */
    private GraphicsDevice graphicsDevice;
    /**
     * The GraphicsConfiguration object extracted from this GameFrame
     */
    private GraphicsConfiguration graphicsConfiguration;
    /**
     * This field handles an array with the avaible DisplayMode objects on the running
     * windowed system. The setFullScreenDisplayMode() uses this field to ensure that it's able
     * to change to it.
     *
     * @see #setFullScreenDisplayMode(java.awt.DisplayMode)
     */
    private DisplayMode[] avaibleDisplayModes;
    /**
     * This field references at its initial time to the DisplayMode instance returned
     * from graphicsDevice.getDisplayMode(). Since the GameFrame has had a call to the
     * setFullScreenDisplayMode() method, the field will use to refer to one of the avaible
     * DisplayMode object contained in avaibleDisplayModes array.
     *
     * @see #getDisplayMode()
     * @see #setFullScreenDisplayMode(java.awt.DisplayMode)
     */
    private DisplayMode currentDisplayMode;
    /**
     * Field used to indicate if the Java Full Screen Exclusive Mode is avaible
     * on this windowing system.
     *
     * @see #setFullScreenON()
     * @see #setFullScreenOFF() 
     */
    private boolean isFullScreenModeSupported;
    /**
     * Field used to indicate if DisplayMode changes are suported on this windowing
     * system.
     *
     * @see #setFullScreenDisplayMode(java.awt.DisplayMode)
     */
    private boolean areDisplayChangesSupported;
    /**
     * Field used to indicate if this GameFrame instance is running with the
     * Java Full Screen Exclusive Mode enabled. It's default value is false
     */
    private boolean isFullScreenModeOn = false;

    //
    //Constructors
    //
    /**
     * Private constructor. To get a GameFrame instance it shoud be requested
     * using the static method GameFrame.instantiate() .
     *
     * It checks for it's possible null fields initializing them if necessary.
     * When all fields have been initialized, it call the addListeners() and the
     * addGameEngine() methods.
     *
     * The GameFrame will be finally shown when the application calls the
     * showGameFrame() method.
     *
     * @see #instantiate()
     * @see #addListeners()
     * @see #addGameEngine(userInterface.GameEngine)
     * @see #showGameFrame()
     */
    private GameFrame() {

        if (gameFrameDimension == null) {
            gameFrameDimension = Toolkit.getDefaultToolkit().getScreenSize();
        }

        if (graphicsConfiguration == null) {
            graphicsConfiguration = this.getGraphicsConfiguration();
        }

        if (graphicsDevice == null) {
            graphicsDevice = graphicsConfiguration.getDevice();
        }

        isFullScreenModeSupported = graphicsDevice.isFullScreenSupported();

        areDisplayChangesSupported = graphicsDevice.isDisplayChangeSupported();

        avaibleDisplayModes = graphicsDevice.getDisplayModes();

        currentDisplayMode = graphicsDevice.getDisplayMode();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        addListeners();

    }

    /**
     * This method add Listeners to the GameFrame.
     *
     * When a window event is produced, it's catched and passed to the engine via
     * a engine.processWindowEvent()
     *
     * This method is still under developement.
     */
    private void addListeners() {

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("windowClosing " + e.getID());
            }

            @Override
            public void windowIconified(WindowEvent e) {
                System.out.println("windowIconified" + e.getID());
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                System.out.println("windowDeiconified " + e.getID());
            }

            @Override
            public void windowActivated(WindowEvent e) {
                System.out.println("windowActivated " + e.getID());
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("windowDeactivated " + e.getID());
            }
        });

    }

    /**
     * @param gameEngine - The GameEngine instance
     * 
     * This method append the engine GameEngine instance to the GameFrame
     *
     */
    public void addGameEngine(GameEngine gameEngine) {

        if (gameEngine != null && engine == null) {
            add(gameEngine);
            engine = gameEngine;
        } else {
            System.out.println("Another engine was previously added. Aborting.");
        }

    }

    /**
     *
     * @param newDimension The requested Dimension wich the GameFrame should
     * try to change to.
     *
     * This method assign the gameFrameDimension field to the newDimension Dimension
     * instance, and it sets the GameFrame dimension if, and only if, the Java
     * Full Screen Exclusive Mode is turned off.
     *
     */
    public void setGameFrameDimension(Dimension newDimension) {

        if (newDimension != null) {
            gameFrameDimension = newDimension;
        }

        if (!isFullScreenModeOn) {
            setSize(gameFrameDimension);
        }

    }

    /**
     * This method gets the current GameEngine Dimension.
     *
     * @return gameFrameDimension - The current dimension of the GameEngine
     */
    public Dimension getGameFrameDimension() {
        return this.gameFrameDimension;
    }

    /**
     * This method makes the GameFrame not resizable and it attempts to made it
     * visible. If the Java Full Screen Exclusive Mode is turned on, it calls the
     * setFullScreenOn() method. Furthermore, it packs the GameFrame to its
     * GameEngine and finally makes it visible.
     *
     * @see #setFullScreenON()
     */
    public void showGameFrame() {

        if (isFullScreenModeOn) {
            setFullScreenON();
        } else {
            setFullScreenOFF();
        }
    }

    /**
     * This method checks if the Java Full Screen Exclusive Mode is avaible for
     * the windowing system and if it has not been activated yet. Then it attemps
     * to switch to the Java FSEM. It does nothing otherwise.
     */
    private void setFullScreenON() {

        if (isFullScreenModeSupported && !isFullScreenModeOn) {
            graphicsDevice.setFullScreenWindow(this);
            isFullScreenModeOn = true;
        } else {
            System.out.println("Error: Full Screen Exclusive Mode Not Supported.");
        }

    }

    /**
     * This method is used to switch to the windowed mode when the GameFrame
     * is running in the Java FSEM. It calls the setGameFrameDimension() passing
     * the gameFrameDimension Dimension object to it.
     *
     * @see #setGameFrameDimension(java.awt.Dimension)
     */
    private void setFullScreenOFF() {

        graphicsDevice.setFullScreenWindow(null);

        isFullScreenModeOn = false;

        setGameFrameDimension(gameFrameDimension);
        setVisible(true);
    }

    /**
     *
     * @param newDisplayMode The requested DisplayMode wich the GameFrame should
     * try to change to.
     *
     * This method is used to change the DisplayMode when the GameFrame is running
     * in FSEM.
     * It first checks if the newDisplayMode is in the avaibleDisplayModes, and if it is,
     * it attempts to change it.
     * If a RuntimeException occurs during the process, there will be an attempt
     * to recover the old DisplayMode. If that's not possible GameFrame will exit
     * from running in FSEM.
     */
    public void setFullScreenDisplayMode(DisplayMode newDisplayMode) {

        try {

            if (isFullScreenModeOn && areDisplayChangesSupported) {

                boolean isNewModeAvaible = false;

                for (DisplayMode dm : avaibleDisplayModes) {
                    if (newDisplayMode.equals(dm)) {
                        isNewModeAvaible = true;
                        break;
                    }
                }

                if (isNewModeAvaible) {
                    graphicsDevice.setDisplayMode(newDisplayMode);
                    currentDisplayMode = newDisplayMode;
                }

            } else {

                System.out.println("Nor FSEM on or display changes not supported");

            }

        } catch (RuntimeException runEx) {
            System.out.println("Error: Can't change to the specified DisplayMode. " +
                    "Trying to recover to the old DisplayMode...");
            try {
                graphicsDevice.setDisplayMode(currentDisplayMode);
            } catch (RuntimeException runEx2) {
                System.out.println("Error: Can't recover the old display mode. Turning of FSEM.");
                setFullScreenOFF();
            }
        }
    }

    /**
     * This method gets the current DisplayMode for the GameEngine instace.
     *
     * @return currentDisplayMode - the current DisplayMode
     */
    public DisplayMode getDisplayMode() {
        return currentDisplayMode;
    }

    /**
     * This method is used to get a GameFrame instance following the Single-Town model
     *
     * @return a GameFrame instance
     */
    public static GameFrame instantiate() {

        if (gameFrame == null) {
            gameFrame = new GameFrame();
        }

        return gameFrame;
    }
}
