/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import domain.Collisions.CollisionDirection;
import domain.Collisions.CollisionType;
import domain.Environment;
import domain.GameClient;
import domain.OffLine;
import domain.Player;
import domain.Scenario;
import domain.Sprite;
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
import static org.junit.Assert.*;
import userInterface.GameEngine;

/**
 *
 * @author harmonicaherman
 */
public class GameControlerTest {

    private static GameEngine engine;
    private static GameClient client;
    private static GameControler controler;
    private static Environment environment;

    public static class World extends Environment {

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

        public boolean isGamePaused() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGameMenuified() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public static class TestPlayerSprite extends Player {

        public TestPlayerSprite() {
        }
    }

    public static class TestScenarioSprite extends Scenario {

        public TestScenarioSprite(Point pointRelativeToScreen, int width, int height, int zIndex) {
            super(pointRelativeToScreen, width, height, zIndex);
        }

        public void render(Graphics gr) {
            System.out.println("drawn");
        }

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

        public Point getScenarioCoordinates() {
            return new Point();
        }

        public Rectangle getRectangle() {
            return new Rectangle();
        }

        public Rectangle getVisibleArea() {
            return new Rectangle();
        }

        public int getZIndex() {
            return 0;
        }

        public void setZIndex(int zindex) {
            System.out.println("i");
        }

        public Rectangle getCollisionableArea() {
            return new Rectangle();
        }

        public Rectangle[] getCollisionableAreas() {
            return new Rectangle[0];
        }

        public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
            System.out.println("Collisioned !" + type.toString());
        }
    }

    public static class TestSprite extends Sprite {

        public void render(Graphics gr) {
            System.out.println("drawn");
        }

        public void update() {
//            throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("updated");
        }

        public void loadFromState() {
            //      throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("loaded");
        }

        public void saveToState() {
            //      throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("saved");
        }

        public void updateFromState(State state) {
            //      throw new UnsupportedOperationException("Not supported yet.");
            System.out.println("updated");
        }

        public State getState() {
            return new State();
        }

        public Point getScenarioCoordinates() {
            return new Point();
        }

        public Rectangle getRectangle() {
            return new Rectangle();
        }

        public Rectangle getVisibleArea() {
            return new Rectangle();
        }

        public int getZIndex() {
            return 0;
        }

        public void setZIndex(int zindex) {
            System.out.println("setted");
        }

        public Rectangle getCollisionableArea() {
            return new Rectangle();
        }

        public Rectangle[] getCollisionableAreas() {
            return new Rectangle[4];
        }

        public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
            System.out.println("collisioned");
        }

        public void receiveCollisionFromSprite(Sprite sprite, CollisionType type, CollisionDirection direction) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public GameControlerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        engine = GameEngine.instantiate();
        environment = new World();
        environment.setPlayer(new TestPlayerSprite());
        environment.setScenario(new TestScenarioSprite(new Point(0, 0), 800, 600, 0));
        ArrayList<Sprite> others = new ArrayList<Sprite>();
        others.add(new TestSprite());
        environment.setOthers(others);
        client = OffLine.instantiate();
        client.initialize(environment);
        controler = GameControler.instanciate(engine, client);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("tearDownClass");
    }

    @Before
    public void setUp() {
        System.out.println("setUp");
    }

    @After
    public void tearDown() {
        System.out.println("tearDown");
    }

    /**
     * Test of getUpdatables method, of class GameControler.
     */
    @Test
    public void testGetUpdatables() {
        System.out.println("getUpdatables");
        GameControler instance = controler;
        ArrayList expResult = controler.getUpdatables();
        ArrayList result = instance.getUpdatables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getRenderables method, of class GameControler.
     */
    @Test
    public void testGetDrawables() {
        System.out.println("getDrawables");
        GameControler instance = controler;
        ArrayList expResult = controler.getRenderables();
        ArrayList result = instance.getRenderables();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUpdatables method, of class GameControler.
     */
    @Test
    public void testSetUpdatables() {
        System.out.println("setUpdatables");
        GameControler instance = controler;
        GameEngine gameEngine = engine;
        Environment gameEnvironment = environment;
        instance.setUpdatables();
        gameEngine.testRun();
        ArrayList expecteds = instance.getUpdatables();
        ArrayList result = gameEngine.getUpdatables();
        ArrayList result1 = gameEnvironment.getUpdatables();
        assertEquals(expecteds, result);
        assertEquals(result, result1);
        assertEquals(result1, expecteds);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setRenderables method, of class GameControler.
     */
    @Test
    public void testSetDrawables() {
        System.out.println("setDrawables");
        GameControler instance = controler;
        GameEngine gameEngine = engine;
        Environment gameEnvironment = environment;
        instance.setRenderables();
        gameEngine.testRun();
        ArrayList expecteds = instance.getRenderables();
        ArrayList result = gameEngine.getRenderables();
        ArrayList result1 = gameEnvironment.getRenderables();
        assertEquals(expecteds, result);
        assertEquals(result, result1);
        assertEquals(result1, expecteds);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of processKeyEvent method, of class GameControler.
     */
    @Test
    public void testProcessKeyEvent() {
        System.out.println("processKeyEvent");
        KeyEvent ke = new KeyEvent(engine, KeyEvent.KEY_PRESSED, 0L, KeyEvent.CTRL_DOWN_MASK, KeyEvent.VK_F2, KeyEvent.CHAR_UNDEFINED);
        GameControler instance = controler;
        instance.processKeyEvent(ke);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of processMouseEvent method, of class GameControler.
     */
    @Test
    public void testProcessMouseEvent() {
        System.out.println("processMouseEvent");
        MouseEvent me = new MouseEvent(engine, MouseEvent.BUTTON1, 0L, MouseEvent.BUTTON1_MASK, 0, 0, 1, false);
        GameControler instance = controler;
        instance.processMouseEvent(me);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of instanciate method, of class GameControler.
     */
    @Test
    public void testInstanciate() {
        System.out.println("instanciate");
        GameEngine gameEngine = engine;
        GameClient gameClient = client;
        GameControler expResult = controler;
        GameControler result = GameControler.instanciate(gameEngine, gameClient);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
