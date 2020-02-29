/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
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
    Constants.mainDrive.setMaxOutput(1);
    Controls.getButtons();
    //All controls on joystick 0 (The joystick)
    //Drive code
    double forwardSpeed=Constants.joystick0.getRawAxis(1);
    if(Controls.turboButton){
      forwardSpeed*=-1;
    }
    else if(Controls.slowButton){
      forwardSpeed*=Constants.slowSpeed;
    }
    else{
      forwardSpeed*=Constants.driveSpeed;
    }
    double rotateSpeed=Constants.joystick0.getRawAxis(2);
    rotateSpeed*=Constants.turnSpeed;
    Constants.mainDrive.curvatureDrive(forwardSpeed, rotateSpeed, true);
    //Collection code
    //if the driver wants to move the collector arm, change the position
    if(Controls.collectButton){
      Constants.isCollectorArmDown = !Constants.isCollectorArmDown;
    }
    //apply the new position or maintain the current position
    collectorClass.moveCollector();
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
      Constants.colorWheelMotor.set(ControlMode.PercentOutput,0);
      cp.numChanges=0;
    }
    //Shooter Controls
    if(Controls.shootOnceButton){
      Constants.shootingOnce = !Constants.shootingOnce;
      Constants.towerFeed = false;
    }
    else if(Controls.shootAllButton){
      Constants.shootingAll = !Constants.shootingAll;
      Constants.towerFeed = false;
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
    //Feeder and tower controls
    if(Controls.feedButton){
      Constants.towerFeed=!Constants.towerFeed;
    }
    if(Controls.towerResetButton){
      Constants.isReversingTower = !Constants.isReversingTower;
      Constants.isReversingTower = true;
      Collector.reverseTower();
    }
    else{
      Constants.isReversingTower = false;
    }
    if(!Constants.isReversingTower){
      Collector.towerFeed();
    }
    //turret controls
    shooterClass.rotateTurret();
    //hanging winch stuff
    if(Controls.winchButton){
      hangClass.moveWinch();
    }
    else{
      Constants.winchMotor.set(0);
    }
    hangClass.moveArm();
    shooterClass.moveServos();
    if(Controls.colorArmButton){
      cp.stopColorArm();
      Constants.movingColorArm = true;
      Constants.colorArmPosition = !Constants.colorArmPosition;
    }
    if(Constants.movingColorArm){
      cp.moveColorArm();
    }
    else{
      cp.stopColorArm();
    }
    if(Controls.armOutButton){
      hangClass.extendArm();
    }
    else if(Controls.armInButton){
      hangClass.retractArm();
    }
    else{
      Constants.armExtender.set(ControlMode.PercentOutput,0);
    }
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
