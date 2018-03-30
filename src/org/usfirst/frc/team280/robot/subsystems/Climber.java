package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Climber extends Command {

	WPI_TalonSRX CLimberUpMotor = new WPI_TalonSRX(RobotMap.ClimberTalon1);
	
	WPI_TalonSRX ClimberDownMotor = new WPI_TalonSRX(RobotMap.ClimberTalon2);

	
	public Climber() {
		// Counter counter = new Counter(0);
		// System.out.println(counter.);
	}
	
	public void move(double speed) {
		//Motor.set(speed);
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
}