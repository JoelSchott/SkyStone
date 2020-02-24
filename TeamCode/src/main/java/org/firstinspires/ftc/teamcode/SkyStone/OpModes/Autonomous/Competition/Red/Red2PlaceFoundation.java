package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red 2 Place Foundation")
public class Red2PlaceFoundation extends Red2PlacePark {

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void rightPark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 5, initialAngle);

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -5, initialAngle);

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, initialAngle);
    }

    @Override
    public void middlePark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 5, initialAngle);

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -5, initialAngle);

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, initialAngle);
    }

    @Override
    public void leftPark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 5, initialAngle);

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -5, initialAngle);

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, initialAngle);
    }
}
