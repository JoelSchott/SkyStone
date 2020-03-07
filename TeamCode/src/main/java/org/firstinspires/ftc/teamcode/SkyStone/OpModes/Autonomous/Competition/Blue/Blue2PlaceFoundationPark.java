package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous (name = "Blue 2 Place Foundation Park", group = "Autonomous")
public class Blue2PlaceFoundationPark extends Blue2PlaceFoundation{

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void leftPark(){
        super.leftPark();

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 57, 0, base.gyro.gyro.getIntegratedZValue());
        log("left park");
    }

    @Override
    public void middlePark(){
        super.middlePark();

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 57, 0, base.gyro.gyro.getIntegratedZValue());
        log("middle park");
    }

    @Override
    public void rightPark(){
        super.rightPark();

        base.arms.shutLeftClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 57, 0, base.gyro.gyro.getIntegratedZValue());
        log("right park");
    }
}
