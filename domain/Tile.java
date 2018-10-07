/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 * @author wida47875172
 */
public class Tile {

    private Sprite background;

    private Sprite ocuppier;

    private boolean ocuppied;

    protected Tile(){

    }

    protected Tile(Sprite background){

        this.background = background;
    }

    protected Tile(Sprite background, Sprite ocupier){

        this.background = background;
        this.ocuppier = ocupier;
        this.ocuppied = true;
    }

    public void setOcuppied(boolean isOcuppied){
        this.ocuppied = isOcuppied;
    }

    public boolean isOcuppied(){
        return this.ocuppied;
    }

    public Sprite getOcuppier(){
        return this.ocuppier;
    }
}
