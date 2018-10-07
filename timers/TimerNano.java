
package timers;

import timers.interfaces.*;

/**
 *
 * @author Germán Coines Laguna
 *
 * En esta classe se prueba la granularidad de System.nanoTime()
 * ( tiene una resolución de entre 2 a 6 microsegundos para algunas versiones de Windows)
 *
 *
 */
public class TimerNano implements JannatonTimer{

    private static TimerNano timer;
    private long beforeTime;

    private TimerNano(){

        beforeTime = System.nanoTime();
    }

    public static TimerNano instantiate(){
        if(timer == null)
            timer = new TimerNano();

        return timer;
    }

    public static void getSimpleDiff(){

        long count1 = System.nanoTime();                                        //Devuelve el instante actual en nanosegundos
        long count2 = System.nanoTime();                                        //Devuelve el instante actual en nanosegundos

        long diff = (count2 - count1);                                          //Calculamos la diferencia

        System.out.println("Inicio: " + count1 + "\nFinal: " + count2 + "\nDiferencia (nanosegundos): " + diff);
    }

    public static void getSystemNanoTimeResolution(int precisionFactor){

        long total = 0, count1, count2;

        if(precisionFactor <= 0)
            precisionFactor = 1;

        for(int i = 0; i < precisionFactor; i++){

            count1 = System.nanoTime();
            count2 = System.nanoTime();

            while(count2 == count1)
                count2 = System.nanoTime();

            total += count2 - count1;
        }

        System.out.println("System.nanoTime() Timer Resolution: " + total / precisionFactor + " nanoseconds");
    }

    public long getTimeInstace() {        

        long actualTime = System.nanoTime() - beforeTime;

        return actualTime;
    }
}
