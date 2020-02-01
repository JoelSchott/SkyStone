package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Blue Three Stone", group = "Autonomous")

public class BlueThreeStone extends BlueDoubleStoneEncoders{

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void getThirdStoneLeft(){
        releaseBlock();

        base.drivetrain.gyroEncoderDrive(BlueDoubleStoneEncoders.DRIVE_SPEED, 40, -9, initialAngle);

        straightenOut();

        frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);

        grabBlock();

        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 13, initialAngle);

        straightenOut();

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -40, 0, initialAngle);
    }

    @Override
    public void getThirdStoneMiddle(){
        releaseBlock();

        base.drivetrain.gyroEncoderDrive(BlueDoubleStoneEncoders.DRIVE_SPEED, 35, -9, initialAngle);

        straightenOut();

        frontRangeDriveToDistance(LEFT_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);

        grabBlock();

        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 13, initialAngle);

        straightenOut();

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -35, 0, initialAngle);
    }

    public void getThirdStoneRight(){
        releaseBlock();

        base.drivetrain.gyroEncoderDrive(BlueDoubleStoneEncoders.DRIVE_SPEED, 35, -9, initialAngle);

        straightenOut();

        frontRangeDriveToDistance(LEFT_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);

        grabBlock();

        //drives right to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 13, initialAngle);

        straightenOut();

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -35, 0, initialAngle);
    }
}
