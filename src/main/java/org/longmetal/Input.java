/*
 * LongMetal Robotics 2019. MIT Commons License
 * 
 * Handles input from DS and provides a clean way to interface with it
 */

package org.longmetal;

import edu.wpi.first.wpilibj.Joystick;



/*  Import                                    */
/*  88 8b    d8 88""Yb  dP"Yb  88""Yb 888888  */
/*  88 88b  d88 88__dP dP   Yb 88__dP   88    */
/*  88 88YbdP88 88"""  Yb   dP 88"Yb    88    */
/*  88 88 YY 88 88      YbodP  88  Yb   88    */
/*                                            */

public class Input {



    /*  Declare                                               */
	/*  8888b.  888888  dP""b8 88        db    88""Yb 888888  */
 	/*   8I  Yb 88__   dP   `" 88       dPYb   88__dP 88__    */
 	/*   8I  dY 88""   Yb      88  .o  dP__Yb  88"Yb  88""    */
	/*  8888Y"  888888  YboodP 88ood8 dP""""Yb 88  Yb 888888  */
	/*                                                        */

    private int leftPort;
    private int rightPort;
    private int gamepadPort;

    private Joystick driveStickLeft;
    private Joystick driveStickRight;

    private Joystick gamepad;

    private boolean QuinnDrive;


    private double left;
    private double leftThrottle;
    private boolean leftTrigger;

    private double right;
    private double rightZ;
    private double rightThrottle;
    private boolean rightTrigger;



    /*                                                                      */
    /*   dP""b8  dP"Yb  88b 88 .dP"Y8 888888 88""Yb 88   88  dP""b8 888888  */
    /*  dP   `" dP   Yb 88Yb88 `Ybo."   88   88__dP 88   88 dP   `"   88    */
    /*  Yb      Yb   dP 88 Y88 o.`Y8b   88   88"Yb  Y8   8P Yb        88    */
    /*   YboodP  YbodP  88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88    */
    /*                                                                      */

    public Input(int initLeftPort, int initRightPort, int initGamepadPort) {
        driveStickLeft = new Joystick(leftPort = initLeftPort);
        driveStickRight = new Joystick(rightPort = initRightPort);

        gamepad = new Joystick(gamepadPort = initGamepadPort);

        QuinnDrive = false;
    }

    /**
     * Sets whether Quinn Drive is enabled (left/right sticks are reversed in Curve)
     * @param isQuinnDrive a boolean of the new value
     */
    public void setQuinnDrive(boolean isQuinnDrive) {
        if (QuinnDrive = isQuinnDrive) {
            driveStickLeft = new Joystick(rightPort);
            driveStickRight = new Joystick(leftPort);
        } else {
            driveStickLeft = new Joystick(leftPort);
            driveStickRight = new Joystick(rightPort);
        }
    }

    /**
     * Returns whether Quinn Drive is enabled or not
     * @return a boolean of whether it is enabled or not
     */
    public boolean getQuinnDrive() {
        return QuinnDrive;
    }

    /**
     * Updates inputs from DS
     * [IMPORTANT] Must be called at the beginning of every teleopPeriodic to retreive the newest values
     * It is safe to call multiple times in a period
     */
    public void update() {
        left = driveStickLeft.getY();
        leftThrottle = driveStickLeft.getThrottle();
        leftTrigger = driveStickLeft.getRawButton(0);  // [TODO]   Check to make sure trigger is *actually* 0
        
        right = driveStickRight.getY();
        rightZ = driveStickRight.getZ();
        leftThrottle = driveStickRight.getThrottle();
        rightTrigger = driveStickRight.getRawButton(0);
    }
    

    public double getLeft() {
        return left;
    }

    public double getLeftThrottle() {
        return leftThrottle;
    }
    
    public boolean getLeftTrigger() {
        return leftTrigger;
    }

    

    public double getRight() {
        return right;
    }

    public double getRightZ() {
        return rightZ;
    }

    public double getRightThrottle() {
        return rightThrottle;
    }

    public boolean getRightTrigger() {
        return rightTrigger;
    }

}