package frc.robot;

import com.kauailabs.navx.frc.AHRS;

public class NavX {
    private static AHRS navx = Constants.ahrs;
    public NavX() {
        
    }

    public static double getTotalYaw() {
        return navx.getAngle();
    }

    public static double getYawRate() {
        return navx.getRate();
    }

}