// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.commands.ClimberCommand;
// import frc.robot.commands.ExampleCommand;
// import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.AutonomousSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.DriveTrainCommand;
import frc.robot.commands.IntakerCommand;
import frc.robot.subsystems.IntakerSubsystem;


/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // Connects DrivetrainSubsystem and DriveTrainCommand together
  private final DriveTrainSubsystem m_driveTrainSubsystem = new DriveTrainSubsystem();
  private final DriveTrainCommand m_driveTrainCommand = new DriveTrainCommand(m_driveTrainSubsystem);
  private final IntakerSubsystem intakerSubsystem = new IntakerSubsystem();
  private final IntakerCommand intakerCommand = new IntakerCommand(intakerSubsystem);

  // Connects ShooterSubsystem and ShooterCommand together
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final ShooterCommand m_shooterCommand = new ShooterCommand(m_shooterSubsystem);
  private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
  private final ClimberCommand m_climberCommand = new ClimberCommand(m_climberSubsystem);
  // Creates an autonomous subsystem and command
  private final AutonomousSubsystem m_autonomousSubsystem = new AutonomousSubsystem();
  private final AutonomousCommand m_autonomousCommand = new AutonomousCommand(m_autonomousSubsystem);

  // Connects ClimberSubsystem and ClimberCommand together
  // private final ClimberSubsystem m_climberSubsystem = new ClimberSubsystem();
  // private final ClimberCommand m_climberCommand = new
  // ClimberCommand(m_climberSubsystem);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    System.out.println("DEVCHECK configureButtonBindings");
  }

 /**
   * Returns the ShooterCommand object to run Shooter during teleop
   */
  public Command getShooterCommand() {
    return m_shooterCommand;
  }

  public Command getDriveTrainCommand(){
    return m_driveTrainCommand;
  }

 /**
   * Returns the ClimberCommand object to run Climber during teleop
  /**
   * Returns the DriveTrainCommand object to run drive train during teleop
   */
  public Command getClimberCommand() {
    return m_climberCommand;
  }
  
  public Command getIntakerCommand(){
    return intakerCommand;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Command will run in autonomous
    return m_autonomousCommand;
  }

  public DriveTrainSubsystem getDriveTrainSubsystem() {
    return this.m_driveTrainSubsystem;
  }

  public ShooterSubsystem getShooterSubsystem() {
    return this.m_shooterSubsystem;
  }
}
