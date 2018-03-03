package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.RobotMap;
import org.usfirst.frc.team280.robot.commands.PrintEncoder;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.hal.DIOJNI;

public class Wrist extends Subsystem {

	WPI_TalonSRX Motor = new WPI_TalonSRX(RobotMap.WristTalon);
	Encoder encoder = new Encoder(RobotMap.wrist_encoder_port_a, RobotMap.wrist_encoder_port_b);
	
	public Wrist() {
		// Counter counter = new Counter(0);
		// System.out.println(counter.);
	}
	
	public void printEncoder() {
		DriverStation.reportError("Encoder value: " + encoder.get(), false);
	}
	
	public void rotate(double speed) {
		Motor.set(speed);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new PrintEncoder());
	}
}
