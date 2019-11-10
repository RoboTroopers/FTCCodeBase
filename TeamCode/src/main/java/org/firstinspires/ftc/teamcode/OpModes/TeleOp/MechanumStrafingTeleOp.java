package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.OdometrySystem.OdometryThread;

import static org.firstinspires.ftc.teamcode.Utilities.ControllerFunctions.getJoystickAngleRad;
import static org.firstinspires.ftc.teamcode.ppProject.RobotUtilities.MovementVars.movement_turn;
import static org.firstinspires.ftc.teamcode.ppProject.RobotUtilities.MovementVars.movement_x;
import static org.firstinspires.ftc.teamcode.ppProject.RobotUtilities.MovementVars.movement_y;


@TeleOp(name = "Mechanum Strafing TeleOp")
public class MechanumStrafingTeleOp extends OpMode {
    
    private Robot robot = new Robot();
    
    private double threshold = 0.01;
    
    
    @Override
    public void init() {
        robot.initHardware(hardwareMap);
        Thread odometryThread = new Thread(new OdometryThread(robot));
        odometryThread.start();
        
    }
    
    
    // run until the end of the match (driver presses STOP)
    @Override
    public void loop() {
        
        // Control over horizontal and vertical movement amounts with one joystick, and turn amount using the other.
        if (Math.abs(gamepad1.left_stick_x) > threshold) {
            movement_x = gamepad1.left_stick_x;
        }
        
        if (Math.abs(gamepad1.left_stick_y) > threshold) {
            movement_y = gamepad1.left_stick_y;
        }
        
        if ((gamepad1.right_stick_x) > threshold || Math.abs(gamepad1.right_stick_y) > threshold) {
            movement_turn = getJoystickAngleRad(gamepad1.right_stick_x, gamepad1.left_stick_y);
        }
        
        robot.driveTrain.applyMovement(movement_x, movement_y, movement_turn);
        
        
        if (gamepad1.right_trigger >= 0.5) {
            
            robot.intake.leftIntake.setPower(1);
            robot.intake.rightIntake.setPower(1);
            
        } else if (gamepad1.left_trigger >= 0.5) {
            
            robot.intake.leftIntake.setPower(-1);
            robot.intake.rightIntake.setPower(-1);
            
        } else {
            robot.intake.leftIntake.setPower(0);
            robot.intake.rightIntake.setPower(0);
        }
        
        
        if (gamepad1.right_bumper) {
            robot.accessories.extend();
        } else if (gamepad1.left_bumper) {
            robot.accessories.retract();
        }
        
        
        telemetry.addData("Status", "Running");
        telemetry.addData("front left power", robot.driveTrain.leftFront.getPower());
        telemetry.addData("front right power", robot.driveTrain.rightFront.getPower());
        telemetry.addData("back left power", robot.driveTrain.leftRear.getPower());
        telemetry.addData("back right power", robot.driveTrain.rightRear.getPower());
        telemetry.addData("left intake power", robot.intake.leftIntake.getPower());
        telemetry.addData("right intake power", robot.intake.rightIntake.getPower());
        telemetry.addData("pinger position", robot.accessories.pinger.getPosition());
        telemetry.update();
        
    }
}

