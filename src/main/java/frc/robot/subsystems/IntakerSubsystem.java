// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakerSubsystem extends SubsystemBase {

  private ShuffleboardTab tab = Shuffleboard.getTab("Subsystem");
  
  public NetworkTableEntry intakeSpeed = tab.add("Intake Speed", Constants.Intake.INTAKE_SPEED)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

    public NetworkTableEntry intakeFlipSpeed = tab.add("Intake Flip Speed", Constants.Intake.INTAKE_SPEED)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  private WPI_TalonSRX intakeSpinnerTalon = new WPI_TalonSRX(Constants.Intake.INTAKE_SPINNER_ID);
  private WPI_TalonSRX intakeFlipTalon = new WPI_TalonSRX(Constants.Intake.INTAKE_FLIP_ID);

  private XboxController assistantDriver = new XboxController(Constants.Xbox.XBOX_ASSISTANT_CONTROLLER_ID);
  private XboxController driver = new XboxController(Constants.Xbox.XBOX_DRIVER_CONTROLLER_ID);

  /** Creates a new IntakerSubsystem. */
  public IntakerSubsystem() {
  }

  public void intake() {
    if (driver.getRightBumperPressed())
    {
      lower();
    }
    else if(driver.getRightBumperReleased())
    {
      stopFlip();
    }

    if (driver.getLeftBumperPressed())
    {
      raise();
    }
    else if (driver.getLeftBumperReleased())
    {
      stopFlip();
    }
    // A-Button acts as a brake.
    if (driver.getAButtonPressed()) {
      //clockSpin();
      counterClockSpin();  // 2022-11-05.
      //stop();
   } else if (driver.getAButtonReleased()) {
     stop();
    }
  }

  public void counterClockSpin() {

    intakeSpinnerTalon.set(intakeSpeed.getDouble(Constants.Intake.INTAKE_SPEED) * Constants.Intake.INTAKE_POLARITY_MOD);

  }

  public void clockSpin() {

    intakeSpinnerTalon.set(intakeSpeed.getDouble(Constants.Intake.INTAKE_SPEED) * Constants.Intake.INTAKE_POLARITY_MOD * -1);

  }

  public void stop() {

    intakeSpinnerTalon.set(Constants.NO_SPEED);

  }

  public void lower()
  {
    intakeFlipTalon.set(intakeFlipSpeed.getDouble(Constants.Intake.INTAKE_FLIP_SPEED)*Constants.Intake.INTAKE_FLIP_POLARITY_MOD);
  }

  public void raise()
  {
    intakeFlipTalon.set(intakeFlipSpeed.getDouble(Constants.Intake.INTAKE_FLIP_SPEED)*Constants.Intake.INTAKE_FLIP_POLARITY_MOD *-1);
  }

  public void stopFlip() {

    intakeFlipTalon.set(Constants.NO_SPEED);

  }
}
