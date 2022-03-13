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

  public DriveTrainSubsystem() {
    m_gyro.reset();
    ShuffleboardTab tab = Shuffleboard.getTab("Main");
    driveLeftY = tab.add("Drive Left Stick Y", 0).getEntry();
    driveRightY = tab.add("Drive Right Stick Y", 0).getEntry();
  }

  public void drive(double leftSpeed, double rightSpeed) {

    this.leftFrontSwerveModule.setDriveSpeed(leftSpeed * Constants.Chassis.LEFT_FRONT_DRIVE_POLARITY_MOD * Constants.Chassis.LEFT_FRONT_DRIVE_SPEED_MOD);
    this.leftBackSwerveModule.setDriveSpeed(leftSpeed * Constants.Chassis.LEFT_BACK_DRIVE_POLARITY_MOD * Constants.Chassis.LEFT_BACK_DRIVE_SPEED_MOD);
    this.rightBackSwerveModule.setDriveSpeed(rightSpeed * Constants.Chassis.RIGHT_BACK_DRIVE_POLARITY_MOD * Constants.Chassis.RIGHT_BACK_DRIVE_SPEED_MOD);
    this.rightFrontSwerveModule.setDriveSpeed(rightSpeed * Constants.Chassis.RIGHT_FRONT_DRIVE_POLARITY_MOD * Constants.Chassis.RIGHT_FRONT_DRIVE_SPEED_MOD);

  }

  public void xboxSwerveDrive() {
    
    double driveLeftY = generateDeadZones(driverController.getLeftY() * Constants.Xbox.XBOX_JOYSTICK_POLARITY_MOD);
    double driveRightY = generateDeadZones(driverController.getRightY() * Constants.Xbox.XBOX_JOYSTICK_POLARITY_MOD);
    this.driveRightY.setDouble(driveRightY);
    this.driveLeftY.setDouble(driveLeftY);

    this.drive(driveLeftY, driveRightY);
  }
  
  public double generateDeadZones(double input){
    if (Math.abs(input) < Constants.Xbox.DEADZONE_MAGNITUDE) {
        return 0.0;
    } else {
        return input;
    }
  }
}
