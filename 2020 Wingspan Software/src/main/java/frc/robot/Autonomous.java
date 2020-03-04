
package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private String m_autoSelected;
    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    // Autonomous data
    private int autoStage = 0;
    private double secondsDelay = 0;
    int cyclesOnTarget = 0;

    // Autonomous selection options
    private static final String kDefaultAuto = "Default";
    private static final String leftAuto = "Left Auto";
    private static final String middleAuto = "Middle Auto";
    private static final String rightAuto = "Right Auto";

    public void autoOptions() {
        // get auto mode from smartdashboard
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("Left Auto", leftAuto);
        m_chooser.addOption("Middle Auto", middleAuto);
        m_chooser.addOption("Right Auto", rightAuto);
        SmartDashboard.putData("Auto choices", m_chooser);
        SmartDashboard.putNumber("Autonomous delay", 0);
    }

    public void autoInit() {
        // Resets navx angle
        Constants.ahrs.reset();
        // more wpi lib confusing stuff
        m_autoSelected = m_chooser.getSelected();
        System.out.println("Auto selected: " + m_autoSelected);
        autoStage = 0;
        Constants.servoPosition = 1;
        Constants.mainDrive.setMaxOutput(Constants.autoMaxDriveSpeed);
    }

    public void autoRoutine(Shooter shooterClass, Collector collectorClass) {
        // In the first iteration it gets the delay in seconds before starting auto
        if (autoStage == 0) {
            secondsDelay = SmartDashboard.getNumber("Autonomous delay", 0);
            autoStage++;
        }
        // Checks to see which auto mode is selected(default(nothing), left, middle,
        // right)
        if (m_autoSelected.equals(kDefaultAuto)) {
            //do nothing
        } else if (m_autoSelected.equals(leftAuto)) {
            // If in autoStage 1 waits for secondsDelay seconds
            if (autoStage == 1) {
                if (secondsDelay > 0) {
                    secondsDelay -= Constants.autoTimerDecrement;
                } else {
                    autoStage++;
                }
            }
            // If in autoStage 2 sets turret target angle
            else if (autoStage == 2) {
                setTurretTargetAngle(Constants.autoLeftStage2Angle);
                autoStage++;
            }
            // If in autoStage 3 moves turret to target angle
            else if (autoStage == 3) {
                if (moveTurretToAngle()) {
                    autoStage++;
                }
            }
            // If in autoStage 4 shoots all balls
            else if (autoStage == 4) {
                Constants.shootingAll = true;
                shooterClass.shootAll();
                if (!Constants.shootingAll) {
                    shooterClass.stopMotors();
                    autoStage++;

                    Constants.servoPosition = 0;
                }
            }
            // If in autoStage 5 sets target distance for robot to drive
            else if (autoStage == 5) {
                setTargetDistance(Constants.autoLeftStage5Angle);
                autoStage++;
            }
            // If in autoStage 6 drives robot off line
            else if (autoStage == 6) {
                if (driveDistance()) {
                    autoStage++;
                }
            }
        } else if (m_autoSelected.equals(middleAuto)) {
            // If in autoStage 1 waits for secondsDelay seconds
            if (autoStage == 1) {
                if (secondsDelay > 0) {
                    secondsDelay -= Constants.autoTimerDecrement;
                } else {
                    autoStage++;
                }
            }
            // If in autoStage 2 sets turret target angle
            else if (autoStage == 2) {
                setTurretTargetAngle(Constants.autoMiddleStage2Angle);
                autoStage++;
            }
            // If in autoStage 3 moves turret to target angle
            else if (autoStage == 3) {
                if (moveTurretToAngle()) {
                    autoStage++;
                }
            }
            // If in autoStage 4 shoots all balls
            else if (autoStage == 4) {
                Constants.shootingAll = true;
                shooterClass.shootAll();
                if (!Constants.shootingAll) {
                    shooterClass.stopMotors();
                    autoStage++;
                    Constants.servoPosition = 0;
                }
            }
            // If in autoStage 5 sets target distance for robot to drive
            else if (autoStage == 5) {
                setTargetDistance(Constants.autoMiddleStage5Angle);
                autoStage++;
            }
            // If in autoStage 6 drives robot off line
            else if (autoStage == 6) {
                if (driveDistance()) {
                    autoStage++;
                }
            }
        } else if (m_autoSelected.equals(rightAuto)) {
            // If in autoStage 1 waits for secondsDelay seconds
            if (autoStage == 1) {
                if (secondsDelay > 0) {
                    secondsDelay -= Constants.autoTimerDecrement;
                } else {
                    autoStage++;
                }
            }
            // If in autoStage 2 sets turret target angle
            else if (autoStage == 2) {
                setTurretTargetAngle(Constants.autoRightStage2Angle);
                autoStage++;
            }
            // If in autoStage 3 moves turret to target angle
            else if (autoStage == 3) {
                if (moveTurretToAngle()) {
                    autoStage++;
                }
            }
            // If in autoStage 4 shoots all balls
            else if (autoStage == 4) {
                Constants.shootingAll = true;
                shooterClass.shootAll();
                if (!Constants.shootingAll) {
                    shooterClass.stopMotors();
                    autoStage++;
                    Constants.servoPosition = 0;
                }
            }
            // If in autoStage 5 sets target distance for robot to drive
            else if (autoStage == 5) {
                setTargetDistance(Constants.autoRightStage5Angle);
                autoStage++;
            }
            // If in autoStage 6 drives robot off line
            else if (autoStage == 6) {
                if (driveDistance()) {
                    autoStage++;
                }
            }
        }
        collectorClass.moveCollector();
        shooterClass.moveServos();
        System.out.println("Stage " + autoStage);
        System.out.println("Left " + Constants.leftEncoder.getDistance());
        System.out.println("Right " + Constants.rightEncoder.getDistance());
    }

    // Sets angle for robot to go to
    public void setTargetAngle(int inputTargetAngle) {
        pidControlTurn.setTolerance(Constants.autoTurnPIDTolerance);
        pidControlTurn.setSetpoint(inputTargetAngle);
    }

    // Sets distance for robot to go to
    public void setTargetDistance(double inputTargetDistance) {
        pidControlDrive.setSetpoint(inputTargetDistance);
        pidControlTurn.setSetpoint(0);
        curDistance = 0;
        prevLeftEncoder = Constants.leftEncoder.getDistance();
        prevRightEncoder = Constants.rightEncoder.getDistance();
    }

    // Goes to preset distance
    public boolean driveDistance() {
        double curLeftEncoder = Constants.leftEncoder.getDistance();
        double curRightEncoder = Constants.rightEncoder.getDistance();
        curDistance += (curLeftEncoder - prevLeftEncoder + curRightEncoder - prevRightEncoder) / 2 * 18.75;
        prevLeftEncoder = curLeftEncoder;
        prevRightEncoder = curRightEncoder;
        Constants.mainDrive.curvatureDrive(pidControlDrive.calculate(curDistance), 0, false);
        System.out.println("Distance" + curDistance);
        if (cyclesOnTarget < 3) {
            if (pidControlDrive.atSetpoint()) {
                cyclesOnTarget++;
            } else {
                cyclesOnTarget = 0;
            }
        } else {
            cyclesOnTarget = 0;
            return true;
        }
        return false;
    }

    // Moves robot to set angle
    public boolean moveToTargetAngle() {
        Constants.mainDrive.curvatureDrive(0, pidControlTurn.calculate(NavX.getTotalYaw()), true);
        if (cyclesOnTarget < 3) {
            if (pidControlTurn.atSetpoint()) {
                cyclesOnTarget++;
            } else {
                cyclesOnTarget = 0;
            }
        } else {
            cyclesOnTarget = 0;
            return true;
        }
        return false;
    }

    // Sets angle for turret to go to
    public void setTurretTargetAngle(int inputTargetAngle) {
        turretTargetAngle = inputTargetAngle;
    }

    // Moves turret to target angle
    public boolean moveTurretToAngle() {
        if (cyclesOnTarget < 3) {
            if (Shooter.rotateTurret(turretTargetAngle)) {
                cyclesOnTarget++;
            } else {
                cyclesOnTarget = 0;
            }
        } else {
            cyclesOnTarget = 0;
            return true;
        }
        return false;
    }

    public Autonomous() {
    }
}