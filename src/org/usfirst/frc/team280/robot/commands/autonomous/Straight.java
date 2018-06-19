package org.usfirst.frc.team280.robot.commands.autonomous;

import org.usfirst.frc.team280.robot.RobotMap;
import org.usfirst.frc.team280.robot.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Straight extends Command {
	public WPI_TalonSRX LMMotor, RMMotor, LSMotor, RSMotor, LS1Motor, RS1Motor;
	DifferentialDrive drive, driveSlave, driveSlave1;
	
    public Straight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

			LMMotor = new WPI_TalonSRX(RobotMap.LMTalon);
			RMMotor = new WPI_TalonSRX(RobotMap.RMTalon);		
			LSMotor = new WPI_TalonSRX(RobotMap.LSTalon);
			RSMotor = new WPI_TalonSRX(RobotMap.RSTalon);
			LS1Motor = new WPI_TalonSRX(RobotMap.LS1Talon);
			RS1Motor = new WPI_TalonSRX(RobotMap.RS1Talon);

			drive = new DifferentialDrive(LMMotor, RMMotor);
			driveSlave = new DifferentialDrive(LSMotor, RSMotor);
			driveSlave1 = new DifferentialDrive(LS1Motor, RS1Motor);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Timer timer = new Timer();
		timer.start();
    	
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
			timer.stop();
		}
		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
