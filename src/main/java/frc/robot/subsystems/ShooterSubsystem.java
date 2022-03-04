/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Shuffle;

@SuppressWarnings("unused")
public class ShooterSubsystem extends SubsystemBase {

  private ShuffleboardTab tab = Shuffleboard.getTab("Constants");
  public NetworkTableEntry lowShooterSpeed = tab.add("Low Shooter Speed", 1.0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();
  public NetworkTableEntry highShooterSpeed = tab.add("High Shooter Speed", 1.0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();
  public NetworkTableEntry feederSpeed = tab.add("Feeder Speed", 1.0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  /**
   * TalonFX Controllers - for Shooter
   */
  private WPI_TalonSRX shooterFrontTalonSRX = new WPI_TalonSRX(Constants.LEFT_SHOOTER_ID);
  private WPI_TalonSRX shooterBackTalonSRX = new WPI_TalonSRX(Constants.RIGHT_SHOOTER_ID);

  /**
   * TalonFX Controllers - for Feeder.
   */
  private WPI_TalonSRX feederTalonSRX = new WPI_TalonSRX(Constants.FEEDER_ID);

  /**
   * Xbox controller object used in the case the driver drives with an Xbox
   * controller
   * XButton for shoot Low
   * YButton for shoot High
   */
  private XboxController assistantDriverController = new XboxController(Constants.XBOX_ASSISTANT_DRIVER_CONTROLLER_ID);

  /**
   * Constructor forShooterSubsystem.
   */
  public ShooterSubsystem() {
    shootStop(); // default state.
  }

  /**
   * Method for using Xbox Controllers for the Shooter.
   * Note: When the shooting, the Feeder should also feed.
   * Feeder assigned to XBoxController BButton.
   */
  public void shoot() {

    if (assistantDriverController.getBButtonPressed()) {
      startFeeder();
    }

    // If the X button is pressed, the shoot Low process will begin.
    if (assistantDriverController.getXButtonPressed()) {
      startFeeder();
      shootLow();
    }

    // If the Y button is pressed, the shoot High process will begin.
    if (assistantDriverController.getYButtonPressed()) {
      startFeeder();
      shootHigh();
    }

    // Temporary stop for the shooter
    if (assistantDriverController.getXButtonReleased()
        || assistantDriverController.getYButtonReleased()) {
      shootStop();
    }

    if (assistantDriverController.getBButtonReleased()) {
      stopFeeder();
    }

  }

  public void shootHigh() {
    shooterFrontTalonSRX.set(highShooterSpeed.getDouble(Constants.SHOOTER_HIGH_SPEED));
    shooterBackTalonSRX.set(-1 * highShooterSpeed.getDouble(Constants.SHOOTER_HIGH_SPEED));
  }

  public void shootLow() {
    shooterFrontTalonSRX.set(lowShooterSpeed.getDouble(Constants.SHOOTER_LOW_SPEED));
    shooterBackTalonSRX.set(-1 * lowShooterSpeed.getDouble(Constants.SHOOTER_LOW_SPEED));
  }

  public void shootStop() {
    shooterFrontTalonSRX.set(Constants.NO_SPEED);
    shooterBackTalonSRX.set(Constants.NO_SPEED);
    stopFeeder();
  }

  private void startFeeder() {
    feederTalonSRX.set(feederSpeed.getDouble(Constants.FEEDER_SPEED));
  }

  private void stopFeeder() {
    feederTalonSRX.set(Constants.NO_SPEED);
  }

}
