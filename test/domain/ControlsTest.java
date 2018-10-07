/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import domain.Collisions.CollisionType;
import domain.interfaces.Action;
import domain.interfaces.FiredAction;
import domain.interfaces.FiredActionable;
import java.util.ArrayList;
import java.util.HashMap;
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
public class ControlsTest {

    private static Controls controls;

    public ControlsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        controls = Controls.instantiate();
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

    @Test
    public void testInitialize() throws Exception {
        System.out.println("initialize");
        ArrayList<FiredActionable> firedActionables = new ArrayList<FiredActionable>();
        firedActionables.add(new FiredActionable() {

            public boolean hasActionFocus() {
                return true;
            }

            public void setActionFocus(boolean actionFocus) {

            }

            public String getActionableName() {
                return "no named";
            }

            public ArrayList<Action> getActions() {

                ArrayList<Action> actions = new ArrayList<Action>();
                actions.add(new FiredAction() {

                    public int getDefaultEventCode() {
                        return 54;
                    }

                    public String getActionName() {
                        return "action 1";
                    }

                    public CollisionType getCollisionTypeCausable() {
                        return CollisionType.TALKING;
                    }

                    public CollisionType getCollisionTypesReactionable() {
                        return CollisionType.ATTACKING;
                    }

                    public void executeAction() {
                        System.out.println("Talking...");
                    }

                    public void finishAction() {

                    }
                });
                actions.add(new FiredAction() {

                    public int getDefaultEventCode() {
                        return 55;
                    }

                    public String getActionName() {
                        return "action 1";
                    }

                    public CollisionType getCollisionTypeCausable() {
                        return CollisionType.TALKING;
                    }

                    public CollisionType getCollisionTypesReactionable() {
                        return CollisionType.ATTACKING;
                    }

                    public void executeAction() {
                        System.out.println("Talking...");
                    }

                    public void finishAction() {

                    }
                });

                return actions;
            }

            public void processAction(Action action) {
                action.executeAction();
            }

            public void processReaction(CollisionType collisionType, domain.Collisions.CollisionDirection collisionDirection) {
                System.out.println("Not implemented");
            }

            public void addAction(Action action) {
                System.out.println("Something added.");
            }
        });
        controls.initialize(firedActionables);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getFiredAction method, of class Controls.
     */
    @Test
    public void testGetFiredAction() {
        System.out.println("getFiredAction");
        int eventCode = 54;
        FiredAction expResult = controls.getFiredAction(eventCode);
        FiredAction result = controls.getFiredAction(eventCode);
        if(expResult == null || result == null)
            fail("expected or result are null");
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getEventCode method, of class Controls.
     */
    @Test
    public void testGetEventCode() {
        System.out.println("getEventCode");
        FiredAction firedAction = new FiredAction() {

                    public int getDefaultEventCode() {
                        return 55;
                    }

                    public String getActionName() {
                        return "action 1";
                    }

                    public CollisionType getCollisionTypeCausable() {
                        return CollisionType.TALKING;
                    }

                    public CollisionType getCollisionTypesReactionable() {
                        return CollisionType.ATTACKING;
                    }

                    public void executeAction() {
                        System.out.println("Talking...");
                    }

                    public void finishAction(){
                        
                    }
                };
        Integer expResult = 55;
        Integer result = controls.getEventCode(firedAction);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setKeyCodeForFiredAction method, of class Controls.
     */
    @Test
    public void testSetKeyCodeForFiredAction() {
        System.out.println("setKeyCodeForFiredAction");
        int oldKeyCode = 55;
        FiredAction fa = controls.getFiredAction(55);
        int newKeyCode = 60;
        controls.setKeyCodeForFiredAction(oldKeyCode, newKeyCode);
        FiredAction fa2 = controls.getFiredAction(60);
        if(newKeyCode != controls.getEventCode(fa2))
            fail("setKEyCodeForFiredAction didn't worked as expected!");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");

    }

    /**
     * Test of getFiredActions method, of class Controls.
     */
    @Test
    public void testGetFiredActions() {
        System.out.println("getFiredActions");
        FiredAction[] result = controls.getFiredActions();
        // TODO review the generated test code and remove the default call to fail.
        if(result != null)
            if(result.length == 0)
                fail("The aren't FiredActions? Maybe something is going wrong man!");
    }

    /**
     * Test of getEventCodes method, of class Controls.
     */
    @Test
    public void testGetEventCodes() {
        System.out.println("getEventCodes");
        Integer[] expResult = {54, 60};
        Integer[] result = controls.getEventCodes();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getControls method, of class Controls.
     */
    @Test
    public void testGetControls() {
        System.out.println("getControls");
        HashMap<Integer, FiredAction> expResult = new HashMap<Integer, FiredAction>();
        expResult.put(54, controls.getFiredAction(54));
        expResult.put(60, controls.getFiredAction(60));
        HashMap result = controls.getControls();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of instantiate method, of class Controls.
     */
    @Test
    public void testInstantiate() {
        System.out.println("instantiate");
        Controls expResult = controls;
        Controls result = Controls.instantiate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class Controls.
     */
    

}