
package gameEnsamblers;

import control.GameControler;
import domain.GameClient;
import domain.OffLine;
import domain.StaticEnvironment;
import java.io.File;
import userInterface.GameEngine;
import userInterface.GameFrame;

/**
 * This class is provided to help fast-making a simple StaticEnvironmented
 * game.
 *
 * Passing a few parameters to its constructor, it's autoconfigured and ready to run.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public class StaticGameEnsambler {

    //
    // Fields
    //
    /**
     * The GameFrame instance in wich the game will be displayed.
     */
    private GameFrame gameFrame;
    /**
     * The GameEngine wich will perform the update and render operations.
     */
    private GameEngine gameEngine;
    
    /**
     * The GameClient instance
     */
    private GameClient gameClient;
    /**
     * The Environment to be setted up and running.
     */
    private StaticEnvironment environment;

    //
    // Constructor
    //
    /**
     *
     * This constructor sets the GameEnsambler and starts an OffLine game.
     *
     * @param gameEnviroment A StaticEnvironment instance (propperly setted, this
     * means that the game domain is all properly instanciated and setted.) wich
     * represents the domain of the game.
     *
     * @param windowWidth The desired window width
     * @param windowHeight The desired window height
     * @param framesPerSecond The desired frames per second
     * @param showEngineStatus true if the GameEngine status are requested to be displayed. false if not.
     *
     * @throws Exception if the passed windowWidth or windowHeight have negative or zero values.
     * @throws NullPointerException if the passed gameEnvironment is null.
     */
    public StaticGameEnsambler(StaticEnvironment gameEnviroment, int windowWidth, int windowHeight, int framesPerSecond,
            boolean showEngineStatus) throws Exception{

        gameFrame = GameFrame.instantiate();

        gameEngine = GameEngine.instantiate();

        if(windowWidth > 0 && windowHeight > 0)
            gameEngine.setEngineDimension(windowWidth, windowHeight);
        else
            throw new Exception("The width or height values are equals or smaller than 0. They should be bigger.");

        if(showEngineStatus)
            gameEngine.showEngineStatus();

        if(framesPerSecond > 0)
            gameEngine.setFps(framesPerSecond);
        else
            System.out.println("Invalid fps value. setted to defaults: " + gameEngine.getFps() + " fps.");

        gameClient = OffLine.instantiate();

        if(gameEnviroment != null)
            environment = gameEnviroment;
        else
            throw new NullPointerException("Environment is null");

        gameClient.initialize(environment);

        GameControler.instanciate(gameEngine, gameClient);

        gameFrame.add(gameEngine);

        gameFrame.pack();

        gameFrame.setVisible(true);
    }

    /**
     * This static method provides a help for loading files within the a game instance class.
     *
     * @param path The path of the file
     * @param requester The runtime class who uses this method ( as <code>this.getClass()</code> )
     *
     * @return The requested file if found.
     *
     * @throws NullPointerException if the file is not found
     */
    public static File loadFile(String path, Class requester) throws NullPointerException{

        File file;

        try {
            file = new File(requester.getResource(path).getPath());
        } catch (NullPointerException nullEx) {
            file = new File(path);
        }

        return file;
    }
}
