package org.usfirst.frc.team280.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team280.robot.*;

public class ArmMoveDown extends Command {

	@Override
	protected void initialize() {
		Robot.arm.move(-0.5);
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		Robot.arm.move(0);
	}
}
