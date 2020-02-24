package frc.robot;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Telemetry {
    
    public Telemetry() {

    }

    public void debugGeneric(String msg) {
        //print a timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        System.out.println("==========" + dtf.format(now) + "==========");
        //print the message
        System.out.println("Message: " + msg);
        //print an ending line
        System.out.println("=======================================");
    }

    public void debugEncoders(String msg, Collector collectorClass) {
        //print a timestamp
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        System.out.println("==========" + dtf.format(now) + "==========");
        //print the message
        System.out.println("Message: " + msg);
        //print the debug information
        System.out.println(Constants.targetRevsCollectorArm);
        //Constants.collectorLift.set(joystick1.getRawAxis(1)*.7);
        System.out.println("Velocity"+Constants.shooterMotor.getEncoder().getVelocity());
        System.out.println("leftDist"+Constants.leftEncoder.getDistance());
        System.out.println("rightDist"+Constants.rightEncoder.getDistance());
        //print an ending line
        System.out.println("=======================================");
    }

    public void sendDashboardData() {
        //navx debug
        SmartDashboard.putNumber("Total Yaw", NavX.getTotalYaw());
        SmartDashboard.putNumber("Current Yaw Rate", NavX.getYawRate());
    }
}