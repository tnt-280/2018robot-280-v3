package org.usfirst.frc.team280.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team280.robot.*;

public class RotateWristUp extends Command {
	private boolean started = false;

	@Override
	protected void initialize() {
		//added to test motor 
		if (!started) {
			started = true;
			Robot.wrist.getPIDController().disable();
			Robot.wrist.rotate(-0.85);
		} else {
			execute();
		}
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected void end() {
		started = false;
		Robot.wrist.rotate(0);
		Robot.wrist.getPIDController().setSetpoint(Robot.wrist.getEncoderValue());
		Robot.wrist.getPIDController().enable();
	}
}
