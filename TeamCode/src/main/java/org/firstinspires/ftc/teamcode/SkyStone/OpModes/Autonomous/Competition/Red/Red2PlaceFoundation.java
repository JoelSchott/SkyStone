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
        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -5, -5, initialAngle);
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, 0, -35, initialAngle);
        straightenOut();

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        base.arms.shutRightClamp();
        //parks

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 40, 0, base.gyro.gyro.getIntegratedZValue());
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

        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -5, -5, initialAngle);
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, 0, -35, initialAngle);
        straightenOut();

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 40, 0, base.gyro.gyro.getIntegratedZValue());
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

        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -5, -5, initialAngle);
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, 0, -35, initialAngle);        straightenOut();

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 40, 0, base.gyro.gyro.getIntegratedZValue());
        log("parks");

    }
}
