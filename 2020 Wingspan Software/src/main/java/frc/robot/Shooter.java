/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.controller.PIDController;
public class Shooter {
    private double skew;
    private double distance;
    private double motorSpeed;
    private boolean reachedSpeed=false;
    private double shootAllSeconds=4;
    private boolean turretCanGoLeft = true;
    private boolean turretCanGoRight = true;
    private PIDController shooterPid = new PIDController(.0012,.00044,.012);
    private CANPIDController shootMotorPID = Constants.shooterMotor.getPIDController();
    //private PIDController shooterPid = new PIDController(.00002,.00000481,0);
    private int cyclesOnTarget = 0;
    public Shooter(){
        shootMotorPID.setP(.00002);
        shootMotorPID.setI(.00000481);
    }
    public void target(){
        
    }
    //Shoots one ball
    public void shootOnce(){
        //Checks if shooterMotor is at correct velocity
        if(Constants.shooterMotor.getEncoder().getVelocity()<=-3650){
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
        shootAllSeconds -= Constants.timerDecrement;
        if(shootAllSeconds == 0){
            Constants.towerFeed = false;
            Constants.shootingOnce = false;
            stopMotors();
        }
    }
    public void shootAll(){
        System.out.println("V"+Constants.shooterMotor.getEncoder().getVelocity());
        startMotors();
        if(shootAllSeconds>=0){
            shootAllSeconds-=Constants.timerDecrement;
            /*
            if(shooterPid.atSetpoint()){
                cyclesOnTarget++;
                if(cyclesOnTarget >= 3){
                    Constants.towerFeed = true;
                }
            }
            */
            if(Constants.shooterMotor.getEncoder().getVelocity()<=-3000){
                Constants.towerFeed = true;
            }
            else{
                Constants.towerFeed = false;
                cyclesOnTarget = 0;
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
        //shootMotorPID.setReference(Constants.shooterPIDTarget, ControlType.kVelocity);
        //shooterPid.setSetpoint(Constants.shooterPIDTarget);
        //shooterPid.setTolerance(Constants.shooterPIDTolerance);
        //Constants.shooterMotor.pidWrite(shootMotorPID.);
        //set the turret hood to be up (should be up by the time the shooter spins up) will be reset in stopMotors()
        //Constants.shooterMotor.set(shooterPid.calculate(Constants.shooterMotor.getEncoder().getVelocity()));\
        Constants.shooterMotor.set(-1);
        Constants.servoPosition = 1;
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
    }    
}
