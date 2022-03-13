package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.WPI_CANCoder;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("unused")
public class ClimberSubsystem extends SubsystemBase {

  /**
   * TalonFX Controllers - for Climber (Winch)
   */
  private WPI_TalonFX leftWinchTalonFX = new WPI_TalonFX(Constants.Climber.LEFT_WINCH_TALON_FX_ID);
  private WPI_TalonFX rightWinchTalonFX = new WPI_TalonFX(Constants.Climber.RIGHT_WINCH_TALON_FX_ID);
  // private WPI_CANCoder leftTalonFXEncoder = new WPI_CANCoder(Constants.Climber.LEFT_WINCH_TALON_FX_ID);

  private Servo leftLatch = new Servo(0); // PWM Port number
  // private Servo rightLatch = new Servo(1); // PWM Port number
  private boolean hasLifted = false;
  private boolean needsCorrection = false;
  private boolean enableCorrection = true;

  private ShuffleboardTab tab = Shuffleboard.getTab("Subsystem");

  private NetworkTableEntry leftEncoderPosition;

  public NetworkTableEntry latchLockPosition = tab.add("Latch Lock Position", Constants.Climber.LATCH_LOCKED_POS)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  public NetworkTableEntry latchUnlockedPosition = tab.add("Latch Unlock Position", Constants.Climber.LATCH_UNLOCKED_POS)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1)).getEntry();

  /**
   * Xbox controller object used in the case the driver drives with an Xbox
   * controller
   * XButton for shoot Low
   * YButton for shoot High
   */
  private XboxController assistantDriverController = new XboxController(Constants.Xbox.XBOX_ASSISTANT_CONTROLLER_ID);

  /**
   * Constructor forShooterSubsystem.
   */
  public ClimberSubsystem() {

    leftEncoderPosition = tab.add("Left Arm Encoder", 0).getEntry();

    this.leftWinchTalonFX.getSensorCollection().setIntegratedSensorPosition(0, 1000);

    stopClimber();
  }

  /**
   * Method for using Xbox Controllers for the Climber.
   * When LeftBumper and RightBumper are both pressed, the Climb
   * process will start.
   */
  public void climb() {
    readEncoder();

    if (readEncoder() >= (Constants.Climber.CLIMBER_ENCODER_MAX_VAL - Constants.Climber.CLIMBER_ENCODER_STEP) && enableCorrection && !needsCorrection) {

      needsCorrection = true;

    } else if (needsCorrection) {

      contract();

      if (readEncoder() <= (Constants.Climber.CLIMBER_ENCODER_MAX_VAL - (Constants.Climber.CLIMBER_ENCODER_STEP * 2))){
        needsCorrection = false;
        stopClimber();
      }

    } else if (assistantDriverController.getLeftBumperPressed() && !assistantDriverController.getRightBumperPressed()){
      
      extend();
      hasLifted = true;

    } else if (assistantDriverController.getRightBumperPressed() && !assistantDriverController.getLeftBumperPressed() && hasLifted){

      contract();

    } else if (assistantDriverController.getLeftBumperReleased()){

      stopClimber();

    } else if (assistantDriverController.getRightBumperReleased()){

      stopClimber();

    } else if (assistantDriverController.getStartButtonPressed()){

      disableCorrections();

    } else if (assistantDriverController.getBackButtonPressed()){

      enableCorrections();

    } else if (assistantDriverController.getRightStickButtonPressed()){
      
      leftLatch.set(latchUnlockedPosition.getDouble(Constants.Climber.LATCH_UNLOCKED_POS));
      //rightLatch.set(latchUnlockedPosition.getDouble(Constants.Climber.LATCH_UNLOCKED_POS));

    } else if (assistantDriverController.getLeftStickButtonPressed()){

      leftLatch.set(latchLockPosition.getDouble(Constants.Climber.LATCH_LOCKED_POS));
      //rightLatch.set(latchLockPosition.getDouble(Constants.Climber.LATCH_LOCKED_POS));

    }
  }

  private void extend() {
    leftWinchTalonFX.set(Constants.Climber.LEFT_CLIMB_SPEED * Constants.Climber.LEFT_CLIMBER_POLARITY_MOD); 
    rightWinchTalonFX.set(Constants.Climber.RIGHT_CLIMB_SPEED * Constants.Climber.RIGHT_CLIMBER_POLARITY_MOD);
  }

  private void contract() {
    leftWinchTalonFX.set((-1) * Constants.Climber.LEFT_CLIMB_SPEED * Constants.Climber.LEFT_CLIMBER_POLARITY_MOD); 
    rightWinchTalonFX.set((-1) * Constants.Climber.RIGHT_CLIMB_SPEED * Constants.Climber.LEFT_CLIMBER_POLARITY_MOD);
  }

  private void stopClimber() {
    leftWinchTalonFX.set(Constants.NO_SPEED);
    rightWinchTalonFX.set(Constants.NO_SPEED);
  }

  public double readEncoder() {
    this.leftEncoderPosition.setDouble(Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));

    // if (Constants.DEBUG){
    //   System.out.println("Encoder Position: " + Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));
    // }

    return Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition());
  }

  private void disableCorrections() {
    enableCorrection = false;
    needsCorrection = false;
  }

  private void enableCorrections() {
    enableCorrection = true;
  }
}
