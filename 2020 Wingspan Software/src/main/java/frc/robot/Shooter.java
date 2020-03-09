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
    public void target(){
        
    }
    //Shoots one ball
    public void shootOnce(){
        Constants.servoPosition = 2;
        System.out.println("V"+Constants.shooterMotor.getEncoder().getVelocity());
        startMotors();
        if(shootAllSeconds>=0){
            shootAllSeconds-=Constants.timerDecrement;
            if(Constants.shooterMotor.getEncoder().getVelocity()<=-5000){
                Constants.towerFeed = true;
            }
            else{
                Constants.towerFeed = false;
            }
        }
        else{
            Constants.shootingOnce = false;
            Constants.towerFeed = false;
        }
        Collector.towerFeed();
    }
    public void shootAll(){
        Constants.servoPosition = 1;
        System.out.println("V"+Constants.shooterMotor.getEncoder().getVelocity());
        startMotors();
        if(shootAllSeconds>=0){
            shootAllSeconds-=Constants.timerDecrement;
            if(Constants.shooterMotor.getEncoder().getVelocity()<=-5000){
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
        //set the turret hood to stay down
        Constants.servoPosition = 0;
    }
    public void startMotors(){
        Constants.shooterMotor.set(-1);
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
            Constants.turretMotor.set(ControlMode.PercentOutput,Constants.turretSpeed*.9);
        }
        else if (Constants.turretEncoder.getDistance() < degrees - 1) {
            Constants.turretMotor.set(ControlMode.PercentOutput,-Constants.turretSpeed*.9);
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
            Constants.turret1.set(0);
            Constants.turret2.set(1);
        }
        else if(Constants.servoPosition == 2){
            Constants.turret1.set(.5);
            Constants.turret2.set(.5);
        }
    }    
}
