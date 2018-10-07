/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

import domain.Collisions.CollisionDirection;
import domain.Collisions.CollisionType;
import domain.interfaces.Action;
import domain.interfaces.Collisionable;
import domain.interfaces.FiredAction;
import domain.interfaces.ReactedAction;
import java.awt.Dimension;
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
import static org.junit.Assert.*;
import persistence.State;

/**
 *
 * @author German
 */
public class CollisionsTest {

    private static Environment environment;
    private static Collisions collisions;
    private static Phisics phisics;

    private static class World extends Environment{

        public void update() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void loadFromState() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void saveToState() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void updateFromState(State state) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public State getState() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGamePauseable() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGameOvereable() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGameMenuifiable() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGameMultiStage() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void toDoIfPaused() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void toDoIfOver() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void toDoIfMenuified() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void toDoIfStageCompleted() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGameOver() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isStageCompleted() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGamePaused() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean isGameMenuified() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
    private static class TestPlayerSprite extends Player{

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
                    throw new UnsupportedOperationException("Not supported yet.");
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
                    throw new UnsupportedOperationException("Not supported yet.");
                }
        });        
            addAction(new ReactedAction() {

            public String getActionName() {
                return "defend";
            }

            public CollisionType getCollisionTypeCausable() {
                return CollisionType.DEFENDING;
            }

            public CollisionType[] getCollisionTypesReactionable() {
                CollisionType[] collisions = new CollisionType[1];
                collisions[0] = CollisionType.ATTACKING;
                return collisions;
            }

            public void executeAction() {
                System.out.println("DEFENDING!!!");
            }

                public void finishAction() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public CollisionDirection getDirectionReactionable() {
                    throw new UnsupportedOperationException("Not supported yet.");
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
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });
            
            
        }
        
        @Override
        public Point getScenarioCoordinates() {
                return new Point(25,25);
            }

        @Override
            public Rectangle getVisibleArea() {
                return new Rectangle(new Point(0,0), new Dimension(60, 60));
            }

        @Override
            public int getZIndex() {
                return 0;
            }

        @Override
            public Rectangle getCollisionableArea() {
                return new Rectangle(new Point(25,25), new Dimension(10, 10));
            }

        @Override
            public Rectangle[] getCollisionableAreas() {
                Rectangle[] collisionableAreas = new Rectangle[4];

                collisionableAreas[0] = new Rectangle(new Point(20,20), new Dimension(5,5));
                collisionableAreas[1] = new Rectangle(new Point(35,20), new Dimension(5,5));
                collisionableAreas[2] = new Rectangle(new Point(20,35), new Dimension(5,5));
                collisionableAreas[3] = new Rectangle(new Point(35,35), new Dimension(5,5));

                return collisionableAreas;
            }

        @Override
            public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
                System.out.println("I received a collision from " + sprite.getClass().getSimpleName() + " with a collision type " +
                        type.toString());
            }
    }
    private static class TestScenarioSprite extends Scenario{

        public TestScenarioSprite(){
            super(new Point(0,0), 50, 50, 0);
        }

        @Override
        public Point getScenarioCoordinates() {
                return new Point(0,0);
            }

        @Override
            public Rectangle getVisibleArea() {
                return new Rectangle(new Point(0,0), new Dimension(600,600));
            }

        @Override
            public int getZIndex() {
                return 0;
            }

        @Override
            public Rectangle getCollisionableArea() {
                return new Rectangle(new Point(0,0), new Dimension(600, 600));
            }

        @Override
            public Rectangle[] getCollisionableAreas() {
                Rectangle[] collisionableAreas = new Rectangle[4];

                collisionableAreas[0] = new Rectangle(new Point(0,20), new Dimension(5,5));
                collisionableAreas[1] = new Rectangle(new Point(35,20), new Dimension(5,5));
                collisionableAreas[2] = new Rectangle(new Point(20,35), new Dimension(5,5));
                collisionableAreas[3] = new Rectangle(new Point(35,35), new Dimension(5,5));

                return collisionableAreas;
            }

        @Override
            public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
                System.out.println("I received a collision from " + sprite.getClass().getSimpleName() + " with a collision type " +
                        type.toString());
            }

    }
    private static class TestSprite extends AIControlled{

        @Override
        public Point getScenarioCoordinates() {
                return new Point(25,25);
            }

        @Override
            public Rectangle getVisibleArea() {
                return new Rectangle(new Point(0,0), new Dimension(60, 60));
            }

        @Override
            public int getZIndex() {
                return 0;
            }

        @Override
            public Rectangle getCollisionableArea() {
                return new Rectangle(new Point(25,25), new Dimension(10, 10));
            }

        @Override
            public Rectangle[] getCollisionableAreas() {
                Rectangle[] collisionableAreas = new Rectangle[4];

                collisionableAreas[0] = new Rectangle(new Point(20,20), new Dimension(5,5));
                collisionableAreas[1] = new Rectangle(new Point(35,20), new Dimension(5,5));
                collisionableAreas[2] = new Rectangle(new Point(20,35), new Dimension(5,5));
                collisionableAreas[3] = new Rectangle(new Point(35,35), new Dimension(5,5));

                return collisionableAreas;
            }

            public void receiveCollisionFromSprite(Sprite sprite, CollisionType type) {
                System.out.println("I received a collision from " + sprite.getClass().getSimpleName() + " with a collision type " +
                        type.toString());
            }

        public void loadFromState() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void saveToState() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void updateFromState(State state) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public State getState() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }


    public CollisionsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
        environment = new World();
        environment.setPlayer(new TestPlayerSprite());
        environment.setScenario(new TestScenarioSprite());
        ArrayList<Sprite> others = new ArrayList<Sprite>();
        others.add(new TestSprite());
        environment.setOthers(others);

        phisics = Phisics.instantiate();

        if(phisics.getEnvironment() == null)
            phisics.setEnvironment(environment);

        collisions = Collisions.instantiate(phisics);
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
     * Test of checkCollisionableIntersectionCollision method, of class Collisions.
     */
    @Test
    public void testCheckCollisionableCollision() {
        System.out.println("checkCollisionableCollision");

        boolean expResult = true;
        boolean result = Collisions.checkCollisionableIntersectionCollision(environment.getPlayer(), environment.getScenario());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkCollisionableIntersectionCollision method, of class Collisions.
     */
    @Test
    public void testCheckCollisionableCollision_Collisionable_Action() {
        System.out.println("checkCollisionableCollision");
        Collisionable causable = environment.getPlayer();
        Action action = new Action() {

            public String getActionName() {
                return "shoot";
            }

            public CollisionType getCollisionTypeCausable() {
                return CollisionType.ATTACKING;
            }

            public CollisionType getCollisionTypesReactionable() {
                return CollisionType.UNDEFINED;
            }

            public void executeAction() {
                System.out.println("I'm shooting somewhere.");
            }

            public void finishAction() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        boolean expResult = true;
        boolean result = Collisions.checkCollisionableIntersectionCollision(causable, action);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkCollisionableIntersectionCollision method, of class Collisions.
     */
    @Test
    public void testCheckCollisionableCollision_3args() {
        System.out.println("checkCollisionableCollision");
        Collisionable causable = environment.getPlayer();
        Action action = new Action() {

            public String getActionName() {
                return "shoot";
            }

            public CollisionType getCollisionTypeCausable() {
                return CollisionType.ATTACKING;
            }

            public CollisionType getCollisionTypesReactionable() {
                return CollisionType.UNDEFINED;
            }

            public void executeAction() {
                System.out.println("I'm shooting somewhere.");
            }

            public void finishAction() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        Tile tile = new Tile();
        boolean expResult = false;
        boolean result = Collisions.checkCollisionableTileCollision(causable, action, tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkPointCollision method, of class Collisions.
     */
    @Test
    public void testCheckPointCollision_Collisionable_Point() {
        System.out.println("checkPointCollision");
        Collisionable c = environment.getOthers().get(0);
        Point p = new Point(100,25);
        boolean expResult = false;
        boolean result = Collisions.checkPointCollision(c, p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkPointCollision method, of class Collisions.
     */
    @Test
    public void testCheckPointCollision_Point() {
        System.out.println("checkPointCollision");
        Point point = new Point(30, 30);
        boolean expResult = true;
        boolean result = Collisions.checkPointCollision(point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of checkTileCollision method, of class Collisions.
     */
    @Test
    public void testCheckTileCollision() {
        System.out.println("checkTileCollision");
        Tile tile = new Tile(environment.getScenario(), environment.getPlayer());
        boolean expResult = true;
        boolean result = Collisions.checkTileCollision(tile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getVisibleCollisionables method, of class Collisions.
     */
    @Test
    public void testGetVisibleCollisionables() {
        System.out.println("getVisibleCollisionables");
        Collisionable visionable = environment.getPlayer();
        ArrayList<Collisionable> expResult = new ArrayList<Collisionable>();
        //expResult.add(environment.getPlayer());
        //expResult.add(environment.getScenario());
        expResult.add(environment.getScenario());
        expResult.add(environment.getOthers().get(0));
        ArrayList result = Collisions.getVisibleCollisionables(visionable);
        assertArrayEquals(expResult.toArray(), result.toArray());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCollisionablesInArea method, of class Collisions.
     */
    @Test
    public void testGetCollisionablesInArea() {
        System.out.println("getCollisionablesInArea");
        Rectangle area = new Rectangle(new Point(0,0), new Dimension(200,200));
        ArrayList<Collisionable> expResult = new ArrayList<Collisionable>();
        expResult.add(environment.getPlayer());
        expResult.add(environment.getOthers().get(0));
        ArrayList result = Collisions.getCollisionablesInArea(area);
        assertArrayEquals(expResult.toArray(), result.toArray());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCollisionableAtPoint method, of class Collisions.
     */
    @Test
    public void testGetCollisionableAtPoint() {
        System.out.println("getCollisionableAtPoint");
        Point point = new Point(26,26);
        Collisionable expResult = environment.getPlayer();
        Collisionable result = Collisions.getCollisionableAtPoint(point);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of setCollisionablesToOutdated method, of class Collisions.
     */
    @Test
    public void testSetCollisionablesToOutdated() {
        System.out.println("setCollisionablesToOutdated");
        collisions.setCollisionablesToOutdated();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}