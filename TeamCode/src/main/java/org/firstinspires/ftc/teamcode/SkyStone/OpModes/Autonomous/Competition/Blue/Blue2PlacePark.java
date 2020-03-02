package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Blue 2 Place Park", group = "Autonomous")
public class Blue2PlacePark extends Blue1Place1Deliver{

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void depositSecondStoneLeft(){
        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("left strafe after grabbing second stone");

        straightenOut();
        log("left straighten after strafe after grabbing second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -93, -5, initialAngle);
        log("left depositing second stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void leftPark(){
        //drive forward and right
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 7, 7, initialAngle);
        log("left drive forward and right to park");

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 45, 0, initialAngle);
        log("left park by driving forward");
    }

    @Override
    public void depositSecondStoneMiddle(){
        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("middle strafe after grabbing second stone");

        straightenOut();
        log("middle straighten after strafe after grabbing second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -81, -5, initialAngle);
        log("middle depositing second stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void middlePark() {
        //drive forward and right
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 10, initialAngle);
        log("middle drive forward and right to park");

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 25, 0, initialAngle);
        log("middle drive forward to park");
    }

    @Override
    public void depositSecondStoneRight(){
        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("right strafe after grabbing second stone");

        straightenOut();
        log("right straighten after strafe after grabbing second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -89, -5, initialAngle);
        log("right depositing second stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void rightPark(){
        //drive forward and right
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 10, initialAngle);
        log("right drive forward and right");

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 25, 0, initialAngle);
        log("right drive forward to park");
    }
}
