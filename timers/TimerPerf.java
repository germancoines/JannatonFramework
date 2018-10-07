/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package timers;

import timers.interfaces.*;
import sun.misc.Perf;

/**
 *
 * @author harmonicaherman
 *
 * En esta classe se prueba un contador de alta Resolución
 * ( de 2 a 6 microsegundos para algunas versiones de Windows)
 *
 * Perf (sun.misc.Perf) no es un temporizador, pero puede usarse para medir
 * intervalos de tiempo.
 *
 */
public class TimerPerf implements JannatonTimer{

    private static Perf perf;

    public TimerPerf(){

        
    }

    public static void getSimpleDiff(){

        if(perf == null)
            perf = Perf.getPerf();

        long countFreq = perf.highResFrequency();                               //Retorna los computos por segundo que
                                                                                //realiza Perf
        long count1 = perf.highResCounter();                                    //Retorna el valor actual del contador
        long count2 = perf.highResCounter();                                    //Retorna el valor actual del contador

        long diff = (count2 - count1) * 1000000000L / countFreq;                //Pasamos a nanosegundos

        System.out.println("Inicio: " + count1 + "\nFinal: " + count2 + "\nDiferencia (nanosegundos): " + diff);
    }

    public static void getPerfResolution(int precisionFactor){

        if(perf == null)
            perf = Perf.getPerf();

        long diff = 0, count1, count2;

        long freq = perf.highResFrequency();

        for(int i = 0; i < precisionFactor; i++){

            count1 = perf.highResCounter();
            count2 = perf.highResCounter();

            while(count2 == count1)
                count2 = perf.highResCounter();

            diff += count2 - count1;
        }

        System.out.println("Perf Timer Resolution: " + (diff * 1000000000L) / (precisionFactor * freq) + " nanoseconds");
    }

    public long getTimeInstace() {
        if(perf == null)
            perf = Perf.getPerf();

        return perf.highResCounter() * 1000000000L / perf.highResFrequency();
    }
}
