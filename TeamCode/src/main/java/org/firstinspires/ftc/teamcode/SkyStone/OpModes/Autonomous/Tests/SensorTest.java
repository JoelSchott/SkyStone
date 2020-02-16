package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Tests;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name = "Sensor Test")
public class SensorTest extends LinearOpMode {

    private ModernRoboticsI2cRangeSensor range;
    private Rev2mDistanceSensor distanceSensor;

    public void runOpMode(){

        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "frontRange");
        telemetry.addLine("successfully made front range sensor");
        telemetry.update();

        distanceSensor = hardwareMap.get(Rev2mDistanceSensor.class, "range");

        telemetry.addLine("Ready to go");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {

            telemetry.addData("rev range sensor distance is ", distanceSensor.getDistance(DistanceUnit.INCH));
            telemetry.addLine();
            telemetry.addData("other range sensor distance is ", range.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }

    }
}
