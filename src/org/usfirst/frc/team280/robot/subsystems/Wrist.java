package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Wrist extends PIDSubsystem {

	public static WPI_TalonSRX Motor = new WPI_TalonSRX(RobotMap.WristTalon);
	public static Encoder encoder = new Encoder(RobotMap.wrist_encoder_port_a, RobotMap.wrist_encoder_port_b);
	
	public Wrist() {
		super(0.01, 0, 0);
	}
	
	@Override
	protected void initDefaultCommand() {
	}

	@Override
	protected double returnPIDInput() {
		return getEncoderValue();
	}

	@Override
	protected void usePIDOutput(double output) {
		Motor.set(output);
	}
	
	public void rotate(double speed) {
		Motor.set(speed);
	}
	
	public double getEncoderValue() {
		return encoder.get();
	}
	
	public static void encZero() {
		encoder.reset();
	}
}
