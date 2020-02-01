package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "Red Three Stone", group = "Autonomous")
public class RedThreeStone extends RedDoubleStoneEncoders {

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void getThirdStoneLeft(){

        releaseBlock();

        base.drivetrain.gyroEncoderDrive(RedDoubleStoneEncoders.DRIVE_SPEED, 35, 12, initialAngle);

        straightenOut();

        frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);

        grabBlock();

        //drives left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

        straightenOut();

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -35, 0, initialAngle);
    }

    @Override
    public void getThirdStoneMiddle(){

        releaseBlock();

        base.drivetrain.gyroEncoderDrive(RedDoubleStoneEncoders.DRIVE_SPEED, 35, 12, initialAngle);

        straightenOut();

        frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);

        grabBlock();

        //drives left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

        straightenOut();

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -35, 0, initialAngle);
    }

    @Override
    public void getThirdStoneRight(){

        releaseBlock();

        base.drivetrain.gyroEncoderDrive(RedDoubleStoneEncoders.DRIVE_SPEED, 43, 9, initialAngle);

        straightenOut();

        frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);

        grabBlock();

        //drives left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

        straightenOut();

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -43, 0, initialAngle);
    }

}
