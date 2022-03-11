// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.IntakerSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An Intaker command that uses an Intaker subsystem. */
@SuppressWarnings("unused")
public class IntakerCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final IntakerSubsystem intakerSubsystem;

  /**
   * Creates a new IntakerCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakerCommand(IntakerSubsystem subsystem) {
    intakerSubsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intakerSubsystem.intake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
