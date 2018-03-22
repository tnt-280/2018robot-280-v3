package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class Wrist extends PIDSubsystem {

	static WPI_TalonSRX Motor = new WPI_TalonSRX(RobotMap.WristTalon);
	public static Encoder encoder = new Encoder(RobotMap.wrist_encoder_port_a, RobotMap.wrist_encoder_port_b);
	
	public Wrist() {
		super(0.01, 0, 0);
	}
	
	@Override
	protected void initDefaultCommand() {
		encRotate(1);
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
	
	public static void encRotate(double counts) {
		// Run motor up to number of encoder counts supplied
		
		if (counts > 1) { // Check if counts is positive
			if (encoder.get() < counts) {
				Motor.set(0.85); // Set to 0.85 for actual
				DriverStation.reportError("counts is positive.", false);
			}
		} else if (counts < 0) { // Check if counts is negative
			if (encoder.get() < counts) {
				Motor.set(-0.85); // Set to -0.85 for actual
				DriverStation.reportError("counts is negative.", false);
			}
		} else {
			Motor.set(0);
			DriverStation.reportError("Wrist motor set to zero.", false);
		}
	}
	
	public static void encZero() {
		encoder.reset();
	}
}
