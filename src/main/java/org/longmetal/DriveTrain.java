package org.longmetal;



/*  Import                                    */
/*  88 8b    d8 88""Yb  dP"Yb  88""Yb 888888  */
/*  88 88b  d88 88__dP dP   Yb 88__dP   88    */
/*  88 88YbdP88 88"""  Yb   dP 88"Yb    88    */
/*  88 88 YY 88 88      YbodP  88  Yb   88    */
/*                                            */

import edu.wpi.first.wpilibj.SpeedControllerGroup;
	/* Allows multiple speed controllers to be grouped together
	 * (see lines 27, 33, 35) */
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
    // Allows for controlling drive trains such as
    // KOP, tank drive, or West Coast. (Ours is KOP)

import com.revrobotics.CANSparkMax;
    // The main class to control Spark MAXs over CAN.
import com.revrobotics.CANSparkMax.IdleMode;
    // Allows us to define whether the motors brake (bad)
    // or coast when the speed is set to 0.
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
    // This is an important class that tells the Spark MAXs
    // whether to try to drive the motors as brushed
    // or brushless (there's a big difference).

import org.longmetal.util.Dual;
    // A custom class that can contain two values ([vc] hover for more information)

import org.longmetal.Input;

public class DriveTrain {
    /* The class to control the drive train. */



    /*  Declare                                               */
	/*  8888b.  888888  dP""b8 88        db    88""Yb 888888  */
 	/*   8I  Yb 88__   dP   `" 88       dPYb   88__dP 88__    */
 	/*   8I  dY 88""   Yb      88  .o  dP__Yb  88"Yb  88""    */
	/*  8888Y"  888888  YboodP 88ood8 dP""""Yb 88  Yb 888888  */
	/*                                                        */


    protected DifferentialDrive driveTrain;

    // These objects are declared here because they may be used
    // elsewhere as this is a 'low level' class.

    protected CANSparkMax m_rearLeft,
        m_frontLeft,
        m_rearRight,
        m_frontRight;
    protected SpeedControllerGroup leftMotors,
        rightMotors;



	/*  Constants                                                           */
	/*   dP""b8  dP"Yb  88b 88 .dP"Y8 888888    db    88b 88 888888 .dP"Y8  */
	/*  dP   `" dP   Yb 88Yb88 `Ybo."   88     dPYb   88Yb88   88   `Ybo."  */
	/*  Yb      Yb   dP 88 Y88 o.`Y8b   88    dP__Yb  88 Y88   88   o.`Y8b  */
	/*   YboodP  YbodP  88  Y8 8bodP'   88   dP""""Yb 88  Y8   88   8bodP'  */
    /*                                                                      */
    
    final public double kMAX_SPEED_MULT = 0.5;



    /*                                                                      */
    /*   dP""b8  dP"Yb  88b 88 .dP"Y8 888888 88""Yb 88   88  dP""b8 888888  */
    /*  dP   `" dP   Yb 88Yb88 `Ybo."   88   88__dP 88   88 dP   `"   88    */
    /*  Yb      Yb   dP 88 Y88 o.`Y8b   88   88"Yb  Y8   8P Yb        88    */
    /*   YboodP  YbodP  88  Y8 8bodP'   88   88  Yb `YbodP'  YboodP   88    */
    /*                                                                      */

    public DriveTrain() { // Default constructor
        // Create the objects and set their properties
        m_rearLeft = new CANSparkMax(1, MotorType.kBrushless);
        m_rearLeft.setIdleMode(IdleMode.kCoast);
        m_frontLeft = new CANSparkMax(2, MotorType.kBrushless);
        m_frontLeft.setIdleMode(IdleMode.kCoast);
        leftMotors = new SpeedControllerGroup(m_rearLeft, m_frontLeft);

        m_rearRight = new CANSparkMax(3, MotorType.kBrushless);
        m_rearRight.setIdleMode(IdleMode.kCoast);
        m_frontRight = new CANSparkMax(4, MotorType.kBrushless);
        m_frontRight.setIdleMode(IdleMode.kCoast);
        rightMotors = new SpeedControllerGroup(m_rearRight, m_frontRight);

        driveTrain = new DifferentialDrive(leftMotors, rightMotors);
    }



    /**
     * Drives the robot in Curvature mode with limits applied.
     * @param speedRaw The raw joystick value for speed
     * @param speedThrottleRaw The raw joystick value for speed throttle (changing max speed)
     * @param curvatureRaw The raw joystick value for curvature
     * @param curvatureThrottleRaw The raw joystick value for curvature throttle (changing max curvature rate)
     * @return A Dual that can be used to know the current speed/curvature of the robot
     */
    public Dual<Double> curve(double speedRaw, double speedThrottleRaw, double curvatureRaw, double curvatureThrottleRaw) {
        double modifierX = (0.7 * speedThrottleRaw - 1.05) / 2; // Create a speed modifier
		double modifierZ = (curvatureThrottleRaw - 1) * -0.25;   // Create a curvature modifier

		double driveX = speedRaw * modifierX * kMAX_SPEED_MULT; // Calculate the speed
		double driveZ = curvatureRaw * modifierZ;               // Calculate the curvature

        driveTrain.curvatureDrive(driveX, driveZ, true);    // Drive
        return new Dual<Double>(driveX, driveZ);                    // Return drive values
    }

    /**
     * Drives the robot in Curvature mode with limits applied
     * Takes an Input to simplify teleop code
     * @param input Contains the DS Input
     * @return A Dual that can be used to know the current speed/curvature of the robot
     */

    public Dual<Double> curve(Input input) {
        return curve(input.getLeft(), input.getLeftThrottle(), input.getRightZ(), input.getRightThrottle());
    }

    /**
     * Drives the robot in Curvature mode with no limits(!)
     * THIS IS A DANGEROUS METHOD! IT DOES NOT APPLY ANY LIMITS!
     * DO NOT USE UNLESS YOU KNOW WHAT YOU ARE DOING!
     * @param speedRaw The raw speed to drive the robot at.
     * @param curvatureRaw The raw curvature to drive at
     * @param secret A checksum to make sure you really want to do this
     * @return A Dual of the current speed/curvature of the robot. If it is (0, 0) and the inputs were not, the checksum failed
     * @throws IllegalArgumentException Thrown when the checksum fails.
     */
    @Deprecated
    public Dual<Double> curveRaw(double speedRaw, double curvatureRaw, double secret) throws IllegalArgumentException {
        if (secret == Math.pow(speedRaw, 2) * Math.pow(curvatureRaw, 3)) {  // Validate checksum; checksum passed
            driveTrain.curvatureDrive(speedRaw, curvatureRaw, true);        // Drive
            return new Dual<Double>(speedRaw, curvatureRaw);                        // Return drive values
        } else {    // Checksum failed
            driveTrain.curvatureDrive(0, 0, true);                          // Stop
            throw new IllegalArgumentException("The checksum did not pass.");   // Fail
        }
    }

    /**
     * Drives the robot in Tank mode with limits applied.
     * @param leftRaw The raw joystick value for the left motors
     * @param rightRaw The raw joystick value for the right motors
     * @param speedThrottleRaw The raw joystick value for speed throttle
     * @param triggers A boolean array of the values of the two joystick triggers (if a trigger is pressed, both sides drive at the speed of that joystick). Should be formatted {leftTrigger, rightTrigger}. MUST HAVE LENGTH OF TWO!
     * @return A Dual of the current values of the speeds actually applied to the drive train
     * @throws IllegalArgumentException Thrown if `triggers` does not have a length of two.
     */
    public Dual<Double> tank(double leftRaw, double rightRaw, double speedThrottleRaw, boolean[] triggers) throws IllegalArgumentException {
        if (triggers.length == 2) { // Validate input
            double modifier = (0.7 * speedThrottleRaw - 1.05) / 2; // Calculate modifier
            double left = leftRaw * modifier;   // Calculate left speed
            double right = rightRaw * modifier; // Calculate right speed
            if (triggers[0]) {  // Left trigger is pressed
                right = left;   // Set right speed to left speed
            } else if (triggers[1]) {   // Right trigger is pressed
                left = right;           // Set left speed to right speed
            }
            driveTrain.tankDrive(left, right);  // Drive
            return new Dual<Double>(Math.pow(left, 2), Math.pow(right, 2)); // Return drive values
        } else {    // Input is illegal
            throw new IllegalArgumentException("The argument `triggers` is required to have length 2.");    // Fail
        }
    }

    /**
     * Drives the robot in Tank mode with limits applied
     * Takes an Input to simplify teleop code
     * @param input Contains the DS Input
     * @return A Dual of the current values of the speeds actually applied to the drive train
     */
    public Dual<Double> tank(Input input) {
        boolean[] triggers = {input.getLeftTrigger(), input.getRightTrigger()};

        if (!input.getQuinnDrive()) {
            return tank(input.getLeft(), input.getRight(), input.getLeftThrottle(), triggers);
        } else {
            boolean tmp = triggers[0];
            triggers[0] = triggers[1];
            triggers[1] = tmp;
            return tank(input.getRight(), input.getLeft(), input.getRightThrottle(), triggers);
        }
    }

    /**
     * Drives the robot in Tank mode with no limits(!)
     * THIS IS A DANGEROUS METHOD! IT DOES NOT APPLY LIMITS!
     * DO NOT USE UNLESS YOU KNOW WHAT YOU ARE DOING!
     * @param leftRaw The raw speed to drive the left side at
     * @param rightRaw The raw speed to drive the right side at
     * @param secret A checksum to make sure you really want to do this
     * @return A Dual of the current values of the speeds actually applied to the drive train
     * @throws IllegalArgumentException Thrown when the checksum fails.
     */
    @Deprecated
    public Dual<Double> tankRaw(double leftRaw, double rightRaw, double secret) throws IllegalArgumentException {
        if (secret == Math.pow(leftRaw, 2) * Math.pow(rightRaw, 2)) {   // Validate checksum; checksum passed
            driveTrain.tankDrive(leftRaw, rightRaw, false); // Drive
            return new Dual<Double>(leftRaw, rightRaw);             // Return drive values
        } else {    // Checksum failed
            throw new IllegalArgumentException("The checksum did not pass.");   // Fail
        }
    }
}