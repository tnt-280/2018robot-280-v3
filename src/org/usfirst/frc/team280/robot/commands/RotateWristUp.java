package org.usfirst.frc.team280.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team280.robot.*;

public class RotateWristUp extends Command {

	@Override
	protected void initialize() {
		Robot.wrist.rotate(-0.55);
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
