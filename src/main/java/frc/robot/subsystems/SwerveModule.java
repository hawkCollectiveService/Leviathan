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


  public SwerveModule(int driveMotorID, int steerMotorID, String name) {
    ShuffleboardTab tab = Shuffleboard.getTab(name + " Swerve Module");
    driveEncoder = tab.add("driveEncoderValue", 0).getEntry();
    steerEncoder = tab.add("steerEncoderValue", 0).getEntry();
    driveVoltage = tab.add("driveMotorVoltage", 0).getEntry();
    steerVoltage = tab.add("steerMotorVoltage", 0).getEntry();

    m_driveMotor = new CANSparkMax(driveMotorID, Constants.BRUSHLESS_MOTOR);
    m_steerMotor = new WPI_TalonSRX(steerMotorID);

    m_driveEncoder = m_driveMotor.getEncoder();
    m_steerEncoder = new WPI_CANCoder(steerMotorID);
  }

  public double getDriveEncoder() {
    driveEncoder.setDouble(this.m_driveEncoder.getPosition());
    return this.m_driveEncoder.getPosition();
  }

  public double getSteerEncoder() {
    steerEncoder.setDouble(this.m_steerEncoder.getPosition());
    return this.m_steerEncoder.getPosition();
  }
  
  public void setDriveSpeed(double driveSpeed) {
    driveVoltage.setDouble(driveSpeed);
    m_driveMotor.setVoltage(driveSpeed);
  }

  public void setSteerSpeed(double steerSpeed) {
    steerVoltage.setDouble(steerSpeed);
    m_steerMotor.setVoltage(steerSpeed);
  }

  public void orientTo(int degree, double speed) {
    double offset = this.m_steerEncoder.getPosition() - degree;

    if( Math.abs(offset) > Constants.DEGREE_TOLERANCE){
      if(offset > 0) {
        setSteerSpeed(-1 * speed);
      } else {
        setSteerSpeed(speed);
      }
    } else {
      setSteerSpeed(0);
    }
  }
}
