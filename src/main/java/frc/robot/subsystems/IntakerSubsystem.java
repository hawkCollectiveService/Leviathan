// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakerSubsystem extends SubsystemBase {

  private WPI_TalonSRX intakeTalon = new WPI_TalonSRX(Constants.INTAKE_ID);
  private XboxController assistantDriver = new XboxController(Constants.XBOX_ASSISTANT_DRIVER_CONTROLLER_ID);

  /** Creates a new IntakerSubsystem. */
  public IntakerSubsystem() {}

  
  public void intake()
  {
    // This method will be called once per scheduler run
    if (assistantDriver.getAButtonPressed())
    {
      intakeTalon.set(Constants.INTAKE_SPEED * Constants.INTAKE_POLARITY_MOD);
    }
    if (assistantDriver.getAButtonReleased())
    {
      intakeTalon.set(Constants.NO_SPEED);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
