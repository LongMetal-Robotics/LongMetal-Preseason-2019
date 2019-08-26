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

import org.longmetal.util.Vector;
    // A custom class that can contain two values ([vc] hover for more information)

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

    private CANSparkMax m_rearLeft,
        m_frontLeft,
        m_rearRight,
        m_frontRight;
    private SpeedControllerGroup leftMotors,
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
     * Drives the robot
     * @param speedRaw The raw joystick value for speed
     * @param speedThrottleRaw The raw joystick value for speed throttle (changing max speed)
     * @param rotationRaw The raw joystick value for rotation
     * @param rotationThrottleRaw The raw joystick value for rotation throttle (changing max rotation rate)
     * @return A vector that can be used to know the current speed/curvature of the robot
     */
    public Vector curve(double speedRaw, double speedThrottleRaw, double rotationRaw, double rotationThrottleRaw) {
        double modifierX = ((0.7 * speedThrottleRaw - 1.05) / 2);
		double modifierZ = (rotationThrottleRaw - 1) * -0.25;

		double driveX = speedRaw * modifierX * kMAX_SPEED_MULT;
		double driveZ = rotationRaw * modifierZ;

        driveTrain.curvatureDrive(driveX, driveZ, true);
        return new Vector(driveX, driveZ);
    }

    /**
     * THIS IS A DANGEROUS METHOD. IT DOES NOT APPLY ANY LIMITS.
     * DO NOT USE UNLESS YOU KNOW WHAT YOU ARE DOING
     * @param speedRaw The raw speed to drive the robot at.
     * @param rotationRaw The raw curvature to drive at
     * @param secret A checksum to make sure you really want to do this
     * @return A vector of the current speed/curvature of the robot. If it is (0, 0) and the inputs were not, the checksum failed
     */
    @Deprecated
    public Vector curveRaw(double speedRaw, double rotationRaw, double secret) {
        if (secret == Math.pow(speedRaw, 2) * Math.pow(rotationRaw, 3)) {
            driveTrain.curvatureDrive(speedRaw, rotationRaw, true);
            return new Vector(speedRaw, rotationRaw);
        } else {
            driveTrain.curvatureDrive(0, 0, true);
            return new Vector(0, 0);
        }
    }
}