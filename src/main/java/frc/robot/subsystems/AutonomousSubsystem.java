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
  ShooterSubsystem shoot;
  DriveTrainSubsystem drive;
  ClimberSubsystem climb;

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
  public AutonomousSubsystem(ShooterSubsystem shooter, DriveTrainSubsystem drive, ClimberSubsystem climb) {
    this.shoot = shooter;
    this.drive = drive;
    this.climb = climb;
  }

  /**
   * Method to be called on scheduler to run autonomous
   */
  public void autonomous() {
    // Gather selection from Shuffleboard that was declared in Robot
    // String choice = Robot.startingPositionChooser.getSelected();
    climb.climb();

    if(firstRun){

      drive.drive(autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED), autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED));   //Start driving backwards

      Timer.delay(autoDriveDelay.getDouble(Constants.Autonomous.AUTO_DRIVE_TIME_DELAY));                                        //Give the robot time to drive backwards
      
      drive.drive(0, 0);     //Stop driving
      shoot.shootHigh();        //Start shooting
      
      Timer.delay(autoShooterDelay.getDouble(Constants.Autonomous.AUTO_SHOOT_TIME_DELAY));                                          //Give the robot time to shoot
      
      shoot.shootStop();        //Stop shooting
      drive.drive(autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED), autoDriveSpeed.getDouble(Constants.Autonomous.AUTO_DRIVE_SPEED));   //Start driving backwards
      
      Timer.delay(autoDriveDelay.getDouble(Constants.Autonomous.AUTO_DRIVE_TIME_DELAY));                                        //Give the robot time to drive backwards
      
      drive.drive(0, 0);     //Stop driving
      
      firstRun = false;                                        //Dont repeat this logic
    }
  }
}