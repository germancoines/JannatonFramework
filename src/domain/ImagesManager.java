
package domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * This class is the responsible of managing the images of the game.
 *
 * @author Germán Coines Laguna
 * @author Alberto Languiz Polo
 *
 */
public class ImagesManager {

    //
    // Fields
    //
    /**
     * The ImagesManager instance itself
     */
    private static ImagesManager imagesManager;
    /**
     * This HashMap contains all the BufferedImages of the game stored in an ArrayList
     * wich is referenced by a name. At this way, a single or a sequence of images
     * can be accesed by it's name.
     */
    private HashMap<String, ArrayList<BufferedImage>> imagesNamesMap;

    //
    // Constructors
    //
    /**
     * The default constructor. It initializes the imagesNamesMap HashMap.
     */
    private ImagesManager() {

        imagesNamesMap = new HashMap<String, ArrayList<BufferedImage>>();

    }

    /**
     * This method is used to get an ImagesManager instance following the Single-Town model
     *
     * @return an ImagesManager instance.
     */
    public static ImagesManager instantiate() {

        if (imagesManager == null) {
            imagesManager = new ImagesManager();
        }

        return imagesManager;
    }

    /**
     * This method loads a BufferedImage from an input file File instance.
     * If something causes an Exception, the image is not loaded.
     *
     * @param imageName The name that specify the ArrayList at wich the image will
     * be contained. If the name already exists, then the image is added to the
     * indicated ArrayList.
     * @param inputFile The File instance wich represents an image.
     */
    public void loadImage(String imageName, File inputFile) {

        try {

            BufferedImage bfImage = ImageIO.read(inputFile);

            if(bfImage == null)
                throw new NullPointerException();
            else if(!imagesNamesMap.containsKey(imageName)){
                ArrayList<BufferedImage> bfImages = new ArrayList<BufferedImage>();
                bfImages.add(bfImage);
                imagesNamesMap.put(imageName, bfImages);
            } else {
                imagesNamesMap.get(imageName).add(bfImage);
            }

        } catch (NullPointerException nullEx) {

            System.out.println("The file '" + inputFile + "' was not found.");

        } catch (IOException ioEx) {

            System.out.println("Error reading file. Image not loaded.");

        } catch (IllegalArgumentException illEx) {

            System.out.println("The argument was null! Image not loaded");
        }
    }

    /**
     * This method is used to get a single BufferedImage from the imagesNamesMap.
     *
     * @param imageName The name specifying the ArrayList from wich to get the
     * first BufferedImage.
     *
     * @return The first BufferedImage at the specifyied ArrayList. null if the
     * imagesNamesMap is null or if the HashMap imagesNamesMap doesn't contains
     * the given imageName at its KeySet
     */
    protected BufferedImage getImage(String imageName){
        if(imagesNamesMap != null)
            if(imagesNamesMap.get(imageName) != null)
                return imagesNamesMap.get(imageName).get(0);
            else{
                System.out.println("WARNING! The image" + imageName + "was not found!");
                return null;
            }
        else{
            System.out.println("WARNING! The image" + imageName + "was not found!");
            return null;
        }
            
    }

    /**
     * This method is used to get a single BufferedImage from the imagesNamesMap.
     *
     * @param imageName The name specifying the ArrayList from wich to get the
     * ArrayList of BufferedImage.
     *
     * @return The ArrayList of BufferedImage for the given imageName. null if the
     * imagesNamesMap is null or if the HashMap imagesNamesMap doesn't contains
     * the given imageName at its KeySet
     */
    protected ArrayList<BufferedImage> getImages(String imageName){
        if(imagesNamesMap != null)
            return imagesNamesMap.get(imageName);
        else{
                System.out.println("WARNING! The image " + imageName + " was not found!");
                return null;
            }
    }

    /**
     *
     * This method is used to get an ImagesAnimator with a sequence of images
     *
     * @param sprite The Sprite asking for the ImagesAnimator instance
     * @param imagesName The name specifying the desired set of images
     * @param animationPeriod The period that indicates how often the ImagesAnimator
     * setImage() method will be invoked.
     * @param sequenceDuration The total amount of time that a sequence should take
     * @param isRepeating A boolean value indicating if the ImagesAnimator should restart
     * the sequence when it reaches or passes the end of it.
     *
     * @return an ImagesAnimator instance with a sequence of images loaded. 
     *
     * @see ImagesAnimator 
     */
    protected ImagesAnimator getAnimator(Sprite sprite, String imagesName, int animationPeriod,
            double sequenceDuration, boolean isRepeating){

        return new ImagesAnimator(sprite, imagesName, imagesNamesMap.get(imagesName), animationPeriod,
                sequenceDuration, isRepeating);
    }

    /**
     * This nested class is the responsible of managing the image sequences.
     */
    protected class ImagesAnimator{

        //
        // Fields
        //
        /**
         * The Sprite instance to manage it's animation.
         */
        private Sprite sprite;
        /**
         * The current ImagesAnimator name, wich corresponds the imagesNamesMap key
         * value for the images arraylist.
         */
        private String name;
        /**
         * The sequence of images stored in an ArrayList of BufferedImages.
         */
        private ArrayList<BufferedImage> images;
        /**
         * The total amount of images to animate.
         */
        private int imagesNumber;
        /**
         * The current position at the sequence of images.
         */
        private int position;
        /**
         * The period that indicates how often the setImage() method will be invoked.
         */
        private int animationPeriod;
        /**
         * The total amount of time that the animation has taken.
         */
        private long animationTotalTime;
        /**
         * The amount of time in milliseconds that each sequence image will be displayed.
         * (This field is internally calculated)
         */
        private int showPeriod;
        /**
         * The total amount of time that a single sequence reproduction should take.
         */
        private double sequenceDuration;
        /**
         * A boolean value indicating wheter the ImagesAnimator should restart the
         * sequence when it has been finished.
         */
        private boolean isRepeating;
        /**
         * A boolean value to indicate if the ImagesAnimator is animating the sequence.
         */
        private boolean isAnimating = true;

        //
        // Constructor.
        //
        /**
         * This constructor initializes all the ImagesAnimator fields with the passed values
         * and calculates the showPeriod.
         *
         * @param sprite The Sprite asking for the ImagesAnimator instance
         * @param name The current ImagesAnimator name
         * @param images The ArrayListof BufferedImages to animate
         * @param animationPeriod The period that indicates how often the ImagesAnimator
         * setImage() method will be invoked.
         * @param sequenceDuration The total amount of time that a sequence should take
         * @param isRepeating A boolean value indicating if the ImagesAnimator should restart
         * the sequence when it reaches or passes the end of it.
         */
        protected ImagesAnimator(Sprite sprite, String name, ArrayList<BufferedImage> images, int animationPeriod,
                double sequenceDuration, boolean isRepeating){

            this.sprite = sprite;
            this.name = name;
            this.images = images;
            this.animationPeriod = animationPeriod;
            this.sequenceDuration = sequenceDuration;
            this.isRepeating = isRepeating;

            if(this.images != null)
                this.imagesNumber = images.size();

            else{
                System.out.println("The ImagesAnimator images is null! Not animating.");
                this.imagesNumber = 0;
                this.isAnimating = false;
            }

            if(this.animationPeriod <= 0){
                System.out.println("The ImagesAnimator animation period equals 0 or is negative. It's turned to a default valid value. (500 ms)");
                this.animationPeriod = 500;
            }

            if(this.sequenceDuration <= 0){
                System.out.println("The ImagesAnimator sequenceDuration equals 0 or is negative. It's turned to a default valid value. (1 s)");
                this.sequenceDuration = 1;
            }

            this.animationTotalTime = 0L;

            if(this.imagesNumber > 0)
                this.showPeriod = (int) (1000 * this.sequenceDuration / this.imagesNumber);
            else
                this.showPeriod = (int) (1000 * this.sequenceDuration / 1);
        }

        /**
         * This method is the responsible of updating the Sprite image.
         * If its animating the sequence, the animation total time is calculated as
         * here follows:
         * 
         *  <code>animationTotalTime = (animationTotalTime + animationPeriod) % (long) (1000 * sequenceDuration);</code>
         * 
         * once the total animation time is calcullated then the position is setted like
         * 
         *  <code>position = (int) (this.animationTotalTime / this.showPeriod);</code>
         * 
         * finally, the method checks if the ImagesAnimator is at the sequence end. If it is,
         * and it shouldn't repeat the sequence, it's stopped.
         * a call to setSpriteImage() is at last made.
         *
         * @see #stop()
         * @see #setSpriteImage()
         */
        protected void updateSpriteImage(){
            
            if(this.isAnimating){
                
                this.animationTotalTime = (this.animationTotalTime + this.animationPeriod) % (long) (1000 * this.sequenceDuration);
                
                this.position = (int) (this.animationTotalTime / this.showPeriod);
                
                if((this.position >= this.imagesNumber - 1) && (!this.isRepeating))
                    stop();
                
                setSpriteImage();
            }
        }

        /**
         * This method sets the image and the dimension of the Sprite.
         */
        private void setSpriteImage(){
            if(this.imagesNumber > 0){
                if(this.position < this.imagesNumber)
                    this.sprite.image = images.get(this.position);

                int newWidth = this.sprite.image.getWidth();
                int newHeight = this.sprite.image.getHeight();

                int newX = this.sprite.getScenarioCoordinates().x;
                int newY = this.sprite.getScenarioCoordinates().y;

                if(newWidth > this.sprite.width){
                    newX = newX - ((newWidth - this.sprite.width) / 2);
                } else if(newWidth < this.sprite.width){
                    newX = newX + ((newWidth - this.sprite.width) / 2);
                }

                if(newHeight > this.sprite.height){
                    newY = newY - ((newHeight - this.sprite.height) );
                } else if(newHeight < this.sprite.height){
                    newY = newY - ((newHeight - this.sprite.height) );
                }

                this.sprite.setScenarioCoordinates(newX, newY);

                this.sprite.width = this.sprite.image.getWidth();
                this.sprite.height = this.sprite.image.getHeight();
            }
        }

        /**
         * This method stops the ImagesAnimator.
         */
        protected void stop(){
            this.isAnimating = false;
        }

        /**
         *
         * @return true if the animation sequence has reached or passed the end and is not
         * repeating anymore.
         */
        protected boolean isAnimationFinished(){
            return ((this.position >= this.imagesNumber - 1) && (!this.isRepeating));
        }

        /**
         * This method is used to restart the ImagesAnimator at the given position.
         * The animationTotalTime field will be recalculated and the isAnimating field
         * will be setted to true.
         *
         * @param position The position at wich to restart the ImagesAnimator.
         * If it's out of the ArrayList bounds it will be setted to 0.
         */
        protected void restartAtPosition(int position){

            if(this.imagesNumber > 0){
                if(position < 0 || position > this.imagesNumber - 1){
                    System.out.println("Position out of bounds. Starting at 0.");
                    position = 0;
                }

            this.position = position;
            this.animationTotalTime = (long) this.position * this.showPeriod;
            this.isAnimating = true;
            }
        }

        /**
         * This method resumes the ImagesAnimator by setting the isAnimating value
         * to true
         */
        protected void resume(){
            if(this.imagesNumber > 0)
                this.isAnimating = true;
        }

        /**
         *
         * @return the ImagesAnimator current name
         */
        public String getName(){
            return this.name;
        }

        /**
         *
         * @return the ImagesAnimator current position
         */
        public int getCurrentPosition(){
            return this.position;
        }
    }
}
