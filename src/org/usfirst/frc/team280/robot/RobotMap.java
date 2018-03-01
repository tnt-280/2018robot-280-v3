/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team280.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	// TalonSRX CAN Assignments
	public static final int RMTalon = 9; // Master
	public static final int RSTalon = 11; // Slave
	public static final int LMTalon = 3;
	public static final int LSTalon = 5;
	public static final int ArmTalon = 4;
	public static final int WristTalon = 8;

	//lift arm reed switches
	public static final int DIO_arm_switch_high = 3;
	public static final int DIO_arm_switch_low = 4;
	
	//joystick button function assignment for lift arm
	public static final int button_seek_up = 1;
	public static final int button_seek_mid = 2;
	public static final int button_seek_down = 3;
	public static final int button_wrist_up = 8;
	public static final int button_wrist_down = 9;
	
	//joystick button functions for grip and wrist 
	public static final int button_grip_open = 4;
	public static final int button_grip_close = 7;
	public static final int button_grip_in = 5; 
	public static final int button_grip_out = 6;
	
}
