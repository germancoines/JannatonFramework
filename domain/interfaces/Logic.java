

package domain.interfaces;

/**
 * This interface is intended to make the game logic a bit faster to develop.
 * Since a simple game only has a few states, they are all refered here.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public interface Logic {
    /**
     *
     * @return a boolean value indicating if the game instance is pausable or not.
     */
    public boolean isGamePauseable();
    /**
     *
     * @return a boolean value indicating if the game instance is overeable or not.
     */
    public boolean isGameOvereable();
    /**
     *
     * @return a boolean value indicating if the game instance is menuifiable or not.
     */
    public boolean isGameMenuifiable();
    /**
     *
     * @return a boolean value indicating if the game instance is multistaged or not.
     */
    public boolean isGameMultiStage();
    /**
     * This method should implement the pause logic for the game
     */
    public void toDoIfPaused();
    /**
     * This method should implement the game over logic for the game
     * At last it should call gameOver()
     */
    public void toDoIfOver();
    /**
     * This method should implement the menuified logic for the game.
     * At last it should call showMenu()
     */
    public void toDoIfMenuified();
    /**
     * This method should implement the completed stage logic for the game
     */
    public void toDoIfStageCompleted();

    /**
     * This method should implement the game paused requirement checkings.
     * if the Game is paused, a call to toDoIfPaused() should be invoked.
     *
     * @return true if the game is paused. false otherwise.
     */
    public boolean isGamePaused();
    /**
     * This method should implement the gameover requirement checkings
     * if the Game is over, a call to toDoIfOver() should be invoked.
     *
     * @return true if the game is over. false otherwise.
     */
    public boolean isGameOver();

    /**
     * This method should implement the game menuified requirement checkings
     * if the Game is menuified, a call to toDoIfMenuified() should be invoked.
     *
     * @return true if the game is over. false otherwise.
     */
    public boolean isGameMenuified();
    /**
     * This method should implement the stage complete requirement checkings.
     * If the stage is completed, a call to toDoIfStageCompleted() should be invoked.
     *
     * @return true if the stage is complete.
     */
    public boolean isStageCompleted();
}
