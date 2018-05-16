package org.usfirst.frc.team280.robot.commands.autonomous;

import org.usfirst.frc.team280.robot.commands.GripWheelOut;
import org.usfirst.frc.team280.robot.commands.RotateWristEncoder;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightStartSwitch extends CommandGroup {

    public RightStartSwitch() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		System.out.println("Game Data: " + gameData);
		if (gameData.equals("LRL") || gameData.equals("LLL")) {
			addSequential(new Straight(2));
		} else if (gameData.equals("RLR") || gameData.equals("RRR")) {
	    	addSequential(new Straight(2));
	    	addSequential(new RotateWristEncoder(-7));
	    	addSequential(new GripWheelOut());
		} else { 
			DriverStation.reportError("I'm just gonna cross the line. Invalid GameData recieved. Data: " + gameData, false);
			addSequential(new Straight(2));
		}
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
