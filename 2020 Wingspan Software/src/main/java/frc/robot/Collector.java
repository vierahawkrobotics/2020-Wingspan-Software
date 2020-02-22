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
public class Collector {
  //private int maxNumBalls = 5;

  private Pot potClass = new Pot(Constants.collectorUpVolts,Constants.collectorDownVolts);
  public void moveCollector() {
    //move the collector arm to the requested position
    //if going down, use a PID-like system that slows down as it approces the bottom
    //if going up, move motor untill you reach the position, speed is static
    if (Constants.isCollectorArmDown) {
      //if the abs of the pot voltage exeeds the maximum speed for going up, set the speed to the max speed (maintaining direction of motor) 
      if (Math.abs(potClass.getPercentage()) >= Constants.collectorArmSpeedDown) {
        Constants.collectorLift.set(Constants.collectorArmSpeedDown * (potClass.getPercentage() / Math.abs(potClass.getPercentage())));
      }
      else {
        Constants.collectorLift.set(Constants.collectorArmSpeedDown / Math.abs(Constants.collectorArmSpeedDown) * potClass.getPercentage());
      }
      Constants.collectorMotor.set(Constants.collectorWheelSpeed);
    }
    else {
      //if going up, do not use PID and instead just run motor until at desired position
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
    if(Constants.towerFeed){
      Constants.feeder.set(ControlMode.PercentOutput,Constants.feederSpeed);
      Constants.towerMotor.set(.6);
    }
    else{
      Constants.feeder.set(ControlMode.PercentOutput,0);
      Constants.towerMotor.set(0);
    }
  }
}
