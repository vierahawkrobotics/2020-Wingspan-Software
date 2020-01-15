/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.util.Color;

/**
 * Add your docs here.
 */
public class Constants {
    //Declares motor variables
    public static Talon left1 = new Talon(0);
    public static Talon left2 = new Talon(1);
    public static Talon right1 = new Talon(2);
    public static Talon right2 = new Talon(3);
    public static Talon spinnyMotor = new Talon(4);
    //Declares I2C port and color sensor
    private static final I2C.Port i2cPort = I2C.Port.kOnboard;
    public static ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
    //Declares colormatch and target  colors
    public static ColorMatch colorMatcher = new ColorMatch();
    public static Color blueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static Color greenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static Color redTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static Color yellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);
    //State variables
    public static boolean isSpinning = false;
    public static boolean isGoingToColor = false;
    public static Color targetColor;
}
