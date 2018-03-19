package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Grip extends Subsystem {
	
	public static DigitalInput limitClose = new DigitalInput(RobotMap.wristCloseLimit);
	public static DigitalInput limitOpen = new DigitalInput(RobotMap.wristOpenLimit);
	WPI_TalonSRX Motor = new WPI_TalonSRX(RobotMap.GripTalon);
	public boolean opening = false;
	public boolean closing = false;
	
	public Grip() {
		
	}
	
	public void move(double speed, int status) {

		DriverStation.reportError("Opening Limit Switch = " + limitOpen.get(), false);
		DriverStation.reportError("Closing Limit Switch = " + limitClose.get(), false);
		
		if ((limitOpen.get() && status == 2) || (limitClose.get() && status == 1))
		{
			Motor.set(0);
		}
		else 
		{
			Motor.set(speed);
		}
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}

}
