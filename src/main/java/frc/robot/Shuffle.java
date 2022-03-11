package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public final class Shuffle {
    
    private static ShuffleboardTab tab = Shuffleboard.getTab("Constants");
    public static NetworkTableEntry lowShooterSpeed = tab.add("Low Shooter Speed", 1.0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    public static NetworkTableEntry highShooterSpeed = tab.add("High Shooter Speed", 1.0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    public static NetworkTableEntry intakeSpeed = tab.add("Intake Speed", 1.0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    public static NetworkTableEntry feederSpeed = tab.add("Feeder Speed", 1.0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();

}
