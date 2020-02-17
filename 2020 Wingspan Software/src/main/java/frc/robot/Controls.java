/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Controls {
    //Button variables
    public static boolean turboButton;
    public static boolean collectButton;
    public static boolean moveCollectorButton;
    public static boolean releaseArmButton;
    public static boolean blueButton;
    public static boolean greenButton;
    public static boolean redButton;
    public static boolean yellowButton;
    public static boolean spinButton;
    public static boolean shootOnceButton;
    public static boolean shootAllButton;
    public static void getButtons(){
        turboButton = Constants.joystick0.getRawButton(1);
        collectButton = Constants.joystick0.getRawButtonPressed(5);
        moveCollectorButton = Constants.joystick0.getRawButtonPressed(6);
        releaseArmButton = Constants.joystick0.getRawButtonPressed(11);
        blueButton = Constants.joystick1.getRawButtonPressed(1);
        greenButton = Constants.joystick1.getRawButtonPressed(2);
        redButton = Constants.joystick1.getRawButtonPressed(3);
        yellowButton = Constants.joystick1.getRawButtonPressed(4);
        spinButton = Constants.joystick1.getRawButtonPressed(6);
        shootOnceButton = Constants.joystick1.getRawButtonPressed(7);
        shootAllButton = Constants.joystick1.getRawButtonPressed(8);
    }
}
