package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue 2 Place Foundation", group = "Autonomous")
public class Blue2PlaceFoundation extends Blue2PlacePark {

    @Override
    public void runOpMode(){
        super.runOpMode();
    }


    @Override
    public void leftPark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -1.5, initialAngle);
        log("left drive flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 18, initialAngle);
        log("left drive forward and right with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.3, 0.5, 90, 4);
        log("left turn to place foundation");

        //strafe into wall
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -5, base.gyro.gyro.getIntegratedZValue());

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
        log("left park");
    }

    @Override
    public void middlePark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -1.5, initialAngle);
        log("middle drive flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 18, initialAngle);
        log("middle drive forward and right with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.3, 0.5, 90, 4);
        log("middle turn to place foundation");

        //strafe into wall
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -5, base.gyro.gyro.getIntegratedZValue());

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
        log("middle park");
    }

    @Override
    public void rightPark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -1.5, initialAngle);
        log("right drive flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 5, 18, initialAngle);
        log("right drive forward and right");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.3, 0.5, 90, 4);
        log("right turn to place foundation");

        //strafe into wall
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -5, base.gyro.gyro.getIntegratedZValue());

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
        log("right park");
    }
}
