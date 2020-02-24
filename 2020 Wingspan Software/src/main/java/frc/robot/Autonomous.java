
package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;

/**
 * Add your docs here.
 */
public class Autonomous {
    private double curDistance;
    private double prevLeftEncoder;
    private double prevRightEncoder;
    private int turretTargetAngle;
    private PIDController pidControlTurn = new PIDController(.03, 0.01, 0);
    private PIDController pidControlDrive = new PIDController(.03, 0, 0);
    int cyclesOnTarget = 0;
    //Sets angle for robot to go to
    public void setTargetAngle (int inputTargetAngle) {
        pidControlTurn.setTolerance(2);
        pidControlTurn.setSetpoint(inputTargetAngle);
    }
    //Sets distance for robot to go to
    public void setTargetDistance (double inputTargetDistance){
        pidControlDrive.setSetpoint(inputTargetDistance);
        pidControlTurn.setSetpoint(0);
        curDistance = 0;
        prevLeftEncoder = Constants.leftEncoder.getDistance();
        prevRightEncoder = Constants.rightEncoder.getDistance();
    }
    //Goes to preset distance
    public boolean driveDistance () {
        double curLeftEncoder=Constants.leftEncoder.getDistance();
        double curRightEncoder=Constants.rightEncoder.getDistance();
        curDistance+=(curLeftEncoder-prevLeftEncoder+curRightEncoder-prevRightEncoder)/2*18.75;
        prevLeftEncoder=curLeftEncoder;
        prevRightEncoder=curRightEncoder;
        Constants.mainDrive.curvatureDrive(pidControlDrive.calculate(curDistance),0,true);
        System.out.println("Distance"+curDistance);
        return pidControlDrive.atSetpoint();
    }
    //Moves robot to set angle
    public boolean moveToTargetAngle () {
        Constants.mainDrive.curvatureDrive(0, pidControlTurn.calculate(NavX.getTotalYaw()), true);
        return pidControlTurn.atSetpoint();
    }
    //Sets angle for turret to go to
    public void setTurretTargetAngle (int inputTargetAngle) {
        turretTargetAngle = inputTargetAngle;
    }
    //Moves turret to target angle
    public boolean moveTurretToAngle () {
        if(cyclesOnTarget<3){
            if(Shooter.rotateTurret(turretTargetAngle)){
                cyclesOnTarget++;
            }
            else{
                cyclesOnTarget=0;
            }
        }
        else{
            return true;
        }
        return false;
    }
    public Autonomous(){
    }
}