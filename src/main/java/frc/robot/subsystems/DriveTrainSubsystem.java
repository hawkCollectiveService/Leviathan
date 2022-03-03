/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AnalogGyro;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {
  public static final double kMaxSpeed = 3.0; // 3 meters per second
  public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

  private XboxController driverController = new XboxController(Constants.XBOX_DRIVER_CONTROLLER_PORT_ID);
  private XboxController assistantController = new XboxController(Constants.XBOX_ASSISTANT_DRIVER_CONTROLLER_ID);

  private final SwerveModule leftFrontSwerveModule = new SwerveModule(Constants.LEFT_FRONT_SPARK_MAX_ID,
      Constants.LEFT_FRONT_TALON_SRX_ID);
  private final SwerveModule rightFrontSwerveModule = new SwerveModule(Constants.RIGHT_FRONT_SPARK_MAX_ID,
      Constants.RIGHT_FRONT_TALON_SRX_ID);
  private final SwerveModule leftBackSwerveModule = new SwerveModule(Constants.LEFT_BACK_SPARK_MAX_ID,
      Constants.LEFT_BACK_TALON_SRX_ID);
  private final SwerveModule rightBackSwerveModule = new SwerveModule(Constants.RIGHT_BACK_SPARK_MAX_ID,
      Constants.RIGHT_BACK_TALON_SRX_ID);

  private final AnalogGyro m_gyro = new AnalogGyro(Constants.GYRO_PORT);

  public DriveTrainSubsystem() {
    m_gyro.reset();
  }


  public void drive(double xSpeed, double ySpeed, double rSpeed) {

    double speed = 0.5;

    if (xSpeed != 0 && ySpeed !=0){

        //Driving Logic
        if (isPositive(xSpeed) && isPositive(ySpeed)){

            setModuleOrientation(45, speed);
            setModuleSpeed(speed);

        } else if (!isPositive(xSpeed) && !isPositive(ySpeed)) {

            setModuleOrientation(225, speed);
            setModuleSpeed(speed);
            
        } else if (!isPositive(xSpeed) && isPositive(ySpeed)) {

            setModuleOrientation(315, speed);
            setModuleSpeed(speed);
            
        } else if (isPositive(xSpeed) && !isPositive(ySpeed)) {

            setModuleOrientation(135, speed);
            setModuleSpeed(speed);
            
        }

    } else if (xSpeed != 0 && ySpeed == 0){

        if(isPositive(ySpeed)){

            setModuleOrientation(0, speed);
            setModuleSpeed(speed);

        } else {

            setModuleOrientation(180, speed);
            setModuleSpeed(speed);

        }

    } else if (xSpeed == 0 && ySpeed != 0){

        if(isPositive(xSpeed)){

            setModuleOrientation(90, speed);
            setModuleSpeed(speed);

        } else {

            setModuleOrientation(270, speed);
            setModuleSpeed(speed);

        }

    } else if (xSpeed == 0 && ySpeed == 0 && rSpeed != 0) {

        setModulesOrientationForRotation(rSpeed);
        
    } else if (xSpeed == 0 && ySpeed == 0 && rSpeed == 0) {

        setModuleSpeed(0.0);

    } 


  }

  public void xboxSwerveDrive() {
    
    double driveLeftY = generateDeadZones(driverController.getLeftY());
    double driveLeftX = generateDeadZones(driverController.getLeftX());
    double driveRightX = generateDeadZones(driverController.getRightX());

    this.drive(driveLeftX, driveLeftY, driveRightX);
  }
  
  public double generateDeadZones(double input){
    if (input < Math.abs(Constants.DEADZONE_MAGNITUDE)) {
        return 0.0;
    } else {
        return input;
    }
  }

  public boolean isPositive(double input){
    return input > 0;
  }

  public void setModuleOrientation(int degree, double rSpeed){
    this.leftFrontSwerveModule.orientTo(degree, rSpeed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD);
    this.leftBackSwerveModule.orientTo(degree, rSpeed * Constants.LEFT_BACK_STEER_SPEED_MOD * Constants.LEFT_BACK_STEER_POLARITY_MOD);
    this.rightFrontSwerveModule.orientTo(degree, rSpeed * Constants.RIGHT_FRONT_STEER_SPEED_MOD * Constants.RIGHT_FRONT_STEER_POLARITY_MOD);
    this.rightBackSwerveModule.orientTo(degree, rSpeed * Constants.RIGHT_BACK_STEER_SPEED_MOD * Constants.RIGHT_BACK_STEER_POLARITY_MOD);
  }

  public void setModulesOrientationForRotation(double rSpeed){

    if (isPositive(rSpeed)){
        this.leftFrontSwerveModule.orientTo(45, rSpeed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD);
        this.leftBackSwerveModule.orientTo(315, rSpeed * Constants.LEFT_BACK_STEER_SPEED_MOD * Constants.LEFT_BACK_STEER_POLARITY_MOD);
        this.rightFrontSwerveModule.orientTo(135, rSpeed * Constants.RIGHT_FRONT_STEER_SPEED_MOD * Constants.RIGHT_FRONT_STEER_POLARITY_MOD);
        this.rightBackSwerveModule.orientTo(225, rSpeed * Constants.RIGHT_BACK_STEER_SPEED_MOD * Constants.RIGHT_BACK_STEER_POLARITY_MOD);
    } else {
        this.leftFrontSwerveModule.orientTo(225, rSpeed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD);
        this.leftBackSwerveModule.orientTo(135, rSpeed * Constants.LEFT_BACK_STEER_SPEED_MOD * Constants.LEFT_BACK_STEER_POLARITY_MOD);
        this.rightFrontSwerveModule.orientTo(315, rSpeed * Constants.RIGHT_FRONT_STEER_SPEED_MOD * Constants.RIGHT_FRONT_STEER_POLARITY_MOD);
        this.rightBackSwerveModule.orientTo(45, rSpeed * Constants.RIGHT_BACK_STEER_SPEED_MOD * Constants.RIGHT_BACK_STEER_POLARITY_MOD);
    }

  }

  public void setModuleSpeed(double speed){
    this.leftFrontSwerveModule.setDriveSpeed(speed * Constants.LEFT_FRONT_DRIVE_SPEED_MOD * Constants.LEFT_FRONT_DRIVE_POLARITY_MOD);
    this.leftBackSwerveModule.setDriveSpeed(speed * Constants.LEFT_BACK_DRIVE_SPEED_MOD * Constants.LEFT_BACK_DRIVE_POLARITY_MOD);
    this.rightFrontSwerveModule.setDriveSpeed(speed * Constants.RIGHT_FRONT_DRIVE_SPEED_MOD * Constants.RIGHT_FRONT_DRIVE_POLARITY_MOD);
    this.rightBackSwerveModule.setDriveSpeed(speed * Constants.RIGHT_BACK_DRIVE_SPEED_MOD * Constants.RIGHT_BACK_DRIVE_POLARITY_MOD);
  }

}
