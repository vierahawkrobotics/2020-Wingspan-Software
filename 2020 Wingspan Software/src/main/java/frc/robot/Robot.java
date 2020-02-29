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
  //Autonomous selection options
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
  //Autonomous data
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
    //Resets angle
    Constants.ahrs.reset();
    //more wpi lib confusing stuff
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
    autoStage=0;
    Constants.mainDrive.setMaxOutput(.5);
  }

  /**
   * This function is called periodically during autonomous.
   */
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
        autoClass.setTurretTargetAngle(121);
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
          shooterClass.stopMotors();
          autoStage++;
        }
      }
      //If in autoStage 5 sets target distance for robot to drive
      else if(autoStage == 5){
        autoClass.setTargetDistance(72);
        autoStage++;
      }
      //If in autoStage 6 drives robot off line
      else if(autoStage == 6){
        if(autoClass.driveDistance()){
          autoStage++;
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
        autoClass.setTurretTargetAngle(90);
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
          shooterClass.stopMotors();
          autoStage++;
        }
      }
      //If in autoStage 5 sets target distance for robot to drive
      else if(autoStage == 5){
        autoClass.setTargetDistance(72);
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
        autoClass.setTurretTargetAngle(62);
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
          shooterClass.stopMotors();
          autoStage++;
        }
      }
      //If in autoStage 5 sets target distance for robot to drive
      else if(autoStage == 5){
        autoClass.setTargetDistance(72);
        autoStage++;
      }
      //If in autoStage 6 drives robot off line
      else if(autoStage == 6){
        if(autoClass.driveDistance()){
          autoStage++;
        }
      }
    }
    collectorClass.moveCollector();
    shooterClass.moveServos();
    System.out.println("Stage "+autoStage);
    System.out.println("Left "+Constants.leftEncoder.getDistance());
    System.out.println("Right "+Constants.rightEncoder.getDistance());
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
    hangClass.extendArm();
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
    //System.out.println("FI"+Constants.joystick0.getRawAxis(1));
    //System.out.println("TI"+Constants.joystick0.getRawAxis(2));
  }
  /**
   * This function is called periodically during test mode.
   */
  public void testPeriodic() {	 
    Constants.servoPosition=0;
    shooterClass.moveServos();
    Constants.winchMotor.set(Constants.joystick1.getRawAxis(1));
  }
} 
