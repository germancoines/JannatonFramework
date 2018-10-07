package domain;

import control.interfaces.Renderable;
import control.interfaces.Updatable;
import domain.interfaces.Collisionable;
import domain.interfaces.FiredActionable;
import domain.interfaces.Logic;
import java.awt.Dimension;
import java.util.ArrayList;
import persistence.interfaces.Statitzable;

/**
 * This class acts as the Environment wich contains all the game interacting instances.
 * It's as the 'world'. It's subclasses will specify diferent kinds of logic and 
 * positioning methods, and the global control variables for a game instance.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract class Environment implements Statitzable, Logic {

    //
    // Fields
    //
    /**
     * the Player instance for the current game
     */
    private Player player;
    /**
     * The Scenario instance for the current game
     */
    private Scenario scenario;
    /**
     * The rest of Sprite instances of the game
     */
    private ArrayList<Sprite> others;
    /**
     * The Menu instance for the game (if it has one)
     */
    private Menu menu;
    /**
     * The Phisics instance for this Environment
     */
    protected Phisics phisics;
    /**
     * The Dimension of the client (a GameEpplet or a GameFrame)
     */
    protected Dimension clientScreen;

    //
    // Constructors
    //
    /**
     * The default constructor. Initializes the others field an initializes and sets
     * the phisics field.
     *
     * @see Phisics#instantiate()
     * @see Phisics#setEnvironment(Environment)
     */
    public Environment() {

        others = new ArrayList<Sprite>();

        phisics = Phisics.instantiate();
        phisics.setEnvironment(this);

    }

    /**
     *
     * @return The Player instance for this Environment
     */
    protected Player getPlayer() {
        return player;
    }

    /**
     *
     * @return The Scenario instance for this Environment
     */
    protected Scenario getScenario() {
        return scenario;
    }

    /**
     *
     * @return The Menu instance for this Environment
     */
    protected Menu getMenu() {
        return menu;
    }

    /**
     *
     * @return The ArrayList containing the rest of Sprite instances for this Environment
     */
    protected ArrayList<Sprite> getOthers() {
        return others;
    }

    /**
     * This method is used to set the Player sprite.
     *
     * @param sprite The Player instance to be setted as the current player.
     * If the passed value is null this method does nothing.
     */
    public void setPlayer(Player sprite) {

        if (sprite != null) {
            player = sprite;
            player.environment = this;
        } else {
            System.out.println("WARNING! sprite is null. Player not assigned.");
        }

    }

    /**
     * This method is used to set the Scenario sprite.
     *
     * @param sprite The Scenario instance to be setted as the current Scenario.
     * If the passed value is null this method does nothing.
     */
    public void setScenario(Scenario sprite) {
        if (sprite != null) {
            scenario = sprite;
            scenario.environment = this;
        } else {
            System.out.println("WARNING! sprite is null. Scenario not assigned.");
        }
    }

    /**
     * This method is used to set the Menu sprite.
     *
     * @param sprite The Menu instance to be setted as the current Menu.
     * If the passed value is null this method does nothing.
     */
    public void setMenu(Menu sprite) {
        if (sprite != null) {
            menu = sprite;
            menu.environment = this;
        } else {
            System.out.println("WARNING! sprite is null. Menu not assigned.");
        }
    }

    /**
     * This method is used to set the others ArrayList sprites.
     *
     * @param sprites The ArrayList of Sprites to be setted as the current others.
     * If the passed value is null this method does nothing.
     */
    public void setOthers(ArrayList<Sprite> sprites) {
        if (sprites != null) {

            for (Sprite sprite : sprites) {
                if (sprite != null) {
                    sprite.environment = this;
                } else {
                    System.out.println("WARNING! a sprite instance at others is null.");
                    
                }
            }

            others = sprites;

        } else {
            System.out.println("WARNING! sprites is null. Others not assigned.");
        }
    }

    /**
     * This method is used to remove a Sprite instance from the others ArrayList.
     *
     * @param sprite The instance to be removed.
     */
    public void removeFromOthers(Sprite sprite) {

        others.remove(sprite);
    }

    /**
     * This method is used to add a Sprite instance to the others ArrayList.
     *
     * @param sprite The instance to be added. If the sprite is null, this method
     * does nothing.
     */
    public void addToOthers(Sprite sprite) {
        if (sprite != null) {
            others.add(sprite);
        } else {
            System.out.println("WARNING! The sprite instance is null. Not added.");
        }
    }

    /**
     *
     * @return The Updatable ArrayList updatables of this Environment.
     */
    public ArrayList<Updatable> getUpdatables() {
        ArrayList<Updatable> updatables = new ArrayList<Updatable>();

        if (player != null) {
            updatables.add((Updatable) player);
        } else {
            System.out.println("WARNING! Player is null. Not Added.");
        }

        if (scenario != null) {
            updatables.add((Updatable) scenario);
        } else {
            System.out.println("WARNING! Scenario is null. Not Added.");
        }

        if (others != null) {
            for (int i = 0; i < others.size(); i++) {
                if (others.get(i) != null) {
                    updatables.add((Updatable) others.get(i));
                } else {
                    System.out.println("WARNING! A Sprite in others is null. Not Added.");
                }
            }
        } else {
            System.out.println("WARINING! Others is null. Not Added.");
        }

        return updatables;

    }

    /**
     *
     * @return The Renderable ArrayList renderables of this Environment.
     */
    public ArrayList<Renderable> getRenderables() {
        ArrayList<Renderable> drawables = new ArrayList<Renderable>();

        if (player != null) {
            drawables.add((Renderable) player);
        } else {
            System.out.println("WARNING! Player is null. Not Added.");
        }

        if (scenario != null) {
            drawables.add((Renderable) scenario);
        } else {
            System.out.println("WARNING! Scenario is null. Not Added.");
        }

        if (others != null) {
            for (int i = 0; i < others.size(); i++) {
                if (others.get(i) != null) {
                    drawables.add((Renderable) others.get(i));
                } else {
                    System.out.println("WARNING! A Sprite in others is null. Not Added.");
                }
            }
        } else {
            System.out.println("WARNING! Others is null. Not Added.");
        }

        return drawables;
    }

    /**
     *
     * @return all the FiredActionable instances of the Environment.
     */
    public ArrayList<FiredActionable> getFiredActionables() {

        ArrayList<FiredActionable> firedActionables = new ArrayList<FiredActionable>();

        if (player != null) {
            firedActionables.add((FiredActionable) player);
        } else {
            System.out.println("WARNING! Player is null. Not Added.");
        }

        if (menu != null) {
            firedActionables.add((FiredActionable) menu);
        } else {
            System.out.println("WARNING! Menu is null. Not Added.");
        }

        if (others != null) {
            for (Sprite sprite : others) {
                if (sprite != null) {
                    if (sprite instanceof UserControlled) {
                        firedActionables.add((FiredActionable) sprite);
                    }
                } else {
                    System.out.println("WARNING! A Sprite in others is null. Skipped.");
                }
            }
        } else {
            System.out.println("WARNING! Others is null. Skipped.");
        }

        return firedActionables;
    }

    /**
     *
     * @return all the Collisionable instances of the Environment.
     */
    public ArrayList<Collisionable> getCollisionables() {

        ArrayList<Collisionable> collisionables = new ArrayList<Collisionable>();

        if (player != null) {
            collisionables.add(player);
        } else {
            System.out.println("WARNING! Player is null. Not added.");
        }
        if (scenario != null) {
            collisionables.add(scenario);
        } else {
            System.out.println("WARNING! Scenario is null. Not added.");
        }

        if (others != null) {
            for (Collisionable collisionable : others) {
                if (collisionable != null) {
                    collisionables.add(collisionable);
                } else {
                    System.out.println("WARNING! A Collisionable in others is null. Skipped.");
                }
            }
        } else {
            System.out.println("WARNING! Others is null. Not Added.");
        }

        return collisionables;
    }

    /**
     * This method is used to set the clientDimension field.
     *
     * @param clientDimension the new Dimension.
     */
    protected void setDimension(Dimension clientDimension) {

        clientScreen = new Dimension(clientDimension);
    }

    /**
     *
     * @return The Phisics instance assigned to this Environment.
     */
    public Phisics getPhisics() {
        return this.phisics;
    }
}
