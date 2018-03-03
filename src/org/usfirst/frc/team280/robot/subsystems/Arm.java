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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {

	
	static DigitalInput dInput0, dInput1, dInput2;
	WPI_TalonSRX Motor = new WPI_TalonSRX(RobotMap.ArmTalon);
	
	public Arm() {
		
	dInput2 = new DigitalInput(RobotMap.DIO_arm_switch_low);//low switch for arm position
	dInput1 = new DigitalInput(RobotMap.DIO_arm_switch_high);//high switch for arm position

	}
	
	public void floor() {
		Motor.set(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}
