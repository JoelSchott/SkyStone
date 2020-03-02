package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SkyStone.MainBase;
import org.firstinspires.ftc.teamcode.Sky_Stone_Components.FourWheelMecanum;

@Autonomous(name = "Path Test", group = "Autonomous")
public class PathTest extends LinearOpMode {

    MainBase base;

    static final double DRIVE_SPEED = 1;
    static final double TURN_SPEED = 0.4;

    private double distance = 48;

    @Override
    public void runOpMode(){

        base = new MainBase(hardwareMap, telemetry, this);
        base.init();

        telemetry.addLine("All Systems Go");
        telemetry.update();

        base.drivetrain.setInitalAngle(0);
        double initialAngle = base.gyro.gyro.getIntegratedZValue();

        waitForStart();

        base.drivetrain.gyroEncoderDrive(1, 20, 0, initialAngle);

    }

}
