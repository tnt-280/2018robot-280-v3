package org.usfirst.frc.team280.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team280.robot.RobotMap;
import org.usfirst.frc.team280.robot.subsystems.Wrist;



// WARNING: THIS COMMAND IS NOT COMPLETE. RUN AT OWN RISK. CAN AND WILL OVER-ROTATE WRIST.

public class RotateWristEncoder extends Command {

	public static double counts = -10;
	boolean finished = false;

	public RotateWristEncoder() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Wrist.encRotate(counts);
		DriverStation.reportError("Rotating encoder.", false);
		if (counts != 0 ) { // Check that counts is non-zero
			while (counts > 0) { // Check if counts is positive
				if (Wrist.encoder.get() < counts) {
					Wrist.Motor.set(-0.85); // Set to 0.85 for actual
					DriverStation.reportError("counts is positive.", false);
				} else {
					break;
				}
			} while (counts < 0) { // Check if counts is negative
				if (Wrist.encoder.get() > counts) {
					Wrist.Motor.set(0.85); // Set to -0.85 for actual
					DriverStation.reportError("counts is negative.", false);
				} else {
					break;
				}
			} 
		} else {
			Wrist.Motor.set(0);
			DriverStation.reportError("Wrist motor set to zero.", false);
			finished = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		counts = 0;
		Wrist.Motor.set(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
