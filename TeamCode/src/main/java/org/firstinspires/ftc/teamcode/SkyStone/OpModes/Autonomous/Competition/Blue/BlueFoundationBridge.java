package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.SkyStone.MainBase;

import java.util.List;

@Autonomous(name = "Blue Foundation Bridge", group = "Autonomous")
public class BlueFoundationBridge extends LinearOpMode {
    private MainBase base;

    private List<Recognition> stones;

    private final static double DRIVE_SPEED = 1.0;
    private final static double MINIMUM_TURN_SPEED = 0.1;


    @Override
    public void runOpMode(){

        base = new MainBase(hardwareMap,telemetry,this);
        base.init();
        base.drivetrain.setInitalAngle(0);

        double initialAngle = base.gyro.gyro.getIntegratedZValue();

        waitForStart();

        //strafe forward and right to get in front of foundation
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 15, initialAngle);

        straightenOut();

        //strafes right to foundation
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 8, initialAngle);

        straightenOut();

        //slowly strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 8, initialAngle);

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(750);


        //strafes back and left
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -15, -5, initialAngle);

        //turns with foundation
        base.drivetrain.gyroTurn(0.1, 0.5, 90, 5);

        //places foundation in corner
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -2, 20, base.gyro.gyro.getIntegratedZValue());

        //lets go of foundation
        base.foundation.moveRightServo(1);
        sleep(500);
        base.foundation.moveRightServo(0);

        //drives forward for parking
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 8, 0, base.gyro.gyro.getIntegratedZValue());

        //drives left for parking near bridge
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -3, -37, base.gyro.gyro.getIntegratedZValue());

    }

    private void straightenOut(){
        base.drivetrain.gyroTurn(0.25, 0.4, 0, 0.45);
        telemetry.addData("Angle is ", base.gyro.heading());
        telemetry.update();
    }
}