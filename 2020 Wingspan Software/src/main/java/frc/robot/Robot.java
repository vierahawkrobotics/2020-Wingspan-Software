/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.TimedRobot;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  
  //Class instantiation
  private Hang hangClass = new Hang();
  private Collector collectorClass = new Collector();
  private Control_Panel cp = new Control_Panel();
  private Shooter shooterClass = new Shooter();
  private Telemetry telemetryClass = new Telemetry();
  private Autonomous autoClass = new Autonomous();
  private Teleop teleopClass = new Teleop();
  
  /*
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    //add auto options to dashboard
    autoClass.autoOptions();
    //Initializes the collector motor
    Constants.collectorMotor.setInverted(true);
    //init colorsensor
    Constants.colorMatcher.addColorMatch(Constants.blueTarget);
    Constants.colorMatcher.addColorMatch(Constants.redTarget);
    Constants.colorMatcher.addColorMatch(Constants.greenTarget);
    Constants.colorMatcher.addColorMatch(Constants.yellowTarget);
    //init encoders
    Constants.leftEncoder.reset();
    Constants.ahrs.reset();
    Constants.leftEncoder.setDistancePerPulse(1.0/2048.0);//1 rev of encoder
    Constants.rightEncoder.reset();
    Constants.rightEncoder.setDistancePerPulse(1.0/2048.0);//1 rev of encoder
    Constants.turretEncoder.reset();
    Constants.turretEncoder.setDistancePerPulse(1.0/1472.0*360);//1 rev of motor times 360 degrees for every rotation
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    autoClass.autoInit();
  }

  /**
   * This function is called periodically during autonomous.
   */
  public void autonomousPeriodic() {
    autoClass.autoRoutine(shooterClass, collectorClass);
  }
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    teleopClass.teleopRoutine(collectorClass, shooterClass, hangClass, cp);
  }
  /**
   * This function is called periodically during test mode.
   */
  public void testPeriodic() {
    //GO DOWM
    Constants.servoPosition=0;
    shooterClass.moveServos();
    Constants.winchMotor.set(Constants.joystick1.getRawAxis(1));
  }
} 
