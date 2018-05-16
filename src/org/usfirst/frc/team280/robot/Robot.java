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

import org.usfirst.frc.team280.robot.commands.RotateWristEncoder;
import org.usfirst.frc.team280.robot.commands.autonomous.*;
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
	public static SendableChooser <Command> chooser = new SendableChooser<>();

	//variables for lift arm control
	WPI_TalonSRX WristMotor = new WPI_TalonSRX(RobotMap.WristTalon);
	public static Wrist wrist = new Wrist();
	public static Grip grip = new Grip();
	public static Arm arm = new Arm();
	public static GripWheel gripWheel = new GripWheel();
	public static Climber climber = new Climber();
	Timer timer;
	//String auto = "None";

	WPI_TalonSRX LMMotor = new WPI_TalonSRX(RobotMap.LMTalon);
	WPI_TalonSRX RMMotor = new WPI_TalonSRX(RobotMap.RMTalon);		
	WPI_TalonSRX LSMotor = new WPI_TalonSRX(RobotMap.LSTalon);
	WPI_TalonSRX RSMotor = new WPI_TalonSRX(RobotMap.RSTalon);

	//Climber Talons and Motors
	WPI_TalonSRX ClimberUpMotor = new WPI_TalonSRX(RobotMap.ClimberTalon1); 
	WPI_TalonSRX ClimberDownMotor = new WPI_TalonSRX(RobotMap.ClimberTalon2); 


	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public enum PinType { DigitalIO, PWM, AnalogIn, AnalogOut };

	@Override
	public void robotInit() {
		//put initialization algorithms here that will always be needed

		//turn on both of the cameras
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);

		//bring in user input
		m_oi = new OI();

		//give the driver station the options for starting position
		chooser.addObject("Left Switch", new LeftStartSwitch());
		chooser.addObject("Mid Switch", null);
		chooser.addObject("Right Switch", null);
		chooser.addObject("Left Scale", new LeftStartScale()); //Once you have all of the autonomous commands created, replace null with the command pertaining to that chooser option
		chooser.addObject("Mid Scale", null);
		chooser.addObject("Right Scale", null);
		
		//Set default option, just cross the line (or run into the switch if you're at mid)
		/* chooser.addDefault("Cross Line", new Straight(2)); */
		chooser.addDefault("ScoreSwitch", new RightStartSwitch());
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

		autoCommand = (Command) chooser.getSelected();

		/*
		String gameData;
		Command option;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		option = Robot.chooser.getSelected();
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
			Command autoCommand = new Straight();


		}
		*/

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
		
		if (autoCommand != null) autoCommand.start(); 

	}

	@Override
	public void autonomousPeriodic() {


		Scheduler.getInstance().run(); // Runs autonomous command.
		/*
		if (timer.get() <2.0) {
			LMMotor.set(-0.5);
			RMMotor.set(0.5);
			LSMotor.set(-0.5);
			RSMotor.set(0.5);
		}

		if (timer.get() >=2.0) {
			LMMotor.set(0);
			RMMotor.set(0);
			LSMotor.set(0);
			RSMotor.set(0);
		} else {

		}
		 */

	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autoCommand != null) {
			autoCommand.cancel();
		}


		//initialize the button state variables which control the lift arm to prevent button state bug

		Robot.wrist.encZero();
		Robot.wrist.Motor.set(0);

	}

	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		//put code here that will run regularly during teleop mode

		arm.update_arm_position(); // Update the arm position based on its state variables and joystick buttons
		
		//DriverStation.reportError("Encoder value: " + Wrist.encoder.get(), false);

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
