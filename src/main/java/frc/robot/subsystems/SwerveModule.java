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

  private static final double kWheelRadius = Constants.RADIUS_OF_WHEEL;

  private final CANSparkMax m_driveMotor;
  private final WPI_TalonSRX m_steerMotor;
  private final RelativeEncoder m_driveEncoder;
  private final WPI_CANCoder m_steerEncoder;

  private NetworkTableEntry driveEncoder;
  private NetworkTableEntry steerEncoder;
  private NetworkTableEntry driveVoltage;
  private NetworkTableEntry steerVoltage;

  private String name;

  public SwerveModule(int driveMotorID, int steerMotorID, String name) {
    this.name = name;

    ShuffleboardTab tab = Shuffleboard.getTab(name + " Swerve Module");
    driveEncoder = tab.add("driveEncoderValue", 0).getEntry();
    steerEncoder = tab.add("steerEncoderValue", 0).getEntry();
    driveVoltage = tab.add("driveMotorVoltage", 0).getEntry();
    steerVoltage = tab.add("steerMotorVoltage", 0).getEntry();

    m_driveMotor = new CANSparkMax(driveMotorID, Constants.BRUSHLESS_MOTOR);
    m_steerMotor = new WPI_TalonSRX(steerMotorID);

    m_driveEncoder = m_driveMotor.getEncoder();
    m_steerEncoder = new WPI_CANCoder(steerMotorID);
    resetEncoders();
  }

  public double getDriveEncoder() {
    driveEncoder.setDouble(this.m_driveEncoder.getPosition());
    return this.m_driveEncoder.getPosition();
  }

  public double getSteerEncoder() {
    steerEncoder.setDouble(Math.abs(m_steerMotor.getSensorCollection().getAnalogIn()));
    //System.out.println("SteerEncoder for " + this.name + " has pos " + m_steerMotor.getSelectedSensorPosition());
    return Math.abs(m_steerMotor.getSensorCollection().getAnalogIn());
  }

  public void resetEncoders() {
    this.m_steerMotor.getSensorCollection().setAnalogPosition(0, 1000);
    this.m_steerMotor.setStatusFramePeriod(4, 1);
  }
  
  public void setDriveSpeed(double driveSpeed) {
    driveVoltage.setDouble(driveSpeed);
    m_driveMotor.setVoltage(driveSpeed);
  }

  public void setSteerSpeed(double steerSpeed) {
    steerVoltage.setDouble(steerSpeed);
    m_steerMotor.setVoltage(steerSpeed);
  }

  public boolean orientTo(int degree, double speed, boolean allowSignal) {
    int scale = (int)getSteerEncoder() / 360;
    int desired = 360 * scale + degree;

    double offset = getSteerEncoder() - desired;

    System.out.println("Scale " + scale + "Encoder: " + getSteerEncoder() + " Desired: " + desired + " Offset: " + offset);

    if( Math.abs(offset) > Constants.DEGREE_TOLERANCE && allowSignal){
      // if(offset > 0) {
      //   setSteerSpeed(-1 * speed);
      //   return true;
      // } else {
        setSteerSpeed(speed);
        return true;
      //}
    } else {
      setSteerSpeed(0);
      return false;
    }
  }
}
