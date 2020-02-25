package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue 3 Place Foundation", group = "Autonomous")
public class Blue3PlaceFoundation  extends Blue2PlaceFoundation{

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void getThirdStoneLeft(){
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 5, 5, initialAngle);
        log("left drive forward after depositing second");

        straightenOut();
        log("left straighten after strafing after depositing second");

        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 58, 0, initialAngle);
        log("left to other zone third stone with encoders");

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -10, initialAngle);
        log("left next to third stone encoders");

        straightenOut();
        log("left straighten near third stone");

        //drives to distance from both walls
        frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("left third stone adjustment with sensors");

        grabStone();

        raiseArm();
    }

    @Override
    public void depositThirdStoneLeft(){
        //drive right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("left drive right after grabbing third stone");

        straightenOut();
        log("left straighten out after strafe right after third stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -59, -5, initialAngle);
        log("left drove to foundation with third stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void leftPark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -5, initialAngle);
        log("left drive flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 5, initialAngle);
        log("left drive forward and right with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);
        log("left turn to place foundation");

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
        log("left park");
    }


    @Override
    public void getThirdStoneMiddle(){
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 5, 5, initialAngle);
        log("middle drive forward after depositing second");

        straightenOut();
        log("middle straighten after strafing after depositing second");

        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 50, 0, initialAngle);
        log("middle to other zone third stone with encoders");

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -10, initialAngle);
        log("middle next to third stone encoders");

        straightenOut();
        log("middle straighten near third stone");

        //drives to distance from wall
        frontRangeDriveToDistance(LEFT_FIRST_DISTANCE_TO_WALL);

        log("middle third stone adjustment with sensors");

        grabStone();

        raiseArm();
    }

    @Override
    public void depositThirdStoneMiddle(){
        //drive right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("middle drive right after grabbing third stone");

        straightenOut();
        log("middle straighten out after strafe right after third stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -51, -5, initialAngle);
        log("drove to foundation with third stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void middlePark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -5, initialAngle);
        log("middle flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 5, initialAngle);
        log("middle drive forward and right with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);
        log("middle turn to place foundation");

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
        log("middle park");
    }


    @Override
    public void getThirdStoneRight(){
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 5, 5, initialAngle);
        log("right drive forward after depositing second");

        straightenOut();
        log("right straighten after strafing after depositing second");

        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 50, 0, initialAngle);
        log("right to other zone third stone with encoders");

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, -10, initialAngle);
        log("right next to third stone encoders");

        straightenOut();
        log("right straighten near third stone");

        //drives to distance from both walls
        frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("right third stone adjustment with sensors");

        grabStone();

        raiseArm();
    }

    @Override
    public void depositThirdStoneRight(){
        //drive right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("drive right after grabbing third stone");

        straightenOut();
        log("straighten out after strafe right after third stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -51, -5, initialAngle);
        log("drove to foundation with third stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void rightPark(){
        //strafes flush to foundation
        base.drivetrain.gyroEncoderDrive(0.3, 0, -5, initialAngle);
        log("flush to foundation");

        //grabs foundation
        base.foundation.moveLeftServo(1);
        sleep(300);

        //drives forward and right before turning
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 5, initialAngle);
        log("forward and right with foundation");

        //turns to place foundation
        base.drivetrain.gyroTurn(0.1, 1, 90, 4);
        log("turn to place foundation");

        //releases foundation
        base.foundation.moveLeftServo(-1);
        sleep(300);
        base.foundation.moveLeftServo(0);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 40, base.gyro.gyro.getIntegratedZValue());
        log("right park");
    }

}
