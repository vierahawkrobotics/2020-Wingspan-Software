package frc.robot;



public class Pot { 
    private double minVolts;
    private double maxVolts;

    public Pot(double curMinVolts, double curMaxVolts) {
        minVolts = curMinVolts;
        maxVolts = curMaxVolts;

    }
    //take the max position pot voltage and compare it to the current voltage to get a percentage
    //100% = have reached target, <100% = have not reached target
    public double getPercentage() {
        System.out.println(Constants.potCollectorArm.getAverageVoltage());
        if (Constants.potCollectorArm.getAverageVoltage() == 0) {
            return 0;
        }
        else {
            return (Constants.potCollectorArm.getAverageVoltage() - maxVolts) / Constants.potCollectorArm.getAverageVoltage();
        }
        
        
    }
    //take the min position pot voltage and compare it to the current voltage to get a percentage
    //100% = have reached target, <100% = have not reached target
    public double getReversePercentage() {
        System.out.println(Constants.potCollectorArm.getAverageVoltage());
        if (Constants.potCollectorArm.getAverageVoltage() == 0) {
            return 0;
        }
        else {
            return (Constants.potCollectorArm.getAverageVoltage() - minVolts) / Constants.potCollectorArm.getAverageVoltage();
        }
        
        
    }
    //get the current voltage from the pot
    public double getRawVolts() {
        return Constants.potCollectorArm.getAverageVoltage();
    }
    
    
}
