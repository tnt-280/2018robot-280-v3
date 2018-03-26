/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team280.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SerialPort;

import org.usfirst.frc.team280.robot.RobotMap;
import org.usfirst.frc.team280.robot.commands.RotateWristEncoder;
import org.usfirst.frc.team280.robot.subsystems.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */

public class Robot extends TimedRobot { // THIS IS VERSION 3 OF THE WORKSPACE.
	public static final Drivetrain Drivetrain = new Drivetrain();
	public static OI m_oi;

	//AHRS ahrs = new AHRS(SerialPort.Port.kMXP);

	Command m_autonomousCommand;
	Command autoCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	//variables for lift arm control
	WPI_TalonSRX LiftArmMotor = new WPI_TalonSRX(RobotMap.ArmTalon);
	WPI_TalonSRX WristMotor = new WPI_TalonSRX(RobotMap.WristTalon);
	public static Wrist wrist = new Wrist();
	public static Grip grip = new Grip();
	public static Arm arm = new Arm();
	public static GripWheel gripWheel = new GripWheel();
	boolean RS_button_5, RS_button_3, arm_seek_down, arm_seek_up, arm_seek_mid, WristUp, WristDown;
	static DigitalInput dInput2, dInput1, dInput0;
	Timer timer;
	//String auto = "None";

	WPI_TalonSRX LMMotor = new WPI_TalonSRX(RobotMap.LMTalon);
	WPI_TalonSRX RMMotor = new WPI_TalonSRX(RobotMap.RMTalon);		
	WPI_TalonSRX LSMotor = new WPI_TalonSRX(RobotMap.LSTalon);
	WPI_TalonSRX RSMotor = new WPI_TalonSRX(RobotMap.RSTalon);


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public enum PinType { DigitalIO, PWM, AnalogIn, AnalogOut };

	@Override
	public void robotInit() {
		//put initialization algorithms here that will always be needed

		dInput2 = new DigitalInput(RobotMap.DIO_arm_switch_low);//low switch for arm position
		dInput1 = new DigitalInput(RobotMap.DIO_arm_switch_high);//high switch for arm position
		dInput0 = new DigitalInput(RobotMap.DIO_arm_switch_mid);//mid switch for arm position

		//turn on both of the cameras
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);

		//bring in user input
		m_oi = new OI();
		
		//give the driver station the options for starting position
		chooser.addObject("Left", null);
		chooser.addObject("Mid", null);
		chooser.addObject("Right", null);
		
		//collect the operating mode from the Driver station
		SmartDashboard.putData("Auto mode", chooser);
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */

	@Override
	public void autonomousInit() {
		// m_autonomousCommand = m_chooser.getSelected();
		
		// autoCommand = new RotateWristEncoder();
		
		autoCommand = null;

		/* // Prototype Autonomous Selection Code
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		/* 
		//schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		*/

		timer = new Timer();
		timer.start();

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		// Character 0: Your Switch | Char. 1: Scale | Char. 2: Opposing Switch
		// (Competition) Valid GameData is as follows: LLL, RRR, LRL, RLR	
		if (gameData.equals("LRL")) {
			DriverStation.reportError("GameData recieved! Data is LRL. Selecting option 1.", false);
		} else if (gameData.equals("RLR")) {
			DriverStation.reportError("GameData recieved! Data is RLR. Selecting option 2.", false);
		} else if (gameData.equals("LLL")) {
			DriverStation.reportError("GameData recieved! Data is LLL. Selecting option 3.", false);
		} else if (gameData.equals("RRR")) {
			DriverStation.reportError("GameData recieved! Data is RRR. Selecting option 4.", false);
		} else { 
			DriverStation.reportError("Invalid GameData recieved. Data: " + gameData, false);
		}

	}

	@Override
	public void autonomousPeriodic() {

		
		Scheduler.getInstance().run(); // Runs autonomous command.
		
		/*
		if (timer.get() <1.5) {
			LMMotor.set(-0.5);
			RMMotor.set(0.5);
			LSMotor.set(-0.5);
			RSMotor.set(0.5);
		}

		if (timer.get() >=1.5) {
			LMMotor.set(0);
			RMMotor.set(0);
			LSMotor.set(0);
			RSMotor.set(0);
		}
		
		*/
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}


		//initialize the button state variables which control the lift arm to prevent button state bug
		RS_button_5 = false;
		RS_button_3 = false;
		arm_seek_down = false;
		arm_seek_up = false;
		arm_seek_mid = false;
		
		Wrist.encZero();
		Wrist.Motor.set(0);

	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//put code here that will run regularly during teleop mode

		update_arm_position(); // Update the arm position based on its state variables and joystick buttons


		DriverStation.reportError("Encoder value: " + Wrist.encoder.get(), false);

	}

	/**
	 * Returns the status of DIO pin 24 
	 *
	 * @return true if this is the practice robot
	 */
	public static boolean armSwitchHi() {
		//DriverStation.reportError("Arm reed switch high:" + !dInput2.get(), false);
		return !dInput1.get();
	}

	public static boolean armSwitchLow() {
		//DriverStation.reportError("Arm reed switch low:" + !dInput1.get(), false);
		return !dInput2.get();
	}

	public static boolean armSwitchMid() {
		//DriverStation.reportError("Arm reed switch mid:" + !dInput0.get(), false);
		return !dInput0.get();
	}

	//note: I made this into a subfunction so that it can be used in autonomous or teleoperation modes
	public void update_arm_position()
	{
		//update the arm position without issuing a command apart from joystick behaviors
		update_arm_position(2);//the command 2 does not denote a specific action
	}

	public void update_arm_position(int seekPosition)
	{
		//update the position of the lift arm based on joystick or command values
		//seekPosition will be -1, 0 or 1 to command bottom, mid or top position seek

		//control the motors that run the lift arm:
		//update the accounting of the limit switches
		boolean arm_high_switch_set = armSwitchHi();
		boolean arm_mid_switch_set = armSwitchMid();
		boolean arm_low_switch_set = armSwitchLow();

		/*  BEGIN UNUSED MANUAL MOVE CODE





		//update the pressed/un-pressed state of the arm movement buttons
		if(!RS_button_5 && Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_move_up))
		{
			//if the state is released and the button has been pressed, change the state to pressed
			RS_button_5 = true;
		}
		else if(RS_button_5 && Robot.m_oi.armJoystick.getRawButtonReleased(RobotMap.button_move_up))
		{
			//if the state is pressed, and the button has been released, change the state to released
			RS_button_5 = false;
		}

		if(!RS_button_3 && Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_move_down))
		{
			//if the state is released and the button has been pressed, change the state to pressed
			RS_button_3 = true;
		}
		else if(RS_button_3 && Robot.m_oi.armJoystick.getRawButtonReleased(RobotMap.button_move_down))
		{
			//if the state is pressed, and the button has been released, change the state to released
			RS_button_3 = false;
		}

		 */ // END UNUSED MANUAL MOVE CODE

		//also use three buttons to seek the position of the arm based on the reed switches
		boolean RS_button_arm_down = Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_seek_down);
		boolean RS_button_arm_up = Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_seek_up);
		boolean RS_button_arm_mid = Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_seek_mid);

		//then act based on the button state to turn on the motor....0



		if((RS_button_5 || RS_button_arm_up || RS_button_arm_mid || (seekPosition == 1) || (seekPosition == 0)) && !arm_high_switch_set)
		{
			LiftArmMotor.set(0.85); // Default Competition Value: 0.85
			if(RS_button_arm_up || (seekPosition == 1))
			{
				arm_seek_up = true;
			}
			if(RS_button_arm_mid || (seekPosition == 0))
			{
				arm_seek_mid = true; // if the mid seek is pressed, go up- if we hit either the high or mid limit switch we'll stop motion
			}
		}

		if((RS_button_3 || RS_button_arm_down  || (seekPosition == -1)) && !arm_low_switch_set)
		{
			LiftArmMotor.set(-0.65); // Default Competition Value: -0.65
			if(RS_button_arm_down || (seekPosition == -1))
			{
				arm_seek_down = true;
			}
			if(RS_button_arm_mid || (seekPosition == 0)) 
			{
				arm_seek_mid = true;
			}
		}

		//then act based on the state variables and switches to turn off the motors
		if(arm_high_switch_set && (RS_button_5 || arm_seek_up || arm_seek_mid ))
		{
			//if the 'raise arm' button is pressed and we're at the upper limit switch
			//turn off the motor
			LiftArmMotor.set(0);
			if(arm_seek_up)
			{
				arm_seek_up = false;
			}
			if(arm_seek_mid)
			{
				arm_seek_mid = false;
			}
		}

		if(arm_mid_switch_set && arm_seek_mid)
		{
			LiftArmMotor.set(0);
			arm_seek_mid = false;
		}

		if(arm_low_switch_set && (RS_button_3 || arm_seek_down))
		{
			//if the 'lower arm' button is pressed and we're at the lower limit switch
			//turn off the motor
			LiftArmMotor.set(0);
			if(arm_seek_down)
			{
				arm_seek_down = false;
			}
		}


		if(!RS_button_3 && !RS_button_5 && !arm_seek_down && !arm_seek_up && !arm_seek_mid)
		{
			//if neither arm movement button is pressed, turn off the motor
			//also turn off the motor if we're not seeking a target postion
			LiftArmMotor.set(0);
		}


	}

	public void flush_button_inputs()
	{
		//then flush the button buffers by reading the buttons once and dumping the result
		//without this code, the arm will run at power on if the button was previously pressed

		Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_seek_mid);
		Robot.m_oi.armJoystick.getRawButtonReleased(RobotMap.button_seek_mid);

		Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_seek_up);
		Robot.m_oi.armJoystick.getRawButtonReleased(RobotMap.button_seek_up);

		Robot.m_oi.armJoystick.getRawButtonPressed(RobotMap.button_seek_down);
		Robot.m_oi.armJoystick.getRawButtonReleased(RobotMap.button_seek_down);
	} 

}
