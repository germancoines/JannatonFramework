/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain;

/**
 *
 * @author wida36528678
 */
public class Phisics {

    private static Phisics phisics;
    private Paths paths;
    private Calculator calculator;
    private Collisions collisions;
    private Environment environment;

    private Phisics(){

        collisions = Collisions.instantiate(this);

        calculator = Calculator.instantiate();

        paths = Paths.instantiate();
    }
    
    protected Environment getEnvironment(){
        
        return this.environment;
    }

    public static Phisics instantiate(){

        if(phisics == null)
            phisics = new Phisics();

        return phisics;
    }

    protected void setEnvironment(Environment environment){
        this.environment = environment;
    }

    public Collisions getCollisions(){
        return this.collisions;
    }

    public Paths getPaths(){
        return this.paths;
    }

    public Calculator getCalculator(){
        return this.calculator;
    }
}
