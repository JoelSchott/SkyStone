package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red 2 Place Foundation")
public class Red2PlaceFoundation extends Red2PlacePark {

    private final static double FOUNDATION_DISTANCE = 27.7;

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void rightPark(){

        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 3.5, initialAngle);
        leftRangeDriveToDistance(FOUNDATION_DISTANCE);
        log("drives flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -13, -13, initialAngle);
        base.drivetrain.gyroEncoderDriveBIGCoefficient(DRIVE_SPEED, 0, -37, initialAngle);
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -5, 0 ,initialAngle);
        straightenOut();

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);


    }

    @Override
    public void middlePark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 3.5, initialAngle);
        leftRangeDriveToDistance(FOUNDATION_DISTANCE);
        log("drives flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -13, -13, initialAngle);
        base.drivetrain.gyroEncoderDriveBIGCoefficient(DRIVE_SPEED, 0, -37, initialAngle);
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -5, 0 ,initialAngle);
        straightenOut();

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);
        straightenOut();



    }

    @Override
    public void leftPark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 3.5, initialAngle);
        leftRangeDriveToDistance(FOUNDATION_DISTANCE);
        log("drives flush to foundation");


        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        straightenOut();

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -13, -13, initialAngle);
        base.drivetrain.gyroEncoderDriveBIGCoefficient(DRIVE_SPEED, 0, -37, initialAngle);
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -5, 0 ,initialAngle);
        straightenOut();

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

    }
}
