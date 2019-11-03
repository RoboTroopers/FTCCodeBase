package org.firstinspires.ftc.teamcode.neilppProject.treamcode;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.neilppProject.treamcode.RobotMovement.followCurve;


public class MyOpMode extends OpMode {

    @Override
    public void init() {
    }

    @Override
    public void loop() {
       ArrayList<CurvePoint> allPoints = new ArrayList<>();
        allPoints.add(new CurvePoint(180,180,1.0,1,20,Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint(220,180,1,1,20, Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint(280,100,1,1,20, Math.toRadians(50),1.0));
        allPoints.add(new CurvePoint(280,0,1,1,20, Math.toRadians(50),1.0));
        followCurve(allPoints, Math.toRadians(90));

      // RobotMovement.goToPosition(358/2,358/2,0.5,Math.toRadians(90),0.3);

    }
}
