package userInterface;

import control.GameControler;
import control.interfaces.Renderable;
import control.interfaces.Updatable;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import timers.TimerNano;
import timers.interfaces.JannatonTimer;

/**
 *
 * This class acts as the game engine. It's the responsible of the updating,
 * rendering, and drawing Sprite processes. This three processes are continuosly
 * excecuted in a separated Thread, causing them to be other game processes
 * independent (if a non updating state is desired, it should be caused by setting
 * the updatables field to an empty updatables arrayList or overriding the update()
 * method from the updatables with control variables).
 *
 * This Thread is started when the GameEngine is added to a GameFrame or a GameApplet.
 *
 * The rendering tecnique implemented is Active Rendering, handling the possibility
 * of controlling the frames per second rate at the choice of the user. *
 *
 * @author Alberto Languiz
 * @author Germán Coines
 *
 */
public class GameEngine extends JPanel implements Runnable {

    //
    //Fields
    //
    /**
     * The GameEngine instance itself
     *
     * @see #instantiate()
     */
    private static GameEngine engine;
    /**
     * The GameControler instance asociated to this GameEngine.
     *
     * The GameController acts as a Control layer between the domain of the game
     * and the external events raised from the GameFrame, GameApplet or the
     * GameEngine itself.
     *
     * @see #addGameController(control.GameControler)
     *
     */
    private GameControler controller;
    /**
     * An ArrayList containing all the Updatable instances that the GameEngine
     * should update to, at the game updating state stage.
     *
     * @see control.interfaces.Updatable#update()
     * @see #gameUpdate()
     */
    private ArrayList<Updatable> updatables;
    /**
     * An ArrayList containing all the Drawables instances that the GameEngine
     * should render and render to, at the game render and render state stages.
     *
     * @see Renderable#render(java.awt.Graphics)
     * @see #gameDraw()
     */
    private ArrayList<Renderable> renderables;
    /**
     * This field holds the updatables 'upToDate' state. That means if this field
     * contains a false value, the updatables ArrayList is outdated. This field
     * should not be modified since the GameControler instance will do if it detects
     * that the updatable instances from the GameClient has been changed.
     * At every updating stage the GameEngine instace checks for this field to know
     * if it has to ask for the updated updatables ArrayList to the GameControler.
     *
     * @see #gameUpdate()
     * @see #setUpdatablesToOutDated() 
     * @see GameControler#getUpdatables()
     */
    private boolean updatablesOutDated = false;
    /**
     * This field holds the renderables 'upToDate' state. That means if this field
     * contains a false value, the renderables ArrayList is outdated. This field
     * should not be modified since the GameControler instance will do, if it detects
     * that the renderables instances from the GameClient has been changed.
     * At every drawing stage the GameEngine instace checks for this field to know
     * if it has to ask for the updated renderables ArrayList to the GameControler.
     */
    private boolean renderablesOutDated = false;
    /**
     * The Image Object used to obtain the Graphics object used at the rendering
     * stage. The GameEngine first creates that Image Object with it's width and
     * height values, and latter extracts the Graphics object used to render the
     * renderables to it. At the drawing stage, that Image object will be drawn to
     * the screen by the GameEngine graphics instace, replacing the old one.
     * This technique is called "DoubleBuffering".
     *
     * @see #gameDraw()
     * @see #gameRender()
     * @see Component#createImage(int, int) 
     */
    private Image image;
    /**
     * The Graphics object used to render by the GameEngine
     *
     * @see #gameDraw()
     */
    private Graphics graphics;
    /**
     * The GameEngine's width
     *
     * @see #setWidth(int)
     */
    private int width;
    /**
     * The GameEngine's height
     *
     * @see #setHeight(int)
     */
    private int height;
    /**
     * This field holds the Thread wich update, render, and render the updatables
     * and renderables instances in a continuous loop. By taking this processes in
     * a separated Thread we'll be able to control the desired FPS and UPS.
     * This technique is known as Active Rendering, since the rendering stage is
     * always 'active'
     *
     * @see #run()
     * @see #addNotify()
     */
    private Thread animator;
    /**
     * boolean field that indicates if the animation loop is currently running.
     * If it's value changes to false, the GameEngine will stop.
     *
     * @see #run()
     * @see #terminate()
     */
    private boolean running;
    /**
     * The desired Frames Per Second that the GameEngine instance should accomplish.
     * Obviously, the real FPS obtained will vary on the CPU and GPU demands.
     * The default value is 50 FPS, but this can be changed via the setFps() method
     *
     * @see #setFps(int)
     */
    private int fps = 60;
    /**
     * The time that the GameEngine should take in a loop to acomplish the desired
     * frames per second.
     * (in milliseconds)
     *
     * @see #updatePeriod()
     * @see #setFps(int)
     */
    private long period = (long) 1000.0 / fps;
    /**
     * This field indicates how many delays the GameEngine will permit before
     * calling <code>Thread.yield()</code> to allow other Threads to be
     * processed (if they need it).
     *
     * A delay is produced when the GameEngine loop doesn't finish within the
     * specified period.
     */
    private int numberOfDelaysPerYield = 16;
    /**
     *
     */
    private int maxFrameSkips = 16;
    /**
     * boolean field to control if the GameEngine status should be rendered and
     * drawn to the screen. (It's useful while the testing and development stages).
     *
     * @see #showEngineStatus()
     * @see #hideEngineStatus()
     */
    private boolean showingEngineStatus = false;
    /**
     * This field holds one of the JannatonTimer instances provided by the timers
     * package. In the current version we provide two implemented timers, the
     * TimerPerf and the TimerNano.
     *
     * @see TimerPerf
     * @see TimerNano
     */
    private JannatonTimer timer;
    /**
     * This field holds a reference to the Stats Object wich will perform the
     * necessary operations for showing the GameEngine status, if it's required.
     *
     * @see #showEngineStatus()
     * @see #hideEngineStatus()
     */
    private Stats stats;

    //
    // Constructors
    //
    /**
     * Private constructor. To get a GameEngine instance it shoud be requested
     * using the static method GameEngine.instantiate().
     *
     * It initializes the timer field using the TimerNano by default. We do that
     * because the TimerPerf will probably be removed by Sun.
     * It initializes the stats field and requests the focus for the GameEngine.
     * The key and mouse listeners are added here too.
     *
     * @see GameEngine#instantiate()
     */
    private GameEngine() {

        timer = TimerNano.instantiate();

        stats = Stats.instantiate(timer, period, 500, 10);


        setFocusable(true);
        requestFocus();

        addListeners();
    }

    //
    // Methods
    //
    /**
     * This method is used to get a GameEngine instance following the Single-Town model
     *
     * @return a GameEngine instance
     *
     */
    public static GameEngine instantiate() {

        if (engine == null) {
            engine = new GameEngine();
        }

        return engine;
    }

    /**
     * This method is called by the GameEngine constructor, and it adds the
     * Key and Mouse Listeners.
     *
     * The implemented Key and Mouse Adapters uses to pass the capturated Events
     * to the GameControler.
     *
     * In this version the KeyTyped event is not supportted due to EventProcessing
     * problems. That will be solved in a further release of our JannatonFrameWork.
     */
    private void addListeners() {

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ke) {
                controller.processKeyEvent(ke);
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                controller.processKeyEvent(ke);
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                controller.processMouseEvent(me);
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                controller.processMouseEvent(me);
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                controller.processMouseEvent(me);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                controller.processMouseEvent(me);
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                controller.processMouseEvent(me);
            }

            @Override
            public void mousePressed(MouseEvent me) {
                controller.processMouseEvent(me);
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                controller.processMouseEvent(me);
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent me) {
                controller.processMouseEvent(me);
            }
        });

    }

    /**
     * This method checks if the animator field is null. If it is, the GameEngine
     * will assignate a new Thread to this field passing itself as the Runnable
     * target and then, starts the Thread.
     *
     * In a simple way, this method starts the GameEngine updating, rendering,
     * and drawing loop.
     */
    protected void startEngine() {

        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }

    /**
     * This method is the heart of the GameEngine. It's invoked by the <code>animator.start()</code>
     * method in <code>GameEngine.startEngine()</code>.
     *
     * It implements the <code>run()</code> method from the implementing <code>Runnable</code> interface
     * making the updating rendering and drawing stages to execute continuosly by the animator Thread
     * until a control boolean variable <code>running</code> is set to false.
     *
     * The method initializes a few local variables to control the time that the loop iteration
     * takes, in order to accurate the period interval.
     *
     * When one loop iteration finishes, a sleepTime value is calculated
     * as here follows: <code>sleepTime = (period - timeDiff) - overSleepTime;</code> 
     * If the iteration loop takes less time than the desired period (that means 
     * the <code>sleepTime &gt; 0</code> ) the animator Thread will sleep a while
     * giving a chance to other processes or Threads to execute (The Garbage Collector,
     * for example).
     *
     * If the iteration loop has taken more time than the period
     * (<code>sleepTime $gt; 0</code>), then the excess interval is stored and the delay
     * count is incremented. If it reaches the <code>numberOfDelaysPerYield</code> value,
     * then <code>Thread.yield()</code> is invoked to give a chance to other Threads
     * to execute.
     * 
     *
     * @see GameEngine#gameUpdate()
     * @see GameEngine#gameRender()
     * @see GameEngine#gameDraw()
     *
     */
    public void run() {

        long beforeTime, timeDiff, afterTime, sleepTime, overSleepTime = 0L, excess = 0L;
        int delays = 0;

        beforeTime = timer.getTimeInstace();

        running = true;

        while (running) {

            gameUpdate();
            gameRender();
            gameDraw();

            afterTime = timer.getTimeInstace();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1000000L);
                } catch (InterruptedException interruptedException) {
                    System.out.println("Not resting.");
                }

                overSleepTime = (timer.getTimeInstace() - afterTime) - sleepTime;

            } else {
                excess -= sleepTime;
                overSleepTime = 0L;

                if (++delays >= numberOfDelaysPerYield) {
                    Thread.yield();
                    delays = 0;
                }
            }

            beforeTime = timer.getTimeInstace();

            int skips = 0;

            while ((excess > period) && (skips < maxFrameSkips)) {
                excess -= period;
                gameUpdate();
                skips++;
            }

        }
    }

    /**
     * This method overrides it's superClass (Jpanel) addNotify().
     * It actually calls to <code>super.addNotify()</code> and
     * <code>GameEngine.startEngine()</code>.
     *
     * That means that the GameEngine loop will start running when it's added
     * to it's parent JComponent.
     */
    @Override
    public void addNotify() {

        super.addNotify();
        startEngine();
    }

    /**
     * This method updates the Game by calling the update method from all the
     * Updatable elements in game.
     *
     * It firsts checks if the updatables are out dated or null (for if the flys!),
     * and if they are, the GameEngine will update its updatables instances as it's shown here
     * <code> if (updatablesOutDated || updatables == null) { this.setUpdatables(controller.getUpdatables());
     * updatablesOutDated = false;} </code>
     *
     * Once the updatables are updated, then all the instances (sprites) are updated.
     *
     * @see GameEngine#setUpdatables(java.util.ArrayList)
     * @see Updatable#update()
     * @see Sprite#update()
     */
    private void gameUpdate() {

        if (updatablesOutDated || updatables == null) {
            this.setUpdatables(controller.getUpdatables());
            updatablesOutDated = false;
        }

        if (updatables != null) {
            for (Updatable sprite : updatables) {
                sprite.update();
            }
        }

    }

    /**
     * This method renders the Game by calling the render method on all the
     * Renderable elements in game.
     *
     * It firsts checks if the renderables are outdated or null (for if the flys!),
     * and if they are, the GameEngine will update its renderables instances as it's shown here
     * <code> if (renderablesOutDated || renderables == null) { this.setRenderables(controller.getRenderables());
     * renderablesOutDated = false;} </code>
     *
     * Since the renderables are updated, the method checks if the Image object
     * holded by the <code>image</code> field is null. If it is, it creates the
     * image using <code>JPanel.createImage(int width, int height)</code> using
     * the GameEngine width and height. If image exists or if it's just newly
     * instantiated, a new Graphics object is created from it (while the old drawn
     * Image is yet on shown at the screen, like a DoubleBuffer).
     *
     * This Graphics instance fills the background and after that the Renderable
     * instances from the game (sprites) are all rendered.
     *
     * Finally, if the GameEngine is asked to show its status the method call the
     * <code>Stats.reportStats()</code> method and render its output to the upper
     * left corner.
     *
     * @see GameEngine#setRenderables(java.util.ArrayList)
     * @see Renderable#render()
     * @see Sprite#render()
     */
    private void gameRender() {

        if (renderablesOutDated || renderables == null) {
            this.setRenderables(controller.getRenderables());
            renderablesOutDated = false;
        }

        if (image == null) {
            image = createImage(width, height);
            if (image == null) {
                System.out.println("dbImage is null");
                return;
            } else {
                graphics = image.getGraphics();
            }
        }

        graphics.fillRect(0, 0, width, height);

        if (renderables != null) {
            for (Renderable sprite : renderables) {
                sprite.render(graphics);
            }
        } else {
            System.out.println("drawables in GameEngine is null!");
        }

        if (showingEngineStatus) {
            stats.reportStats();
            stats.render(graphics);
        }

    }

    /**
     * This method draws the rendered image to the GameEngine using it's Graphics
     * object.
     */
    private void gameDraw() {

        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (image != null)) {
                g.drawImage(image, 0, 0, null);
                g.dispose();
            }
            //The next line is needed by some OS wich currently doesn't synchronise
            //the display buffer.
            Toolkit.getDefaultToolkit().sync();
        } catch (Exception ex) {
            System.out.println("Graphics Error! :" + ex.getMessage());
        }
    }

    /**
     * This method sets the showingEngineStatus field to true, making the GameEngine
     * instance keep showing it's status.
     */
    public void showEngineStatus() {
        this.showingEngineStatus = true;
    }

    /**
     * This method sets the showingEngineStatus field to false, making the GameEngine
     * instance keep not showing it's status.
     */
    public void hideEngineStatus() {
        this.showingEngineStatus = false;
    }

    /**
     * This method gets the current frames per second desired value.
     *
     * @return fps - the current frames per second value.
     */
    public int getFps() {
        return fps;
    }

    /**
     * This method sets the current frames per second desired value and updates
     * the period.
     * It sets the <code>Stats</code> period too.
     *
     * @param fps - the required non negative frames per second value
     *
     * @see GameEngine#updatePeriod()
     * @see Stats#setPeriod(int)
     */
    public void setFps(int fps) {
        if (fps > 0) {
            this.fps = fps;
            this.stats.setPeriod(fps);
        }
        updatePeriod();
    }

    /**
     * This method returns the maximum number of frames that the GameEngine is
     * allowed to skip if the iteration loop exceeds the period.
     *
     * @return maxFrameSkips - the number of frames that GameEngine can skip
     */
    public int getMaxFrameSkips() {
        return maxFrameSkips;
    }

    /**
     * This method sets the maxFrameSkips field
     *
     * @param maxFrameSkips - the desired integer non-negative value.
     */
    public void setMaxFrameSkips(int maxFrameSkips) {
        if (maxFrameSkips > 0) {
            this.maxFrameSkips = maxFrameSkips;
        }
    }

    /**
     * This method returns the number of delays that the GameEngine will permit
     * before invoking <code>Thread.yield()</code>
     *
     * @return numberOfDelaysPerYield - the number of permitted delays
     */
    public int getNumberOfDelaysPerYield() {
        return numberOfDelaysPerYield;
    }

    /**
     * This method sets the number of delays that the GameEngine will permit
     * before invoking <code>Thread.yield()</code>
     *
     * @param numberOfDelaysPerYield - the desired integer non negative value
     */
    public void setNumberOfDelaysPerYield(int numberOfDelaysPerYield) {
        if (numberOfDelaysPerYield > 0) {
            this.numberOfDelaysPerYield = numberOfDelaysPerYield;
        }
    }

    /**
     * This method updates the period that a single loop iteration from the
     * GameEngine.run() method should take (in milliseconds)
     */
    private void updatePeriod() {
        this.period = (long) 1000.0 / fps;
    }

    /**
     * This method returns the renderables ArrayList.
     *
     * @return renderables - the Renderable ArrayList
     */
    public ArrayList<Renderable> getRenderables() {
        return renderables;
    }

    /**
     * This method sets the renderables ArrayList.
     *
     * @param renderables - a non null ArrayList of Renderable
     */
    private void setRenderables(ArrayList<Renderable> renderables) {
        if (renderables != null) {
            this.renderables = renderables;
        } else {
            System.out.println("Warning! Renderables not setted.");
        }
    }

    /**
     * This method returns the updatables ArrayList
     *
     * @return updatables
     */
    public ArrayList<Updatable> getUpdatables() {
        return updatables;
    }

    /**
     * This method sets the updatables ArrayList.
     *
     * @param updatables - a non null <code>rrayList of Updatable instances
     */
    private void setUpdatables(ArrayList<Updatable> updatables) {
        this.updatables = updatables;
    }

    /**
     * This method sets the GameEngine width
     *
     * @param width - the desired non negative width
     */
    public void setWidth(int width) {

        if (width > 0) {
            this.width = width;
        }
    }

    /**
     * This method sets the GameEngine height
     *
     * @param height - the desired non negative height
     */
    public void setHeight(int height) {

        if (height > 0) {
            this.height = height;
        }
    }

    /**
     * This method sets the GameEngine width and height as its preferred Dimension
     *
     * @param width - the desired non negative width
     * @param height - the desired non negative height
     *
     * @see JPanel#setPreferredSize(java.awt.Dimension) 
     */
    public void setEngineDimension(int width, int height) {
        setWidth(width);
        setHeight(height);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * This method tells the GameEngine that its updatables are outdated. Doing that
     * wil cause the updatables to update at the next updating stage.
     */
    public void setUpdatablesToOutDated() {
        this.updatablesOutDated = true;
    }

    /**
     * This method tells the GameEngine that its renderables are outdated. Doing that
     * wil cause the renderables to update at the next rendering stage.
     */
    public void setRenderablesToOutDated() {
        this.renderablesOutDated = true;
    }

    /**
     * This method adds a GameControler instance to the GameEngine. If another
     * GameControler instance was previously added, this method does nothing.
     *
     * @param gameController - the desired non null GameControler instance.
     */
    public void addGameController(GameControler gameController) {

        if (gameController != null && controller == null) {
            controller = gameController;
        } else {
            System.out.println("Another controller was previously added. Aborting.");
        }
    }

    /**
     * This method causes the updating rendering and drawing loop finish at the
     * end of the current iteration.
     */
    public void terminate() {
        running = false;
    }

    /**
     * This method is used for internal Testing purposes, you shouldn't use it.
     */
    public void testRun() {

        long beforeTime, timeDiff, afterTime, sleepTime, overSleepTime = 0L, excess = 0L;
        int delays = 0;

        beforeTime = System.nanoTime();

        int count = 0;

        while (count < 1000) {

            gameUpdate();
            gameRender();
            gameDraw();

            afterTime = System.nanoTime();
            timeDiff = afterTime - beforeTime;
            sleepTime = (period - timeDiff) - overSleepTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1000000L);
                } catch (InterruptedException interruptedException) {
                    System.out.println("Not resting.");
                }

                overSleepTime = (System.nanoTime() - afterTime) - sleepTime;

            } else {
                excess -= sleepTime;
                overSleepTime = 0L;

                if (++delays >= numberOfDelaysPerYield) {
                    Thread.yield();
                    delays = 0;
                }
            }

            beforeTime = System.nanoTime();

            int skips = 0;

            while ((excess > period) && (skips < numberOfDelaysPerYield)) {
                excess -= period;
                gameUpdate();
                skips++;
            }

            count++;
        }
    }
}
