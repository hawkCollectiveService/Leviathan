// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.DriveTrainCommand;
import frc.robot.commands.IntakerCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.AutonomousSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IntakerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;


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

  // Connects DrivetrainSubsystem and DriveTrainCommand together
  private final DriveTrainSubsystem driveTrainSubsystem = new DriveTrainSubsystem();
  private final DriveTrainCommand driveTrainCommand = new DriveTrainCommand(driveTrainSubsystem);
  
  // Connects the IntakerSubsystem and IntakerCommand together
  private final IntakerSubsystem intakerSubsystem = new IntakerSubsystem();
  private final IntakerCommand intakerCommand = new IntakerCommand(intakerSubsystem);

  // Connects ShooterSubsystem and ShooterCommand together
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final ShooterCommand shooterCommand = new ShooterCommand(shooterSubsystem);

  // Connects ClimberSubsystem and ClimberCommand together
  private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  private final ClimberCommand climberCommand = new ClimberCommand(climberSubsystem);

  // Connects AutonomousSubsystem and AutonomousCommand together
  private final AutonomousSubsystem autonomousSubsystem = new AutonomousSubsystem();
  private final AutonomousCommand autonomousCommand = new AutonomousCommand(autonomousSubsystem);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
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

  public Command getShooterCommand() {
    return shooterCommand;
  }

  public Command getDriveTrainCommand(){
    return driveTrainCommand;
  }

  public Command getClimberCommand() {
    return climberCommand;
  }

  public Command getIntakerCommand(){
    return intakerCommand;
  }

  public Command getAutonomousCommand() {
    return autonomousCommand;
  }

  public DriveTrainSubsystem getDriveTrainSubsystem() {
    return this.driveTrainSubsystem;
  }

  public ShooterSubsystem getShooterSubsystem() {
    return this.shooterSubsystem;
  }
}
