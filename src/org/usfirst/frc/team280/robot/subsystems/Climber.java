package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climber extends Command {

	public WPI_TalonSRX ClimberUpMotor = new WPI_TalonSRX(RobotMap.ClimberTalon1);
	
	public WPI_TalonSRX ClimberDownMotor = new WPI_TalonSRX(RobotMap.ClimberTalon2);

	
	public Climber() {
		// Counter counter = new Counter(0);
		// System.out.println(counter.);
	}
	
	public void move(double speed) {
		/*
		if (speed > 0) {
			ClimberUpMotor.set(speed);
		} else if (speed < 0) {
			ClimberDownMotor.set(speed);
		} else {
			ClimberUpMotor.set(0);
			ClimberDownMotor.set(0);
		}
		*/
		ClimberUpMotor.set(speed);
		ClimberDownMotor.set(speed);
	}

	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	public Object getPIDController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void end() {
		ClimberUpMotor.set(0);
		ClimberDownMotor.set(0);
	}
}