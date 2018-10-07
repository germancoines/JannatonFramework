package userInterface;

import control.interfaces.Renderable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import timers.interfaces.JannatonTimer;

/**
 * <p>This class is the responsible of calculating the frames per second that the
 * GameEngine (or maybe other graphics engine) is offering. </p>
 *
 * <p>To get the current stats, it's assumed that at the <code>reportStats()</code>
 * method will be invoked only once at the engine loop (may be at the end, or at
 * the beggining). </br></br>
 *
 * Once the <code>reportStats()</code> has been called, then a call to <code>render()</code>
 * should be made in order to render the status results.
 * </p>
 *
 * @author German Coines Laguna
 */
public class Stats implements Renderable {

    /**
     * The maximum time that the Stats instance will take before processing another
     * invoked reportStats()
     */
    private long maxStatsInterval = 1000L;
    /**
     * The amount of frames per second results to calculate the average frames per second
     */
    private int numberFpsToAverage = 100;
    /**
     * The amount of time since the last reportStats() call was completely executed
     */
    private long statsInterval = 0L;
    /**
     * The time at wich the last reportStats() call was finished
     */
    private long prevStatsTime;
    /**
     * The total amount of time since the first reportStats() call was made
     */
    private long totalElapsedTime = 0L;
    /**
     * The total amount of frames displayed since the first reportStats() call was made
     */
    private long frameCount = 0;
    /**
     * An array of numberFpsToAverage length to store the double values that represents
     * the calculated frames per second. This field is used latter to calculate
     * the average frames per second.
     */
    private double fpsStore[] = new double[numberFpsToAverage];

    /**
     * The total status calcullated count
     */
    private long statsCount = 0;
    /**
     * The field to hold the calculated average frames per second
     */
    private double averageFPS = 0.0;
    /**
     * A DecimalFormat instance to format the fps and afps output
     */
    private DecimalFormat df = new DecimalFormat("0.#");
    /**
     * A DecimalFormat instance to format the elapsed time output
     */
    private DecimalFormat timeDf = new DecimalFormat("0.####");
    /**
     * The GameEngine requested period.
     */
    private long period;
    /**
     * The JannatonTimer instance used to take the time
     */
    private JannatonTimer timer;
    /**
     * The field that will hold the requested frames per second
     */
    private String requestedFps = "";
    /**
     * The field that will hold the timming error (in tant per cent)
     */
    private String timerStats = "";
    /**
     * The field that will hold the resulting frames per second
     */
    private String resultFps = "";
    /**
     * This field holds the color that theresult String will have when it gets
     * rendered.
     */
    private Color fontColor = Color.yellow;
    /**
     * The Graphics object to render the output.
     */
    private Graphics graphics;
    /**
     * The instance itself.
     */
    private static Stats stats;

    /**
     * Private Constructor. To get a Stats instance you should call the static method
     * <code>Stats.instantiate(JannatonTimer timer, long period, long refreshTime, int fpsCountToAverage)</code>
     *
     * @param timer The JannatonTimer to get the time
     * @param period The GameEngine required period
     * @param refreshTime The interval between each <code>reportStats()</code>
     * @param fpsCountToAverage The total amount of resulting frames per second to use in the average calculation.
     */
    private Stats(JannatonTimer timer, long period, long refreshTime, int fpsCountToAverage) {

        this.timer = timer;
        this.period = period;
        maxStatsInterval = refreshTime;
        numberFpsToAverage = fpsCountToAverage;

    }

    /**
     * This method provides a Stats instace following the "Single-town" model.
     *
     * @param timer The JannatonTimer to get the time
     * @param period The GameEngine required period
     * @param refreshTime The interval between each <code>reportStats()</code>
     * @param fpsCountToAverage The total amount of resulting frames per second to use in the average calculation.
     *
     * @return a Stats instance
     */
    public static Stats instantiate(JannatonTimer timer, long period, long refreshTime, int fpsCountToAverage){

        if(stats == null)
            stats = new Stats(timer, period, refreshTime, fpsCountToAverage);

        return stats;
    }

    /**
     * This method calculates the frames per second, the average frames per second,
     * and the timing error.
     *
     * In order to get correct results only and just one reportStats() call should
     * be made on each GameEngine loop iteration.
     */
    public void reportStats() {
        frameCount++;
        statsInterval += period;
        if (statsInterval >= maxStatsInterval) {
            long timeNow = timer.getTimeInstace();

            long realElapsedTime = timeNow - prevStatsTime;

            totalElapsedTime += realElapsedTime;
            long sInterval = (long) statsInterval * 1000000L;
            double timingError =
                    ((double) (realElapsedTime - sInterval)) / sInterval * 100.0;
            double actualFPS = 0;
            if (totalElapsedTime > 0) {
                actualFPS = (((double) frameCount / totalElapsedTime) * 1000000000L);
            }

            fpsStore[(int) statsCount % numberFpsToAverage] = actualFPS;
            statsCount++;
            double totalFPS = 0.0;
            for (int i = 0; i < numberFpsToAverage; i++) {
                totalFPS += fpsStore[i];
            }

            if (statsCount < numberFpsToAverage)
            {
                averageFPS = totalFPS / statsCount;
            } else {
                averageFPS = totalFPS / numberFpsToAverage;
            }

            requestedFps = "requested fps: " + 1000 / period + " period: " + period + "ms \n";
            timerStats = timeDf.format((double) statsInterval / 1000) + " " +
                    timeDf.format((double) realElapsedTime / 1000000000L) + "s\n" +
                    "timing error: " + df.format(timingError) + "%\n";
            resultFps = "total frames: " + frameCount + " fps: " + df.format(actualFPS) + " afps: " + df.format(averageFPS);

            prevStatsTime = timeNow;
            statsInterval = 0L;
        }
    }

    /**
     * This method is used to set the period. It's value will be calculated by passing
     * to the method the GameEngine frames per second.
     *
     * @param fps The required frames per second
     */
    protected void setPeriod(int fps){
        this.period = (long) 1000.0 / fps;
    }

    /**
     * Sets the timer instance.
     *
     * @param timer A JannatonTimer instance.
     */
    public void setTimer(JannatonTimer timer){

        if(timer != null)
            this.timer = timer;

    }

    /**
     * Sets the color that the Graphics object wich renders the output will use.
     *
     * @param color The desired Color for the output
     */
    public void setFontColor(Color color){
        this.fontColor = color;
    }

    /**
     * Since Stats implements the Renderable interface it can be rendered by the
     * GameEngine, so it's able to render it's result.
     * If the Stats graphics field is null, it will get the Graphics instance
     * passed by parameter in this method.
     *
     * @param g The Graphics instance that Stats will use.
     */
    public void render(Graphics g) {

        if(graphics != null){
        
            graphics.setFont(new Font("Arial", Font.BOLD, 14));
            graphics.setColor(fontColor);

            graphics.drawString(requestedFps, 20, 20);
            graphics.drawString(timerStats, 20, 40);
            graphics.drawString(resultFps, 20, 60);

        } else {
            graphics = g.create();
        }
    }
}
