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
public class Collector {
  public int numBalls;
  private int maxNumBalls = 5;
  public void collectBalls() {
    Constants.collectorMotor.set(Constants.collectorSpeed);
  }
  public void moveCollector(double target) {
    if (target - .025 < Constants.collectorEncoder.getDistance()
        && Constants.collectorEncoder.getDistance() < target + .025) {
      Constants.collectorLift.set(0);
    } 
    else if (target > Constants.collectorEncoder.getDistance()) {
      Constants.collectorLift.set(.25);
    } 
    else {
      Constants.collectorLift.set(-.6);
    }
  }
}
