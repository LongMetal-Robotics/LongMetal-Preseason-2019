package org.longmetal.util;

/**
 * A simple class for grouping two values together into an object
 */
public class Dual {
    private double a;
    private double b;

    /**
     * Constructor
     * @param a 
     * @param b
     */
    public Dual(double a, double b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Getter for a
     * @return the value of a
     */
    public double geta() {
        return a;
    }

    /**
     * Getter for b
     * @return the value of b
     */
    public double getb() {
        return b;
    }

    /**
     * Setter for a
     * @param a the value to store to a
     */
    public void seta(double a) {
        this.a = a;
    }

    /**
     * Setter for b
     * @param b the value to store to b
     */
    public void setb(double b) {
        this.b = b;
    }
}