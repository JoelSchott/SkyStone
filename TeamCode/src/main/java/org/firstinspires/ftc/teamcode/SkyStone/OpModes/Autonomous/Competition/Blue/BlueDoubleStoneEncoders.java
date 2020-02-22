package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CustomPhoneCameraSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.SkyStone.MainBaseWebcam;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;


@Autonomous(name = "Blue Double Stone Encoders", group = "Autonomous")
public class BlueDoubleStoneEncoders extends LinearOpMode {

    public MainBaseWebcam base;

    public final static double DRIVE_SPEED = 1.0;
    public static final double MAX_TURN_SPEED = 0.5;
    public final static double MINIMUM_TURN_SPEED = 0.1;
    public final static double DISTANCE_ADJUSTMENT_SPEED = 0.15;

    public final static double FIRST_DISTANCE = 29.5;
    public final static double FIRST_LEFT_DISTANCE = 13.6;
    public static final double COLLECTING_DISTANCE = 2.9;

    public static final double RIGHT_BRIDGE_DISTANCE = 56;
    public static final double MIDDLE_BRIDGE_DISTANCE = 48;
    public static final double LEFT_BRIDGE_DISTANCE =  40;

    public static final double RIGHT_FIRST_DISTANCE_TO_WALL = 22.2;
    public static final double RIGHT_SECOND_DISTANCE_TO_WALL = 1.71;

    public static final double MIDDLE_FIRST_DISTANCE_TO_WALL = 29.2;
    public static final double MIDDLE_SECOND_DISTANCE_TO_WALL = 8;

    public static final double LEFT_FIRST_DISTANCE_TO_WALL = 35.64;
    public static final double LEFT_SECOND_DISTANCE_TO_WALL = 14.8;

    public static final double AROUND_GATE_DISTANCE = 13;
    public static final double RIGHT_PARKING_DISTANCE = 5;
    public static final double PARKING_DISTANCE = 15;


    private CustomPhoneCameraSkyStone.SkyStonePosition position = CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN;

    public int initialAngle;

    private File angleFile;
    private FileOutputStream angleOutputStream;
    private PrintStream anglePrintStream;

    private File logFile;
    private FileOutputStream logOutputStream;
    private PrintStream logPrintStream;

    @Override
    public void runOpMode(){

        base = new MainBaseWebcam(hardwareMap,telemetry,this);
        base.init();

        base.drivetrain.setInitalAngle(0);

        base.gyro.gyro.resetZAxisIntegrator();
        initialAngle = base.gyro.gyro.getIntegratedZValue();

        telemetry.clearAll();
//        try{
//            angleFile = new File(Environment.getExternalStorageDirectory(), "angle");
//            if (angleFile.exists()){
//                angleFile.delete();
//            }
//            angleFile.createNewFile();
//
//            angleOutputStream = new FileOutputStream(angleFile);
//            anglePrintStream = new PrintStream(angleOutputStream);
//            anglePrintStream.flush();
//
//            logFile = new File(Environment.getExternalStorageDirectory(), "log");
//            logOutputStream = new FileOutputStream(logFile);
//            logPrintStream = new PrintStream(logOutputStream);
//            logPrintStream.flush();
//            logPrintStream.println("__new__ " + System.currentTimeMillis());
//        }
//        catch(Exception e){
//            telemetry.addLine(e.getMessage());
//        }


        telemetry.addLine("May the Force be with us");
        telemetry.update();

        waitForStart();

        base.gyro.gyro.resetZAxisIntegrator();

        log("initialPosition");

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -15.5, initialAngle);
        straightenOut();

        frontRangeDriveToDistance(FIRST_DISTANCE);
        leftRangeDriveToDistance(FIRST_LEFT_DISTANCE);

        base.drivetrain.setPowers(0);

        sleep(800);

        log("seeingStones");

        List<Recognition> stones = base.webcam.getObjects();
        position = CustomPhoneCameraSkyStone.BLUETwoStonesGetPosition(stones);


        for (Recognition stone : stones){
            double midPoint = (stone.getRight() + stone.getLeft())/2.0;
            telemetry.addLine("Saw " + stone.getLabel() + " with confidence " + stone.getConfidence() + " with midpoint " + midPoint);
        }
        telemetry.addData("State is ", position);

        base.getTelemetry().addData("front distance is " , base.frontRange.customDistanceInInches());
        base.getTelemetry().addData("left distance is ", base.leftRange.customDistanceInInches());

        base.getTelemetry().addData("heading is ", base.gyro.heading());

        telemetry.update();

        if (position == CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN){
            position = CustomPhoneCameraSkyStone.SkyStonePosition.RIGHT;
        }

        switch(position){
            case LEFT:

                //drives back and left after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -13, -12, initialAngle);
                log("leftFirstStoneEncoders");

                straightenOut();
                log("leftFirstStoneStraighten");

                //drives to specific distance from both walls
                frontRangeDriveToDistance(LEFT_FIRST_DISTANCE_TO_WALL);
                log("leftFirstStoneFrontRange");

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 12, initialAngle);
                log("leftFirstStoneStrafeRight");

                straightenOut();
                log("leftFirstStrafeRightStraighten");

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -LEFT_BRIDGE_DISTANCE, 0, initialAngle);
                log("leftDepositFirstStone");

                releaseBlock();

                straightenOut();
                log("leftReleaseFirstStone");

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, LEFT_BRIDGE_DISTANCE + 25, -6.6, initialAngle);
                log("leftSecondStoneEncoders");

                straightenOut();
                log("leftSecondStoneStraighten");

                //drives to distance from both walls
                frontRangeDriveToDistance(LEFT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);
                log("leftSecondStoneRangeSensors");

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14.3, initialAngle);
                log("leftSecondStoneStrafeRight");

                straightenOut();
                log("leftSecondStoneStrafeRightStraighten");

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(LEFT_BRIDGE_DISTANCE + 22), 0, initialAngle);
                log("leftDepositSecondStone");

                getThirdStoneLeft();

                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20, 3);
                log("leftParkTurn");

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 17, 0, base.gyro.gyro.getIntegratedZValue());
                log("leftFinalPark");

                break;

            case MIDDLE:

                //drives back after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -2.5, -12, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 12, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -MIDDLE_BRIDGE_DISTANCE, 0, initialAngle);

                releaseBlock();

                straightenOut();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, MIDDLE_BRIDGE_DISTANCE + 27, -4, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(MIDDLE_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14.3, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(MIDDLE_BRIDGE_DISTANCE + 23), 0, initialAngle);

                getThirdStoneMiddle();

                //turns to face parking location
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20, 3);

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 12, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case RIGHT:

                //drives forward after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 6.2, -11.5, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 12, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -RIGHT_BRIDGE_DISTANCE, 0, initialAngle);

                releaseBlock();

                straightenOut();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, RIGHT_BRIDGE_DISTANCE + 26.2, -8, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(RIGHT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14.3, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(RIGHT_BRIDGE_DISTANCE + 25), 0, initialAngle);

                getThirdStoneRight();

                //turns to face parking location
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20 ,3);

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 13, 0, base.gyro.gyro.getIntegratedZValue());

                break;
        }

        base.drivetrain.setPowers(0);

        writeAngle();
    }

    private void writeAngle(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(), "angle");
            if (file.exists()){
                file.delete();
            }
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            PrintStream printStream = new PrintStream(outputStream);
            printStream.flush();
            int angle = base.gyro.heading();
            while (angle > 360){
                angle -= 360;
            }
            while (angle < 0){
                angle += 360;
            }
            printStream.println(angle);
            printStream.close();
            outputStream.close();
        }
        catch(Exception e){
            telemetry.addLine("problem with i/o");
            telemetry.update();
            e.printStackTrace();
        }

    }

    private void log(String state){
//        logPrintStream.println("state " + state);
//        logPrintStream.println("heading " + base.gyro.heading());
//        logPrintStream.println("frontRightEncoders " + base.drivetrain.frontRight.getCurrentPosition());
//        logPrintStream.println("frontLeftEncoders " + base.drivetrain.frontLeft.getCurrentPosition());
//        logPrintStream.println("backLeftEncoders " + base.drivetrain.backLeft.getCurrentPosition());
//        logPrintStream.println("backRightEncoders " + base.drivetrain.backRight.getCurrentPosition());
//        logPrintStream.println("frontRange " + base.frontRange.customDistanceInInches());
//        logPrintStream.println("leftRange " + base.leftRange.customDistanceInInches());
    }


    public void straightenOut(){
        base.drivetrain.gyroTurn(0.25, 0.4, 0, 0.45);
        telemetry.addData("Angle is ", base.gyro.heading());
        //telemetry.update();
    }

    public void frontRangeDriveToDistance(double distance){
        double error = Math.abs(base.frontRange.customDistanceInInches() - distance);
        double buffer = 0.8;
        if (base.frontRange.distance(DistanceUnit.INCH) < 0 || error > 20){
            return;
        }
        if (error > buffer){
            if (base.frontRange.customDistanceInInches() > distance){
                while (error > buffer){
                    base.drivetrain.setPowers(DISTANCE_ADJUSTMENT_SPEED);
                    error = Math.abs(base.frontRange.customDistanceInInches() - distance);
                }
            }
            else if (base.frontRange.customDistanceInInches() < distance){
                while (error > buffer){
                    base.drivetrain.setPowers(-DISTANCE_ADJUSTMENT_SPEED);
                    error =  Math.abs(base.frontRange.customDistanceInInches() - distance);
                }
            }
        }
        base.drivetrain.stop();
    }

    public void leftRangeDriveToDistance(double distance){
        double error = Math.abs(base.leftRange.customDistanceInInches() - distance);
        double buffer = 0.8;
        if (base.leftRange.distance(DistanceUnit.INCH) < 0 || error > 20){
            return;
        }
        if (error > buffer){
            if (base.leftRange.customDistanceInInches() > distance){
                while (error > buffer){
                    base.drivetrain.frontRight.setPower(DISTANCE_ADJUSTMENT_SPEED);
                    base.drivetrain.frontLeft.setPower(-DISTANCE_ADJUSTMENT_SPEED);
                    base.drivetrain.backLeft.setPower(DISTANCE_ADJUSTMENT_SPEED);
                    base.drivetrain.backRight.setPower(-DISTANCE_ADJUSTMENT_SPEED);
                    error = Math.abs(base.leftRange.customDistanceInInches() - distance);
                }
            }
        }
        base.drivetrain.stop();
    }



    public void grabBlock(){
//        base.arms.setLeftPower(1);
//        sleep(350);
//        base.arms.setLeftPower(0.3);
    }

    public void releaseBlock(){
//        base.arms.setLeftPower(-1);
//        sleep(750);
//        base.arms.setLeftPower(-0.05);
    }

    public void getThirdStoneLeft(){ }
    public void getThirdStoneMiddle(){}
    public void getThirdStoneRight(){}
}
