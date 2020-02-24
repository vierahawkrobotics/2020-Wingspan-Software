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
    private static boolean povPressed = false;
    public static boolean turboButton;
    public static boolean collectButton;
    public static boolean blueButton;
    public static boolean greenButton;
    public static boolean redButton;
    public static boolean yellowButton;
    public static boolean spinButton;
    public static boolean shootOnceButton;
    public static boolean shootAllButton;
    public static boolean slowButton;
    public static boolean extendHookButton;
    public static boolean retractHookButton;
    public static boolean feedButton;
    public static boolean startLinePresetButton;
    public static boolean startLineOffsetButton;
    public static boolean trenchPresetButton;
    public static boolean towerResetButton;
    public static boolean winchButton;
    public static void getButtons(){
        System.out.println("POV"+Constants.joystick1.getPOV());
        turboButton = Constants.joystick0.getRawButton(1);
        slowButton = Constants.joystick0.getRawButton(2);
        extendHookButton = Constants.joystick0.getRawButtonPressed(9);
        retractHookButton = Constants.joystick0.getRawButtonPressed(7);
        collectButton = Constants.joystick0.getRawButtonPressed(11);
        blueButton = Constants.joystick1.getRawButtonPressed(1);
        greenButton = Constants.joystick1.getRawButtonPressed(2);
        redButton = Constants.joystick1.getRawButtonPressed(3);
        yellowButton = Constants.joystick1.getRawButtonPressed(4);
        shootOnceButton = Constants.joystick1.getRawButtonPressed(6);
        feedButton = Constants.joystick1.getRawButtonPressed(7);
        shootAllButton = Constants.joystick1.getRawButtonPressed(8);
        spinButton = Constants.joystick1.getRawButtonPressed(10);
        towerResetButton = Constants.joystick1.getRawButtonPressed(9);
        startLinePresetButton = false;
        startLineOffsetButton = false;
        trenchPresetButton = false;
        winchButton = false;
        if(Constants.joystick1.getPOV()==0 && !povPressed){
            startLinePresetButton = true;
            povPressed = true;
        }
        else if(Constants.joystick1.getPOV() == 90 && !povPressed){
            startLineOffsetButton = true;
            povPressed = true;
        }
        else if(Constants.joystick1.getPOV() == 180 && !povPressed){
            trenchPresetButton = true;
            povPressed = true;
        }
        else if(Constants.joystick1.getPOV() == 270 && !povPressed){
            winchButton = true;
            povPressed = true;
        }
        else{
            povPressed = false;
        }
    }
}
