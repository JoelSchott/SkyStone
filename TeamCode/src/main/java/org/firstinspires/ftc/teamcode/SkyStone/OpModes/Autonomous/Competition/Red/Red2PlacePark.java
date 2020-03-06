package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red 2 Place Park")
public class Red2PlacePark extends Red1Place1Deliver{

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void depositSecondStoneRight(){
        straightenOut();

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drive left after grabbing first stone");

        straightenOut();
        log("straighten out after driving left after collecting first stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, -92,10, initialAngle);
        log("drive to foundation");

        lowerArmSleep();

        releaseStone();

        raiseArm();
    }

    @Override
    public void rightPark(){
        //drives left
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 7, -7, initialAngle);
        log("drive forward and left to park");

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 36, 0, initialAngle);
        log("drives forward to park");
    }

    @Override
    public void depositSecondStoneMiddle(){
        straightenOut();

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drive left after grabbing first stone");

        straightenOut();
        log("straighten out after driving left after collecting first stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, -98,11.5, initialAngle);
        log("drive to foundation");

        lowerArmSleep();

        releaseStone();

        raiseArm();
    }

    @Override
    public void middlePark(){
        //drives left
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 7, -7, initialAngle);
        log("drive forward and left to park");

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 36, 0, initialAngle);
        log("drives forward to park");
    }

    @Override
    public void depositSecondStoneLeft(){
        straightenOut();

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drive left after grabbing first stone");

        straightenOut();
        log("straighten out after driving left after collecting first stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDriveCoefficient(DRIVE_SPEED, -104,10.5, initialAngle);
        log("drive to foundation");

        lowerArmSleep();

        releaseStone();

        raiseArm();
    }

    @Override
    public void leftPark(){
        //drives left
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 7, -7, initialAngle);
        log("drives forward and left to park");

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 36, 0, initialAngle);
        log("drives forward to park");
    }

}
