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
	public static final int RMTalon = 4; // Master
	public static final int RS1Talon = 5; // Slave 1
	public static final int RS2Talon = 6; // Slave 2
	public static final int LMTalon = 1;
	public static final int LS1Talon = 2;
	public static final int LS2Talon = 3;
	public static final int LiftTalon = 7; // Talon CAN id that controls the lift arm

	//lift arm limit switches
	public static final int DIO_arm_switch_high = 1; // DIO pin for arm control reed switch
	public static final int DIO_arm_switch_mid = 0;
	public static final int DIO_arm_switch_low = 2;
	
	//joystick button function assignment for lift arm
	public static final int button_seek_up = 6;
	public static final int button_seek_mid = 2;
	public static final int button_seek_down = 4;
	public static final int button_move_up = 5;
	public static final int button_move_down = 3;
}
