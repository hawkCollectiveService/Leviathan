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
  private final WPI_CANCoder steerEncoder;

  private NetworkTableEntry driveEncoderUI;
  private NetworkTableEntry steerEncoderUI;
  private NetworkTableEntry driveVoltage;
  private NetworkTableEntry steerVoltage;

  private String name;

  public SwerveModule(int driveMotorID, int steerMotorID, String name) {
    this.name = name;

    ShuffleboardTab tab = Shuffleboard.getTab(name + " Swerve Module");
    driveEncoderUI = tab.add("driveEncoderValue", 0).getEntry();
    steerEncoderUI = tab.add("steerEncoderValue", 0).getEntry();
    driveVoltage = tab.add("driveMotorVoltage", 0).getEntry();
    steerVoltage = tab.add("steerMotorVoltage", 0).getEntry();

    driveMotor = new CANSparkMax(driveMotorID, Constants.Chassis.BRUSHLESS_MOTOR);
    steerMotor = new WPI_TalonSRX(steerMotorID);

    driveEncoder = driveMotor.getEncoder();
    steerEncoder = new WPI_CANCoder(steerMotorID);
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

  public boolean orientTo(int degree, double speed, boolean allowSignal) {
    int scale = (int)getSteerEncoder() / 360;
    int desired = 360 * scale + degree;

    double offset = getSteerEncoder() - desired;

    System.out.println("Scale " + scale + "Encoder: " + getSteerEncoder() + " Desired: " + desired + " Offset: " + offset);

    if( Math.abs(offset) > Constants.Swerve.DEGREE_TOLERANCE && allowSignal){
      if(offset > 0) {
        setSteerSpeed(-1 * speed);
        return true;
      } else {
        setSteerSpeed(speed);
        return true;
      }
    } else {
      setSteerSpeed(0);
      return false;
    }
  }
}
