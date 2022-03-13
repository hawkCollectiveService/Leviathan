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
  private XboxController driverController = new XboxController(Constants.Xbox.XBOX_DRIVER_CONTROLLER_ID);

  private NetworkTableEntry driveLeftY;
  private NetworkTableEntry driveRightY;

  private final SwerveModule leftFrontSwerveModule = new SwerveModule(Constants.Chassis.LEFT_FRONT_SPARK_MAX_ID,
      Constants.Chassis.LEFT_FRONT_TALON_SRX_ID, "LEFT_FRONT");
  private final SwerveModule rightFrontSwerveModule = new SwerveModule(Constants.Chassis.RIGHT_FRONT_SPARK_MAX_ID,
     Constants.Chassis.RIGHT_FRONT_TALON_SRX_ID, "RIGHT_FRONT");
  private final SwerveModule leftBackSwerveModule = new SwerveModule(Constants.Chassis.LEFT_BACK_SPARK_MAX_ID,
     Constants.Chassis.LEFT_BACK_TALON_SRX_ID, "LEFT_BACK");
  private final SwerveModule rightBackSwerveModule = new SwerveModule(Constants.Chassis.RIGHT_BACK_SPARK_MAX_ID,
     Constants.Chassis.RIGHT_BACK_TALON_SRX_ID, "RIGHT_BACK");

  private final AnalogGyro m_gyro = new AnalogGyro(Constants.Chassis.GYRO_PORT);

  private boolean m_lf_allow = true;
  private boolean m_lb_allow = true;
  private boolean m_rf_allow = true;
  private boolean m_rb_allow = true;
  private int lastControl = 0; 

  public DriveTrainSubsystem() {
    m_gyro.reset();
    ShuffleboardTab tab = Shuffleboard.getTab("Main");
    driveLeftY = tab.add("Drive Left Stick Y", 0).getEntry();
    driveRightY = tab.add("Drive Right Stick Y", 0).getEntry();
  }

  public void driveSwerve(double xSpeed, double ySpeed, double rSpeed) {

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
            m_lf_allow = this.leftBackSwerveModule.orientTo(45, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        } else if (!isPositive(xSpeed) && !isPositive(ySpeed)) {
            if (lastControl != 2){
                lastControl = 2;
                m_lf_allow = true;
            }

            if (Constants.DEBUG)
                System.out.println("X is negative && Y is negative");
            m_lf_allow = this.leftBackSwerveModule.orientTo(225, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);
            
        } else if (!isPositive(xSpeed) && isPositive(ySpeed)) {
            if (lastControl != 3){
                lastControl = 3;
                m_lf_allow = true;
            }

            if (Constants.DEBUG)
                System.out.println("X is negative && Y is positive");
            m_lf_allow = this.leftBackSwerveModule.orientTo(315, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);
            
        } else if (isPositive(xSpeed) && !isPositive(ySpeed)) {
            if (lastControl != 4){
                lastControl = 4;
                m_lf_allow = true;
            }

            if (Constants.DEBUG)
                System.out.println("X is positive && Y is negative");
            m_lf_allow = this.leftBackSwerveModule.orientTo(135, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
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

            m_lf_allow = this.leftBackSwerveModule.orientTo(0, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        } else {
            if (lastControl != 6){
                lastControl = 6;
                m_lf_allow = true;
            }
            if (Constants.DEBUG)
                System.out.println("Y axis negative");
            
            m_lf_allow = this.leftBackSwerveModule.orientTo(180, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
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

            m_lf_allow = this.leftBackSwerveModule.orientTo(90, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
            setModuleSpeed(speed);

        } else {
            if (lastControl != 8){
                lastControl = 8;
                m_lf_allow = true;
            }
            if (Constants.DEBUG)
                System.out.println("X axis negative");

            m_lf_allow = this.leftBackSwerveModule.orientTo(270, speed * Constants.Chassis.LEFT_FRONT_STEER_SPEED_MOD * Constants.Chassis.LEFT_FRONT_STEER_POLARITY_MOD, m_lf_allow);
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

  public void drive(double leftSpeed, double rightSpeed) {

    this.leftFrontSwerveModule.setDriveSpeed(leftSpeed * Constants.Chassis.LEFT_FRONT_DRIVE_POLARITY_MOD * Constants.Chassis.LEFT_FRONT_DRIVE_SPEED_MOD);
    this.leftBackSwerveModule.setDriveSpeed(leftSpeed * Constants.Chassis.LEFT_BACK_DRIVE_POLARITY_MOD * Constants.Chassis.LEFT_BACK_DRIVE_SPEED_MOD);
    this.rightBackSwerveModule.setDriveSpeed(rightSpeed * Constants.Chassis.RIGHT_BACK_DRIVE_POLARITY_MOD * Constants.Chassis.RIGHT_BACK_DRIVE_SPEED_MOD);
    this.rightFrontSwerveModule.setDriveSpeed(rightSpeed * Constants.Chassis.RIGHT_FRONT_DRIVE_POLARITY_MOD * Constants.Chassis.RIGHT_FRONT_DRIVE_SPEED_MOD);

  }

  public void xboxSwerveDrive() {
    
    double driveLeftY = generateDeadZones(driverController.getLeftY() * Constants.Xbox.XBOX_POLARITY_MOD);
    double driveRightY = generateDeadZones(driverController.getRightY() * Constants.Xbox.XBOX_POLARITY_MOD);
    this.driveRightY.setDouble(driveRightY);
    this.driveLeftY.setDouble(driveLeftY);

    //this.drive(driveLeftY, driveRightY);
    this.driveSwerve(generateDeadZones(driverController.getLeftX()), generateDeadZones(driverController.getLeftY()), 0);
  }
  
  public double generateDeadZones(double input){
    if (Math.abs(input) < Constants.Xbox.DEADZONE_MAGNITUDE) {
        return 0.0;
    } else {
        return input;
    }
  }

  public boolean isPositive(double input){
    return input > 0;
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
    speed = 0;
    //this.leftFrontSwerveModule.setDriveSpeed(speed * Constants.Chassis.LEFT_FRONT_DRIVE_SPEED_MOD * Constants.Chassis.LEFT_FRONT_DRIVE_POLARITY_MOD);
    this.leftBackSwerveModule.setDriveSpeed(speed * Constants.Chassis.LEFT_BACK_DRIVE_SPEED_MOD * Constants.Chassis.LEFT_BACK_DRIVE_POLARITY_MOD);
    //this.rightFrontSwerveModule.setDriveSpeed(speed * Constants.RIGHT_FRONT_DRIVE_SPEED_MOD * Constants.RIGHT_FRONT_DRIVE_POLARITY_MOD);
    //this.rightBackSwerveModule.setDriveSpeed(speed * Constants.Chassis.RIGHT_BACK_DRIVE_SPEED_MOD * Constants.ChassRIGHT_BACK_DRIVE_POLARITY_MOD);
  }

  public void stopTurns(){
    this.leftFrontSwerveModule.setSteerSpeed(0.0);
    //this.leftBackSwerveModule.setSteerSpeed(0.0);
    //this.rightFrontSwerveModule.setSteerSpeed(0.0);
    //this.rightBackSwerveModule.setSteerSpeed(0.0);
  }
}
