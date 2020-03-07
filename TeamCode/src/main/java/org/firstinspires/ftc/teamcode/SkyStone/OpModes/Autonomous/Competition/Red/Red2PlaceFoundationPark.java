package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Red 2 Place Foundation Park", group = "Autonomous")
public class Red2PlaceFoundationPark extends Red2PlaceFoundation{

    @Override
    public void runOpMode(){
        super.runOpMode();
    }

    @Override
    public void rightPark(){
        super.rightPark();

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 40, 0, base.gyro.gyro.getIntegratedZValue());
        log("parks");
    }

    @Override
    public void middlePark(){
        super.middlePark();

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 40, 0, base.gyro.gyro.getIntegratedZValue());
        log("parks");
    }

    @Override
    public void leftPark(){
        super.leftPark();

        base.arms.shutRightClamp();

        //parks
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 40, 0, base.gyro.gyro.getIntegratedZValue());
        log("parks");
    }
}
