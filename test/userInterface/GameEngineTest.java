/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import control.GameControler;
import domain.Collisions.CollisionType;
import domain.Environment;
import domain.GameClient;
import domain.OffLine;
import domain.Player;
import domain.interfaces.Action;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persistence.State;
import static org.junit.Assert.*;

/**
 *
 * @author harmonicaherman
 */
public class GameEngineTest {


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


    public GameEngineTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {


    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of instantiate method, of class GameEngine.
     */
    @Test
    public void testInstantiate() {
        System.out.println("instantiate");
        GameEngine expResult = GameEngine.instantiate();
        GameEngine result = GameEngine.instantiate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if(expResult == null || result == null)
            fail("Not instantiated");
    }

    /**
     * Test of run method, of class GameEngine.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        GameClient client = OffLine.instantiate();
        World world = new World();
        world.setPlayer(new Player());
        client.initialize(world);
        GameEngine instance = GameEngine.instantiate();
        GameControler controler = GameControler.instanciate(instance, client);
        
        instance.testRun();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addNotify method, of class GameEngine.
     */
    @Test
    public void testAddNotify() {
        System.out.println("addNotify");
        GameEngine instance = GameEngine.instantiate();
        instance.addNotify();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of showEngineStatus method, of class GameEngine.
     */
    @Test
    public void testShowEngineStatus() {
        System.out.println("showEngineStatus");
        GameEngine instance = GameEngine.instantiate();
        instance.showEngineStatus();

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of hideEngineStatus method, of class GameEngine.
     */
    @Test
    public void testHideEngineStatus() {
        System.out.println("hideEngineStatus");
        GameEngine instance = GameEngine.instantiate();
        instance.hideEngineStatus();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getFps method, of class GameEngine.
     */
    @Test
    public void testGetFps() {
        System.out.println("getFps");
        GameEngine instance = GameEngine.instantiate();
        int expResult = 50;
        instance.setFps(expResult);
        int result = instance.getFps();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setFps method, of class GameEngine.
     */
    @Test
    public void testSetFps() {
        System.out.println("setFps");
        int fps = 80;
        GameEngine instance = GameEngine.instantiate();
        instance.setFps(fps);
        int result = instance.getFps();
        assertEquals(fps, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxFrameSkips method, of class GameEngine.
     */
    @Test
    public void testGetMaxFrameSkips() {
        System.out.println("getMaxFrameSkips");
        GameEngine instance = GameEngine.instantiate();
        int expResult = 5;
        instance.setMaxFrameSkips(expResult);
        int result = instance.getMaxFrameSkips();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setMaxFrameSkips method, of class GameEngine.
     */
    @Test
    public void testSetMaxFrameSkips() {
        System.out.println("setMaxFrameSkips");
        int maxFrameSkips = 10;
        GameEngine instance = GameEngine.instantiate();
        instance.setMaxFrameSkips(maxFrameSkips);
        int result = instance.getMaxFrameSkips();
        assertEquals(maxFrameSkips, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberOfDelaysPerYield method, of class GameEngine.
     */
    @Test
    public void testGetNumberOfDelaysPerYield() {
        System.out.println("getNumberOfDelaysPerYield");
        GameEngine instance = GameEngine.instantiate();
        int expResult = 16;
        int result = instance.getNumberOfDelaysPerYield();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setNumberOfDelaysPerYield method, of class GameEngine.
     */
    @Test
    public void testSetNumberOfDelaysPerYield() {
        System.out.println("setNumberOfDelaysPerYield");
        int numberOfDelaysPerYield = 20;
        GameEngine instance = GameEngine.instantiate();
        instance.setNumberOfDelaysPerYield(numberOfDelaysPerYield);
        int result = instance.getNumberOfDelaysPerYield();
        assertEquals(numberOfDelaysPerYield, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setWidth method, of class GameEngine.
     */
    @Test
    public void testSetWidth() {
        System.out.println("setWidth");
        int width = 800;
        GameEngine instance = GameEngine.instantiate();
        instance.setWidth(width);

        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setHeight method, of class GameEngine.
     */
    @Test
    public void testSetHeight() {
        System.out.println("setHeight");
        int height = 600;
        GameEngine instance = GameEngine.instantiate();
        instance.setHeight(height);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setUpdatablesToOutDated method, of class GameEngine.
     */
    @Test
    public void testSetUpdatablesToOutDated() {
        System.out.println("setUpdatablesToOutDated");
        GameEngine instance = GameEngine.instantiate();
        instance.setUpdatablesToOutDated();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");

    }

    /**
     * Test of setRenderablesToOutDated method, of class GameEngine.
     */
    @Test
    public void testSetDrawablesToOutDated() {
        System.out.println("setDrawablesToOutDated");
        GameEngine instance = GameEngine.instantiate();
        instance.setRenderablesToOutDated();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}