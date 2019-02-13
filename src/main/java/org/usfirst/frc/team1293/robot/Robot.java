package org.usfirst.frc.team1293.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
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

	Faults f = new Faults();

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
		talonsrx_arm.configFactoryDefault();
		talonsrx_arm.setInverted(false);
		talonsrx_arm.setSensorPhase(false);

		talonsrx_roller = new TalonSRX(20);

		myRobot = new DifferentialDrive(leftS, rightS);
		sol = new Solenoid(0);
		CameraServer.getInstance().startAutomaticCapture(0);
		CameraServer.getInstance().startAutomaticCapture(1);
	}

	public void teleopPeriodic() {
		myRobot.arcadeDrive(joystick.getY(Hand.kLeft), joystick.getX(Hand.kRight));
		double inspeed = joystick.getTriggerAxis(Hand.kLeft) * -0.5;
		double outspeed = joystick.getTriggerAxis(Hand.kLeft) * 0.5;
		
		if (joystick.getAButton()) {
				talonsrx_arm.set(ControlMode.PercentOutput, inspeed);
		}
		else if (joystick.getBButton()) {
				talonsrx_arm.set(ControlMode.PercentOutput, outspeed);
		}
		else if (joystick.getYButton()) {
				talonsrx_roller.set(ControlMode.PercentOutput, inspeed);
		}
		else {
			talonsrx_arm.setSelectedSensorPosition(0);
		}

		talonsrx_arm.getFaults(f);
		if (joystick.getXButtonPressed() ) {
			System.out.println("Sensor Velocity: " + talonsrx_arm.getSelectedSensorVelocity());
			System.out.println("Sensor Position: " + talonsrx_arm.getSelectedSensorPosition());
			System.out.println("Out %: " + talonsrx_arm.getMotorOutputPercent());
			System.out.println("Out of Phase: " + f.SensorOutOfPhase);
		}
		Scheduler.getInstance().run();
	}	

	public void autonomousInit() {
		// autonomous commands
	}

}