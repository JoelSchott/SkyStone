package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CustomPhoneCameraSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.SkystoneDetector;
import org.firstinspires.ftc.teamcode.SkyStone.MainBaseWebcam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Calendar;

@Autonomous(name = "Red Delivery", group = "Autonomous")
public class RedDelivery extends LinearOpMode {

    public final static double DRIVE_SPEED = 1.0;
    public static final double MAX_TURN_SPEED = 0.5;
    public final static double MINIMUM_TURN_SPEED = 0.1;
    public final static double DISTANCE_ADJUSTMENT_SPEED = 0.15;

    public static final double COLLECTING_DISTANCE = 27.33;

    public static final double LEFT_BRIDGE_DISTANCE = 56;
    public static final double MIDDLE_BRIDGE_DISTANCE = 48;
    public static final double RIGHT_BRIDGE_DISTANCE =  40;

    public static final double FAR_LEFT_DISTANCE_TO_WALL = 20.86;
    public static final double LEFT_SECOND_DISTANCE_TO_WALL = 1.32;

    public static final double MIDDLE_FIRST_DISTANCE_TO_WALL = 27.7;
    public static final double MIDDLE_SECOND_DISTANCE_TO_WALL = 8.2;

    public static final double RIGHT_FIRST_DISTANCE_TO_WALL = 34.4;
    public static final double RIGHT_SECOND_DISTANCE_TO_WALL = 14.3;

    public static final double AROUND_GATE_DISTANCE = 13;
    public static final double RIGHT_PARKING_DISTANCE = 5;
    public static final double PARKING_DISTANCE = 15;

    public MainBaseWebcam base;

    private SkystoneDetector detector;
    private CustomPhoneCameraSkyStone.SkyStonePosition position = CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN;

    public int initialAngle;

    private File angleFile;
    private PrintWriter angleWriter;

    private File logFile;
    private PrintWriter logWriter;

    @Override
    public void runOpMode(){

        base = new MainBaseWebcam(hardwareMap,telemetry,this);
        base.init();

        detector = new SkystoneDetector(this, true, true);

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

        base.drivetrain.setInitalAngle(180);
        base.gyro.gyro.resetZAxisIntegrator();
        initialAngle = base.gyro.gyro.getIntegratedZValue();

        position = detector.getDecision();
        detector.stopStreaming();

        telemetry.addData("position is ", position);
        telemetry.update();

        Calendar c = Calendar.getInstance();
        logWriter.println("NEW RED " + position + " " + c.getTime());
        log("initial position");

        if (position == CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN){
            position = CustomPhoneCameraSkyStone.SkyStonePosition.RIGHT;
        }

        switch(position){
            case LEFT:

                //drives forward and right after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 8, 26.5, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(FAR_LEFT_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                straightenOut();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -62, 5, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 5, -5, initialAngle);

                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 78, 10, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(LEFT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -75, 0, initialAngle);

                getThirdStoneLeft();

                //turns to face gate side of line
                base.drivetrain.gyroTurn(0.3, MAX_TURN_SPEED, 150, 5);

                //releases and parks
                base.arms.setRightPosition(0.1);
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case MIDDLE:

                //strafes right next to the stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 26.5, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                straightenOut();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -72, 5, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 5, -5, initialAngle);

                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 69, 10, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(MIDDLE_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                straightenOut();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -76, 0, initialAngle);

                getThirdStoneMiddle();

                //turns to face gate side of line
                base.drivetrain.gyroTurn(0.3, MAX_TURN_SPEED, 150, 5);

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case RIGHT:

                //drives back and right after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -7, 26.5, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                straightenOut();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -60,5, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  5, -5, initialAngle);

                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  55.2, 5, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(RIGHT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                straightenOut();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -54, 0, initialAngle);

                getThirdStoneRight();

                //turns to face gate side of line
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 160, 5);

                releaseBlock();

                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());

                break;
        }


        base.drivetrain.setPowers(0);

        writeAngle();

    }

    private void writeAngle(){
        try{
//            angle
//            printStream.println(angle);
//            printStream.close();
//            outputStream.close();
        }
        catch(Exception e){
            telemetry.addLine("problem with i/o");
            telemetry.update();
            e.printStackTrace();
        }

    }

    public void straightenOut(){
        base.drivetrain.gyroTurn(0.2, 0.8, 180, 2);
    }

    private void log(String position){
        logWriter.println("position " + position);
        logWriter.println("heading " + base.gyro.heading());
        logWriter.println("frontRightEncoders " + base.drivetrain.frontRight.getCurrentPosition());
        logWriter.println("frontLeftEncoders " + base.drivetrain.frontLeft.getCurrentPosition());
        logWriter.println("backLeftEncoders " + base.drivetrain.backLeft.getCurrentPosition());
        logWriter.println("backRightEncoders " + base.drivetrain.backRight.getCurrentPosition());
        logWriter.println("frontRange " + base.frontRange.customDistanceInInches());
        logWriter.println("leftRange " + base.leftRange.customDistanceInInches());
    }

    public void frontRangeDriveToDistance(double distance){
        double error = Math.abs(base.frontRange.customDistanceInInches() - distance);
        double buffer = 0.8;
        if (base.frontRange.distance(DistanceUnit.INCH) < 0 || error > 10){
            return;
        }
        if (error > buffer){
            if (base.frontRange.customDistanceInInches() > distance){
                while (error > buffer && error < 10){
                    base.drivetrain.setPowers(DISTANCE_ADJUSTMENT_SPEED);
                    error = Math.abs(base.frontRange.customDistanceInInches() - distance);
                }
            }
            else if (base.frontRange.customDistanceInInches() < distance){
                while (error > buffer && error < 10){
                    base.drivetrain.setPowers(-DISTANCE_ADJUSTMENT_SPEED);
                    error =  Math.abs(base.frontRange.customDistanceInInches() - distance);
                }
            }
        }
    }

    public void leftRangeDriveToDistance(double distance){
        double error = Math.abs(base.leftRange.customDistanceInInches() - distance);
        double buffer = 0.8;
        if (base.leftRange.distance(DistanceUnit.INCH) < 0 || error > 20){
            return;
        }
        if (error > buffer){
            if (base.leftRange.customDistanceInInches() < distance){
                while (error > buffer){
                    base.drivetrain.frontRight.setPower(-DISTANCE_ADJUSTMENT_SPEED);
                    base.drivetrain.frontLeft.setPower(DISTANCE_ADJUSTMENT_SPEED);
                    base.drivetrain.backLeft.setPower(-DISTANCE_ADJUSTMENT_SPEED);
                    base.drivetrain.backRight.setPower(DISTANCE_ADJUSTMENT_SPEED);
                    error =  Math.abs(base.leftRange.customDistanceInInches() - distance);
                }
            }
        }
    }

    public void grabBlock(){
        base.arms.setRightPosition(0.75);
        sleep(300);
    }

    public void releaseBlock(){
        base.arms.setRightPosition(0.1);
        sleep(300);
    }

    public void getThirdStoneLeft(){ }
    public void getThirdStoneMiddle(){}
    public void getThirdStoneRight(){}
}
