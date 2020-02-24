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
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -73, -5, initialAngle);
        log("left depositing second stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void leftPark(){
        //drive forward and right
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 10, initialAngle);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 25, 0, initialAngle);
    }

    @Override
    public void depositSecondStoneMiddle(){
        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("left strafe after grabbing second stone");

        straightenOut();
        log("left straighten after strafe after grabbing second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -81, -5, initialAngle);
        log("left depositing second stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void middlePark() {
        //drive forward and right
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 10, initialAngle);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 25, 0, initialAngle);
    }

    @Override
    public void depositSecondStoneRight(){
        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, 9, initialAngle);
        log("left strafe after grabbing second stone");

        straightenOut();
        log("left straighten after strafe after grabbing second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -89, -5, initialAngle);
        log("left depositing second stone");

        lowerArm();

        releaseStone();

        raiseArm();
    }

    @Override
    public void rightPark(){
        //drive forward and right
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 10, initialAngle);

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 25, 0, initialAngle);
    }
}
