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
	WPI_TalonSRX LMMotor, RMMotor, LS1Motor, RS1Motor, LS2Motor, RS2Motor;
	//and a drivetrain reference
	DifferentialDrive drive;
	
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
		LS1Motor = new WPI_TalonSRX(RobotMap.LS1Talon);
		RS1Motor = new WPI_TalonSRX(RobotMap.RS1Talon);		

		
		// Set slave motors to follow master
		RS1Motor.follow(RMMotor);
		RS2Motor.follow(RMMotor);
		LS1Motor.follow(LMMotor);
		LS2Motor.follow(LMMotor);
		
		drive = new DifferentialDrive(LMMotor, RMMotor);
		
		
	}
	
	public void tankDrive(double x, double y) {
		//before acting on the input 'joystick' values, lets compare
		//the reading from the magnetic encoders.
		//x is left joystick, y is right joystick
		
		//remove the oldest value from the motor velocity queues if the queue is full
		if(RMMotor_velocity_queue.size() == arrayLength)
		{
			int RMRemovedValue = RMMotor_velocity_queue.removeLast();
			RMMotor_queue_sum -= RMRemovedValue;
			
			int LMRemovedValue = LMMotor_velocity_queue.removeLast();
			LMMotor_queue_sum -= LMRemovedValue;
		}
			
		//read in the magnetic encoder speed for the drivetrain motor
		int speedR = RMMotor.getSelectedSensorVelocity(0);
		int speedL = LMMotor.getSelectedSensorVelocity(0);

		//add new values to the queue and to the sum
		RMMotor_velocity_queue.addFirst(speedR);
		RMMotor_queue_sum += speedR;
		
		LMMotor_velocity_queue.addFirst(speedL);
		LMMotor_queue_sum += speedL;

		//next we should average the array values
		double speedR_avg = speedR/RMMotor_velocity_queue.size();
		double speedL_avg = speedL/LMMotor_velocity_queue.size();
		
		//then normalize by the maximum speed so that we have a -1 to 1 value
		//which is similar to the joystick command
		speedR_avg = speedR_avg/6600;
		speedL_avg = speedL_avg/6600;
		//this error message is for troubleshooting the motor speed measurement, comment it out if it is not used
		// DriverStation.reportError("SpeedL_avg: " +  speedL_avg + " | SpeedL: " + speedL, false);	
		
		//This value is the error between the target and applied actuator power
		double L_error = (x - speedL_avg);
		double R_error = (y - speedR_avg);

		//we need to accumulate the error for the error integral and error differential
		//remove the oldest value from the queue if the queue is full
		if(RMMotor_PID_error_queue.size() == arrayLength)
		{
			double RMMotor_error_RemovedValue = RMMotor_PID_error_queue.removeLast();
			RMMotor_integrated_error -= RMMotor_error_RemovedValue;
			
			double LMMotor_error_RemovedValue = LMMotor_PID_error_queue.removeLast();
			LMMotor_integrated_error -= LMMotor_error_RemovedValue;
		}
		
		//if queue size is 0, then latest error is 0, otherwise peek the first value
		double latest_R_error = (RMMotor_PID_error_queue.size() == 0)? 0 : RMMotor_PID_error_queue.peekFirst();
		double latest_L_error = (LMMotor_PID_error_queue.size() == 0)? 0 : LMMotor_PID_error_queue.peekFirst();
		
		//put the latest error into the error queue and update the sum- the sum is the integrated error
		RMMotor_PID_error_queue.addFirst(R_error);
		RMMotor_integrated_error += R_error;
		
		LMMotor_PID_error_queue.addFirst(L_error);
		LMMotor_integrated_error += L_error;
		
		
		//then we need to get an average slope over the PID error queue
		
		//if error diff queue is full, remove the last error diff and subtract it from the sum
		if(RMMotor_PID_error_diff_queue.size() == arrayLength)
		{
			double RMMotor_error_diff_removed_value = RMMotor_PID_error_diff_queue.removeLast();
			RMMotor_error_slope_sum -= RMMotor_error_diff_removed_value;
			
			double LMMotor_error_diff_removed_value = LMMotor_PID_error_diff_queue.removeLast();
			LMMotor_error_slope_sum -= LMMotor_error_diff_removed_value;
		}
		
		//calculate the next error diff by using the "peeked" error value and the new error value
		//then add it to the front of the queue
		//add the value to the sum
		//calculate the average based on the size of the queue
		double latest_R_error_diff = R_error - latest_R_error;
		RMMotor_PID_error_diff_queue.addFirst(latest_R_error_diff);
		RMMotor_error_slope_sum += latest_R_error_diff;
		double RM_error_slope_average = RMMotor_error_slope_sum/RMMotor_PID_error_diff_queue.size();
		
		double latest_L_error_diff = L_error - latest_L_error;
		LMMotor_PID_error_diff_queue.addFirst(latest_L_error_diff);
		LMMotor_error_slope_sum += latest_L_error_diff;
		double LM_error_slope_average = LMMotor_error_slope_sum/LMMotor_PID_error_diff_queue.size();
		
		//finally, we should include changes to the actuator control based on the PID error and gain settings
		x += L_error*propGain + LMMotor_integrated_error*intGain + LM_error_slope_average*diffGain;
		y += R_error*propGain + RMMotor_integrated_error*intGain + RM_error_slope_average*diffGain;
		
		
		
		//command the motor based on the adjusted joystick values
		drive.tankDrive(x,y);
	}
	
	
	//tank drive alterative using queue data structure
	public void tankDriveQueue(double x, double y) {
		
		//if queue is full, remove last value in Queue
		if(RMMotor_velocity_queue.size() == arrayLength)
		{
			int RMRemovedValue = RMMotor_velocity_queue.removeLast();
			RMMotor_queue_sum -= RMRemovedValue;
			
			int LMRemovedValue = LMMotor_velocity_queue.removeLast();
			LMMotor_queue_sum -= LMRemovedValue;
		}
		
		//before acting on the input 'joystick' values, lets compare
		//the reading from the magnetic encoders.
		//x is left joystick, y is right joystick
		
		//read in the magnetic encoder speed for the drivetrain motor
		int speedR = -1 * RMMotor.getSelectedSensorVelocity(0);
		int speedL = LMMotor.getSelectedSensorVelocity(0);

		//add new values to the queue
		RMMotor_velocity_queue.addFirst(speedR);
		RMMotor_queue_sum += speedR;
		
		LMMotor_velocity_queue.addFirst(speedL);
		LMMotor_queue_sum += speedL;

		
		//next we should average the array values
		double speedR_avg = speedR/RMMotor_velocity_queue.size();
		double speedL_avg = speedL/LMMotor_velocity_queue.size();

		
		//then normalize by the maximum speed so that we have a -1 to 1 value
		//which is similar to the joystick command
		speedR_avg = speedR_avg/6600;
		speedL_avg = speedL_avg/6600;
		//this error message is for troubleshooting the motor speed measurement, comment it out if it is not used
		// DriverStation.reportError("SpeedL_avg: " +  speedL_avg + " | SpeedL: " + speedL, false);	
		
		
		//if the the magnetic encoder velocity values are not close to the command from the joysticks, 
		//adjust the joystick values to compensate for mismatched motor mechanical
		//resistance
		double L_diff = x - speedL_avg;
		double R_diff = y - speedR_avg;
		//x += L_diff;
		//y += R_diff;
		
		
		//command the motor based on the adjusted joystick values
		drive.tankDrive(x,y);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new DriveTrainCommand());
    }
}
