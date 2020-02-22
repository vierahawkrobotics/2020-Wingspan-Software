/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
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
  private static final String leftAuto = "Left Auto";
  private static final String middleAuto = "Middle Auto";
  private static final String rightAuto = "Right Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  //Class instantiation
  private Hang hangClass = new Hang();
  private Collector collectorClass = new Collector();
  private Control_Panel cp = new Control_Panel();
  private Shooter shooterClass = new Shooter();
  private Telemetry telemetryClass = new Telemetry();
  private Autonomous autoClass = new Autonomous();
  private int autoStage = 0;
  private double secondsDelay = 0;
  /*
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    //wpi lib stuff idk what it does
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("Left Auto", leftAuto);
    m_chooser.addOption("Middle Auto", middleAuto);
    m_chooser.addOption("Right Auto", rightAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    SmartDashboard.putNumber("Autonomous delay", 0);
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
    Constants.collectorEncoder.reset();
    Constants.collectorEncoder.setDistancePerPulse(1.0/2048.0);//1 rev of encoder
    Constants.turretEncoder.reset();
    Constants.turretEncoder.setDistancePerPulse(1/284.75 * 360);//1 rev of motor times 360 degrees for every rotation
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
    //Resets angle
    Constants.ahrs.reset();
    //more wpi lib confusing stuff
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
    autoStage=0;
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //In the first iteration it gets the delay in seconds before starting auto
    if(autoStage == 0){
      secondsDelay = SmartDashboard.getNumber("Autonomous delay", 0);
      autoStage++;
    }
    //Checks to see which auto mode is selected(default(nothing), left, middle, right)
    if(m_autoSelected.equals(kDefaultAuto)){
    }
    else if (m_autoSelected.equals(leftAuto)){
      System.out.println(autoStage);
      //If in autoStage 1 waits for secondsDelay seconds
      if (autoStage == 1) {
        if(secondsDelay>0){
          secondsDelay-=.02;
        }
        else{
          autoStage++;
        }
      }
      //If in autoStage 2 sets turret target angle
      else if (autoStage==2) {
        autoClass.setTurretTargetAngle(66.77);
        autoStage++;
      }
      //If in autoStage 3 moves turret to angle
      else if (autoStage==3) {
        if(autoClass.moveTurretToAngle()){
          autoStage++;
        }
      }
      //If in autoStage 4 shoots all balls from robot
      else if (autoStage==4) {
        collectorClass.moveCollector();
        Constants.shootingAll=true;
        shooterClass.shootAll();
        if(Constants.shootingAll==false){
          shooterClass.stopMotors();
          autoStage++;
        }
      }
      //If in autoStage 5 sets target distance for robot to drive to
      else if (autoStage == 5) {
        collectorClass.moveCollector();
        autoClass.setTargetDistance(220);
        autoStage++;
      }
      //If in autostage 6 moves collector down and drives distance
      else if (autoStage == 6) {
        collectorClass.moveCollector();
        collectorClass.collectBalls();
        if(autoClass.driveDistance()){
          autoStage++;
        }
      }
      //If in autoStage 7 turns off collector and sets target angle for turret
      else if (autoStage == 13) {
        Constants.collectorMotor.set(0);
        autoClass.setTurretTargetAngle(75.76);
        autoStage++;
      }
      //If in autoStage 8 moves turret to target angle
      else if (autoStage == 14) {
        if(autoClass.moveTurretToAngle()){
          autoStage++;
        }
      }
      //If in autoStage 9 shoots all balls
      else if (autoStage == 15) {
        Constants.shootingAll = true;
        shooterClass.shootAll();
        if(Constants.shootingAll == false){
          autoStage++;
          shooterClass.stopMotors();
        }
      }
    }
    else if (m_autoSelected.equals(middleAuto)){
      //If in autoStage 1 waits for secondsDelay seconds
      if(autoStage == 1){
        if(secondsDelay>0){
          secondsDelay-=.02;
        }
        else{
          autoStage++;
        }
      }
      //If in autoStage 2 sets turret target angle
      else if(autoStage == 2){
        autoClass.setTurretTargetAngle(180);
        autoStage++;
      }
      //If in autoStage 3 moves turret to target angle
      else if(autoStage == 3){
        if(autoClass.moveTurretToAngle()){
          autoStage++;
        }
      }
      //If in autoStage 4 shoots all balls
      else if(autoStage == 4){
        Constants.shootingAll = true;
        shooterClass.shootAll();
        if(!Constants.shootingAll){
          autoStage++;
        }
      }
      //If in autoStage 5 sets target distance for robot to drive
      else if(autoStage == 5){
        autoClass.setTargetDistance(18);
        autoStage++;
      }
      //If in autoStage 6 drives robot off line
      else if(autoStage == 6){
        if(autoClass.driveDistance()){
          autoStage++;
        }
      }
    }
    else if (m_autoSelected.equals(rightAuto)){
      if(autoStage == 1){
        autoClass.setTargetAngle(-15);
        autoStage++;
      }
      else if(autoStage == 2){
        if(autoClass.moveToTargetAngle()){
          autoStage++;
        }
      }
      else if(autoStage == 3){
        autoClass.setTargetDistance(84);
        autoStage++;
      }
      else{
        autoClass.driveDistance();
      }
    }
    System.out.println(NavX.getTotalYaw());
    System.out.println("Left"+Constants.leftEncoder.getDistance());
    System.out.println("Right"+Constants.rightEncoder.getDistance());
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
    //if the driver wants to move the collector arm, change the position
    if(Controls.moveCollectorButton){
      Constants.isCollectorArmDown = !Constants.isCollectorArmDown;
    }
    //apply the new position or maintain the current position
    collectorClass.moveCollector();
    //activate the ball colection motor if the driver wants to collect balls
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
    //update the status (location) of the turret
    shooterClass.updateRanges();
    //turret controls
    shooterClass.rotateTurret();
    //hanging winch stuff
    double winchSpeed = Constants.joystick1.getRawAxis(3)*Constants.winchSpeed;
    hangClass.moveWinch(winchSpeed);
    //Hanging wheels
    double hangWheelSpeed = Constants.joystick1.getRawAxis(0)*Constants.hangWheelSpeed;
    hangClass.moveHangWheels(hangWheelSpeed);
    //print the encoder values
    //telemetryClass.debugEncoders("Encoder Values",collectorClass);
    //send the dashboard data
    //telemetryClass.sendDashboardData();
    //System.out.println(Constants.turretEncoder.getDistance());
    //test auto turret
    //shooterClass.rotateTurret(45);
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
} 
