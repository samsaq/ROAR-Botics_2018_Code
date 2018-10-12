package org.usfirst.frc.team6683.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot = new RobotDrive(1, 2);
	Joystick stick = new Joystick(0);
	Timer timer = new Timer();
	CameraServer server;
	Button button11 = new JoystickButton(stick, 11);
	// Button button6 = new JoystickButton(stick,6); // Remember to check if 6 is
	// used for anything else
	// Button button5 = new JoystickButton(stick,5); // Same as above, but for 5 too
	// CANTalon canTalon = new CANTalon(0);
	Talon motor6 = new Talon(6); /*
									 * adds a new talon motor - I assume this is the controller we're using - will
									 * need to check if I need to change it off channel 6
									 */

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		server = CameraServer.getInstance();
		server.startAutomaticCapture();
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		if (timer.get() < 7.0) {
			myRobot.drive(0.5, 0.0); // dr ive forwards half speed
		} else {
			myRobot.drive(0.0, 0.0); // stop robot
		}
	}

	/**
	 * This function is called once each time the robot enters tele-operated mode
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

		double x = stick.getX();
		double y = stick.getY();
		double slider = -(1.0 - stick.getRawAxis(3)) / 2.0;
		SmartDashboard.putNumber("slider value", stick.getThrottle());
		SmartDashboard.putNumber("scaled slider", slider);
		// double slider = stick.getRawAxis(3);
		double L = -y + x;
		double R = -y - x;
		double max = Math.abs(L);
		if (max < Math.abs(R))
			max = Math.abs(R);
		if (max > 1) {
			L /= max;
			R /= max;
		}
		if (slider > 0) {
			myRobot.setLeftRightMotorOutputs(-(slider * R), -(slider * L));
		} else {
			myRobot.setLeftRightMotorOutputs(-(slider * L), -(slider * R));
		}

		/*
		 * if (stick.getRawButton(11)) { //check later if I can delete this, seems not
		 * to do anything atm // canTalon.set(0.9); }/* /*if (stick.getRawButton(6)){
		 * motor6.set(0.5); }?* /*if(stick.getRawButton(5)) { motor6.set(-0.5); }/* /*
		 * double L = stick.getRawAxis(1); double R = stick.getRawAxis(5);
		 * myRobot.setLeftRightMotorOutputs( -L, -R);
		 * SmartDashboard.putNumber("Left Stick", L);
		 * SmartDashboard.putNumber("Right Stick", R);
		 */
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
