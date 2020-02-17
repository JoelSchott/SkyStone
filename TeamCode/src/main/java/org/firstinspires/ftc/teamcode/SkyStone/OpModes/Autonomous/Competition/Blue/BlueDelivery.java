package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CustomPhoneCameraSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.SkyStone.MainBaseWebcam;
import org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.SkystoneDetector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;


@Autonomous(name = "Blue Stone Delivery", group = "Autonomous")
public class BlueDelivery extends LinearOpMode {

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
    private SkystoneDetector detector;
    public MainBaseWebcam base;

    public int initialAngle;

    private File angleFile;
    private PrintWriter angleWriter;

    private File logFile;
    private PrintWriter logWriter;

    @Override
    public void runOpMode(){

        base = new MainBaseWebcam(hardwareMap,telemetry,this);
        base.init();

        detector = new SkystoneDetector(this, true, false);

        base.drivetrain.setInitalAngle(0);
        base.gyro.gyro.resetZAxisIntegrator();
        initialAngle = base.gyro.gyro.getIntegratedZValue();

        try{
            angleFile = new File(Environment.getExternalStorageDirectory(), "angle");
            if (angleFile.exists()){
                angleFile.delete();
            }
            angleFile.createNewFile();
            angleWriter = new PrintWriter(new BufferedWriter(new FileWriter(angleFile, false)));

            logFile = new File(Environment.getExternalStorageDirectory(), "log");
            logWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
        }
        catch(Exception e){
            telemetry.addLine(e.getMessage());
        }

        telemetry.clearAll();
        telemetry.addLine("May the Force be with us");
        telemetry.update();

        waitForStart();

        base.drivetrain.setInitalAngle(0);
        base.gyro.gyro.resetZAxisIntegrator();
        initialAngle = base.gyro.gyro.getIntegratedZValue();

        position = detector.getDecision();
        detector.stopStreaming();

        telemetry.addData("position is ", position);
        telemetry.update();

        Calendar c = Calendar.getInstance();
        logWriter.println("NEW BLUE " + position + " " + c.getTime());
        log("initialPosition");

        if (position == CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN){
            position = CustomPhoneCameraSkyStone.SkyStonePosition.LEFT;
        }

        switch(position){
            case LEFT:

                //drives back and left after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -13, -27.5, initialAngle);
                log("left to first stone with encoders");

                straightenOut();
                log("left straighten after near first stone");

                //drives to specific distance from both walls
                frontRangeDriveToDistance(LEFT_FIRST_DISTANCE_TO_WALL);
                log("left adjust distance to first stone with range sensor");

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 12, initialAngle);
                log("left strafe right after grabbing first stone");

                straightenOut();
                log("left straighten out after strafing right with first stone");

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -60, 5, initialAngle);
                log("left ready to deposit stone");

                releaseBlock();

                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 5, 5, initialAngle);
                log("left drive forward after depositing first");

                straightenOut();
                log("left straighten after strafing after depositing first");

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 80, -6.6, initialAngle);
                log("left near second stone with encoders");

                straightenOut();
                log("left straighten near second stone");

                //drives to distance from both walls
                frontRangeDriveToDistance(LEFT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);
                log("left second stone adjustment with sensors");

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14.3, initialAngle);
                log("left strafe after grabbing second stone");

                straightenOut();
                log("left straighten after strafe after grabbing second stone");

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -62, 0, initialAngle);
                log("left depositing second stone");

                getThirdStoneLeft();

                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20, 3);
                log("left turning to park");

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 17, 0, base.gyro.gyro.getIntegratedZValue());
                log("left final park");

                break;

            case MIDDLE:

                //drives left after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -27.5, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 12, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -68, -5, initialAngle);

                releaseBlock();

                straightenOut();

                //strafes right towards wall
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,5,5, initialAngle);

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 90, -5, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(MIDDLE_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14.3, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -71, 0, initialAngle);

                getThirdStoneMiddle();

                //turns to face parking location
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20, 3);

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 12, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case RIGHT:

                //drives forward after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 6.2, -27, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 12, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -76, -5, initialAngle);

                releaseBlock();

                //strafe right towards wall
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,5,5, initialAngle);

                straightenOut();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 82.2, -8, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(RIGHT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14.3, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -81, 0, initialAngle);

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

        logWriter.close();
    }

    private void writeAngle(){
        try{

            int angle = base.gyro.heading();
            while (angle > 360){
                angle -= 360;
            }
            while (angle < 0){
                angle += 360;
            }
            angleWriter.println(angle);
            angleWriter.close();
        }
        catch(Exception e){
            telemetry.addLine("problem with i/o");
            telemetry.update();
            e.printStackTrace();
        }

    }

    private void log(String position){
        logWriter.println("position : " + position);
        logWriter.println("heading : " + base.gyro.heading());
        logWriter.println("frontRightEncoders : " + base.drivetrain.frontRight.getCurrentPosition());
        logWriter.println("frontLeftEncoders : " + base.drivetrain.frontLeft.getCurrentPosition());
        logWriter.println("backLeftEncoders : " + base.drivetrain.backLeft.getCurrentPosition());
        logWriter.println("backRightEncoders : " + base.drivetrain.backRight.getCurrentPosition());
        logWriter.println("frontRange : " + base.frontRange.customDistanceInInches());
        logWriter.println("leftRange : " + base.leftRange.customDistanceInInches());
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
        base.arms.setLeftPower(1);
        sleep(350);
        base.arms.setLeftPower(0.3);
    }

    public void releaseBlock(){
        base.arms.setLeftPower(-1);
        sleep(750);
        base.arms.setLeftPower(-0.05);
    }

    public void getThirdStoneLeft(){ }
    public void getThirdStoneMiddle(){}
    public void getThirdStoneRight(){}
}
