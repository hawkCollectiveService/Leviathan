/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;

import frc.robot.Constants;

/**
 * @since 2022-02-xx
 * @version 2022-02-24 -integrated SwerveModule.<br/> 
 */
@SuppressWarnings("unused")
public class DriveTrainSubsystem extends SubsystemBase {
  public static final double kMaxSpeed = 3.0; // 3 meters per second
  public static final double kMaxAngularSpeed = Math.PI; // 1/2 rotation per second

  /**
   * Xbox controller object used in the case the driver drives with an Xbox
   * controller
   */
  private XboxController driverController = new XboxController(Constants.XBOX_DRIVER_CONTROLLER_PORT_ID);

  /**
   * Swerve Drive Kinematics
   * The SwerveDriveKinematics class converts between a
   * ChassisSpeeds object and several SwerveModuleState objects, which contains
   * velocities and angles for each swerve module of a swerve drive robot.
   */
  // Locations for the swerve drive modules relative to the robot center.
  Translation2d m_frontLeftLocation = new Translation2d(Constants.DISTANCE_FROM_CENTER,
      Constants.DISTANCE_FROM_CENTER);
  Translation2d m_frontRightLocation = new Translation2d(Constants.DISTANCE_FROM_CENTER,
      -Constants.DISTANCE_FROM_CENTER);
  Translation2d m_backLeftLocation = new Translation2d(-Constants.DISTANCE_FROM_CENTER,
      Constants.DISTANCE_FROM_CENTER);
  Translation2d m_backRightLocation = new Translation2d(-Constants.DISTANCE_FROM_CENTER,
      -Constants.DISTANCE_FROM_CENTER);

  private final SwerveModule leftFrontSwerveModule = new SwerveModule(Constants.LEFT_FRONT_SPARK_MAX_ID,
      Constants.LEFT_FRONT_TALON_SRX_ID);
  private final SwerveModule rightFrontSwerveModule = new SwerveModule(Constants.RIGHT_FRONT_SPARK_MAX_ID,
      Constants.RIGHT_FRONT_TALON_SRX_ID);
  private final SwerveModule leftBackSwerveModule = new SwerveModule(Constants.LEFT_BACK_SPARK_MAX_ID,
      Constants.LEFT_BACK_TALON_SRX_ID);
  private final SwerveModule rightBackSwerveModule = new SwerveModule(Constants.RIGHT_BACK_SPARK_MAX_ID,
      Constants.RIGHT_BACK_TALON_SRX_ID);

  private final AnalogGyro m_gyro = new AnalogGyro(Constants.GYRO_PORT);

  // Creating my kinematics object using the module locations
  SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
      m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation);

  private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics, m_gyro.getRotation2d());

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(1);
  private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(1);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(1);

  // TODO: Refactor this class so that declarations and initializations are
  // separated by the constructor.
  public DriveTrainSubsystem() {
    m_gyro.reset();
  }

  /**
   * Method to drive the robot using joystick info.
   *
   * @param xSpeed        Speed of the robot in the x direction (forward).
   * @param ySpeed        Speed of the robot in the y direction (sideways).
   * @param rot           Angular rate of the robot.
   * @param fieldRelative Whether the provided x and y speeds are relative to the
   *                      field.
   */
  @SuppressWarnings("ParameterName")
  public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
    var swerveModuleStates = m_kinematics.toSwerveModuleStates(
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rot, m_gyro.getRotation2d())
            : new ChassisSpeeds(xSpeed, ySpeed, rot));
    SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, kMaxSpeed);

    leftFrontSwerveModule.setDesiredState(swerveModuleStates[0], false, false, true);
    rightFrontSwerveModule.setDesiredState(swerveModuleStates[1], true, true, true);
    leftBackSwerveModule.setDesiredState(swerveModuleStates[2], true, true, false);
    rightBackSwerveModule.setDesiredState(swerveModuleStates[3], false, true, false);
  }

  public void xboxSwerveDrive(boolean fieldRelative) {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    var xSpeed = -m_xspeedLimiter.calculate(MathUtil.applyDeadband(driverController.getLeftY(), 0.02))
        * DriveTrainSubsystem.kMaxSpeed;

    // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    var ySpeed = -m_yspeedLimiter.calculate(MathUtil.applyDeadband(driverController.getLeftX(), 0.02))
        * DriveTrainSubsystem.kMaxSpeed;

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    var rot = -m_rotLimiter.calculate(MathUtil.applyDeadband(driverController.getRightX(), 0.02))
        * DriveTrainSubsystem.kMaxAngularSpeed;

    //this.drive(xSpeed, ySpeed, rot, fieldRelative);
  
    if (Math.abs(xSpeed) < 0.2) xSpeed=0;
    if (Math.abs(ySpeed) < 0.2) ySpeed=0;
    if (Math.abs(rot) < 0.2) rot=0;
    // this.drive(0.0, 0.0, 0.0, fieldRelative);  // devTest
    this.drive(xSpeed, ySpeed, rot, fieldRelative);
  }

}
