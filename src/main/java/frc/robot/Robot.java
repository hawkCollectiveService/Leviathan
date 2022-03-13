package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.cscore.UsbCamera;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  private static RobotContainer m_robotContainer = new RobotContainer();
  private Command m_autonomousCommand;

  public static SendableChooser<String> startingPositionChooser = new SendableChooser<>();
  public UsbCamera intakeCamera = CameraServer.startAutomaticCapture();
  public UsbCamera liftCamera = CameraServer.startAutomaticCapture();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    CommandScheduler.getInstance().cancelAll();

    // This will perform all our button bindings, and put our autonomous chooser & cameras on the dashboard.
    System.out.println("DEVCHECK robotInit");
    
    startingPositionChooser.addOption("Move Off Line", "Move");
    startingPositionChooser.addOption("Shoot", "Shoot");
    startingPositionChooser.addOption("Test", "Test");

    Shuffleboard.getTab("Main").add("Lift Camera", liftCamera);
    Shuffleboard.getTab("Main").add("Intak Camera", intakeCamera);

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    CommandScheduler.getInstance().run();
  
  }

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {

    CommandScheduler.getInstance().cancelAll();
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

    m_robotContainer.getAutonomousCommand().execute();

  }

  @Override
  public void teleopInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    m_robotContainer.getShooterCommand().execute();
    m_robotContainer.getClimberCommand().execute();
    m_robotContainer.getIntakerCommand().execute();
    m_robotContainer.getDriveTrainCommand().execute();

  }

  @Override
  public void testInit() {

    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

  }

  // Method to return the instance of robot container
  public static RobotContainer getRobotContainer() {

    return m_robotContainer;

  }

}
