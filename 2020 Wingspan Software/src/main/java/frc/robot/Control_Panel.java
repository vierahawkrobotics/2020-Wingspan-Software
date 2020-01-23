/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.util.Color;

/**
 * Add your docs here.
 */

public class Control_Panel {
    //Declares number of changes and last color (initialized to blue b/c I like blue)
    public int numChanges = 0;
    private Color lastColor = Constants.blueTarget;
    boolean isExtended=false;
    public void deployWheel(){

    }
    public void storeWheel(){

    }
    //Spins the wheel for 3-5 full revolutions
    public void spinWheel() {
        //Sets the motor to full speed
        Constants.spinnyMotor.set(Constants.spinnerSpeed);
        //Sets the colorMatchResult to the closest target color
        ColorMatchResult match = Constants.colorMatcher.matchClosestColor(Constants.colorSensor.getColor());
        //Checks if number of changes is greater than 25 (8 per cycle, 24 is three cycles +1 for safety)
        if (numChanges <= 25) {
            //Checks if color has changed to the next color in the sequence (reduces false positives)
            if (lastColor == Constants.blueTarget && match.color == Constants.yellowTarget) {
                lastColor = match.color;
                numChanges++;
            } 
            else if (lastColor == Constants.yellowTarget && match.color == Constants.greenTarget) {
                lastColor = match.color;
                numChanges++;
            } 
            else if (lastColor == Constants.greenTarget && match.color == Constants.redTarget) {
                lastColor = match.color;
                numChanges++;
            } 
            else if (lastColor == Constants.redTarget && match.color == Constants.blueTarget) {
                lastColor = match.color;
                numChanges++;
            }
        } 
        else {
            //Turns off motor, resets numchanges, disables method
            Constants.spinnyMotor.set(0);
            Constants.isSpinning = false;
            numChanges = 0;
        }
    }
    //Moves motor until the sensor detects the target color
    public void spinToColor() {
        //Sets the color wheel motor to spin
        Constants.spinnyMotor.set(Constants.spinnerSpeed);
        //Sets the colorMatchResult to the closest target color
        ColorMatchResult match = Constants.colorMatcher.matchClosestColor(Constants.colorSensor.getColor());
        //Checks if the color detected is the correct color
        if (match.color == Constants.targetColor) {
            //Stops motor and disables method
            Constants.spinnyMotor.set(0);
            Constants.isGoingToColor = false;
        }
    }
}
