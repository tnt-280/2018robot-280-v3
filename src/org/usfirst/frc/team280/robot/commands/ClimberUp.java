package org.usfirst.frc.team280.robot.commands;

import org.usfirst.frc.team280.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberUp extends Command {

	@Override
	protected void initialize() {
		Robot.climber.move(1);
	}
	
	
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void end() {
		Robot.climber.move(0);
	}
}
