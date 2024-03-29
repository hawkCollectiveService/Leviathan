/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {

  private final ClimberSubsystem climberSubsystem;
  /**
   * Creates a new ClimberCommand.
   */
  public ClimberCommand(ClimberSubsystem subsystem) {
    climberSubsystem = subsystem;
    addRequirements(subsystem);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Calls which drive mode we're going to use during competitions
  @Override
  public void execute() {
    climberSubsystem.climb();
    climberSubsystem.exceedsLimits(); // 2022-11-02. Is this line needed??  Should be removed and tested.
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}