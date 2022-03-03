// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.Constants;

public class SwerveModule {

  private static final double kWheelRadius = Constants.RADIUS_OF_WHEEL;

  private final CANSparkMax m_driveMotor;
  private final WPI_TalonSRX m_steerMotor;

  private final RelativeEncoder m_driveEncoder;
  private final WPI_CANCoder m_steerEncoder;

  private double driveDistancePerPulse;
  private double steerDistancePerPulse;

  public SwerveModule(int driveMotorID, int steerMotorID) {
    m_driveMotor = new CANSparkMax(driveMotorID, Constants.BRUSHLESS_MOTOR);
    m_steerMotor = new WPI_TalonSRX(steerMotorID);

    m_driveEncoder = m_driveMotor.getEncoder();
    m_steerEncoder = new WPI_CANCoder(steerMotorID);

    driveDistancePerPulse = 2 * Math.PI * kWheelRadius / Constants.DRIVE_ENCODER_RESOLUTION;
    steerDistancePerPulse = 2 * Math.PI / Constants.STEER_ENCODER_RESOLUTION;
  }

  public double getDriveEncoder() {
    return this.m_driveEncoder.getPosition();
  }

  public double getSteerEncoder() {
    return this.m_steerEncoder.getPosition();
  }
  
  public void setDriveSpeed(double driveSpeed) {
    m_driveMotor.setVoltage(driveSpeed);
  }

  public void setSteerSpeed(double steerSpeed) {
    m_steerMotor.setVoltage(steerSpeed);
  }

  public void orientTo(int degree) {
    //Steer the wheels to a particular orientation
  }
}
