/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.hal.PDPJNI;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Constants {
    public static PDPJNI pdp = new PDPJNI();
    public static DigitalInput towerSensor = new DigitalInput(6);
    //Declares motor variables
    public static Talon leftDriveMotor1 = new Talon(0);
    public static Talon leftDriveMotor2 = new Talon(1);
    public static Talon rightDriveMotor1 = new Talon(2);
    public static Talon rightDriveMotor2 = new Talon(3);
    public static Talon collectorLift = new Talon(5);
    public static Talon collectorMotor= new Talon(4);
    public static Spark towerMotor = new Spark(6);
    public static CANSparkMax shooterMotor = new CANSparkMax(1,MotorType.kBrushless);
    public static CANSparkMax winchMotor = new CANSparkMax(2, MotorType.kBrushless);
    public static VictorSPX colorWheelMotor = new VictorSPX(3);
    public static VictorSPX hangArmMotor = new VictorSPX(4);
    public static VictorSPX colorWheelArm = new VictorSPX(5);
    public static VictorSPX armExtender = new VictorSPX(7);
    public static VictorSPX feeder = new VictorSPX(8);
    public static VictorSPX turretMotor = new VictorSPX(6);
    public static Servo turret1 = new Servo(7);
    public static Servo turret2 = new Servo(8);
    //Drivetrain instantiation
    public static SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftDriveMotor1, leftDriveMotor2);
    public static SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightDriveMotor1, rightDriveMotor2);;
    public static DifferentialDrive mainDrive = new DifferentialDrive(leftGroup, rightGroup);
    //Joystick instantiation (Joystick 0 is joystick, joystick 1 is controller)
    public static Joystick joystick0 = new Joystick(0);
    public static Joystick joystick1 = new Joystick(1);
    //Declares I2C port and color sensor
    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    public static ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    //Declares encoders
    public static Encoder leftEncoder= new Encoder(0,1,false, EncodingType.k4X);
    public static Encoder rightEncoder= new Encoder(2,3,true, EncodingType.k4X);
    public static Encoder turretEncoder = new Encoder(4,5, true, EncodingType.k4X);
    //Declares colormatch and target  colors
    public static ColorMatch colorMatcher = new ColorMatch();
    public static Color blueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static Color greenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static Color redTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static Color yellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    //init navx
    public static AHRS ahrs = new AHRS(SPI.Port.kMXP);
    //State variables
    public static boolean isSpinning = false;
    public static boolean isGoingToColor = false;
    public static boolean wheelDeploying = false;
    public static boolean wheelRetracting = false;
    public static boolean armReleasing = false;
    public static boolean ballsCollecting = false;
    public static boolean isTargeting = false;
    public static boolean shootingOnce = false;
    public static boolean shootingAll = false;
    public static boolean shootingWithVision = false;
    public static boolean startingShooter = false;
    public static boolean stoppingShooter = false;
    public static Color targetColor;
    public static double targetRevsCollectorArm = 0.0;
    public static double targetTurretDegrees = 0.0;
    public static boolean isCollectorArmDown = false;
    public static boolean towerFeed = false;
    public static boolean isReversingTower = false;
    public static int servoPosition = 0;
    public static boolean colorArmPosition = false;
    public static boolean movingColorArm = false;
    public static boolean kiddoIsShort = true;
    //Power Variables
    public static double maxShootingSpeed = 4000; //dummy value
    public static double driveSpeed = -.75;
    public static double autoMaxDriveSpeed = .5;
    public static double slowSpeed = -.35;
    public static double turnSpeed = .6;
    public static double winchSpeed = 1;
    public static double controlPanelSpeed = 1;
    public static double collectorSpeed = .8;
    public static double turretSpeed = -.6;
    public static double feederSpeed = -.25;
    public static double hangArmSpeed=-.3;
    public static double armDeploySpeed = 1;
    public static double colorWheelArmSpeed = .25;
    public static double reverseTowerMotorSpeed = -.15;
    public static double towerMotorSpeed = .85;
    //pot voltages and declaration
    public static AnalogInput potCollectorArm = new AnalogInput(0);
    public static double collectorUpVolts = 2.65;
    public static double collectorDownVolts = 4.2;
    //auto timing
    public static double timerDecrement = 0.02;
    //auto angles
    public static int autoLeftStage2Angle = 121;
    public static int autoLeftStage5Angle = 72;
    public static int autoMiddleStage2Angle = 90;
    public static int autoMiddleStage5Angle = 72;
    public static int autoRightStage2Angle = 62;
    public static int autoRightStage5Angle = 72;
    public static double autoTurnPIDTolerance = 2;
    //PID variables
    public static int shooterPIDTarget = 3000;
    public static int shooterPIDTolerance = 50;
    public static double collectorPIDTolerance = .05;
    public static int hangPIDBaseTarget = 2;
    //limelight
    public static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight-hwkrb");
    public static NetworkTableEntry limelighttx = table.getEntry("tx");
    public static NetworkTableEntry limelightty = table.getEntry("ty");
    public static NetworkTableEntry limelightta = table.getEntry("ta");
}
