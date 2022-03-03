/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANEncoder;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings("unused")
public class AutonomousSubsystem extends SubsystemBase {

  private boolean startUpCheck = true;

  /**
   * Creates a new AutonomousSubsystem.
   */
  public AutonomousSubsystem() {
    // Imverts motors for proper driving
    // leftFrontSparkController.setInverted(true);
    // leftBackSparkController.setInverted(true);
    // leftFrontSparkEncoder.setPosition(0.0);
  }

  /**
   * Method to be called on scheduler to run autonomous
   */
  public void autonomous() {
    // Gather selection from Shuffleboard that was declared in Robot
    String choice = Robot.startingPositionChooser.getSelected();

    if (startUpCheck) {
      startUp();
    } else {
      // TODO: implement autonomous logic here.
      // Autonomous Logic (constraint: 15 Secs)
      // 1. Select start position (1,2,3)
      // 2. Shoot Low
      // 3. Back-up N-feet based on Start Position
      // 4. Pickup another Ball by color
      // 5. Position to shoot
      // 6. Shoot High
    }
  }

  /**
   * Method to unleash intake mechanism
   */
  public void startUp() {
    // Robot.hookServo.setPosition(0.0);
    // ballPickupController.set(Constants.SPEED_CONTROL,
    // -Constants.AUTONOMOUS_RELEASE_INTAKE_MANIPULATOR_SPEED);
    // Timer.delay(3.0);
    stopStartUp();
    startUpCheck = false;
  }

  /**
   * Method to stop unleashing the intake mechanism
   */
  public void stopStartUp() {
    // ballPickupController.set(Constants.SPEED_CONTROL, Constants.NO_SPEED);
  }

}