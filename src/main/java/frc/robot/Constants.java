// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

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

    /**
     * Debug variable used to control logging output to the console.
     */
    public static final boolean DEBUG = false;

    // Zero 
    public static final double NO_SPEED = 0.0;

    public static class Climber {

        // Falcon Motor CAN IDs
        public static final int LEFT_WINCH_TALON_FX_ID = 13;
        public static final int RIGHT_WINCH_TALON_FX_ID = 15;
        public static final int CENTER_LATCH_ID = 30;
        
        // Speed modifier variables
        public static final double LEFT_CLIMB_SPEED = 0.4;
        public static final double RIGHT_CLIMB_SPEED = 0.4;
        public static final double LEFT_CORRECTION_SPEED = 0.125;
        public static final double RIGHT_CORRECTION_SPEED = 0.125;
        public static final double CENTER_LATCH_SPEED = 0.4; // 2022-11-03 better value for new latch design.

        // Polarity modifier variables
        public static final int RIGHT_CLIMBER_POLARITY_MOD = 1;
        public static final int LEFT_CLIMBER_POLARITY_MOD = -1;
        public static final int CENTER_POLARITY_MOD = 1;

        public static final double LEFT_CLIMBER_ENCODER_MAX_VAL = 14900;
        public static final double LEFT_CLIMBER_ENCODER_MID_VAL = 7450;
        public static final double LEFT_CLIMBER_ENCODER_MIN_VAL = 0.0;
        public static final double LEFT_CLIMBER_ENCODER_STEP = 3506; // TODO. 2022-11-02. Should this be 2048 which is one rotation??
        public static final double LEFT_CLIMBER_ENCODER_UPPER_LIMIT = 20000.0;//NEEDS TO BE DETERMINED

        public static final double RIGHT_CLIMBER_ENCODER_MAX_VAL = 14900;//NEEDS TO BE DETERMINED
        public static final double RIGHT_CLIMBER_ENCODER_MID_VAL = 7450;//NEEDS TO BE DETERMINED
        public static final double RIGHT_CLIMBER_ENCODER_MIN_VAL = 0.0;
        public static final double RIGHT_CLIMBER_ENCODER_STEP = 3506;//TODO. 2022-11-02. Should this be 2048 which is one rotation??
        public static final double RIGHT_CLIMBER_ENCODER_UPPER_LIMIT = 20000.0;//NEEDS TO BE DETERMINED
        
        public static final int CLIMBER_LEFT_LATCH_PWM_PORT = 0;    // Servo
        public static final int CLIMBER_RIGHT_LATCH_PWM_PORT = 1;   // Servo

        public static final double LATCH_LOCKED_POS = 0.0;      // 0 degrees
        public static final double LATCH_UNLOCKED_POS = 0.66;  // 180 degrees

    }

    public static class Shooter {
        
        // CAN IDs for Shooter
        public static final int LEFT_SHOOTER_ID = 11;
        public static final int RIGHT_SHOOTER_ID = 12;

        // Speed modifier variables
        public static final double SHOOTER_LOW_SPEED = 0.6;
        public static final double SHOOTER_HIGH_SPEED = 0.8;

        // Polarity modifier variables
        public static final int SHOOTER_TOP_POLARITY_MOD = 1;
        public static final int SHOOTER_BOTTOM_POLARITY_MOD = -1;
        
    }

    public static class Feeder {
        
        // CAN IDs for Feeder
        public static final int FEEDER_ID = 10;

        // Speed modifier variables
        public static final double FEEDER_SPEED = 0.85;

        // Polarity modifier variables
        public static final int FEEDER_POLARITY_MOD = 1;
        
    }

    public static class Intake {

        // CAN IDs for Intake
        public static final int INTAKE_SPINNER_ID = 9;
        public static final int INTAKE_FLIP_ID = 1;

        // Speed modifier variables
        public static final double INTAKE_SPEED = 0.5;  // 2022-11-05. Increase to 0.5
        public static final double INTAKE_FLIP_SPEED = 0.95;  // 2022-11-05. /22-11-05 after comp value before changes was .65

        // Polarity modifier variables
        public static final int INTAKE_POLARITY_MOD = -1;
        public static final int INTAKE_FLIP_POLARITY_MOD = -1;

    }

    public static class Chassis {

        public static final int GYRO_PORT = 0;

        /**
         * CAN IDs for TalonSRX Controllers for Steering Motors
         * on the SwerveDrive.
         */
        public static final int LEFT_FRONT_TALON_SRX_ID = 3;
        public static final int LEFT_BACK_TALON_SRX_ID = 2;
        public static final int RIGHT_FRONT_TALON_SRX_ID = 100; // Controller is not used.
        public static final int RIGHT_BACK_TALON_SRX_ID = 4;

        /**
         * Motor types & CAN IDs for SparkMax Controllers for Drive Motors (NEO Rev-21-1650)
         * of the SwerveDrive.
         */
        public static final MotorType BRUSHLESS_MOTOR = MotorType.kBrushless;
        public static final MotorType BRUSHED_MOTOR = MotorType.kBrushed;
        public static final int LEFT_FRONT_SPARK_MAX_ID = 5;
        public static final int LEFT_BACK_SPARK_MAX_ID = 8;
        public static final int RIGHT_FRONT_SPARK_MAX_ID = 6;
        public static final int RIGHT_BACK_SPARK_MAX_ID = 7;

        /**
         * Speed modifier variables for the drive motors
         */
        public static final double LEFT_FRONT_DRIVE_SPEED_MOD = 20; //  11/5/2022 original value was 6
        public static final double RIGHT_FRONT_DRIVE_SPEED_MOD = 20;
        public static final double LEFT_BACK_DRIVE_SPEED_MOD = 20;
        public static final double RIGHT_BACK_DRIVE_SPEED_MOD = 20;

        /**
         * Polarity modifier variables for the drive motors
         */
        public static final int LEFT_FRONT_DRIVE_POLARITY_MOD = -1;
        public static final int RIGHT_FRONT_DRIVE_POLARITY_MOD = 1;
        public static final int LEFT_BACK_DRIVE_POLARITY_MOD = 1;
        public static final int RIGHT_BACK_DRIVE_POLARITY_MOD = -1;

        /**
         * Speed modifier variables for the steer motors
         */
        public static final double LEFT_FRONT_STEER_SPEED_MOD = 0;
        public static final double RIGHT_FRONT_STEER_SPEED_MOD = 0;
        public static final double LEFT_BACK_STEER_SPEED_MOD = 0;
        public static final double RIGHT_BACK_STEER_SPEED_MOD = 0;

        /**
         * Polarity modifier variables for the steer motors
         */
        public static final int LEFT_FRONT_STEER_POLARITY_MOD = 1;
        public static final int RIGHT_FRONT_STEER_POLARITY_MOD = -1;
        public static final int LEFT_BACK_STEER_POLARITY_MOD = -1;
        public static final int RIGHT_BACK_STEER_POLARITY_MOD = -1;
        
    }

    public static class Swerve {

        // Kinematics measurement for Swerve Drive.
        public static final double DISTANCE_FROM_CENTER = 0.175; // meters
        public static final double RADIUS_OF_WHEEL = 0.05013; // meters

        /**
         * The drive encoder uses 1024 ticks per revolution and is accessed by getPosition()
         * whereas the Lamprey encoder getPosition() return current degree of the encoder.
         */
        public static final int DRIVE_ENCODER_RESOLUTION = 1024;  // 10-bit encoder. 2^10=1024
        public static final int STEER_ENCODER_RESOLUTION = 360;   // 42 ticks per revoluion vendor info

        public static final double DRIVE_PID_PROPORTIONAL_GAIN = 1.0;  // Default P value for PIDController in SwerveModule (0=Disable)
        public static final double DRIVE_PID_INTEGRAL_GAIN = 0.0;      // Default I value for PIDController in SwerveModule (0=Disable)
        public static final double DRIVE_PID_DERIVATIVE_GAIN = 0.0;    // Default D value for PIDController in SwerveModule (0=Disable)

        public static final double STEER_PID_PROPORTIONAL_GAIN = 1.0;  // Default P value for PIDController in SwerveModule (0=Disable)
        public static final double STEER_PID_INTEGRAL_GAIN = 0.0;      // Default I value for PIDController in SwerveModule (0=Disable)
        public static final double STEER_PID_DERIVATIVE_GAIN = 0.0;    // Default D value for PIDController in SwerveModule (0=Disable)

        // The maximum allowable tolerance of difference in desired minus actual degree
        public static final int DEGREE_TOLERANCE = 10;

    }

    public static class Xbox {

        /**
         * Port IDs for various controllers used for driving
         */
        public static final int XBOX_ASSISTANT_CONTROLLER_ID = 2;
        public static final int XBOX_DRIVER_CONTROLLER_ID = 3;
        public static final int XBOX_JOYSTICK_POLARITY_MOD = -1;
        public static final double DEADZONE_MAGNITUDE = 0.2;
    
    }

    public static class Autonomous {

        public static double AUTO_DRIVE_SPEED = -0.4;
        public static double AUTO_DRIVE_TIME_DELAY = 1.5;
        public static double AUTO_SHOOT_TIME_DELAY = 2.0;

    }
}


