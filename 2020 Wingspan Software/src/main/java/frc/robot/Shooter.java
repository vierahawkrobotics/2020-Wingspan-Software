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
public class Shooter {
    private double skew;
    private double distance;
    private double motorSpeed;
    public void target(){
        
        
    }
    public void shootOnce(){
        if(Constants.shooterMotor.getEncoder().getVelocity()<=-4650){
            Constants.towerMotor.set(.5);
        }
        else{
            Constants.towerMotor.set(0);
        }
        startMotors();
    }
    public void shootAll(){
        
    }
    public void stopMotors(){
        Constants.shooterMotor.set(0);
        Constants.towerMotor.set(0);
    }
    public void startMotors(){
        Constants.shooterMotor.set(Constants.shooterSpeed);
    }
}
