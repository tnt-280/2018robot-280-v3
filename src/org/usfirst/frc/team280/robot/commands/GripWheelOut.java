package org.usfirst.frc.team280.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team280.robot.*;

public class GripWheelOut extends Command {
	Timer timer = new Timer();

	@Override
	protected void initialize() {
		timer.start();
		Robot.gripWheel.intake(1);
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return timer.get() > 1.5;
	}

	@Override
	protected void end() {
		Robot.gripWheel.intake(0);
	}
}
