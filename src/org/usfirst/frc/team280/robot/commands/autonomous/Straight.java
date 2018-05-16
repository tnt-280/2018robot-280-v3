package org.usfirst.frc.team280.robot.commands.autonomous;

import org.usfirst.frc.team280.robot.Robot;
import org.usfirst.frc.team280.robot.RobotMap;
import org.usfirst.frc.team280.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Straight extends Command {
	private Timer timer = new Timer();
	private double length;
	
    public Straight(int lengthIn) {
    	length = lengthIn;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.Drivetrain.tankDrive(-0.7, -0.7);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= length;    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.Drivetrain.tankDrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
