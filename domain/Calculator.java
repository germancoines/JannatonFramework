/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author wida36528678
 */
public class Calculator {

    private static Calculator calculator;

    public static Calculator instantiate() {
        if (calculator == null) {
            calculator = new Calculator();
        }

        return calculator;
    }

    public static boolean calculatePossibilisticResult(double possibilityPercent) {

        boolean result;

        double randomValue = Math.random() * 100;

        if (possibilityPercent > randomValue) {
            result = true;
        } else {
            result = false;
        }

        return result;

    }

    public static boolean calculatePossibilisticResult(double possibilityPercent, int modifierFromTarget) {

        boolean result;

        double randomValue = Math.random() * 100;

        possibilityPercent += reverseValue(modifierFromTarget);

        if (possibilityPercent > randomValue) {
            result = true;
        } else {
            result = false;
        }

        return result;

    }

    public static boolean calculatePossibilisticResult(double possibilityPercent, int[] modifiersFromTarget) {

        boolean result;

        double randomValue = Math.random() * 100;

        for (int i = 0; i < modifiersFromTarget.length - 1; i++) {
            possibilityPercent += reverseValue(modifiersFromTarget[i]);
        }

        if (possibilityPercent > randomValue) {
            result = true;
        } else {
            result = false;
        }

        return result;

    }

    public static int reverseValue(int value) {

        int reversedValue = 0;

        if (value > 0) {
            reversedValue -= value;
        } else {
            reversedValue += value;
        }

        return reversedValue;

    }
}
