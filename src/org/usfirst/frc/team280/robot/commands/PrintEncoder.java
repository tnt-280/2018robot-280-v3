package org.usfirst.frc.team280.robot.commands;

import org.usfirst.frc.team280.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PrintEncoder extends Command {

    public PrintEncoder() {
       requires(Robot.wrist);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriverStation.reportError("PrintEncoder Started", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.wrist.printEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
