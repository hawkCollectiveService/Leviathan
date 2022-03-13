package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

@SuppressWarnings("unused")
public class AutonomousSubsystem extends SubsystemBase {

  private boolean firstRun = true;
  RobotContainer robotContainer = Robot.getRobotContainer();
  ShooterSubsystem shooterSubsystem;
  DriveTrainSubsystem driveTrainSubsystem;
  ClimberSubsystem climberSubsystem;

  private ShuffleboardTab tab = Shuffleboard.getTab("Autonomous");

  public NetworkTableEntry autoDriveSpeed = tab.add("AUTO DRIVE SPEED", Constants.Autonomous.AUTO_DRIVE_SPEED)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", -1, "max", 0)).getEntry();

  public NetworkTableEntry autoDriveDelay = tab.add("AUTO DRIVE DELAY", Constants.Autonomous.AUTO_DRIVE_TIME_DELAY)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 5)).getEntry();

  public NetworkTableEntry autoShooterDelay = tab.add("AUTO SHOOTER DELAY", Constants.Autonomous.AUTO_SHOOT_TIME_DELAY)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 5)).getEntry();

  /**
   * Creates a new AutonomousSubsystem.
   */
  public AutonomousSubsystem(ShooterSubsystem shooterSubsystem, DriveTrainSubsystem driveTrainSubsystem, ClimberSubsystem climberSubsystem) {
    this.shooterSubsystem = shooterSubsystem;
    this.driveTrainSubsystem = driveTrainSubsystem;
    this.climberSubsystem = climberSubsystem;
  }

  /**
   * Method to be called on scheduler to run autonomous
   */
  public void autonomous() {

    climberSubsystem.climb();

    if(firstRun){

      driveTrainSubsystem.drive(autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED), autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED)); 

      Timer.delay(autoDriveDelay.getDouble(Constants.Autonomous.AUTO_DRIVE_TIME_DELAY));  
      
      driveTrainSubsystem.drive(0, 0);   
      shooterSubsystem.shootHigh();        
      
      Timer.delay(autoShooterDelay.getDouble(Constants.Autonomous.AUTO_SHOOT_TIME_DELAY));                                       
      
      shooterSubsystem.shootStop(); 
      driveTrainSubsystem.drive(autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED), autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED)); 
      
      Timer.delay(autoDriveDelay.getDouble(Constants.Autonomous.AUTO_DRIVE_TIME_DELAY));                                       
      
      driveTrainSubsystem.drive(0, 0);
      
      firstRun = false;
    }
  }
}