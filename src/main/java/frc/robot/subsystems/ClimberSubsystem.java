/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("unused")
public class ClimberSubsystem extends SubsystemBase {
  
  /**
   * TalonFX Controllers - Falcon500 Winch Motors
   */
  private WPI_TalonFX leftTalonFX = new WPI_TalonFX(Constants.LEFT_CLIMBER_TALON_FX_ID);
  // private WPI_TalonFX rightTalonFX = new WPI_TalonFX(Constants.RIGHT_TALON_FX_ID);
     
  //private XboxController assistantDriverController = new XboxController(Constants.XBOX_ASSISTANT_DRIVER_CONTROLLER_ID);
  
  /**
   * Xbox controller object used in the case the driver drives with an Xbox controller
   */
  private XboxController driverController = new XboxController(Constants.XBOX_DRIVER_CONTROLLER_PORT_ID);

  /**
   * Joystick objects used in the case the driver drives with joysticks
   */
  private Joystick leftJoystick = new Joystick(Constants.DRIVER_JOYSTICK_Y_PORT_ID);
  private Joystick rightJoystick = new Joystick(Constants.DRIVER_JOYSTICK_X_PORT_ID);

  /**
   * Speeds used for arcade drive. Y for forwards and backwards. X for turning left and right
   */
  private double yDriveSpeed = 0.0;
  private double xDriveSpeed = 0.0;

  /**
   * Speeds used for tank drive. Left for left side of bot. Right for right side of bot
   */
  private double leftDriveSpeed = 0.0;
  private double rightDriveSpeed = 0.0;

  /**
   * Creates a new driveTrainSubsystem.
   */
  public ClimberSubsystem() 
  {
    System.out.println("DEVCHECK ClimberSubsystem constructor");
    //Sets left side of Spark Maxs inverted for proper functioning
    //leftTalonFX.setInverted(true);
    //leftBackSparkMax.setInverted(true);
  }

  /**
   * Method for using Xbox Controllers for arcade drive
   */
  public void xboxArcadeDrive()
  {
    //Sets forwards and backwards speed (y) to the y-axis of the left joystick. Sets turning speed (x) tp x-axis of right joystick
    yDriveSpeed = driverController.getLeftY() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;
    xDriveSpeed = driverController.getRightX() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;

    //System.out.printf("DriveSpeed:  LeftY=%s  RightX=%s \n", xDriveSpeed, yDriveSpeed);
    //Calls arcade drive method and sends speeds
    arcadeDrive(yDriveSpeed, xDriveSpeed);
  }
  
  /**
   * Method for using Joysticks for arcade drive
   */
  public void joystickArcadeDrive()
  {
    //Sets forwards and backwards speed (y) to the y-axis of the left joystick. Sets turning speed (x) tp x-axis of right joystick
    yDriveSpeed = leftJoystick.getX() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;
    xDriveSpeed = rightJoystick.getY() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;

    //Calls arcade drive method and sends speeds
    arcadeDrive(-yDriveSpeed, -xDriveSpeed);
  }

  /**
   * Method for using an xbox controller for tank drive
   */
  public void xboxTankDrive()
  {
    //Sets left side of bot speed to the y-axis of the left joystick. Sets right side of bot speed to y-axis of the right joystick
    leftDriveSpeed = driverController.getLeftY() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;
    rightDriveSpeed = driverController.getRightY() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;

    //Calls tank drive method and sends speeds
    tankDrive(leftDriveSpeed, rightDriveSpeed);
  }

  /**
   * Method for using Joystick controllers for tank drive
   */
  public void joystickTankDrive()
  {
    //Sets left side of bot speed to the y-axis of the left joystick. Sets right side of bot speed to y-axis of the right joystick
    leftDriveSpeed = leftJoystick.getX() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;
    rightDriveSpeed = rightJoystick.getX() * Constants.TELEOP_DRIVE_SPEED_MODIFIER;

    //Calls tank drive method and sends speeds
    tankDrive(leftDriveSpeed, rightDriveSpeed);
  }

  /**
   * Method that enables movement via arcade style drive
   */
  public void arcadeDrive(double ySpeed, double xSpeed)
  {
    //Assigns motor to forwards/backwards speed if no turning is detected
    drive(ySpeed, ySpeed);
    //If turning is detected, it will be added to one speed side and subtracted from the other speed side to generate the effect of turning
    //whilst moving forwards/backwards at the same time
    if (xSpeed >= 0.05 || xSpeed <= -0.05)
    {
      drive(-xSpeed + ySpeed, xSpeed + ySpeed);
    }
  }

  /**
   * Method that enables movement via tank style drive
   */
  public void tankDrive(double leftJoySpeed, double rightJoySpeed)
  {
    drive(leftJoySpeed, rightJoySpeed);
  }

  /**
   * Assigns speeds to left and right controllers on bot
   */
  public void drive(double leftSpeed, double rightSpeed)
  {
  
    if(!Constants.isTargeting)
    {
      //leftTalonFX.set(leftSpeed);
      //rightTalonFX.set(rightSpeed);
   }
  }
}
