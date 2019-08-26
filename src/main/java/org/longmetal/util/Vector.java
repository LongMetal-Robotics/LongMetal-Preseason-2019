package org.longmetal.util;

/**
 * A simple class for grouping two values together into an object
 */
public class Vector {
    private double x;
    private double y;

    /**
     * Constructor
     * @param x 
     * @param y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x
     * @return the value of x
     */
    public double getX() {
        return x;
    }

    /**
     * Getter for y
     * @return the value of y
     */
    public double getY() {
        return y;
    }

    /**
     * Setter for x
     * @param x the value to store to x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter for y
     * @param y the value to store to y
     */
    public void setY(double y) {
        this.y = y;
    }
}