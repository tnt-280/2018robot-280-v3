package org.usfirst.frc.team280.robot.subsystems;

import java.util.LinkedList;

import org.usfirst.frc.team280.robot.RobotMap;
import org.usfirst.frc.team280.robot.commands.DriveTrainCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.CANTalon;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {
	
	//create variables which contain the Talon SRX CAN controller references
	WPI_TalonSRX LMMotor, RMMotor, LSMotor, RSMotor;
	//and a drivetrain reference
	DifferentialDrive drive;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public DriveTrainSubsystem() 
	{
		
		//Initialize motors
		LMMotor = new WPI_TalonSRX(RobotMap.LMTalon);
		RMMotor = new WPI_TalonSRX(RobotMap.RMTalon);		
		LSMotor = new WPI_TalonSRX(RobotMap.LSTalon);
		RSMotor = new WPI_TalonSRX(RobotMap.RSTalon);
		
		// Set slave motors to follow master
		RSMotor.follow(RMMotor);
		LSMotor.follow(LMMotor);
		
		drive = new DifferentialDrive(LMMotor, RMMotor);
		
	}
	
	public void tankDrive(double x, double y) {
		//command the motor based on the adjusted joystick values
		drive.tankDrive(x,y);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new DriveTrainCommand());
    }
}
