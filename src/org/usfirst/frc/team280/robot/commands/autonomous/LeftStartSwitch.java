package org.usfirst.frc.team280.robot.commands.autonomous;

import org.usfirst.frc.team280.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LeftStartSwitch extends Command {


    public LeftStartSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

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
