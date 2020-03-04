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
        base.drivetrain.gyroEncoderDrive(0.3, 0, 1.5, initialAngle);
        log("drives flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, -18, initialAngle);
        log("drives forward and left with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.3, 0.4, 90, 4);
        log("turns to place foundation");

        //strafe into wall
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 5, base.gyro.gyro.getIntegratedZValue());

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -6, -6, base.gyro.gyro.getIntegratedZValue());

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, base.gyro.gyro.getIntegratedZValue());
        log("parks");
    }

    @Override
    public void middlePark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 1.5, initialAngle);
        log("drives flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, -18, initialAngle);
        log("drives forward and left with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.3, 0.4, 90, 4);
        log("turns to place foundation");

        //strafe into wall
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 5, base.gyro.gyro.getIntegratedZValue());

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, base.gyro.gyro.getIntegratedZValue());
        log("parks");

    }

    @Override
    public void leftPark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 1.5, initialAngle);
        log("drives flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, -18, initialAngle);
        log("drives forward and left with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.3, 0.4, 90, 4);
        log("turns to place foundation");

        //strafe into wall
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 5, base.gyro.gyro.getIntegratedZValue());

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, base.gyro.gyro.getIntegratedZValue());
        log("parks");

    }
}
