package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@SuppressWarnings("unused")
public class ClimberSubsystem extends SubsystemBase {

  /**
   * TalonFX Controllers - for Climber (Winch)
   */
  private WPI_TalonFX leftWinchTalonFX = new WPI_TalonFX(Constants.Climber.LEFT_WINCH_TALON_FX_ID);
  private WPI_TalonFX rightWinchTalonFX = new WPI_TalonFX(Constants.Climber.RIGHT_WINCH_TALON_FX_ID);
  private boolean hasLifted = false;

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
    stopClimber(); // default state.
  }

  /**
   * Method for using Xbox Controllers for the Climber.
   * When LeftBumper and RightBumper are both pressed, the Climb
   * process will start.
   */
  public void climb() {

    // If the LeftBumper and RightBumper are pressed,
    // the Climb Left process will begin.
    if (assistantDriverController.getLeftBumperPressed() && !assistantDriverController.getRightBumperPressed()){
      extend();
      hasLifted = true;
    }
    else if (assistantDriverController.getRightBumperPressed() && !assistantDriverController.getLeftBumperPressed() && hasLifted)
      contract();
    else if (assistantDriverController.getLeftBumperReleased())
      stopClimber();
    else if (assistantDriverController.getRightBumperReleased())
      stopClimber();
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

}
