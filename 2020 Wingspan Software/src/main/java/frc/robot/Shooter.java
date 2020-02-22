/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Shooter {
    private double skew;
    private double distance;
    private double motorSpeed;
    private boolean reachedSpeed=false;
    private double shootAllSeconds=2.5;
    private boolean turretCanGoLeft = true;
    private boolean turretCanGoRight = true;
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
    public void updateRanges() {
        if (Constants.turretEncoder.getDistance() < -90) {
            turretCanGoLeft = false;
        }
        else if(Constants.turretEncoder.getDistance() > 270) {
            turretCanGoRight = false;
        }
        else {
            turretCanGoRight = true;
            turretCanGoLeft = true;
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
    //turret Control
    public void rotateTurret() {
        if (turretCanGoLeft && turretCanGoRight) {
            Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed * Constants.joystick1.getRawAxis(0));
        }
        else if (turretCanGoLeft && Constants.joystick1.getRawAxis(0) < 0) {
            Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed * Constants.joystick1.getRawAxis(0));
        }
        else if (turretCanGoRight && Constants.joystick1.getRawAxis(0) > 0) {
            Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed * Constants.joystick1.getRawAxis(0));
        }
        else {
            Constants.turretMotor.set(ControlMode.PercentOutput,0);
        }
    }
    //used for auto
    /*public void rotateTurret(int degrees) {
        //if the abs of the pot voltage exeeds the maximum speed for going up, set the speed to the max speed (maintaining direction of motor) 
        if (Math.abs(Constants.turretEncoder.getDistance() / degrees) >= Constants.turretSpeed) {
            Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed * (Constants.turretEncoder.getDistance() - degrees / degrees / Math.abs(Constants.turretEncoder.getDistance() - degrees / degrees)));
        }
        else {
            Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed / Math.abs(Constants.turretEncoder.getDistance() - degrees / degrees) * Constants.turretEncoder.getDistance() - degrees / degrees);
        }
        
            
        
        
        //if (Constants.turretEncoder.getDistance() > degrees + 1) {
        //    Constants.turretMotor.set(ControlMode.PercentOutput,-1 * Constants.turretSpeed);
        //}
        //else if (Constants.turretEncoder.getDistance() < degrees - 1) {
        //    Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed);
        //}
        //else {
        //    Constants.turretMotor.set(ControlMode.PercentOutput,0);
        //}
    } */

    
}
