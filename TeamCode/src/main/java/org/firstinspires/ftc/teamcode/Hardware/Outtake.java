package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Outtake {
    
    
    public DcMotor leftPulley;
    public DcMotor rightPulley;
    
    public Servo arm;
    
    public final double ARM_MIN_POS = 0;
    public final double ARM_MAX_POS = 180;


    public DistanceSensor pulleySensor;


    
    public void initHardware(HardwareMap aHwMap) {
        
        leftPulley = aHwMap.get(DcMotor.class, "leftPulley");
        rightPulley = aHwMap.get(DcMotor.class, "rightPulley");
        pulleySensor = aHwMap.get(DistanceSensor.class, "pulleySensor");
        
        arm = aHwMap.get(Servo.class, "arm");
        
    }
    
    
    public void setPulleySpeed(double speed) {
        leftPulley.setPower(speed);
        rightPulley.setPower(speed);
        
    }


    public double getPulleySpeed() { return leftPulley.getPower() + rightPulley.getPower(); }



    public double getPulleyHeight() {

        double height = pulleySensor.getDistance(DistanceUnit.INCH);
        return height;

    }

    
    
    public void setArmPos(double deg) { arm.setPosition(deg); }

    public void setArmMin() { arm.setPosition(ARM_MIN_POS); }

    public void setArmMax() { arm.setPosition(ARM_MAX_POS); }

    
    public double getArmPos() { return arm.getPosition(); }
    
    
}
