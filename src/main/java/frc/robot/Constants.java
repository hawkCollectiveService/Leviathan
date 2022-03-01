// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
@SuppressWarnings("unused")
public final class Constants {
    // Kinematics measurement for Swerve Drive.
    public static final double DISTANCE_FROM_CENTER = 0.425; // meters
    public static final double RADIUS_OF_WHEEL = 0.05013; // meters

    // Note: The drive encoder uses 1024 ticks per revolution and is accessed by getPosition()
    // whereas the Lamprey encoder getPosition() return current degree of the encoder.
    public static final int DRIVE_ENCODER_RESOLUTION = 1024;  // 10-bit encoder. 2^10=1024
    public static final int STEER_ENCODER_RESOLUTION = 360;   // 42 ticks per revoluion vendor info

    public static final double DRIVE_PID_PROPORTIONAL_GAIN = 1.0;  // Default P value for PIDController in SwerveModule (0=Disable)
    public static final double DRIVE_PID_INTEGRAL_GAIN = 0.0;      // Default I value for PIDController in SwerveModule (0=Disable)
    public static final double DRIVE_PID_DERIVATIVE_GAIN = 0.0;    // Default D value for PIDController in SwerveModule (0=Disable)

    public static final double STEER_PID_PROPORTIONAL_GAIN = 1.0;  // Default P value for PIDController in SwerveModule (0=Disable)
    public static final double STEER_PID_INTEGRAL_GAIN = 0.0;      // Default I value for PIDController in SwerveModule (0=Disable)
    public static final double STEER_PID_DERIVATIVE_GAIN = 0.0;    // Default D value for PIDController in SwerveModule (0=Disable)

    public static final int GYRO_PORT = 0;

    /**
     * CAN IDs for TalonSRX Controllers for Steering Motors
     * on the SwerveDrive.
     */
    public static final int LEFT_FRONT_TALON_SRX_ID = 3;
    public static final int LEFT_BACK_TALON_SRX_ID = 2;
    public static final int RIGHT_FRONT_TALON_SRX_ID = 1;
    public static final int RIGHT_BACK_TALON_SRX_ID = 4;

    /**
     * CAN IDs for SparkMax Controllers for Drive Motors (NEO Rev-21-1650)
     * of the SwerveDrive.
     */
    public static final int LEFT_FRONT_SPARK_MAX_ID = 5;
    public static final int LEFT_BACK_SPARK_MAX_ID = 8;
    public static final int RIGHT_FRONT_SPARK_MAX_ID = 6;
    public static final int RIGHT_BACK_SPARK_MAX_ID = 7;

    // CAN IDs for Intake
    public static final int LEFT_INTAKE_ID = 9;
    public static final int RIGHT_INTAKE_ID = 10;

    // CAN IDs for Feeder
    public static final int FEEDER_ID = 11;

    // CAN IDs for Shooter
    public static final int LEFT_SHOOTER_ID = 12;
    public static final int RIGHT_SHOOTER_ID = 13;

    // CAN IDs for Climber controller+motor - TalonFX+Falcon500.
    public static final int LEFT_WINCH_TALON_FX_ID = 13;
    public static final int LEFT_CLIMBER_TALON_FX_ID = 14;
    public static final int RIGHT_WINCH_TALON_FX_ID = 15;
    public static final int RIGHT_CLIMBER_TALON_FX_ID = 17;

    /**
     * Speed modifiers for Teleop
     */
    public static final double TELEOP_DRIVE_SPEED_MODIFIER = 0.40;      // for Swerve, must set > 0.2
    public static final double SHOOTER_TARGETING_TURNING_SPEED = 0.5;
    public static final double NO_SPEED = 0.0;
    public static final double LIFT_CONTROLLER_RELEASE_SPEED = 0.25;
    public static final double LIFT_CONTROLLER_INTAKE_SPEED = -0.25;
    public static final double SHOOTER_MAGAZINE_OUTTAKE_SPEED = 0.3;
    public static final double INTAKE_SPEED = 0.275;

    /**
     * Motor types for the Spark Max Controller
     */
    public static final MotorType BRUSHLESS_MOTOR = MotorType.kBrushless;
    public static final MotorType BRUSHED_MOTOR = MotorType.kBrushed;

    /**
     * Port IDs for various controllers used for driving
     */
    public static final int XBOX_ASSISTANT_DRIVER_CONTROLLER_ID = 2;
    public static final int XBOX_DRIVER_CONTROLLER_PORT_ID = 3;
    public static final int DRIVER_JOYSTICK_X_PORT_ID = 1;
    public static final int DRIVER_JOYSTICK_Y_PORT_ID = 0;

    // variables
    public static boolean isTargeting = false;
}
