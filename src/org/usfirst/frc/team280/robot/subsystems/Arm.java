package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.DIOJNI;

public class Arm extends Subsystem {

	WPI_TalonSRX Motor = new WPI_TalonSRX(RobotMap.ArmTalon);
	
	public Arm() {
		// Counter counter = new Counter(0);
		// System.out.println(counter.);
	}
	
	public void move(double speed) {
		Motor.set(speed);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
