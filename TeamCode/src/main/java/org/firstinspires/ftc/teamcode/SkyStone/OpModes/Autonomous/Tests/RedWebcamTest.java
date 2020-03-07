package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.SkystoneDetector;

@Autonomous(group = "Autonomous", name = "Red Webcam Test")
public class RedWebcamTest extends LinearOpMode {

    SkystoneDetector detector;

    @Override
    public void runOpMode(){

        detector = new SkystoneDetector(this, true, true);

        waitForStart();


        while (opModeIsActive()){

            sleep(100);
            telemetry.addData("Decision is ", detector.getDecision());
        }
    }
}