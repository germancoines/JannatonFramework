
package domain;

import control.interfaces.Renderable;
import control.interfaces.Updatable;
import domain.ImagesManager.ImagesAnimator;
import domain.SoundsManager.AudioClipPlayer;
import domain.SoundsManager.ClipPlayer;
import domain.interfaces.Collisionable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import persistence.interfaces.Statitzable;

/**
 * This class acts as a game entity wich should be updated and rendered.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 */
public abstract class Sprite implements Renderable, Updatable, Statitzable, Collisionable {

    //
    // Fields
    //
    /**
     * Enumeration holding diferent standart kinds of positioning.
     */
    protected static enum ON_SCREEN_POSITION {

        TOP_LEFT, TOP, TOP_RIGHT, MIDDLE_RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, MIDDLE_LEFT, CENTRATED
    };
    /**
     * The Environment reference for the Sprite instance
     */
    protected Environment environment;
    /**
     * A Media reference to allow the Sprite playing sounds, and image animations.
     */
    protected Media mediaManager;
    /**
     * The current BufferedImage that graphically represents this Sprite instance.
     */
    protected BufferedImage image;
    /**
     * an ImagesAnimator instance wich is the responsible of changing the Sprite
     * image in an animated sequence.
     */
    protected ImagesAnimator animator;
    /**
     * An AudioClipPlayer wich is the responsible of playing AudioClip instances.
     */
    protected AudioClipPlayer audioClipPlayer;
    /**
     * A ClipPlayer wich is the responsible of playing AudioClip instances.
     */
    protected ClipPlayer clipPlayer;
    /**
     * The current position of the Sprite instance relative to the upper-left corner
     * of the window represented by a Point.
     */
    protected Point pointRelativeToScreen;
    /**
     * The current position of the Sprite instance relative to the upper-left corner
     * of the Scenario instance represented by a Point.
     */
    protected Point pointRelativeToScenario;
    /**
     * The Sprite's width
     */
    protected int width;
    /**
     * The Sprite's height
     */
    protected int height;
    /**
     * The Sprite's superposition indicator. This value is used by the Collisions
     * class to know at wich layer a Sprite instance is. In a next release this field
     * will be used to order the Renderable instances at the Environment.renderables
     * ArrayList.
     */
    protected int zIndex;
    /**
     * This field holds additional Rectangle instances wich can be used to detect
     * a more accurated collisions or simply to determine any collisionable area for
     * the Sprite instance.
     */
    protected Rectangle[] collisionableAreas;
    /**
     * This value determines wich is the visible area for this Sprite.
     */
    protected int visibilityRange;

    //
    //Constructors
    //
    /**
     * The default Sprite Constructor. It sets the relativeToScreen cordinates at 0,0
     * and sets the relativeToScenario = relativeToScreen. The mediaManager is initialized
     * and the collisionableAreas too.
     */
    protected Sprite() {

        pointRelativeToScreen = new Point(0, 0);
        pointRelativeToScenario = pointRelativeToScreen;

        mediaManager = Media.instantiate();

        this.collisionableAreas = new Rectangle[0];
    }

    /**
     * This constructor sets the relativeToScreen cordinates at the relativeToScreen value
     * and sets the relativeToScenario = relativeToScreen. The mediaManager is initialized
     * and the collisionableAreas too. The width, height, and zIndex values are
     * assigned.
     *
     * @param relativeToScreen The current position of the Sprite instance relative to the upper-left corner
     * of the window represented by a Point.
     * @param width The Sprite's width
     * @param height The Sprite's height
     * @param zIndex The Sprite's superposition indicator
     */
    protected Sprite(Point relativeToScreen, int width, int height, int zIndex) {

        mediaManager = Media.instantiate();

        this.pointRelativeToScreen = relativeToScreen;
        this.pointRelativeToScenario = pointRelativeToScreen;

        this.width = width;
        this.height = height;

        this.zIndex = zIndex;

        this.collisionableAreas = new Rectangle[0];

    }

    /**
     * This constructor sets the relativeToScreen cordinates at the relativeToScreen value
     * and sets the relativeToScenario at the relativeToScenario. The mediaManager is initialized
     * and the collisionableAreas too. The width, height, and zIndex values are
     * assigned.
     *
     * @param relativeToScreen The current position of the Sprite instance relative to the upper-left corner
     * of the window represented by a Point.
     * @param relativeToScenario The current position of the Sprite instance relative to the upper-left corner
     * of the Scenario instance represented by a Point.
     * @param width The Sprite's width
     * @param height The Sprite's height
     * @param zIndex The Sprite's superposition indicator
     */
    protected Sprite(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex) {

        mediaManager = Media.instantiate();

        this.pointRelativeToScreen = relativeToScreen;
        this.pointRelativeToScenario = relativeToScenario;

        this.width = width;
        this.height = height;

        this.zIndex = zIndex;

        this.collisionableAreas = new Rectangle[0];

    }

    /**
     * This constructor sets the relativeToScreen cordinates at the relativeToScreen value
     * and sets the relativeToScenario = relativeToScreen. The mediaManager is initialized
     * and the collisionableAreas are initialized with the collisionableAreas value. The width,
     * height, and zIndex values are assigned.
     *
     * @param relativeToScreen The current position of the Sprite instance relative to the upper-left corner
     * of the window represented by a Point.
     * @param width The Sprite's width
     * @param height The Sprite's height
     * @param zIndex The Sprite's superposition indicator
     * @param collisionableAreas The collisionable areas to append using the <code>addCollisionableAreas()</code>
     * method.
     *
     * @see #addCollisionableAreas(java.awt.Rectangle[])
     */
    protected Sprite(Point relativeToScreen, int width, int height, int zIndex, Rectangle[] collisionableAreas) {

        mediaManager = Media.instantiate();

        this.pointRelativeToScreen = relativeToScreen;
        this.pointRelativeToScenario = pointRelativeToScreen;

        this.width = width;
        this.height = height;

        this.zIndex = zIndex;

        this.collisionableAreas = new Rectangle[0];

        addCollisionableAreas(collisionableAreas);
    }

    /**
     * This constructor sets the relativeToScreen cordinates at the relativeToScreen value
     * and sets the relativeToScenario at the relativeToScenario. The mediaManager is initialized
     * and the collisionableAreas are initialized with the collisionableAreas value.
     * The width, height, and zIndex values are assigned.
     *
     * @param relativeToScreen The current position of the Sprite instance relative to the upper-left corner
     * of the window represented by a Point.
     * @param relativeToScenario The current position of the Sprite instance relative to the upper-left corner
     * of the Scenario instance represented by a Point.
     * @param width The Sprite's width
     * @param height The Sprite's height
     * @param zIndex The Sprite's superposition indicator
     * @param collisionableAreas The collisionable areas to append using the <code>addCollisionableAreas()</code>
     * method.
     *
     * @see #addCollisionableAreas(java.awt.Rectangle[])
     */
    protected Sprite(Point relativeToScreen, Point relativeToScenario, int width, int height, int zIndex, Rectangle[] collisionableAreas) {

        mediaManager = Media.instantiate();

        this.pointRelativeToScreen = relativeToScreen;
        this.pointRelativeToScenario = relativeToScenario;

        this.width = width;
        this.height = height;

        this.zIndex = zIndex;

        this.collisionableAreas = new Rectangle[0];

        addCollisionableAreas(collisionableAreas);
    }

    /**
     * This method is used to help finding a Point relativeToScreen
     *
     * @param relativeToScreenPosition The position from the enumeration ON_SCREEN_POSITION from wich
     * the Point value is requested.
     *
     * @return The relative to screen Point for the specified ON_SCREEN_POSITION value
     */
    public Point findOnScreenPosition(ON_SCREEN_POSITION relativeToScreenPosition) {

        Dimension screenDimension = environment.clientScreen;

        switch (relativeToScreenPosition) {
            case TOP_LEFT:
                return new Point(0, 0);

            case TOP:
                int topX = (screenDimension.width - this.width) / 2;
                return new Point(topX, 0);

            case TOP_RIGHT:
                return new Point(screenDimension.width - this.width, 0);

            case MIDDLE_RIGHT:
                int middleY = (screenDimension.height - this.height) / 2;
                return new Point(screenDimension.width - this.width, middleY);

            case BOTTOM_RIGHT:
                return new Point(screenDimension.width - this.width, screenDimension.height - this.height);

            case BOTTOM:
                int bottomX = (screenDimension.width - this.width) / 2;
                return new Point(bottomX, screenDimension.height - this.height);

            case BOTTOM_LEFT:
                return new Point(0, screenDimension.height - this.height);

            case MIDDLE_LEFT:
                middleY = (screenDimension.height - this.height) / 2;
                return new Point(0, middleY);

            case CENTRATED:
                topX = (screenDimension.width - this.width) / 2;
                middleY = (screenDimension.height - this.height) / 2;
                return new Point(topX, middleY);

            default:
                return new Point(0, 0);
        }

    }

    /**
     * The implementation method for the Renderable interface. Makes the sprite
     * drawing its current image at the pointRelativeToScreen position.
     *
     * @param gr The Graphics object wich will perform the rendering.
     */
    public void render(Graphics gr) {
        gr.drawImage(image, pointRelativeToScreen.x, pointRelativeToScreen.y, null);
    }

    /**
     *
     * @return The current pointRelativeToScreen for this Sprite.
     */
    public Point getScreenCoordinates() {
        return this.pointRelativeToScreen;
    }

    /**
     * This method sets the current screen coordinates for this Sprite.
     *
     * @param x The new pointRelativeToScreen.x value
     * @param y The new pointRelativeToScreen.y value
     */
    protected void setScreenCoordinates(int x, int y) {
        this.pointRelativeToScreen.setLocation(x, y);
    }

    /**
     * This method sets the current screen coordinates for this Sprite.
     *
     * @param point The new pointRelativeToScreen value.
     */
    protected void setScreenCoordinates(Point point) {
        this.pointRelativeToScreen.setLocation(point);
    }

    /**
     *
     * @return The current pointRelativeToScenario for this Sprite
     */
    public Point getScenarioCoordinates() {
        return new Point(pointRelativeToScenario);
    }

    /**
     * This method sets the current Scenario coordinates for this Sprite.
     *
     * @param x The new pointRelativeToScenario.x value
     * @param y The new pointRelativeToScenario.y value
     */
    protected void setScenarioCoordinates(int x, int y) {
        this.pointRelativeToScenario.setLocation(x, y);
    }

    /**
     * This method sets the current scenario coordinates for this Sprite.
     *
     * @param point The new pointRelativeToScenario value.
     */
    protected void setScenarioCoordinates(Point point) {
        this.pointRelativeToScenario.setLocation(point);
    }

    /**
     *
     * @return The Sprite's superposition indicator.
     */
    public int getZIndex() {
        return zIndex;
    }

    /**
     * This method is used to set the Sprite zIndex's
     *
     * @param zindex The Sprite's superposition indicator value.
     */
    public void setZIndex(int zindex) {
        zIndex = zindex;
    }

    /**
     *
     * @return a Rectangle instance wich represents the box wich encapsulates the
     * current Sprite dimension.
     */
    public Rectangle getCollisionableArea() {
        return new Rectangle(pointRelativeToScenario, new Dimension(width, height));
    }

    /**
     *
     * @return The collisionableAreas Rectangle array.
     */
    public Rectangle[] getCollisionableAreas() {
        return collisionableAreas;
    }

    /**
     * This method sets the current collisionableAreas Rectangle array.
     *
     * @param collisionableAreas The new collisionable areas Rectangle array. If this
     * value is null, the method does nothing.
     */
    public void setCollisionableAreas(Rectangle[] collisionableAreas) {

        if(collisionableAreas != null){
            int capacity = collisionableAreas.length;

            this.collisionableAreas = new Rectangle[capacity];

            int count = 0;
            for (Rectangle rectangle : collisionableAreas) {
                this.collisionableAreas[count] = rectangle;
                count++;
            }
        } else {
            System.out.println("WARNING! The collisionableAreas are null. Not Setting.");
        }
    }

    /**
     * This method append the newCollisionableAreas to the current collisionableAreas
     * Rectangle array.
     *
     * @param newCollisionableAreas The Rectangle array to append to the current
     * collisionableAreas. If it's null The method does nothing.
     */
    public void addCollisionableAreas(Rectangle[] newCollisionableAreas) {

        if(newCollisionableAreas != null){
            Rectangle[] upgradedCollisionableAreas;

            int capacity = this.collisionableAreas.length + newCollisionableAreas.length;
            boolean[] skippingRectangles = new boolean[newCollisionableAreas.length];

            for (Rectangle rectangle : this.collisionableAreas) {
                int count = 0;
                if (rectangle != null) {
                    for (Rectangle addingRectangle : newCollisionableAreas) {
                        if (rectangle.equals(addingRectangle) || rectangle.contains(addingRectangle)) {
                            capacity--;
                            skippingRectangles[count] = true;
                            count++;
                        }
                    }
                }
            }

            if (capacity > 0) {
                upgradedCollisionableAreas = new Rectangle[capacity];
                int count = 0;
                for (Rectangle rectangle : this.collisionableAreas) {
                    upgradedCollisionableAreas[count] = rectangle;
                    count++;
                }
                int newCount = 0;
                for (Rectangle rectangle : newCollisionableAreas) {
                    if (!skippingRectangles[newCount]) {
                        upgradedCollisionableAreas[count] = newCollisionableAreas[newCount];
                        count++;
                    }
                    newCount++;
                }

                this.collisionableAreas = upgradedCollisionableAreas;
            }
        } else {
            System.out.println("WARNING! The newCollisionableAreasis null. Not Adding.");
        }
    }

    /**
     * This method implements the <code>update()</code> method from the Updatable interface.
     * At this hierarchy top-level, the update method only update it's current image if
     * an ImageAnimator has been set.
     */
    public void update() {

        if (this.animator != null) {
            this.animator.updateSpriteImage();
        }
    }

    /**
     *
     * @return a Rectangle wich represents the current visible area of this Sprite
     * instance.
     */
    public Rectangle getVisibleArea() {

        int visibleAreaX1 = this.pointRelativeToScenario.x - this.visibilityRange;
        int visibleAreaY1 = this.pointRelativeToScenario.y - this.visibilityRange;
        int visibleAreaX2 = this.pointRelativeToScenario.x + this.width + this.visibilityRange;
        int visibleAreaY2 = this.pointRelativeToScenario.y + this.height + this.visibilityRange;

        return new Rectangle(visibleAreaX1, visibleAreaX2, visibleAreaY1, visibleAreaY2);
    }

    /**
     * This method sets the Sprite's image. If an animator was setted, it's stopped.
     * Then the Media instance for this Sprite is asked for the imageName named
     * BufferedImage. Once the image is setted, then width and height are setted too.
     *
     * @param imageName The name of the ImagesManager requested image.
     */
    public void setImage(String imageName) {

        if(animator != null)
            this.animator.stop();

        BufferedImage settedImage = mediaManager.getImage(imageName);

        if (settedImage != null) {
            this.image = settedImage;
            this.width = settedImage.getWidth();
            this.height = settedImage.getHeight();
        } else {
            System.out.println("Image not found.");
        }
    }

    /**
     *
     * @return The visibilityRange value for this Sprite instance.
     */
    public int getVisibilityRange() {
        return visibilityRange;
    }

    /**
     * This method sets the visibilityRange value.
     *
     * @param visibilityRange The desired visibilityRange.
     */
    public void setVisibilityRange(int visibilityRange) {
        this.visibilityRange = visibilityRange;
    }

    /**
     * This method sets the current animator for the Sprite. First the current
     * animator is set to null to make the GarbageCollector taking care of flushing
     * the old ImagesAnimator instance when it has a chance.
     * Then, the Media instance is asked for the new ImagesAnimator.
     *
     * @param imageName The name for the ImagesManager ArrayList of BufferedImages stored.
     * @param animationPeriod
     * @param sequenceDuration The total
     * @param isRepeating A boolean value indicating wether the animation sequence should
     * be restarted if it reaches the end.
     */
    public void setAnimator(String imageName, int animationPeriod, double sequenceDuration, boolean isRepeating) {

        this.animator = null;
        this.animator = mediaManager.getAnimator(this, imageName, animationPeriod, sequenceDuration, isRepeating);

    }

    /**
     *
     * @return The current animator name.
     */
    public String getAnimatorName() {
        if(this.animator != null)
            return this.animator.getName();
        else return "";
    }

    /**
     * 
     * @return The current animator showing image position.
     */
    public int getAnimatorPosition() {
        if(animator != null)
            return this.animator.getCurrentPosition();
        else
            return 0;
    }

    /**
     * This method stops the animator animation process.
     */
    public void stopAnimator() {
        if(animator != null)
            this.animator.stop();
    }

    /**
     * This method resumes the animator animation process.
     */
    public void resumeAnimator() {
        if(animator != null)
            this.animator.resume();
    }

    /**
     * This method restarts the animator animation process at a given position.
     *
     * @param position The index of the image to be first displayed at the animation
     * sequence.
     */
    public void restartAnimatorAtPosition(int position) {
        if(animator != null)
            this.animator.restartAtPosition(position);
    }

    /**
     * This method sets the current audioClipPlayer for the Sprite. First the current
     * audioClipPlayer is set to null to make the GarbageCollector taking care of flushing
     * the old AudioClipPlayer instance when it has a chance.
     * Then, the Media instance is asked for the new AudioClipPlayer.
     *
     * @param soundName The name for the SoundsManager ArrayList of AudioClips stored.
     */
    public void setAudioClipPlayer(String soundName) {

        this.audioClipPlayer = null;
        this.audioClipPlayer = mediaManager.getAudioClipPlayer(soundName);
    }

    /**
     * This method makes the AudioClipPlayer instance playing an AudioClip.
     *
     * @see AudioClipPlayer#playAudioClip()
     */
    public void playAudioClip() {
        if(audioClipPlayer != null)
            this.audioClipPlayer.playAudioClip();
    }

    /**
     * This method makes the AudioClipPlayer instance looping an AudioClip.
     *
     * @see AudioClipPlayer#loopAudioClip()
     */
    public void loopAudioClip() {
        if(audioClipPlayer != null)
            this.audioClipPlayer.loopAudioClip();
    }

    /**
     * This method makes the AudioClipPlayer instance stopping an AudioClip.
     *
     * @see AudioClipPlayer#stopAudioClip() 
     */
    public void stopAudioClip() {
        if(audioClipPlayer != null)
            this.audioClipPlayer.stopAudioClip();
    }

    /**
     * This method sets the current clipPlayer for the Sprite. First the current
     * clipPlayer is set to null to make the GarbageCollector taking care of flushing
     * the old ClipPlayer instance when it has a chance.
     * Then, the Media instance is asked for the new ClipPlayer.
     *
     * @param soundName The name for the SoundsManager ArrayList of Clips stored.
     */
    public void setClipPlayer(String soundName) {

        this.clipPlayer = null;
        this.clipPlayer = mediaManager.getClipPlayer(soundName);
    }

    /**
     * This method makes the ClipPlayer instance play a Clip.
     *
     * @see ClipPlayer#playClip()
     */
    public void playClip() {
        if(clipPlayer != null)
            this.clipPlayer.playClip();
    }

    /**
     * This method makes the ClipPlayer instance looping a Clip.
     *
     * @see ClipPlayer#loopClip()
     */
    public void loopClip() {
        if(clipPlayer != null)
            this.clipPlayer.loopClip();
    }

    /**
     * This method makes the ClipPlayer instance stopping a Clip.
     *
     * @see ClipPlayer#stopClip()
     */
    public void stopClip() {
        if(clipPlayer != null)
            this.clipPlayer.stopClip();
    }
}
