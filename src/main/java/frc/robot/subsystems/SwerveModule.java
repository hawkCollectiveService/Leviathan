// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
// import edu.wpi.first.wpilibj.Encoder;
// import edu.wpi.first.wpilibj.motorcontrol.MotorController;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import frc.robot.Constants;

public class SwerveModule {

  private static final double kWheelRadius = Constants.RADIUS_OF_WHEEL;
  private static final int kEncoderResolution = Constants.STEER_ENCODER_RESOLUTION;

  private static final double kModuleMaxAngularVelocity = DriveTrainSubsystem.kMaxAngularSpeed;
  private static final double kModuleMaxAngularAcceleration = 2 * Math.PI; // radians per second squared

  private final CANSparkMax m_driveMotor;
  private final WPI_TalonSRX m_steerMotor;

  private final RelativeEncoder m_driveEncoder;
  private final WPI_CANCoder m_steerEncoder;

  private double driveDistancePerPulse;
  private double steerDistancePerPulse;

  // Gains are for example purposes only - must be determined for your own robot!
  private final PIDController m_drivePIDController = new PIDController(
      Constants.DRIVE_PID_PROPORTIONAL_GAIN,
      Constants.DRIVE_PID_INTEGRAL_GAIN,
      Constants.DRIVE_PID_DERIVATIVE_GAIN);

  // Gains are for example purposes only - must be determined for your own robot!
  private final ProfiledPIDController m_turningPIDController = new ProfiledPIDController(
    Constants.STEER_PID_PROPORTIONAL_GAIN,
    Constants.STEER_PID_INTEGRAL_GAIN,
    Constants.STEER_PID_DERIVATIVE_GAIN,
      new TrapezoidProfile.Constraints(
          kModuleMaxAngularVelocity, kModuleMaxAngularAcceleration));

  // Gains (StaticGain, VelocityGain) are for example purposes only - must be determined for your own robot!
  private final SimpleMotorFeedforward m_driveFeedforward = new SimpleMotorFeedforward(1, 3);    // TODO: Adjust drive feedForward gains per testing.
  private final SimpleMotorFeedforward m_turnFeedforward = new SimpleMotorFeedforward(1, 0.5);   // TODO: Adjust steer feedForward gains per testing.

  /**
   * Constructs a SwerveModule with a drive motor, turning motor, drive encoder
   * and turning encoder.
   *
   * @param driveMotorID CAN ID for the drive motor.
   * @param steerMotorID CAN ID for the steering motor.
   */
  public SwerveModule(int driveMotorID, int steerMotorID) {
    m_driveMotor = new CANSparkMax(driveMotorID, Constants.BRUSHLESS_MOTOR);
    m_steerMotor = new WPI_TalonSRX(steerMotorID);

    m_driveEncoder = m_driveMotor.getEncoder();
    m_steerEncoder = new WPI_CANCoder(steerMotorID);

    // Set the distance per pulse for the drive encoder. We can simply use the
    // distance traveled for one rotation of the wheel divided by the encoder
    // resolution.
    // Note: The drive encoder uses 1024 ticks per revolution and is accessed by
    // getPosition()
    // whereas the Lamprey encoder getPosition() return current degree of the
    // encoder.
    // m_driveEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius /
    // kEncoderResolution);
    driveDistancePerPulse = 2 * Math.PI * kWheelRadius / Constants.DRIVE_ENCODER_RESOLUTION;

    // Set the distance (in this case, angle) per pulse for the turning encoder.
    // This is the the angle through an entire rotation (2 * pi) divided by the
    // encoder resolution.
    // m_turningEncoder.setDistancePerPulse(2 * Math.PI / kEncoderResolution);
    steerDistancePerPulse = 2 * Math.PI / Constants.STEER_ENCODER_RESOLUTION;

    // Limit the PID Controller's input range between -pi and pi and set the input
    // to be continuous.
    m_turningPIDController.enableContinuousInput(-Math.PI, Math.PI);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    // return new SwerveModuleState(m_driveEncoder.getRate(), new
    // Rotation2d(m_steerEncoder.get()));
    return new SwerveModuleState(m_driveEncoder.getVelocity() * driveDistancePerPulse,
        new Rotation2d(m_steerEncoder.getPosition()));
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Optimize the reference state to avoid spinning further than 90 degrees
    SwerveModuleState state = SwerveModuleState.optimize(desiredState, new Rotation2d(m_steerEncoder.getPosition()));

    // Calculate the drive output from the drive PID controller.
    // final double driveOutput =
    // m_drivePIDController.calculate(m_driveEncoder.getRate(),
    // state.speedMetersPerSecond);
    final double driveOutput = m_drivePIDController.calculate(m_driveEncoder.getVelocity() * driveDistancePerPulse,
        state.speedMetersPerSecond);

    final double driveFeedforward = m_driveFeedforward.calculate(state.speedMetersPerSecond);

    // Calculate the turning motor output from the turning PID controller.
    final double turnOutput = m_turningPIDController.calculate(m_steerEncoder.getPosition(), state.angle.getRadians());

    final double turnFeedforward = m_turnFeedforward.calculate(m_turningPIDController.getSetpoint().velocity);

    m_driveMotor.setVoltage(driveOutput + driveFeedforward);
    m_steerMotor.setVoltage(turnOutput + turnFeedforward);
  }
}
