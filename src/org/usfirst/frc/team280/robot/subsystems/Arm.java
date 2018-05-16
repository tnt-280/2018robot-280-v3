package org.usfirst.frc.team280.robot.subsystems;

import org.usfirst.frc.team280.robot.Robot;
import org.usfirst.frc.team280.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
	private static DigitalInput dInput2, dInput1, dInput0;
	boolean RS_button_5, RS_button_3, arm_seek_down, arm_seek_up, arm_seek_mid;
	WPI_TalonSRX LiftArmMotor = new WPI_TalonSRX(RobotMap.ArmTalon);

	public Arm() {
		dInput2 = new DigitalInput(RobotMap.DIO_arm_switch_low);//low switch for arm position
		dInput1 = new DigitalInput(RobotMap.DIO_arm_switch_high);//high switch for arm position
		dInput0 = new DigitalInput(RobotMap.DIO_arm_switch_mid);//mid switch for arm position
		
		RS_button_5 = false;
		RS_button_3 = false;
		arm_seek_down = false;
		arm_seek_up = false;
		arm_seek_mid = false;
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
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

		//then act based on the button state to turn on the motor


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
			LiftArmMotor.set(-0.70); // Default Competition Value: -0.65
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
}