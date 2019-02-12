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
	private Talon leftS, rightS;
	private TalonSRX talonsrx_arm, talonsrx_roller;
	private Solenoid sol;
	private SerialPort arduino;



	// building command in terminal: gradlew build -Dorg.gradle.java.home="C:\Program Files\Java\jdk-11.0.2"
	// deploying command in terminal: gradlew deploy -PteamNumber=1293 --offline -Dorg.gradle.java.home="C:\Program Files\Java\jdk-11.0.2"
	@Override
	public void robotInit() {
		joystick = new XboxController(0);
		leftS = new Talon(1);
		rightS = new Talon(0);

		talonsrx_arm = new TalonSRX(10);
		double testPosition1;

		talonsrx_roller = new TalonSRX(20);

		myRobot = new DifferentialDrive(leftS, rightS);
		sol = new Solenoid(0);
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);
	}

	public void teleopPeriodic() {
		myRobot.arcadeDrive(joystick.getY(Hand.kLeft), joystick.getX(Hand.kRight));
		
		if (joystick.getAButton()) {
			System.out.println(talonsrx_arm.getSelectedSensorPosition());

			testPeriodic();
			
		} 
		Scheduler.getInstance().run();
	}	

	public void autonomousInit() {
		// autonomous commands
	}

}