/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import control.GameControlerTest;
import domain.ConfigurationTest;
import domain.GameClientTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import userInterface.GameEngineTest;
import userInterface.GameFrameTest;

/**
 *
 * @author harmonicaherman
 */
public class JannatonFrameworkTest {

    private static GameFrameTest gft;
    private static GameEngineTest get;
    private static GameControlerTest gct;
    private static GameClientTest gclt;
    private static ConfigurationTest config;

    public JannatonFrameworkTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        gft = new GameFrameTest();
        get = new GameEngineTest();
        gct = new GameControlerTest();
        gclt = new GameClientTest();
        config = new ConfigurationTest();
        
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testGameFrame(){
        try{
            gft.setUpClass();
        
            gft.testAddGameEngine();
            gft.testInstantiate();
            gft.testSetFullScreenDisplayMode();
            gft.testSetGameFrameDimension();
            gft.testShowGameFrame();

            gft.tearDownClass();
        } catch(Exception ex){}
    }

    @Test
    public void testGameEngine(){

        try{
            get.setUpClass();

            get.testAddNotify();
            get.testGetFps();
            get.testGetMaxFrameSkips();
            get.testGetNumberOfDelaysPerYield();
            get.testHideEngineStatus();
            get.testInstantiate();
            get.testRun();
            get.testSetDrawablesToOutDated();
            get.testSetFps();
            get.testSetHeight();
            get.testSetMaxFrameSkips();
            get.testSetNumberOfDelaysPerYield();
            get.testSetUpdatablesToOutDated();
            get.testSetWidth();
            get.testShowEngineStatus();

            get.tearDownClass();
        } catch (Exception ex){}
        
    }

    @Test
    public void testGameController(){

        try{
            gct.setUpClass();

            gct.testGetDrawables();
            gct.testGetUpdatables();
            gct.testInstanciate();
            gct.testProcessKeyEvent();
            gct.testProcessMouseEvent();
            gct.testSetDrawables();
            gct.testSetUpdatables();

            gct.tearDownClass();

        } catch (Exception ex) {}
        
    }

    @Test
    public void testGameClient(){

        try{

            gclt.setUpClass();

            gclt.testGetPermittedControls();
            gclt.testGetUpdatables();
            gclt.testGetDrawables();
            gclt.testExecuteKeyEvent();
            gclt.testExecuteMouseEvent();
            gclt.testAddGameController();

            gclt.tearDownClass();

        } catch (Exception ex) {}

    }

    @Test
    public void testConfiguration(){

        try{
            config.setUpClass();
            
            config.testInstantiate();
            config.testGetControls();
            config.testGetDisplay();
            config.testGetSound();
            config.testGetFiredAction();
            
            config.tearDownClass();
        } catch (Exception ex){}
    }
}