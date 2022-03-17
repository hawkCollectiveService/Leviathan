package frc.robot.subsystems;

import java.util.Map;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
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

  // private Servo leftLatch = new Servo(Constants.Climber.CLIMBER_LEFT_LATCH_PWM_PORT);   // PWM Port number 0=leftServo
  // private Servo rightLatch = new Servo(Constants.Climber.CLIMBER_RIGHT_LATCH_PWM_PORT); // PWM Port number 1=rightServo

  private WPI_TalonSRX centerTalonSRX = new WPI_TalonSRX( Constants.Climber.CENTER_LATCH_ID );

  private boolean hasLifted = false;
  private boolean needsCorrection = false;
  private boolean enableCorrection = true;

  private ShuffleboardTab tab = Shuffleboard.getTab("Subsystem");

  private NetworkTableEntry leftEncoderPosition;
  private NetworkTableEntry rightEncoderPosition;

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

    /*
    * The climber encoder sets its value to zero each time the code is dropped. Be cautious of arm location when dropping code. 
    */

    leftEncoderPosition = tab.add("Left Arm Encoder", 0).getEntry();
    rightEncoderPosition = tab.add("Right Arm Encoder", 0).getEntry();  // 2022-03-15
  
    this.leftWinchTalonFX.getSensorCollection().setIntegratedSensorPosition(0, 1000);
    this.rightWinchTalonFX.getSensorCollection().setIntegratedSensorPosition(0, 1000);  // 2022-03-15

    stopClimber();
  }

  /**
   * Method for using Xbox Controllers for the Climber.
   * When LeftBumper and RightBumper are both pressed, the Climb
   * process will start.
   */
  public void climb() {
    // readEncoder()
    exceedsLimits();  // 2022-03-16

    // if (readEncoder() >= (Constants.Climber.LEFT_CLIMBER_ENCODER_MAX_VAL - Constants.Climber.LEFT_CLIMBER_ENCODER_STEP)  && enableCorrection && !needsCorrection) {
    if (exceedsLimits()  && enableCorrection && !needsCorrection) { // 2022-03-16

      needsCorrection = true;

    } else if (needsCorrection) {

      contract();

      if (exceedsLimits()){
        needsCorrection = false;
        stopClimber();
      }

    }
    
    if (assistantDriverController.getLeftBumperPressed() && !assistantDriverController.getRightBumperPressed()){  
      extend();
      hasLifted = true;
    }
    
    if (assistantDriverController.getRightBumperPressed() && !assistantDriverController.getLeftBumperPressed() && hasLifted){
      contract();
    }
    
    if (assistantDriverController.getLeftBumperReleased()){
      stopClimber();
    }
    
    if (assistantDriverController.getRightBumperReleased()){
      stopClimber();
    }
    
    if (assistantDriverController.getStartButtonPressed()){
      System.out.println("Start CLIMBING");
      disableCorrections();
    }
    
    if (assistantDriverController.getBackButtonPressed()){
      System.out.println("Stop CLIMBING (BackButton pressed)");
      enableCorrections();
    }
    
    if (assistantDriverController.getRightStickButtonPressed() ){
      centerTalonSRX.set(Constants.Climber.CENTER_LATCH_SPEED * Constants.Climber.CENTER_POLARITY_MOD);
    }
    if (assistantDriverController.getRightStickButtonReleased()) {
      centerTalonSRX.set(Constants.NO_SPEED);
    }
  }

  /**
   * Returns speed for Climber motor based on needsCorrection.
   * If needsCorrection then return slower speed
   * Else return Climb speed to lift the robot.
   */
  private double getLeftSpeed() {
    return (needsCorrection) 
      ? Constants.Climber.LEFT_CORRECTION_SPEED 
      : Constants.Climber.LEFT_CLIMB_SPEED;
  }

 /**
   * Returns speed for Climber motor based on needsCorrection.
   * If needsCorrection then return slower speed
   * Else return Climb speed to lift the robot.
   */
  private double getRightSpeed() {
    return (needsCorrection) 
      ? Constants.Climber.RIGHT_CORRECTION_SPEED
      : Constants.Climber.RIGHT_CLIMB_SPEED;
  }

  private void extend() {    
    leftWinchTalonFX.set(getLeftSpeed() * Constants.Climber.LEFT_CLIMBER_POLARITY_MOD); 
    rightWinchTalonFX.set(getRightSpeed() * Constants.Climber.RIGHT_CLIMBER_POLARITY_MOD);
  }

  private void contract() {
    leftWinchTalonFX.set((-1) * getLeftSpeed() * Constants.Climber.LEFT_CLIMBER_POLARITY_MOD); 
    rightWinchTalonFX.set((-1) * getRightSpeed() * Constants.Climber.RIGHT_CLIMBER_POLARITY_MOD);
  }

  private void stopClimber() {
    leftWinchTalonFX.set(Constants.NO_SPEED);
    rightWinchTalonFX.set(Constants.NO_SPEED);
    centerTalonSRX.set(Constants.NO_SPEED);  // 2022-03-17
  }

  public double readEncoder() {
    this.leftEncoderPosition.setDouble(Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));
    this.rightEncoderPosition.setDouble(Math.abs(this.rightWinchTalonFX.getSensorCollection().getIntegratedSensorPosition())); // 2022-03-15.

    if (Constants.DEBUG){
      System.out.println("LEFT  Encoder Position: " + Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));
      System.out.println("RIGHT Encoder Position: " + Math.abs(this.rightWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));
    }

    // DevNote: Fix encoder value calculation based on left and right encoders.  See exceedsLimits() method.
    return Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition());  
  }

  /**
   * Reads Climber Left and Right encoder values to determine if exceeded Max height.
   * @return
   */
  public boolean exceedsLimits() {
    this.leftEncoderPosition.setDouble(Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));
    this.rightEncoderPosition.setDouble(Math.abs(this.rightWinchTalonFX.getSensorCollection().getIntegratedSensorPosition())); // 2022-03-15.

    if (Constants.DEBUG){
      System.out.println("LEFT  Encoder Position: " + Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));
      System.out.println("RIGHT Encoder Position: " + Math.abs(this.rightWinchTalonFX.getSensorCollection().getIntegratedSensorPosition()));
    }

    return (Constants.Climber.LEFT_CLIMBER_ENCODER_MAX_VAL - Constants.Climber.LEFT_CLIMBER_ENCODER_STEP) >= Math.abs(this.leftWinchTalonFX.getSensorCollection().getIntegratedSensorPosition())
            || (Constants.Climber.RIGHT_CLIMBER_ENCODER_MAX_VAL - Constants.Climber.RIGHT_CLIMBER_ENCODER_STEP) >= Math.abs(this.rightWinchTalonFX.getSensorCollection().getIntegratedSensorPosition());
  }
  
  private void disableCorrections() {
    enableCorrection = false;
    needsCorrection = false;
  }

  private void enableCorrections() {
    enableCorrection = true;
  }
}
