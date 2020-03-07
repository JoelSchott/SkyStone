package org.firstinspires.ftc.teamcode.SkyStone.OpModes.TeleOp;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkyStone.MainBase;
import org.firstinspires.ftc.teamcode.Sky_Stone_Components.AutoStoneArms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@TeleOp(name = "Main TeleOp", group = "TeleOp")
public class MainTeleOp extends LinearOpMode {

    MainBase base;

    DrivetrainState driveState = DrivetrainState.FIELD_RELATIVE;
    DrivetrainMode driveMode = DrivetrainMode.FULL_SPEED;

    private boolean clamped = false;

    private boolean gamepad2xHeld = false;
    private boolean gamepad2upHeld = false;
    private boolean gamepad2downHeld = false;
    private boolean gamepad2leftHeld = false;
    private boolean gamepad2rightHeld = false;
    private boolean gamepad1leftBumperHeld = false;
    private boolean gamepad1rightBumperHeld = false;
    private boolean gamepad1leftTriggerHeld = false;
    private boolean gamepad1rightTriggerHeld = false;


    private static final int[] ENCODER_PLACING_POSITIONS = {-143, -756, -1231, -1745, -2235, -3016, -1785};
    private int towerLevel = 0;
    private boolean automateLift = false;

    private ElapsedTime rotatorTimer = new ElapsedTime();
    private static final int ROTATE_TIME = 1000;

    enum DrivetrainState{
        ROBOT_RELATIVE,FIELD_RELATIVE
    }

    enum DrivetrainMode{
        FULL_SPEED, SLOW_MODE
    }


    @Override
    public void runOpMode(){

        base = new MainBase(hardwareMap, telemetry, this);
        base.init();

        telemetry.clear();
        telemetry.addLine("All Systems Go");
        telemetry.addLine("May be the Force be with us");
        telemetry.update();

        setAngle();


        waitForStart();

        base.arms.shutRightClamp();
        base.arms.shutLeftClamp();
        base.output.placeMarker();

        while (opModeIsActive()){

            //------------------------------------DRIVING-----------------------------------------------------

            if (gamepad1.a){
                driveState = DrivetrainState.FIELD_RELATIVE;
            }
            else if (gamepad1.b){
                driveState = DrivetrainState.ROBOT_RELATIVE;
            }

            if (gamepad1.x){
                driveMode = DrivetrainMode.FULL_SPEED;
            }
            else if (gamepad1.y){
                driveMode = DrivetrainMode.SLOW_MODE;
            }

            double forward = -gamepad1.left_stick_y;
            double right = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            double scale = 0.3;
            if (driveMode == DrivetrainMode.SLOW_MODE){
                forward = forward * scale;
                right = right * scale;
                turn = turn * scale;
            }

            switch(driveState){
                case ROBOT_RELATIVE:
                    base.drivetrain.robotRelativeDrive(forward, right, turn);
                    break;

                case FIELD_RELATIVE:
                    base.drivetrain.fieldRelativeDrive(forward, right, turn);
                    break;
            }

            if (gamepad1.dpad_left){
                base.drivetrain.setCurrentAngleAs(180);
            }
            else if (gamepad1.dpad_down){
                base.drivetrain.setCurrentAngleAs(270);
            }
            else if (gamepad1.dpad_right){
                base.drivetrain.setCurrentAngleAs(0);
            }
            else if (gamepad1.dpad_up){
                base.drivetrain.setCurrentAngleAs(90);
            }

            //--------------------------------------FOUNDATION MOVING----------------------------------------------
            double foundationPower = Range.clip(gamepad2.left_stick_y, -1 ,1);
            base.foundation.moveRightServo(foundationPower);
            base.foundation.moveLeftServo(foundationPower);

            //------------------------------------ARMS FOR AUTONOMOUS--------------------------------------------
            if (gamepad1.left_bumper){
                if (! gamepad1leftBumperHeld){
                    gamepad1leftBumperHeld = true;
                    if (Math.abs(base.arms.leftArm.getPosition() - AutoStoneArms.LEFT_ARM_UP_POSITION) < 0.1){
                        base.arms.lowerLeftArm();
                    }
                    else{
                        base.arms.raiseLeftArm();
                    }
                }
            }
            else{
                gamepad1leftBumperHeld = false;
            }

            if (gamepad1.left_trigger > 0.5){
                if (! gamepad1leftTriggerHeld){
                    gamepad1leftTriggerHeld = true;
                    if (Math.abs(base.arms.leftClamp.getPosition() - AutoStoneArms.LEFT_CLAMP_OPEN_POSITION) < 0.1){
                        base.arms.clampLeftClamp();
                    }
                    else if (Math.abs(base.arms.leftClamp.getPosition() - AutoStoneArms.LEFT_CLAMP_GRAB_POSITION) < 0.1){
                        base.arms.shutLeftClamp();
                    }
                    else{
                        base.arms.openLeftClamp();
                    }
                }
            }
            else{
                gamepad1leftTriggerHeld = false;
            }


            if (gamepad1.right_bumper){
                if (! gamepad1rightBumperHeld){
                    gamepad1rightBumperHeld = true;
                    if (Math.abs(base.arms.rightArm.getPosition() - AutoStoneArms.RIGHT_ARM_UP_POSITION) < 0.1){
                        base.arms.lowerRightArm();
                    }
                    else{
                        base.arms.raiseRightArm();
                    }
                }
            }
            else{
                gamepad1rightBumperHeld = false;
            }

            if (gamepad1.right_trigger > 0.5){
                telemetry.addLine("trigger held");
                telemetry.addLine("right clamp open position is " + AutoStoneArms.RIGHT_CLAMP_OPEN_POSITION);
                telemetry.addLine("right clamp grab position is " + AutoStoneArms.RIGHT_CLAMP_GRAB_POSITION);
                if (! gamepad1rightTriggerHeld){
                    telemetry.addLine("trigger pressed");
                    gamepad1rightTriggerHeld = true;
                    if (Math.abs(base.arms.rightClamp.getPosition() - AutoStoneArms.RIGHT_CLAMP_OPEN_POSITION) < 0.1){
                        telemetry.addLine("setting to clamp position");
                        base.arms.clampRightClamp();
                    }
                    else if (Math.abs(base.arms.rightClamp.getPosition() - AutoStoneArms.RIGHT_CLAMP_GRAB_POSITION) < 0.1){
                        telemetry.addLine("setting to shut position");
                        base.arms.shutRightClamp();
                    }
                    else{
                        telemetry.addLine("setting to open position");
                        base.arms.openRightClamp();
                    }
                }
            }
            else{
                gamepad1rightTriggerHeld = false;
            }



            //------------------------------------COLLECTING-------------------------------------------------------------
            if (gamepad2.a){
                base.collector.collect(0.78);
            }
            else if (gamepad2.b){
                base.collector.spew(0.25);
            }
            else{
                base.collector.stop();
            }


            //------------------------------------OUTPUT-------------------------------------------------------------
            //-----------CLAMP------------------
            if (gamepad2.x && !gamepad2xHeld){
                gamepad2xHeld = true;
                if (clamped){
                    base.output.clampRelease();
                    clamped = false;
                }
                else{
                    base.output.clampGrab();
                    clamped = true;
                }
            }
            if (!gamepad2.x){
                gamepad2xHeld = false;
            }
            //---------LIFT----------------------

            if (Math.abs(gamepad2.right_stick_y) > 0.1){
                base.output.lift.setPower(gamepad2.right_stick_y);
                automateLift = false;
            }
            else{
                base.output.lift.setPower(0);
            }

            if (gamepad2.y){
                automateLift = true;
            }

            if (gamepad2.dpad_up){
                if (!gamepad2upHeld){
                    gamepad2upHeld = true;
                    towerLevel++;
                }
            }
            else{
                gamepad2upHeld = false;
            }

            if (gamepad2.dpad_down){
                if (!gamepad2downHeld){
                    gamepad2downHeld = true;
                    towerLevel--;
                }
            }
            else{
                gamepad2downHeld = false;
            }
            if (towerLevel > 6){
                towerLevel = 6;
            }
            if (towerLevel < 0){
                towerLevel = 0;
            }

            if (automateLift){

                int targetPosition = ENCODER_PLACING_POSITIONS[towerLevel];
                int currentPosition = base.output.lift.getCurrentPosition();
                int error = Math.abs(targetPosition - currentPosition);

                double power = 1;
                if (error < 200){
                    power = 0.3;
                }

                //negative power to lift motor is up
                //if targetPosition is less than current, we should go up

                if (targetPosition < currentPosition){
                    base.output.lift.setPower(-power);
                }
                else{
                    base.output.lift.setPower(power);
                }

                if (Math.abs(targetPosition - base.output.lift.getCurrentPosition()) < 10){
                    automateLift = false;
                    base.output.lift.setPower(0);
                }
            }


            //----------ROTATION------------------
            if (gamepad2.dpad_left){
                if (!gamepad2leftHeld){
                    gamepad2leftHeld = true;
                    base.output.inRotate();
                }
            }
            else{
                gamepad2leftHeld = false;
            }
            if (gamepad2.dpad_right){
                if (!gamepad2rightHeld){
                    gamepad2rightHeld = true;
                    base.output.outRotate();
                }
            }
            else{
                gamepad2rightHeld = false;
            }


            if (gamepad2.left_trigger > 0.2){
                base.output.blockRotator.setPosition(base.output.blockRotator.getPosition() - 0.01);
            }
            else if (gamepad2.right_trigger > 0.2){
                base.output.blockRotator.setPosition(base.output.blockRotator.getPosition() + 0.01);
            }

            //----------CAPSTONE-----------------
            if (gamepad2.right_bumper){
                base.output.placeMarker();
            }
            else if (gamepad2.left_bumper){
                base.output.retractMarker();
            }


            //------------------------------------TELEMETRY--------------------------------------------------------


            telemetry.addData("DRIVE STATE :", driveState);
            telemetry.addData("DRIVE MODE :", driveMode);
            telemetry.addLine();
            telemetry.addData("TOWER LEVEL IS ", towerLevel);
            telemetry.addLine();
//            telemetry.addData("front distance ", base.frontRange.customDistanceInInches());
//            telemetry.addData("left distance is ", base.leftRange.customDistanceInInches());
//            telemetry.addLine();
//            telemetry.addData("left arm position is " , base.arms.leftArm.getPosition());
//            telemetry.addData("right arm position is ", base.arms.rightArm.getPosition());
//            telemetry.addLine();
//            telemetry.addData("left clamp position is " , base.arms.leftClamp.getPosition());
//            telemetry.addData("right clamp position is ", base.arms.rightClamp.getPosition());
//            telemetry.addData("front right encoders are ", base.drivetrain.frontRight.getCurrentPosition());
//            telemetry.addData("front left ", base.drivetrain.frontLeft.getCurrentPosition());
//            telemetry.addData("back left ", base.drivetrain.backLeft.getCurrentPosition());
//            telemetry.addData("back right ", base.drivetrain.backRight.getCurrentPosition());
            telemetry.update();

        }
    }

    private void setAngle(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "angle");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            int angle = Integer.parseInt(line);
            base.drivetrain.setCurrentAngleAs(angle);
            br.close();
            telemetry.addData("Successfully set angle to ", angle);
            telemetry.update();
        }
        catch(Exception e){
            telemetry.addLine("problem with i/o");
            telemetry.addLine(e.getMessage());
            telemetry.update();
        }
    }
}
