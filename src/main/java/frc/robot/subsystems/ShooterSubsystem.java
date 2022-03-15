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

public class ShooterSubsystem extends SubsystemBase {

  private ShuffleboardTab tab = Shuffleboard.getTab("Subsystem");

  public NetworkTableEntry lowShooterSpeed = tab.add("Low Shooter Speed", Constants.Shooter.SHOOTER_LOW_SPEED)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  public NetworkTableEntry highShooterSpeed = tab.add("High Shooter Speed", Constants.Shooter.SHOOTER_HIGH_SPEED)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  public NetworkTableEntry feederSpeed = tab.add("Feeder Speed", Constants.Feeder.FEEDER_SPEED)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  private WPI_TalonSRX shooterFrontTalonSRX = new WPI_TalonSRX(Constants.Shooter.LEFT_SHOOTER_ID);
  private WPI_TalonSRX shooterBackTalonSRX = new WPI_TalonSRX(Constants.Shooter.RIGHT_SHOOTER_ID);
  private WPI_TalonSRX feederTalonSRX = new WPI_TalonSRX(Constants.Feeder.FEEDER_ID);
  private XboxController assistantController = new XboxController(Constants.Xbox.XBOX_ASSISTANT_CONTROLLER_ID);

  public ShooterSubsystem() {
    shootStop(); 
  }

  public void shoot() {

    if (assistantController.getBButtonPressed()) {
      startFeeder();
    }

    // If the A button is pressed, the shoot Low process will begin.
    if (assistantController.getXButtonPressed()) {
      shootLow();
    }

    // If the Y button is pressed, the shoot High process will begin.
    if (assistantController.getYButtonPressed()) {
      shootHigh();
    }

    // Temporary stop for the shooter
    if (assistantController.getXButtonReleased()
        || assistantController.getYButtonReleased()) {
      shootStop();
    }

    if (assistantController.getBButtonReleased()) {
      stopFeeder();
    }

  }

  public void shootHigh() {
    startFeeder();
    shooterFrontTalonSRX.set(Constants.Shooter.SHOOTER_BOTTOM_POLARITY_MOD * highShooterSpeed.getDouble(Constants.Shooter.SHOOTER_HIGH_SPEED));
    shooterBackTalonSRX.set(Constants.Shooter.SHOOTER_TOP_POLARITY_MOD * highShooterSpeed.getDouble(Constants.Shooter.SHOOTER_LOW_SPEED));
  }

  public void shootLow() {
    startFeeder();
    shooterFrontTalonSRX.set(Constants.Shooter.SHOOTER_BOTTOM_POLARITY_MOD * lowShooterSpeed.getDouble(Constants.Shooter.SHOOTER_LOW_SPEED));
    shooterBackTalonSRX.set(Constants.Shooter.SHOOTER_TOP_POLARITY_MOD * lowShooterSpeed.getDouble(Constants.Shooter.SHOOTER_LOW_SPEED));
  }

  public void shootStop() {
    shooterFrontTalonSRX.set(Constants.NO_SPEED);
    shooterBackTalonSRX.set(Constants.NO_SPEED);
    stopFeeder();
  }

  private void startFeeder() {
    feederTalonSRX.set(Constants.Feeder.FEEDER_POLARITY_MOD * feederSpeed.getDouble(Constants.Feeder.FEEDER_SPEED));
  }

  private void stopFeeder() {
    feederTalonSRX.set(Constants.NO_SPEED);
  }

}
