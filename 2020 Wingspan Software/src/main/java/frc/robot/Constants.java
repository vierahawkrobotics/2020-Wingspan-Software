/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

/**
 * Add your docs here.
 */
public class Constants {
    //Declares motor variables
    public static Talon left1 = new Talon(0);
    public static Talon left2 = new Talon(2);
    public static Talon right1 = new Talon(1);
    public static Talon right2 = new Talon(3);
    public static Talon spinnyMotor = new Talon(4);
    public static Talon collectorMotor= new Talon(5);
    public static CANSparkMax shooterMotor = new CANSparkMax(1,MotorType.kBrushless);
    //Drivetrain instantiation
    public static SpeedControllerGroup leftGroup = new SpeedControllerGroup(left1, left2);
    public static SpeedControllerGroup rightGroup = new SpeedControllerGroup(right1, right2);;
    public static DifferentialDrive mainDrive = new DifferentialDrive(leftGroup, rightGroup);
    //Joystick instantiation (Joystick 0 is joystick, joystick 1 is controller)
    public static Joystick joystick0 = new Joystick(0);
    public static Joystick joystick1 = new Joystick(1);
    //Declares I2C port and color sensor
    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    public static ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    public static Encoder leftEncoder= new Encoder(0,1,false, EncodingType.k4X);
    public static Encoder rightEncoder= new Encoder(2,3,true, EncodingType.k4X);
    //Declares colormatch and target  colors
    public static ColorMatch colorMatcher = new ColorMatch();
    public static Color blueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static Color greenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static Color redTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static Color yellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    //init navx
    public static AHRS ahrs = new AHRS(SPI.Port.kMXP); ;
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
    public static boolean startingShooter = false;
    public static boolean stoppingShooter = false;
    public static Color targetColor;
    //Power Variables
    public static double driveSpeed = .75;
    public static double turnSpeed = .5;
    public static double fastSpeed = 1/driveSpeed;
    public static double shooterSpeed = .75;
    public static double winchSpeed = .75;
    public static double hangWheelSpeed = .75;
    public static double spinnerSpeed = .75;
    public static double deploySpeed = .75;
    public static double spinnerArmSpeed = .75;
    public static double collectorSpeed = .75;
    
    public Constants() {
        
    }
}
