package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Teleop {
    
    public void teleopRoutine(Collector collectorClass, Shooter shooterClass, Hang hangClass, Control_Panel cp) {
        Constants.mainDrive.setMaxOutput(1);
        Controls.getButtons();
        // All controls on joystick 0 (The joystick)
        // Drive code
        double forwardSpeed = Constants.joystick0.getRawAxis(1);
        if (Controls.turboButton) {
            forwardSpeed *= -1;
        } else if (Controls.slowButton) {
            forwardSpeed *= Constants.slowSpeed;
        } else {
            forwardSpeed *= Constants.driveSpeed;
        }
        double rotateSpeed = Constants.joystick0.getRawAxis(2);
        rotateSpeed *= Constants.turnSpeed;
        Constants.mainDrive.curvatureDrive(forwardSpeed, rotateSpeed, true);
        // Collection code
        // if the driver wants to move the collector arm, change the position
        if (Controls.collectButton) {
            Constants.isCollectorArmDown = !Constants.isCollectorArmDown;
        }
        // apply the new position or maintain the current position
        collectorClass.moveCollector();
        // All controls on joystick 1 (The controller)
        // Control panel controls (switches between on and off so pressing the button
        // again stops the motor)
        if (Controls.blueButton) {
            Constants.targetColor = Constants.blueTarget;
            Constants.isGoingToColor = !Constants.isGoingToColor;
        } else if (Controls.greenButton) {
            Constants.targetColor = Constants.greenTarget;
            Constants.isGoingToColor = !Constants.isGoingToColor;
        } else if (Controls.redButton) {
            Constants.targetColor = Constants.redTarget;
            Constants.isGoingToColor = !Constants.isGoingToColor;
        } else if (Controls.yellowButton) {
            Constants.targetColor = Constants.yellowTarget;
            Constants.isGoingToColor = !Constants.isGoingToColor;
        }
        if (Controls.spinButton) {
            Constants.isSpinning = !Constants.isSpinning;
        }
        // Checks if either of the methods that use the control panel motor are active,
        // and if not stops the motor
        if (Constants.isGoingToColor == true) {
            cp.spinToColor();
        } else if (Constants.isSpinning == true) {
            cp.spinWheel();
        } else {
            Constants.colorWheelMotor.set(ControlMode.PercentOutput, 0);
            cp.numChanges = 0;
        }
        // Shooter Controls
        if (Controls.shootOnceButton) {
            Constants.shootingOnce = !Constants.shootingOnce;
            Constants.towerFeed = false;
        } else if (Controls.shootAllButton) {
            Constants.shootingAll = !Constants.shootingAll;
            Constants.towerFeed = false;
        }
        if (Constants.shootingOnce) {
            shooterClass.shootOnce();
        } else if (Constants.shootingAll) {
            shooterClass.shootAll();
        } else {
            shooterClass.stopMotors();
        }
        // Feeder and tower controls
        if (Controls.feedButton) {
            Constants.towerFeed = !Constants.towerFeed;
        }
        if (Controls.towerResetButton) {
            Constants.isReversingTower = !Constants.isReversingTower;
            Constants.isReversingTower = true;
            Collector.reverseTower();
        } else {
            Constants.isReversingTower = false;
        }
        if (!Constants.isReversingTower) {
            Collector.towerFeed();
        }
        // turret controls
        shooterClass.rotateTurret();
        // hanging winch stuff
        if (Controls.winchButton) {
            hangClass.moveWinch();
        } else {
            Constants.winchMotor.set(0);
        }
        hangClass.moveArm();
        shooterClass.moveServos();
        if (Controls.colorArmButton) {
            cp.stopColorArm();
            Constants.movingColorArm = true;
            Constants.colorArmPosition = !Constants.colorArmPosition;
        }
        if (Constants.movingColorArm) {
            cp.moveColorArm();
        } else {
            cp.stopColorArm();
        }
        if (Controls.armOutButton) {
            hangClass.extendArm();
        } else if (Controls.armInButton) {
            hangClass.retractArm();
        } else {
            Constants.armExtender.set(ControlMode.PercentOutput, 0);
        }
    }
}