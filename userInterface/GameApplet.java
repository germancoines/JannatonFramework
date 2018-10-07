package userInterface;

import java.awt.Dimension;
import javax.swing.JApplet;

/**
 *
 * This class acts as the game applet. It can't be resized.
 *
 * @author Alberto Languiz Polo
 * @author German Coines Laguna
 */
public class GameApplet extends JApplet {

    private static GameApplet applet;
    private GameEngine engine;

    private GameApplet() {
        
    }

    /**
     * @param gameEngine - The GameEngine instance
     *
     * This method append the engine GameEngine instance to the GameApplet
     *
     */
    public void addGameEngine(GameEngine gameEngine) {

        if (gameEngine != null && engine == null) {
            add(gameEngine);
            engine = gameEngine;
            setPreferredSize(engine.getPreferredSize());
        } else {
            System.out.println("Another engine was previously added. Aborting.");
        }

    }

    public static GameApplet instantiate() {

        if (applet == null) {
            applet = new GameApplet();
        }

        return applet;
    }

    public void setGameAppletDimension(Dimension newDimension) {

        if (newDimension != null) {
            setSize(newDimension);
        }

    }

    public void showGameApplet(){
        setVisible(true);
    }

    @Override
    public void init(){
        engine.startEngine();
    }

    @Override
    public void start(){

    }
    @Override
    public void destroy() {

        engine.terminate();
        super.destroy();

    }
}
