package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red 3 Place Foundation")
public class Red3PlaceFoundation extends Red2PlaceFoundation{

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void getThirdStoneRight(){
        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  5, -5, initialAngle);
        log("drive left after depositing second stone");

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  45, 0, initialAngle);
        log("drives forward after depositing second stone");

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  10, 10, initialAngle);
        log("drives next to third stone encoders");

        straightenOut();
        log("straightens out after near third stone encoders");

        //drives to distance from both walls
        frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment for third stone");

        grabStone();

        raiseArm();
    }

    @Override
    public void depositThirdStoneRight(){
        straightenOut();

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drive left after grabbing third stone");

        straightenOut();
        log("straighten out after driving left after collecting third stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -68,5, initialAngle);
        log("drive to foundation");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void rightPark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 5, initialAngle);
        log("drive flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -5, initialAngle);
        log("drive forward and left with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);
        log("turn to place foundation");

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, initialAngle);
        log("parks");
    }

    @Override
    public void getThirdStoneMiddle(){
        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  5, -5, initialAngle);
        log("drive left after depositing second stone");

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  38, 0, initialAngle);
        log("drives forward after depositing second stone");

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  10, 10, initialAngle);
        log("drives next to third stone encoders");

        straightenOut();
        log("straightens out after near third stone encoders");

        //drives to distance from both walls
        frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment for third stone");

        grabStone();

        raiseArm();
    }

    @Override
    public void depositThirdStoneMiddle(){
        straightenOut();

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drive left after grabbing third stone");

        straightenOut();
        log("straighten out after driving left after collecting third stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -60,5, initialAngle);
        log("drive to foundation");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void middlePark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 5, initialAngle);
        log("drive flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -5, initialAngle);
        log("drive forward and left with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);
        log("turn to place foundation");

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, initialAngle);
        log("parks");
    }

    @Override
    public void getThirdStoneLeft(){
        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  5, -5, initialAngle);
        log("drive left after depositing second stone");

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  38, 0, initialAngle);
        log("drives forward after depositing second stone");

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  10, 10, initialAngle);
        log("drives next to third stone encoders");

        straightenOut();
        log("straightens out after near third stone encoders");

        //drives to distance from both walls
        frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment for third stone");

        grabStone();

        raiseArm();
    }

    @Override
    public void depositThirdStoneLeft(){
        straightenOut();

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drive left after grabbing third stone");

        straightenOut();
        log("straighten out after driving left after collecting third stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -60,5, initialAngle);
        log("drive to foundation");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void leftPark(){
        //strafes into foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, 5, initialAngle);
        log("drive flush to foundation");

        //grabs foundation
        base.foundation.moveRightServo(-1);
        sleep(300);

        //strafes left and forward before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -5, initialAngle);
        log("drive forward and left with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);
        log("turn to place foundation");

        //releases foundation
        base.foundation.moveRightServo(1);
        sleep(300);
        base.foundation.moveRightServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -30, initialAngle);
        log("parks");
    }


}
