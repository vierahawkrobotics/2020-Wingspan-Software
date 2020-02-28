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
    public static boolean feedButton;
    public static boolean startLinePresetButton;
    public static boolean startLineOffsetButton;
    public static boolean trenchPresetButton;
    public static boolean towerResetButton;
    public static boolean winchButton;
    public static boolean colorArmButton;
    public static boolean armInButton;
    public static boolean armOutButton;
    public static void getButtons(){
        System.out.println("POV"+Constants.joystick1.getPOV());
        turboButton = Constants.joystick0.getRawButton(1);
        slowButton = Constants.joystick0.getRawButton(2);
        collectButton = Constants.joystick0.getRawButtonPressed(11);
        blueButton = Constants.joystick1.getRawButtonPressed(1);
        greenButton = Constants.joystick1.getRawButtonPressed(2);
        redButton = Constants.joystick1.getRawButtonPressed(3);
        yellowButton = Constants.joystick1.getRawButtonPressed(4);
        colorArmButton = Constants.joystick1.getRawButtonPressed(5);
        shootOnceButton = Constants.joystick1.getRawButtonPressed(6);
        feedButton = Constants.joystick1.getRawButtonPressed(7);
        shootAllButton = Constants.joystick1.getRawButtonPressed(8);
        spinButton = Constants.joystick1.getRawButtonPressed(10);
        winchButton = Constants.joystick1.getRawButton(9); 
        startLinePresetButton = false;
        armOutButton = false;
        armInButton = false;
        trenchPresetButton = false;
        if(Constants.joystick1.getPOV()==0){
            if(!povPressed){
                startLinePresetButton = true;
                povPressed = true;
            }
        }
        else if(Constants.joystick1.getPOV() == 90){
            armOutButton = true;
            povPressed = true;
        }
        else if(Constants.joystick1.getPOV() == 180){
            if(!povPressed){
                trenchPresetButton = true;
                povPressed = true;
            }
        }
        else if(Constants.joystick1.getPOV() == 270){
            armInButton = true;
            povPressed = true;
        }
        else{
            povPressed = false;
        }
    }
}
