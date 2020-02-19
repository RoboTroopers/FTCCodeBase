package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Utilities.GamerMath;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit.INCH;
import static org.firstinspires.ftc.teamcode.Utilities.MiscUtil.pause;

public class Outtake implements HardwareComponent{


    public DcMotor leftPulley;
    public DcMotor rightPulley;

    public CRServo leftElbow;
    public CRServo rightElbow;


    public Servo claw;

    public final double CLAW_OPEN_POS = 0.65;
    public final double CLAW_CLOSED_POS = 0.95;


    public DistanceSensor heightSensor;

    public final double HEIGHT_MAX = 2;
    public final double HEIGHT_MID = 5;
    public final double HEIGHT_MIN = 10;



    public void init(HardwareMap aHwMap) {
        leftPulley = aHwMap.get(DcMotor.class, "leftPulley");
        rightPulley = aHwMap.get(DcMotor.class, "rightPulley");

        leftElbow = aHwMap.get(CRServo.class, "leftElbow");
        rightElbow = aHwMap.get(CRServo.class, "rightElbow");

        claw = aHwMap.get(Servo.class, "claw");
        heightSensor = aHwMap.get(DistanceSensor.class, "heightSensor");

    }


    public void setPulleySpeed(double speed) {
        leftPulley.setPower(speed);
        rightPulley.setPower(speed);
    }


    public void stopPulley() {
        setPulleySpeed(0);
    }

    public double getPulleySpeed() {
        return (leftPulley.getPower() + rightPulley.getPower())/2;
    }



    // Set the arm to certain positions and set the wrist position to compensate, keeping the claw parallel to the ground
    public void zombieArms() {
        double speed = 0.5;
        leftElbow.setPower(speed);
        rightElbow.setPower(speed);

        pause(500);

        leftElbow.setPower(0);
        rightElbow.setPower(0);
    }


    public void submit() {
        double speed = 0.5;
        leftElbow.setPower(speed);
        rightElbow.setPower(speed);

        pause(500);

        leftElbow.setPower(0);
        rightElbow.setPower(0);
    }


    public double getElbow() {
        return (leftElbow.getPower()+rightElbow.getPower())/2;
    }



    public void closeClaw() {
        claw.setPosition(CLAW_CLOSED_POS);
    }

    public void openClaw() {
        claw.setPosition(CLAW_OPEN_POS);
    }

    public double getClawPos() {
        return claw.getPosition();
    }


    public double getHeight() {
        return heightSensor.getDistance(INCH);
    }


    public void liftToHeight(double height, double maxSpeed) {
        final double initialError = height-getHeight();
        final double minSpeed = 0.075;
        final double acceptableRange = 0.5;

        double error = initialError;

        while (abs(error) < acceptableRange) {
            error = height-getHeight();
            double errorRatio = error/initialError;
            // Set speed proportional to error if error is between minSpeed and maxSpeed.
            setPulleySpeed(GamerMath.clamp(errorRatio, maxSpeed, minSpeed));
        }

        stopPulley();
    }



    public void liftToMax() {
        liftToHeight(HEIGHT_MAX, 0.1);
    }

    public void resetToMid() {
        liftToHeight(HEIGHT_MID, 0.1);
    }

    public void lowerToMax() {
        liftToHeight(HEIGHT_MIN, 0.1);
    }



    public void autoLift() {
        resetToMid();
        closeClaw();
        pause(1000);
        liftToMax();
        zombieArms();
    }


    public void autoDeposit() {
        lowerToMax();
        openClaw();
        pause(1000);
        liftToMax();
        submit();
        pause(500);
        resetToMid();
    }


    public void fullAutoLiftDeposit() {
        autoLift();
        autoDeposit();
    }


/*
    public void liftStone() {
        armMid();

        pause(1500);

        armIn();
        closeClaw();
        pause(1500);
        armOut();
    }

    public void depositStone() {
        liftStone();

        openClaw();
        pause(1000);
        armMid();
    }*/




}
