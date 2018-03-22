package org.usfirst.frc.team280.robot.commands;

import org.usfirst.frc.team280.robot.subsystems.Wrist;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ZeroWristEncoder extends Command {

		boolean finished = false;
	
    public ZeroWristEncoder() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Wrist.encZero();
    	DriverStation.reportError("Encoder zeroed.", false);
    	finished = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Wrist.encZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
