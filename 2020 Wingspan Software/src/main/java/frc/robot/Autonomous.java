
package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Add your docs here.
 */
public class Autonomous {
    private double curDistance;
    private double turretTargetAngle;
    private PIDController pidControlTurn = new PIDController(.02, 0, 0);
    private PIDController pidControlDrive = new PIDController(0, 0, 0);
    public void setTargetAngle (double inputTargetAngle) {
        pidControlTurn.setTolerance(.25);
        pidControlTurn.setSetpoint(inputTargetAngle);
    }
    public void setTargetDistance (double inputTargetDistance){
        pidControlDrive.setSetpoint(inputTargetDistance);
        pidControlTurn.setSetpoint(0);
    }
    public boolean driveDistance () {
        Constants.mainDrive.curvatureDrive(pidControlDrive.calculate(curDistance),pidControlTurn.calculate(NavX.getTotalYaw()),true);
        return true;
    }
    public boolean moveToTargetAngle () {
        Constants.mainDrive.curvatureDrive(0, pidControlTurn.calculate(NavX.getTotalYaw()), true);
        return pidControlTurn.atSetpoint();
    }
    public void setTurretTargetAngle (double inputTargetAngle) {
        turretTargetAngle = inputTargetAngle;
    }
    public boolean moveTurretToAngle () {
        return true;
    }
}