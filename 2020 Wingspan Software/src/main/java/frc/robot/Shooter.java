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
    private double shootAllSeconds=4;
    private boolean turretCanGoLeft = true;
    private boolean turretCanGoRight = true;
    private boolean turretHoodTimerHasStarted = false;
    private long turretTimer;
    public void target(){
        
    }
    //Shoots one ball
    public void shootOnce(){
        //set the turret hood to be up (should be up by the time the shooter spins up)
        Constants.servoPosition = 2;
        //Checks if shooterMotor is at correct velocity
        if(Constants.shooterMotor.getEncoder().getVelocity()<=-4650){
            //If velocity is high enough, activates feeder motor and sets variable
            Constants.towerFeed = true;
            reachedSpeed = true;
        }
        else{
            //If not at correct velocity turns off feeder motor
            Constants.towerFeed = false;
            //If the speed has already been reached and the speeds drops it will exit the method
            if(reachedSpeed){
                Constants.shootingOnce = false;
                stopMotors();
            }
        }
        Collector.towerFeed();
        startMotors();
        //if the timer for the turret hoos hasn't started yet and the tower has started feeding 
        //(meaning the ball is getting ready to be shot), set the start time and say the time has started
        if (!turretHoodTimerHasStarted && Constants.towerFeed) {
            turretTimer = System.currentTimeMillis();
            turretHoodTimerHasStarted = true;
        }
        else if (System.currentTimeMillis() >= turretTimer + 2000) {
            Constants.servoPosition = 0;
        }
    }
    public void shootAll(){
        startMotors();
        if(shootAllSeconds>=0){
            shootAllSeconds-=.02;
            if(Constants.shooterMotor.getEncoder().getVelocity()<=-4000){
                Constants.towerFeed = true;
            }
            else{
                Constants.towerFeed = false;
            }
        }
        else{
            Constants.shootingAll = false;
            Constants.towerFeed = false;
        }
        Collector.towerFeed();
    }
    public void stopMotors(){
        Constants.shooterMotor.set(0);
        reachedSpeed = false;
        shootAllSeconds=4;
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
    public static boolean rotateTurret(int degrees) {
        if (Constants.turretEncoder.getDistance() > degrees + 1) {
            Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed);
        }
        else if (Constants.turretEncoder.getDistance() < degrees - 1) {
            Constants.turretMotor.set(ControlMode.PercentOutput,-Constants.turretSpeed);
        }
        else {
            Constants.turretMotor.set(ControlMode.PercentOutput,0);
            return true;
        }
        return false;
    } 
    public void moveServos(){
        if(Constants.servoPosition == 0){
            Constants.turret1.set(1);
            Constants.turret2.set(0);
        }
        else if(Constants.servoPosition == 1){
            Constants.turret1.set(.5);
            Constants.turret2.set(.5);
        }
        else if(Constants.servoPosition == 2){
            Constants.turret1.set(0);
            Constants.turret2.set(1);
        }
    }    
}
