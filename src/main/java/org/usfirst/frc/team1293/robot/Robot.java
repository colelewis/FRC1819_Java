package org.usfirst.frc.team1293.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private DifferentialDrive myRobot;
	private XboxController joystick;
	private Hand leftH, rightH;
	private Spark leftS, rightS;
	private SpeedControllerGroup leftDrive, rightDrive;
	private Solenoid sol;
	private I2C arduino; //arduino to be interfaced with, holds pixycam

	// building command in terminal: gradlew build -Dorg.gradle.java.home="C:\Program Files\Java\jdk-11.0.2"
	// deploying command in terminal: gradlew deploy -PteamNumber=1293 --offline -Dorg.gradle.java.home="C:\Program Files\Java\jdk-11.0.2"
	@Override
	public void robotInit() {
		joystick = new XboxController(0);
		leftS = new Spark(1);
		rightS = new Spark(0);
		myRobot = new DifferentialDrive(leftS, rightS);
		arduino = new I2C(I2C.Port.kOnboard, 8);
		sol = new Solenoid(0);
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);
	}

	
	public void teleopPeriodic() {
		myRobot.arcadeDrive(joystick.getY(Hand.kLeft), joystick.getX(Hand.kRight));
		
		if (joystick.getAButton()) {
			testPeriodic();
		} 
		Scheduler.getInstance().run();
	}	
	
	
	@Override
	public void testPeriodic() {
		byte[] sendData = "This text IS the Data.".getBytes();
		byte[] receiveData = new byte[12];
		arduino.transaction(sendData, sendData.length, receiveData, receiveData.length);
		System.out.println("Receieved: " + new String(receiveData, 0, receiveData.length));
	}

	public void autonomousInit() {
		// autonomous commands
	}

}