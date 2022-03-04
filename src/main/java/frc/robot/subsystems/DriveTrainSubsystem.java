/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogGyro;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {
  public static final double kMaxSpeed = 3.0; // 3 meters per second
  public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

  /**
   * Xbox controller object used in the case the driver drives with an Xbox
   * controller
   */
  private XboxController driverController = new XboxController(Constants.XBOX_DRIVER_CONTROLLER_PORT_ID);
  private XboxController assistantController = new XboxController(Constants.XBOX_ASSISTANT_DRIVER_CONTROLLER_ID);

  private NetworkTableEntry driveLeftX;
  private NetworkTableEntry driveLeftY;
  private NetworkTableEntry driveRightX;

  private boolean m_lf_allow = true;
  private boolean m_lb_allow = true;
  private boolean m_rf_allow = true;
  private boolean m_rb_allow = true;
  private int lastControl = 0;

  private final SwerveModule leftFrontSwerveModule = new SwerveModule(Constants.LEFT_FRONT_SPARK_MAX_ID,
      Constants.LEFT_FRONT_TALON_SRX_ID, "LEFT_FRONT");
  //private final SwerveModule rightFrontSwerveModule = new SwerveModule(Constants.RIGHT_FRONT_SPARK_MAX_ID,
  //    Constants.RIGHT_FRONT_TALON_SRX_ID, "RIGHT_FRONT");
  //private final SwerveModule leftBackSwerveModule = new SwerveModule(Constants.LEFT_BACK_SPARK_MAX_ID,
  //    Constants.LEFT_BACK_TALON_SRX_ID, "LEFT_BACK");
  //private final SwerveModule rightBackSwerveModule = new SwerveModule(Constants.RIGHT_BACK_SPARK_MAX_ID,
  //    Constants.RIGHT_BACK_TALON_SRX_ID, "RIGHT_BACK");

  private final AnalogGyro m_gyro = new AnalogGyro(Constants.GYRO_PORT);

  public DriveTrainSubsystem() {
    m_gyro.reset();
    ShuffleboardTab tab = Shuffleboard.getTab("DriveTrain");
    driveLeftX = tab.add("Drive Controller Left Stick X", 0).getEntry();
    driveLeftY = tab.add("Drive Controller Left Stick Y", 0).getEntry();
    driveRightX = tab.add("Drive Controller Right Stick X", 0).getEntry();
  }

  public void drive(double xSpeed, double ySpeed, double rSpeed) {

    double speed = 1;

    if (xSpeed != 0 && ySpeed !=0){

        //Driving Logic
        if (isPositive(xSpeed) && isPositive(ySpeed)){
            if (lastControl != 1){
                lastControl = 1;
                m_lf_allow = true;
            }

            if (Constants.DEBUG)
                System.out.println("X is positive && Y is positive");
            m_lf_allow = this.leftFrontSwerveModule.orientTo(45, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        } else if (!isPositive(xSpeed) && !isPositive(ySpeed)) {
            if (lastControl != 2){
                lastControl = 2;
                m_lf_allow = true;
            }

            if (Constants.DEBUG)
                System.out.println("X is negative && Y is negative");
            m_lf_allow = this.leftFrontSwerveModule.orientTo(225, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);
            
        } else if (!isPositive(xSpeed) && isPositive(ySpeed)) {
            if (lastControl != 3){
                lastControl = 3;
                m_lf_allow = true;
            }

            if (Constants.DEBUG)
                System.out.println("X is negative && Y is positive");
            m_lf_allow = this.leftFrontSwerveModule.orientTo(315, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);
            
        } else if (isPositive(xSpeed) && !isPositive(ySpeed)) {
            if (lastControl != 4){
                lastControl = 4;
                m_lf_allow = true;
            }

            if (Constants.DEBUG)
                System.out.println("X is positive && Y is negative");
            m_lf_allow = this.leftFrontSwerveModule.orientTo(135, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);
        }

    } else if (xSpeed == 0 && ySpeed != 0){

        if(isPositive(ySpeed)){
            if (lastControl != 5){
                lastControl = 5;
                m_lf_allow = true;
            }
            if (Constants.DEBUG)
                System.out.println("Y axis positive");

            m_lf_allow = this.leftFrontSwerveModule.orientTo(0, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        } else {
            if (lastControl != 6){
                lastControl = 6;
                m_lf_allow = true;
            }
            if (Constants.DEBUG)
                System.out.println("Y axis negative");
            
            m_lf_allow = this.leftFrontSwerveModule.orientTo(180, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        }

    } else if (xSpeed != 0 && ySpeed == 0){

        if(isPositive(xSpeed)){
            if (lastControl != 7){
                lastControl = 7;
                m_lf_allow = true;
            }
            
            if (Constants.DEBUG)
                System.out.println("X axis positive");

            m_lf_allow = this.leftFrontSwerveModule.orientTo(90, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        } else {
            if (lastControl != 8){
                lastControl = 8;
                m_lf_allow = true;
            }
            if (Constants.DEBUG)
                System.out.println("X axis negative");

            m_lf_allow = this.leftFrontSwerveModule.orientTo(270, speed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        }

    } else if (xSpeed == 0 && ySpeed == 0 && rSpeed != 0) {

        if (Constants.DEBUG)
            System.out.println("Rotating");
        setModulesOrientationForRotation(rSpeed);
        
    } else if (xSpeed == 0 && ySpeed == 0 && rSpeed == 0) {
        lastControl = 0;
        m_lf_allow = true;
        if (Constants.DEBUG)
            System.out.println("Stopping");
        setModuleSpeed(0.0);
        stopTurns();

    } 
  }

  public void xboxSwerveDrive() {
    
    double driveLeftY = generateDeadZones(driverController.getLeftY() * Constants.XBOX_POLARITY_MOD);
    double driveLeftX = generateDeadZones(driverController.getLeftX());
    double driveRightX = generateDeadZones(driverController.getRightX());

    this.driveLeftX.setDouble(driveLeftX);
    this.driveLeftY.setDouble(driveLeftY);
    this.driveRightX.setDouble(driveRightX);

    this.drive(driveLeftX, driveLeftY, driveRightX);
  }
  
  public double generateDeadZones(double input){
    if (Math.abs(input) < Constants.DEADZONE_MAGNITUDE) {
        return 0.0;
    } else {
        return input;
    }
  }

  public boolean isPositive(double input){
    return input > 0;
  }

  public void setModuleOrientation(int degree, double rSpeed){
    //this.leftFrontSwerveModule.orientTo(degree, rSpeed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD);
    //this.leftBackSwerveModule.orientTo(degree, rSpeed * Constants.LEFT_BACK_STEER_SPEED_MOD * Constants.LEFT_BACK_STEER_POLARITY_MOD);
    //this.rightFrontSwerveModule.orientTo(degree, rSpeed * Constants.RIGHT_FRONT_STEER_SPEED_MOD * Constants.RIGHT_FRONT_STEER_POLARITY_MOD);
    //this.rightBackSwerveModule.orientTo(degree, rSpeed * Constants.RIGHT_BACK_STEER_SPEED_MOD * Constants.RIGHT_BACK_STEER_POLARITY_MOD);
  }

  public void setModulesOrientationForRotation(double rSpeed){

    if (isPositive(rSpeed)){
        //this.leftFrontSwerveModule.orientTo(45, rSpeed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD);
        //this.leftBackSwerveModule.orientTo(315, rSpeed * Constants.LEFT_BACK_STEER_SPEED_MOD * Constants.LEFT_BACK_STEER_POLARITY_MOD);
        //this.rightFrontSwerveModule.orientTo(135, rSpeed * Constants.RIGHT_FRONT_STEER_SPEED_MOD * Constants.RIGHT_FRONT_STEER_POLARITY_MOD);
        //this.rightBackSwerveModule.orientTo(225, rSpeed * Constants.RIGHT_BACK_STEER_SPEED_MOD * Constants.RIGHT_BACK_STEER_POLARITY_MOD);
    } else {
        //this.leftFrontSwerveModule.orientTo(225, rSpeed * Constants.LEFT_FRONT_STEER_SPEED_MOD * Constants.LEFT_FRONT_STEER_POLARITY_MOD);
        //this.leftBackSwerveModule.orientTo(135, rSpeed * Constants.LEFT_BACK_STEER_SPEED_MOD * Constants.LEFT_BACK_STEER_POLARITY_MOD);
        //this.rightFrontSwerveModule.orientTo(315, rSpeed * Constants.RIGHT_FRONT_STEER_SPEED_MOD * Constants.RIGHT_FRONT_STEER_POLARITY_MOD);
        //this.rightBackSwerveModule.orientTo(45, rSpeed * Constants.RIGHT_BACK_STEER_SPEED_MOD * Constants.RIGHT_BACK_STEER_POLARITY_MOD);
    }
  }

  public void setModuleSpeed(double speed){
    this.leftFrontSwerveModule.setDriveSpeed(speed * Constants.LEFT_FRONT_DRIVE_SPEED_MOD * Constants.LEFT_FRONT_DRIVE_POLARITY_MOD);
    //this.leftBackSwerveModule.setDriveSpeed(speed * Constants.LEFT_BACK_DRIVE_SPEED_MOD * Constants.LEFT_BACK_DRIVE_POLARITY_MOD);
    //this.rightFrontSwerveModule.setDriveSpeed(speed * Constants.RIGHT_FRONT_DRIVE_SPEED_MOD * Constants.RIGHT_FRONT_DRIVE_POLARITY_MOD);
    //this.rightBackSwerveModule.setDriveSpeed(speed * Constants.RIGHT_BACK_DRIVE_SPEED_MOD * Constants.RIGHT_BACK_DRIVE_POLARITY_MOD);
  }

  public void stopTurns(){
    this.leftFrontSwerveModule.setSteerSpeed(0.0);
    //this.leftBackSwerveModule.setSteerSpeed(0.0);
    //this.rightFrontSwerveModule.setSteerSpeed(0.0);
    //this.rightBackSwerveModule.setSteerSpeed(0.0);
  }
}
