/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import control.GameControler;
import control.interfaces.Renderable;
import control.interfaces.Updatable;
import domain.Collisions.CollisionDirection;
import domain.Collisions.CollisionType;
import domain.interfaces.Action;
import domain.interfaces.FiredAction;
import domain.interfaces.ReactedAction;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persistence.State;
import userInterface.GameEngine;
import static org.junit.Assert.*;

/**
 *
 * @author wida47875172
 */
public class GameClientTest {

    private static GameClient client;
    private static Environment environment;

    public static class World extends Environment{

        public void update() {
            System.out.println("updated");
        }

        public void loadFromState() {
            System.out.println("loaded");
        }

        public void saveToState() {
            System.out.println("saved");
        }

        public void updateFromState(State state) {
            System.out.println("updated");
        }

        public State getState() {
            return new State();
        }

        public boolean isGamePauseable() {
            return true;
        }

        public boolean isGameOvereable() {
            return true;
        }

        public boolean isGameMenuifiable() {
            return true;
        }

        public boolean isGameMultiStage() {
            return true;
        }

        public void toDoIfPaused() {
        }

        public void toDoIfOver() {
        }

        public void toDoIfMenuified() {
        }

        public void toDoIfStageCompleted() {
            System.out.println("stageComplete");
        }

        public boolean isGameOver() {
            return false;
        }

        public boolean isStageCompleted() {
            return false;
        }        

        public boolean hasActionFocus() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setActionFocus() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getActionableName() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public ArrayList<Action> getActions() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void processAction(Action action) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void processReaction(CollisionType collisionType) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void addAction(Action action) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGamePaused() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGameMenuified() {
            throw new UnsupportedOperationException("Not supported yet.");
        }


        }

    public static class TestPlayerSprite extends Player{

        public TestPlayerSprite(){
            
            addAction(new FiredAction() {

            public int getDefaultEventCode() {
                return KeyEvent.VK_RIGHT;
            }

            public String getActionName() {
                return "Test move Action";
            }

            public CollisionType getCollisionTypeCausable() {
                return CollisionType.MOVING;
            }

            public CollisionType getCollisionTypesReactionable() {
                return CollisionType.UNDEFINED;
            }

            public void executeAction() {
                System.out.println("IM " + getActionName() + ", and i'm doing my programmed action.");
            }

                public void finishAction() {

                }
        });
            addAction(new FiredAction() {

            public int getDefaultEventCode() {
                return KeyEvent.VK_LEFT;
            }

            public String getActionName() {
                return "second Test Action";
            }

            public CollisionType getCollisionTypeCausable() {
                return CollisionType.ATTACKING;
            }

            public CollisionType getCollisionTypesReactionable() {
                return CollisionType.MOVING;
            }

            public void executeAction() {
                System.out.println("ATTACKING!!!");
            }

                public void finishAction() {

                }
        });        
            addAction(new ReactedAction() {

            public String getActionName() {
                return "defend";
            }

            public CollisionType getCollisionTypeCausable() {
                return CollisionType.DEFENDING;
            }

            

            public void executeAction() {
                System.out.println("DEFENDING!!!");
            }

                public void finishAction() {

                }

                public CollisionDirection getDirectionReactionable() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public CollisionType[] getCollisionTypesReactionable() {
                    CollisionType[] collisions = new CollisionType[1];
                    collisions[0] = CollisionType.ATTACKING;
                    return collisions;
                }
        });
            addAction(new FiredAction() {

                public int getDefaultEventCode() {
                    return MouseEvent.BUTTON1;
                }

                public String getActionName() {
                    return "Testing mouse action";
                }

                public CollisionType getCollisionTypeCausable() {
                    return CollisionType.CATCHING;
                }

                public CollisionType getCollisionTypesReactionable() {
                    return CollisionType.UNDEFINED;
                }

                public void executeAction() {
                    System.out.println("Mouse action properly fired!");
                }

                public void finishAction() {

                }
            });
        }
    }

    public static class TestScenarioSprite extends Scenario{

         public TestScenarioSprite(){
            super(new Point(0,0), 50, 50, 0);
        }

        @Override
        public void render(Graphics gr) {
            System.out.println("drawn");
        }

        @Override
        public void update() {
            System.out.println("updated");
        }

        @Override
        public void loadFromState() {
            System.out.println("loaded from state");
        }

        @Override
        public void saveToState() {
            System.out.println("saved");
        }

        @Override
        public void updateFromState(State state) {
            System.out.println("updated");
        }

        @Override
        public State getState() {
            return new State();
        }

        @Override
        public Point getScenarioCoordinates() {
            return new Point();
        }

        public Rectangle getRectangle() {
            return new Rectangle();
        }

        @Override
        public Rectangle getVisibleArea() {
            return new Rectangle();
        }

        @Override
        public int getZIndex() {
            return 0;
        }

        @Override
        public void setZIndex(int zindex) {
            System.out.println("i");
        }

        @Override
        public Rectangle getCollisionableArea() {
            return new Rectangle();
        }

        @Override
        public Rectangle[] getCollisionableAreas() {
            return new Rectangle[0];
        }

        @Override
        public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
            System.out.println("Collisioned !" + type.toString());
        }

    }

    public static class TestSprite extends Sprite{

        @Override
        public void render(Graphics gr) {
            System.out.println("drawn");
        }

        @Override
        public void update() {
            System.out.println("updated");
        }

        public void loadFromState() {
            System.out.println("loaded from state");
        }

        public void saveToState() {
            System.out.println("saved");
        }

        public void updateFromState(State state) {
            System.out.println("updated");
        }

        public State getState() {
            return new State();
        }

        @Override
        public Point getScenarioCoordinates() {
            return new Point();
        }

        public Rectangle getRectangle() {
            return new Rectangle();
        }

        @Override
        public Rectangle getVisibleArea() {
            return new Rectangle();
        }

        @Override
        public int getZIndex() {
            return 0;
        }

        @Override
        public void setZIndex(int zindex) {
            System.out.println("i");
        }

        @Override
        public Rectangle getCollisionableArea() {
            return new Rectangle();
        }

        @Override
        public Rectangle[] getCollisionableAreas() {
            return new Rectangle[0];
        }

        public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
            System.out.println("Collisioned !" + type.toString());
        }

        public void receiveCollisionFromSprite(Sprite sprite, CollisionType type, CollisionDirection direction) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public static class GameClientImpl extends GameClient {


    }

    public GameClientTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        environment = new World();
        environment.setPlayer(new TestPlayerSprite());
        environment.setScenario(new TestScenarioSprite());
        ArrayList<Sprite> others = new ArrayList<Sprite>();
        others.add(new TestSprite());
        environment.setOthers(others);
        
        client = new GameClientImpl();
        client.initialize(environment);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {

        client = null;
        environment = null;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getPermittedControls method, of class GameClient.
     */
    @Test
    public void testGetPermittedControls() {
        System.out.println("getPermittedControls");
        
        int[] expResult = { MouseEvent.BUTTON1, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
        int[] result = client.getPermittedControls();

        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getUpdatables method, of class GameClient.
     */
    @Test
    public void testGetUpdatables() {
        System.out.println("getUpdatables");
        ArrayList<Updatable> expResult = new ArrayList<Updatable>();
        expResult.add(environment.getPlayer());
        expResult.add(environment.getScenario());
        for(Updatable updatable : environment.getOthers())
            expResult.add(updatable);

        ArrayList<Updatable> result = client.getUpdatables();
        assertArrayEquals(expResult.toArray(), result.toArray());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getRenderables method, of class GameClient.
     */
    @Test
    public void testGetDrawables() {
        System.out.println("getDrawables");
        ArrayList<Renderable> expResult = new ArrayList<Renderable>();
        expResult.add(environment.getPlayer());
        expResult.add(environment.getScenario());
        for(Renderable drawable : environment.getOthers())
            expResult.add(drawable);
        ArrayList result = client.getRenderables();
        assertArrayEquals(expResult.toArray(), result.toArray());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of executeKeyEvent method, of class GameClient.
     */
    @Test
    public void testExecuteKeyEvent() {
        System.out.println("executeKeyEvent");
        KeyEvent ke = new KeyEvent(GameEngine.instantiate(), KeyEvent.KEY_PRESSED, 0L,
                KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED);

        KeyEvent ke1 = new KeyEvent(GameEngine.instantiate(), KeyEvent.KEY_PRESSED, 0L,
                KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED);

        KeyEvent ke2 = new KeyEvent(GameEngine.instantiate(), KeyEvent.KEY_PRESSED, 0L,
                KeyEvent.SHIFT_DOWN_MASK, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED);

        client.executeKeyEvent(ke);
        client.executeKeyEvent(ke1);
        client.executeKeyEvent(ke2);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of executeMouseEvent method, of class GameClient.
     */
    @Test
    public void testExecuteMouseEvent() {
        System.out.println("executeMouseEvent");
        MouseEvent me = new MouseEvent(GameEngine.instantiate(), MouseEvent.MOUSE_CLICKED,
                0L, MouseEvent.CTRL_DOWN_MASK, 0, 0, 1, false, MouseEvent.BUTTON1);
        MouseEvent me1 = new MouseEvent(GameEngine.instantiate(), MouseEvent.MOUSE_CLICKED,
                0L, MouseEvent.CTRL_DOWN_MASK, 0, 0, 1, false, MouseEvent.BUTTON3);

        client.executeMouseEvent(me);
        client.executeMouseEvent(me1);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addGameController method, of class GameClient.
     */
    @Test
    public void testAddGameController() {
        System.out.println("addGameController");
        client.addGameController(GameControler.instanciate(GameEngine.instantiate(), client));
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    


}