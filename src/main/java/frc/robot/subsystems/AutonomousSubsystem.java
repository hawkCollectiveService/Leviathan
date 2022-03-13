package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings("unused")
public class AutonomousSubsystem extends SubsystemBase {

  private boolean firstRun = true;
  RobotContainer robotContainer = Robot.getRobotContainer();
  ShooterSubsystem shooter;
  DriveTrainSubsystem drive;

  /**
   * Creates a new AutonomousSubsystem.
   */
  public AutonomousSubsystem(ShooterSubsystem shooter, DriveTrainSubsystem drive) {
    this.shooter = shooter;
    this.drive = drive;
  }

  /**
   * Method to be called on scheduler to run autonomous
   */
  public void autonomous() {
    // Gather selection from Shuffleboard that was declared in Robot
    // String choice = Robot.startingPositionChooser.getSelected();

    if(firstRun){

      drive.drive(-0.50, -0.50);   //Start driving backwards

      Timer.delay(1.5);                                        //Give the robot time to drive backwards
      
      drive.drive(0, 0);     //Stop driving
      shooter.shootHigh();        //Start shooting
      
      Timer.delay(2);                                          //Give the robot time to shoot
      
      shooter.shootStop();        //Stop shooting
      drive.drive(-0.5, -0.5);   //Drive backwards some more
      
      Timer.delay(1.5);                                          //Some time to drive backwards
      
      drive.drive(0, 0);     //Stop driving
      
      firstRun = false;                                        //Dont repeat this logic
    }
  }
}