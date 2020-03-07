/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {

    private double x;
    private double y;
    private double area;
    private double distance;

    public void limelightPeriodic() {
        //read values periodically
        x = Constants.limelighttx.getDouble(0.0);
        y = Constants.limelightty.getDouble(0.0);
        area = Constants.limelightta.getDouble(0.0);
        distance = (10-47) / Math.tan(y * Math.PI/180);

        //post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", x);
        SmartDashboard.putNumber("LimelightY", y);
        SmartDashboard.putNumber("LimelightArea", area);
        SmartDashboard.putNumber("LimelightDistance", distance);
    }

    public double getDistanceFromTarget() {
        return distance;
    }

    public double getAngleXFromTarget() {
        return x;
    }

    public double getAngleYFromTarget() {
        return y;
    }
}
