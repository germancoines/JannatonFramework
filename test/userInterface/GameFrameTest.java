/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface;

import java.awt.Dimension;
import java.awt.DisplayMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author harmonicaherman
 */
public class GameFrameTest {

    public GameFrameTest() {
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
     * Test of addGameEngine method, of class GameFrame.
     */
    @Test
    public void testAddGameEngine() {
        System.out.println("addGameEngine");
        GameEngine gameEngine = GameEngine.instantiate();
        GameFrame instance = GameFrame.instantiate();
        instance.addGameEngine(gameEngine);
        instance.addGameEngine(gameEngine);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of setGameFrameDimension method, of class GameFrame.
     */
    @Test
    public void testSetGameFrameDimension() {
        System.out.println("setGameFrameDimension");
        Dimension newDimension = new Dimension(800, 600);
        GameFrame instance = GameFrame.instantiate();
        instance.setGameFrameDimension(newDimension);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of showGameFrame method, of class GameFrame.
     */
    @Test
    public void testShowGameFrame() {
        System.out.println("showGameFrame");
        GameFrame instance = GameFrame.instantiate();
        instance.showGameFrame();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setFullScreenDisplayMode method, of class GameFrame.
     */
    @Test
    public void testSetFullScreenDisplayMode() {
        System.out.println("setFullScreenDisplayMode");
        GameFrame instance = GameFrame.instantiate();
        DisplayMode newDisplayMode = instance.getDisplayMode();
        instance.setFullScreenDisplayMode(newDisplayMode);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of instantiate method, of class GameFrame.
     */
    @Test
    public void testInstantiate() {
        System.out.println("instantiate");
        GameFrame expResult = GameFrame.instantiate();
        GameFrame result = GameFrame.instantiate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
}