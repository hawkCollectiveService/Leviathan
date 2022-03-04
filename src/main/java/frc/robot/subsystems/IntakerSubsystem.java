// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Shuffle;

public class IntakerSubsystem extends SubsystemBase {

  private ShuffleboardTab tab = Shuffleboard.getTab("Constants");
  public NetworkTableEntry intakeSpeed = tab.add("Intake Speed", 1.0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  private WPI_TalonSRX intakeTalon = new WPI_TalonSRX(Constants.INTAKE_ID);
  private XboxController assistantDriver = new XboxController(Constants.XBOX_ASSISTANT_DRIVER_CONTROLLER_ID);

  /** Creates a new IntakerSubsystem. */
  public IntakerSubsystem() {
  }

  public void intake() {
    // This method will be called once per scheduler run
    if (assistantDriver.getAButtonPressed()) {
      intakeTalon.set(intakeSpeed.getDouble(Constants.INTAKE_SPEED) * Constants.INTAKE_POLARITY_MOD);
    }
    if (assistantDriver.getAButtonReleased()) {
      intakeTalon.set(Constants.NO_SPEED);
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
