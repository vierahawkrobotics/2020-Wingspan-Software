/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * Add your docs here.
 */
public class Hang {
    private boolean armReleased=false;
    private boolean hooked=false;
    public void moveWinch(){
        Constants.winchMotor.set(Constants.winchSpeed);
    }
    public void extendArm(){
        Constants.armExtender.set(ControlMode.PercentOutput,-Constants.armDeploySpeed);
    }
    public void retractArm(){
        Constants.armExtender.set(ControlMode.PercentOutput,Constants.armDeploySpeed);
    }
    public void moveArm(){
        Constants.hangArmMotor.set(ControlMode.PercentOutput,Constants.joystick1.getRawAxis(3)*Constants.hangArmSpeed);
    }
}