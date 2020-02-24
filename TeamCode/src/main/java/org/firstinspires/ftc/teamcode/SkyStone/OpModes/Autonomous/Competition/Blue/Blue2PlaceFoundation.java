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
        base.drivetrain.gyroEncoderDrive(0.3, 0, -5, initialAngle);

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 5, initialAngle);

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
    }

    @Override
    public void middlePark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -5, initialAngle);

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 5, initialAngle);

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
    }

    @Override
    public void rightPark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -5, initialAngle);

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 5, initialAngle);

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
    }
}
