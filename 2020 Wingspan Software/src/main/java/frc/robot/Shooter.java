/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
public class Shooter {
    private double skew;
    private double distance;
    private double motorSpeed;
    private boolean reachedSpeed=false;
    private double shootAllSeconds=2.5;
    public void target(){
        
    }
    //Shoots one ball
    public void shootOnce(){
        //Checks if shooterMotor is at correct velocity
        if(Constants.shooterMotor.getEncoder().getVelocity()<=-4650){
            //If velocity is high enough, activates feeder motor and sets variable
            Constants.towerMotor.set(.3);
            reachedSpeed = true;
        }
        else{
            //If not at correct velocity turns off feeder motor
            Constants.towerMotor.set(0);
            //If the speed has already been reached and the speeds drops it will exit the method
            if(reachedSpeed){
                Constants.shootingOnce = false;
                stopMotors();
            }
        }
        startMotors();
    }
    public void shootAll(){
        if(shootAllSeconds>=0){
            shootAllSeconds-=.02;
        }
        else{
            Constants.shootingAll=false;
        }
        startMotors();
        if(Constants.shooterMotor.getEncoder().getVelocity()<=-4000){
            Constants.towerMotor.set(.7);
        }
        else{
            Constants.towerMotor.set(0);
        }
    }
    public void stopMotors(){
        Constants.shooterMotor.set(0);
        Constants.towerMotor.set(0);
        reachedSpeed = false;
        shootAllSeconds=2.5;
    }
    public void startMotors(){
        Constants.shooterMotor.set(Constants.shooterSpeed);
    }
}
