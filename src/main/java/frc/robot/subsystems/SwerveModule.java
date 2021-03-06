// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import frc.robot.Constants;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

@SuppressWarnings("unused")
public class SwerveModule {

  private static final double kWheelRadius = Constants.Swerve.RADIUS_OF_WHEEL;

  private final CANSparkMax driveMotor;
  private final WPI_TalonSRX steerMotor;
  private final RelativeEncoder driveEncoder;

  private NetworkTableEntry driveEncoderUI;
  private NetworkTableEntry steerEncoderUI;
  private NetworkTableEntry driveVoltage;
  private NetworkTableEntry steerVoltage;

  private String name;

  public SwerveModule(int driveMotorID, int steerMotorID, String name) {
    this.name = name;

    ShuffleboardTab tab = Shuffleboard.getTab("DriveTrain");
    driveEncoderUI = tab.add(name + " driveEncoderValue", 0).getEntry();
    steerEncoderUI = tab.add(name + " steerEncoderValue", 0).getEntry();
    driveVoltage = tab.add(name + " driveMotorVoltage", 0).getEntry();
    steerVoltage = tab.add(name + " steerMotorVoltage", 0).getEntry();

    driveMotor = new CANSparkMax(driveMotorID, Constants.Chassis.BRUSHLESS_MOTOR);
    steerMotor = new WPI_TalonSRX(steerMotorID);

    driveEncoder = driveMotor.getEncoder();
    resetEncoders();
  }

  public void resetEncoders() {
    this.steerMotor.getSensorCollection().setAnalogPosition(0, 1000);
    this.steerMotor.setStatusFramePeriod(4, 1);
  }

  public double getDriveEncoder() {
    driveEncoderUI.setDouble(this.driveEncoder.getPosition());
    return this.driveEncoder.getPosition();
  }

  public double getSteerEncoder() {
    steerEncoderUI.setDouble(Math.abs(steerMotor.getSensorCollection().getAnalogIn()));
    return Math.abs(steerMotor.getSensorCollection().getAnalogIn());
  }
  
  public void setDriveSpeed(double driveSpeed) {
    driveVoltage.setDouble(driveSpeed);
    driveMotor.setVoltage(driveSpeed);
  }

  public void setSteerSpeed(double steerSpeed) {
    steerVoltage.setDouble(steerSpeed);
    steerMotor.setVoltage(steerSpeed);
  }
}