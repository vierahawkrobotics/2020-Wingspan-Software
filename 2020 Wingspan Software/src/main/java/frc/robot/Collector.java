/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Add your docs here.
 */
public class Collector {
  private PIDController pidUp = new PIDController(.75,0,0);
  private PIDController pidDown = new PIDController(.75,0,0);
  private Pot potClass = new Pot(Constants.collectorUpVolts,Constants.collectorDownVolts);
  public Collector(){
    pidUp.setSetpoint(Constants.collectorUpVolts);
    pidUp.setTolerance(Constants.collectorPIDTolerance);
    pidDown.setSetpoint(Constants.collectorDownVolts);
    pidDown.setTolerance(Constants.collectorPIDTolerance);
  }
  public void moveCollector() {
    //move the collector arm to the requested position
    //if going down, use a PID-like system that slows down as it approces the bottom
    //if going up, move motor untill you reach the position, speed is static
    if (Constants.isCollectorArmDown) {
      //if the abs of the pot voltage exeeds the maximum speed for going up, set the speed to the max speed (maintaining direction of motor) 
      System.out.println(potClass.getRawVolts());
      System.out.println(pidDown.atSetpoint());
      if (Math.abs(potClass.getPercentage()) >= Constants.collectorArmSpeedDown) {
        Constants.collectorLift.set(Constants.collectorArmSpeedDown * (potClass.getPercentage() / Math.abs(potClass.getPercentage())));
      }
      //if (!pidDown.atSetpoint()) {
        //Constants.collectorLift.set(pidDown.calculate(potClass.getRawVolts()));
      //}
      else {
        Constants.collectorLift.set(0);
      }
      Constants.collectorMotor.set(Constants.collectorSpeed);
    }
    else {
      //if going up, do not use PID and instead just run motor until at desired position
      //if (!pidUp.atSetpoint()) {
        //Constants.collectorLift.set(pidUp.calculate(potClass.getRawVolts()));
      //}
      if (potClass.getRawVolts() >= Constants.collectorUpVolts) {
        Constants.collectorLift.set(Constants.collectorArmSpeedUp);
      }
      else {
        Constants.collectorLift.set(0);
      }
      Constants.collectorMotor.set(0);
    }
    //System.out.println(potClass.getRawVolts());
    //manual control of collector arm
    //Constants.collectorLift.set(Constants.joystick1.getRawAxis(1)*-.7);
  }
  public static void towerFeed(){
    //if the tower is supposed to be feeding balls and if the robot is either shooting all or one, or doesn't 
    //have a ball ready for te shooter, activate the tower and tower feeder motors.
    //also run the collector motor at reduced speed it it's up to avoid jams
    if(Constants.towerFeed && (Constants.towerSensor.get()||Constants.shootingAll||Constants.shootingOnce)){
      Constants.feeder.set(ControlMode.PercentOutput,Constants.feederSpeed);
      Constants.towerMotor.set(Constants.towerMotorSpeed);
      if(!Constants.isCollectorArmDown){
        Constants.collectorMotor.set(Constants.collectorSpeed/8);
      }
    }
    else{
      Constants.feeder.set(ControlMode.PercentOutput,0);
      Constants.towerMotor.set(0);
      //reverse the tower if it is full of balls
      if(!Constants.towerSensor.get()){
        Constants.towerFeed = false;
        reverseTower();
      }
    }
  }
  public static void reverseTower(){
    Constants.towerMotor.set(Constants.reverseTowerMotorSpeed);
  }
}
