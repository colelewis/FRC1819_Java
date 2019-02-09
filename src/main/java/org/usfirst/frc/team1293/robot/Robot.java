package org.usfirst.frc.team1293.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CAN;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SerialPort;

public class Robot extends IterativeRobot {
	private DifferentialDrive myRobot;
	private XboxController joystick;
	private Hand leftH, rightH;
	private Spark leftS, rightS;
	private TalonSRX talonsrx;
	private Solenoid sol;
	private SerialPort arduino;



	// building command in terminal: gradlew build -Dorg.gradle.java.home="C:\Program Files\Java\jdk-11.0.2"
	// deploying command in terminal: gradlew deploy -PteamNumber=1293 --offline -Dorg.gradle.java.home="C:\Program Files\Java\jdk-11.0.2"
	@Override
	public void robotInit() {
		joystick = new XboxController(0);
		leftS = new Spark(1);
		rightS = new Spark(0);

		arduino = new SerialPort(19200, SerialPort.Port.kUSB);


		talonsrx = new TalonSRX(10);
		double testPosition1;

		myRobot = new DifferentialDrive(leftS, rightS);
		sol = new Solenoid(0);
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);
	}

	public void testPeriodic() {
		byte[] sendData = "This text IS the Data.".getBytes();
		byte[] receiveData = new byte[12];
		arduino.transaction(sendData, sendData.length, receiveData, receiveData.length);
		System.out.println("Receieved: " + new String(receiveData, 0, receiveData.length));
	}

	public void teleopPeriodic() {
		myRobot.arcadeDrive(joystick.getY(Hand.kLeft), joystick.getX(Hand.kRight));
		
		if (joystick.getAButton()) {
			System.out.println(talonsrx.getSelectedSensorPosition());
			
		} 
		Scheduler.getInstance().run();
	}	

	public void autonomousInit() {
		// autonomous commands
	}

}