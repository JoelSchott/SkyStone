package org.firstinspires.ftc.teamcode.SkyStone.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;

//@TeleOp(name = "Test TeleOp", group = "TeleOp")

public class TestTeleOp extends LinearOpMode {

    private CRServo rotator;

    public void runOpMode(){
        rotator = hardwareMap.crservo.get("rotator");

        waitForStart();

        rotator.setPower(gamepad1.right_stick_y);
    }
}
