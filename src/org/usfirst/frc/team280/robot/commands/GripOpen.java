package org.usfirst.frc.team280.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team280.robot.*;

public class GripOpen extends Command {

	public int status = 0; // Status 1 = Closing, 2 = Opening, 0 = Stopped
	
	@Override
	protected void initialize() {
		status = 2;
		Robot.grip.move(-0.75, status);
		
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		status = 0;
		Robot.grip.move(0, status);
	}
}
