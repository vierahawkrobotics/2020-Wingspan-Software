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
    public int numChanges=0;
    private Color lastColor;
    public void spin(){
        Constants.spinnyMotor.set(1);
        ColorMatchResult match=Constants.colorMatcher.matchClosestColor(Constants.colorSensor.getColor());
        if(numChanges <= 30){
            if(match.color!=lastColor){
                lastColor=match.color;
                numChanges++;
                System.out.println(numChanges);
                System.out.println(lastColor);
            }
        }
        else{
            Constants.spinnyMotor.set(0);
            Constants.isSpinning = false;
            numChanges=0;
        }
    }
    public void goToColor(){
        Constants.spinnyMotor.set(1);
        ColorMatchResult match=Constants.colorMatcher.matchClosestColor(Constants.colorSensor.getColor());
        if(match.color == Constants.targetColor){
            Constants.spinnyMotor.set(0);
            Constants.isGoingToColor = false;
        }
    }
}
