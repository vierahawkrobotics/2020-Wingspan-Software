/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //wpi lib stuff idk what it does
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  //Class instantiation
  private Hang hangClass = new Hang();
  private Collector collectorClass = new Collector();
  private Control_Panel cp = new Control_Panel();
  private Shooter shooterClass = new Shooter();
  private Telemetry telemetryClass = new Telemetry();
  private Autonomous autoClass = new Autonomous();
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    //wpi lib stuff idk what it does
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    //Initializes the collector motor
    Constants.collectorMotor.setInverted(true);
    //init colorsensor
    Constants.colorMatcher.addColorMatch(Constants.blueTarget);
    Constants.colorMatcher.addColorMatch(Constants.redTarget);
    Constants.colorMatcher.addColorMatch(Constants.greenTarget);
    Constants.colorMatcher.addColorMatch(Constants.yellowTarget);
    //init encoders
    Constants.leftEncoder.reset();
    Constants.leftEncoder.setDistancePerPulse(1.0/2048.0);
    Constants.rightEncoder.reset();
    Constants.rightEncoder.setDistancePerPulse(1.0/2048.0);
    Constants.collectorEncoder.reset();
    Constants.collectorEncoder.setDistancePerPulse(1.0/2048.0);
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
    //more wpi lib confusing stuff
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      //Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      //Put default auto code here
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Controls.getButtons();
    //All controls on joystick 0 (The joystick)
    //Drive code
    double forwardSpeed=Constants.joystick0.getRawAxis(1);
    if(!Controls.turboButton){
      forwardSpeed*=Constants.driveSpeed;
    }
    else{
      forwardSpeed*=-1;
    }
    double rotateSpeed=Constants.joystick0.getRawAxis(2);
    rotateSpeed*=Constants.turnSpeed;
    Constants.mainDrive.arcadeDrive(forwardSpeed, rotateSpeed);
    //Releases Hanging Arm
    if(Controls.releaseArmButton){
      Constants.armReleasing = !Constants.armReleasing;
    }
    if(Constants.armReleasing){
      hangClass.releaseArm();
    }
    //Collection code
    if(Controls.moveCollectorButton){
      if(Constants.targetRevsCollectorArm == 0){
        Constants.targetRevsCollectorArm = .28;
      }
      else{
        Constants.targetRevsCollectorArm = 0;
      }
    }
    collectorClass.moveCollector(Constants.targetRevsCollectorArm);
    if (Controls.collectButton) {
      Constants.ballsCollecting = !Constants.ballsCollecting;
    }
    if(Constants.ballsCollecting){
      collectorClass.collectBalls();
    }
    else{
      Constants.collectorMotor.set(0);
    }
    //All controls on joystick 1 (The controller)
    //Control panel controls (switches between on and off so pressing the button again stops the motor)
    if(Controls.blueButton){
      Constants.targetColor = Constants.blueTarget;
      Constants.isGoingToColor = !Constants.isGoingToColor;
    }
    else if(Controls.greenButton){
      Constants.targetColor = Constants.greenTarget;
      Constants.isGoingToColor = !Constants.isGoingToColor;
    }
    else if(Controls.redButton){
      Constants.targetColor = Constants.redTarget;
      Constants.isGoingToColor = !Constants.isGoingToColor;
    }
    else if(Controls.yellowButton){
      Constants.targetColor = Constants.yellowTarget;
      Constants.isGoingToColor = !Constants.isGoingToColor;
    }
    if(Controls.spinButton){
      Constants.isSpinning=!Constants.isSpinning;
    }
    //Checks if either of the methods that use the control panel motor are active, and if not stops the motor
    if(Constants.isGoingToColor==true){
      cp.spinToColor();
    }
    else if(Constants.isSpinning==true){
      cp.spinWheel();
    }
    else{
      Constants.controlPanelMotor.set(0);
      cp.numChanges=0;
    }
    //Shooter Controls
    if(Controls.shootOnceButton){
      Constants.shootingOnce = !Constants.shootingOnce;
    }
    else if(Controls.shootAllButton){
      Constants.shootingAll = !Constants.shootingAll;
    }
    if(Constants.shootingOnce){
      shooterClass.shootOnce();
    }
    else if(Constants.shootingAll){
      shooterClass.shootAll();
    }
    else{
      shooterClass.stopMotors();
    }
    //hanging winch stuff
    double winchSpeed = Constants.joystick1.getRawAxis(3)*Constants.winchSpeed;
    hangClass.moveWinch(winchSpeed);
    //Hanging wheels
    double hangWheelSpeed = Constants.joystick1.getRawAxis(0)*Constants.hangWheelSpeed;
    hangClass.moveHangWheels(hangWheelSpeed);
    //print the encoder values
    telemetryClass.debugEncoders("Encoder Values",collectorClass);
    //send the dashboard data
    telemetryClass.sendDashboardData();
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
} 
