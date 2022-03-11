package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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

  /**
   * Creates a new AutonomousSubsystem.
   */
  public AutonomousSubsystem() {}

  /**
   * Method to be called on scheduler to run autonomous
   */
  public void autonomous() {
    // Gather selection from Shuffleboard that was declared in Robot
    String choice = Robot.startingPositionChooser.getSelected();

    if(firstRun){

      robotContainer.getDriveTrainSubsystem().drive(-1, -1);   //Start driving backwards

      Timer.delay(0.5);                                        //Give the robot time to drive backwards
      
      robotContainer.getDriveTrainSubsystem().drive(0, 0);     //Stop driving
      robotContainer.getShooterSubsystem().shootHigh();        //Start shooting
      
      Timer.delay(3);                                          //Give the robot time to shoot
      
      robotContainer.getShooterSubsystem().shootStop();        //Stop shooting
      robotContainer.getDriveTrainSubsystem().drive(-1, -1);   //Drive backwards some more
      
      Timer.delay(2);                                          //Some time to drive backwards
      
      robotContainer.getDriveTrainSubsystem().drive(0, 0);     //Stop driving
      
      firstRun = false;                                        //Dont repeat this logic
    }
  }
}