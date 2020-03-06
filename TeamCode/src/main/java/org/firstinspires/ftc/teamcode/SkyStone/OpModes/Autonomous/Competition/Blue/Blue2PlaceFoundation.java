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
        base.foundation.moveLeftServo(-1);
        sleep(300);

        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, 0, 43, initialAngle);
        straightenOut();

        log("left drive forward and right with foundation");



        //releases foundation
        base.foundation.moveLeftServo(1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 50, 0, base.gyro.gyro.getIntegratedZValue());
        log("left park");
    }

    @Override
    public void middlePark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -1.5, initialAngle);
        log("middle drive flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);

        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, 0, 43, initialAngle);
        straightenOut();
        log("middle drive forward and right with foundation");



        //releases foundation
        base.foundation.moveLeftServo(1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 50, 0, base.gyro.gyro.getIntegratedZValue());
        log("middle park");
    }

    @Override
    public void rightPark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -1.5, initialAngle);
        log("right drive flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);

        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, 0, 43, initialAngle);
        straightenOut();
        log("right drive forward and right");

        //releases foundation
        base.foundation.moveLeftServo(1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 50, 0, base.gyro.gyro.getIntegratedZValue());
        log("right park");
    }
}
