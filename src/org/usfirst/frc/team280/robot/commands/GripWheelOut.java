package org.usfirst.frc.team280.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team280.robot.*;

public class GripWheelOut extends Command {

	@Override
	protected void initialize() {
		Robot.gripWheel.intake(-1);
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		Robot.gripWheel.intake(0);
	}
}
