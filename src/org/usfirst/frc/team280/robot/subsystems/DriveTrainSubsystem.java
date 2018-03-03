package org.usfirst.frc.team280.robot.subsystems;

import java.util.LinkedList;

import org.usfirst.frc.team280.robot.RobotMap;
import org.usfirst.frc.team280.robot.commands.DriveTrainCommand;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrainSubsystem extends Subsystem {
	
	//create variables which contain the Talon SRX CAN controller references
	WPI_TalonSRX LMMotor, RMMotor, LSMotor, RSMotor, LS1Motor, RS1Motor;
	//and a drivetrain reference
	DifferentialDrive drive, driveSlave, driveSlave1;
	
	//we'll be using magnetic encoders to compensate for motor drift
	//and we will want to average the values over time to smooth out the signal
	//these linked lists store the values to average
	int arrayLength = 10;
	//int[] RMMotor_velocity = new int[arrayLength];//adjust the length of the array in order to adjust the averaging time for the autonomous driving motor magnetic encoder
	//int[] LMMotor_velocity = new int[arrayLength];
	
	//created queue for measured magnetic encoder speed and for PID error
	LinkedList<Integer> RMMotor_velocity_queue = new LinkedList<Integer>();
	LinkedList<Integer> LMMotor_velocity_queue = new LinkedList<Integer>();
	LinkedList<Double> RMMotor_PID_error_queue = new LinkedList<Double>();
	LinkedList<Double> LMMotor_PID_error_queue = new LinkedList<Double>();
	LinkedList<Double> RMMotor_PID_error_diff_queue = new LinkedList<Double>();
	LinkedList<Double> LMMotor_PID_error_diff_queue = new LinkedList<Double>();
	
	//create variables to keep track of motor speed measurement sum
	int RMMotor_queue_sum = 0;
	int LMMotor_queue_sum = 0;
	
	//in order to implement a PID, we need to keep a sum of error values
	double LMMotor_integrated_error = 0;
	double RMMotor_integrated_error = 0;
	double LMMotor_error_slope_sum = 0;
	double RMMotor_error_slope_sum = 0;
	
	//parameters that affect the PID response
	double propGain = .5;
	double diffGain = 0;
	double intGain = 0;
	
	
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public DriveTrainSubsystem() 
	{

		LMMotor = new WPI_TalonSRX(RobotMap.LMTalon);
		RMMotor = new WPI_TalonSRX(RobotMap.RMTalon);		
		LSMotor = new WPI_TalonSRX(RobotMap.LSTalon);
		RSMotor = new WPI_TalonSRX(RobotMap.RSTalon);
		LS1Motor = new WPI_TalonSRX(RobotMap.LS1Talon);
		RS1Motor = new WPI_TalonSRX(RobotMap.RS1Talon);
		
		// Set slave motors to follow master
		//RSMotor.set(ControlMode.Follower, RobotMap.RMTalon);
		//LSMotor.set(ControlMode.Follower, RobotMap.LMTalon);

		drive = new DifferentialDrive(LMMotor, RMMotor);
		driveSlave = new DifferentialDrive(LSMotor, RSMotor);
		driveSlave1 = new DifferentialDrive(LS1Motor, RS1Motor);
		
	}
	
	public void tankDrive(double x, double y) {
		//command the motor based on the adjusted joystick values
		drive.tankDrive(x,y);
		driveSlave.tankDrive(x, y);
		driveSlave1.tankDrive(x, y);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new DriveTrainCommand());
    }
}
