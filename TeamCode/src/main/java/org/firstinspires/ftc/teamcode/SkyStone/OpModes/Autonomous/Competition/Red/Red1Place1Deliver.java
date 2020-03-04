package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CustomPhoneCameraSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.SkyStone.MainBase;
import org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.SkystoneDetector;
import org.firstinspires.ftc.teamcode.SkyStone.MainBaseWebcam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

@Autonomous(name = "Red 1 Place 1 Delivery Park", group = "Autonomous")
public class Red1Place1Deliver extends LinearOpMode {

    public final static double DRIVE_SPEED = 1;
    public static final double MAX_TURN_SPEED = 0.5;
    public final static double MINIMUM_TURN_SPEED = 0.1;
    public final static double DISTANCE_ADJUSTMENT_SPEED = 0.15;

    public static final double COLLECTING_DISTANCE = 27;

    public static final double LEFT_BRIDGE_DISTANCE = 56;
    public static final double MIDDLE_BRIDGE_DISTANCE = 48;
    public static final double RIGHT_BRIDGE_DISTANCE =  40;

    public static final double FAR_LEFT_DISTANCE_TO_WALL = 20.86;
    public static final double LEFT_SECOND_DISTANCE_TO_WALL = 1.32;

    public static final double MIDDLE_FIRST_DISTANCE_TO_WALL = 28;
    public static final double MIDDLE_SECOND_DISTANCE_TO_WALL = 9;

    public static final double RIGHT_FIRST_DISTANCE_TO_WALL = 36;
    public static final double RIGHT_SECOND_DISTANCE_TO_WALL = 16.1;

    public static final double AROUND_GATE_DISTANCE = 13;
    public static final double RIGHT_PARKING_DISTANCE = 5;
    public static final double PARKING_DISTANCE = 15;

    public MainBase base;

    private SkystoneDetector detector;
    private CustomPhoneCameraSkyStone.SkyStonePosition position = CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN;

    public int initialAngle;

    private File angleFile;
    private PrintWriter angleWriter;

    private File logFile;
    private PrintWriter logWriter;

    @Override
    public void runOpMode(){

        base = new MainBase(hardwareMap,telemetry,this);
        base.init();

        base.arms.shutLeftClamp();
        base.arms.openRightClamp();

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

                getFirstStoneLeft();

                depositFirstStoneLeft();

                getSecondStoneLeft();

                depositSecondStoneLeft();

                getThirdStoneLeft();

                depositThirdStoneLeft();

                leftPark();

                break;

            case MIDDLE:

                getFirstStoneMiddle();

                depositFirstStoneMiddle();

                getSecondStoneMiddle();

                depositSecondStoneMiddle();

                getThirdStoneMiddle();

                depositThirdStoneMiddle();

                middlePark();

                break;

            case RIGHT:

                getFirstStoneRight();

                depositFirstStoneRight();

                getSecondStoneRight();

                depositSecondStoneRight();

                getThirdStoneRight();

                depositThirdStoneRight();

                rightPark();

                break;
        }


        base.drivetrain.setPowers(0);

        writeAngle();

        logWriter.close();
        detector.stopStreaming();

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

    public void straightenOut(){
        base.drivetrain.gyroTurn(0.35, 0.5, 180, 2);
        telemetry.addLine("angle is " + base.gyro.heading());
        telemetry.update();
    }

    public void log(String position){
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
        if (base.frontRange.distance(DistanceUnit.INCH) < 0 || error > 8){
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
        if (base.leftRange.distance(DistanceUnit.INCH) < 0 || error > 8){
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

    public void lowerArm(){
        base.arms.lowerRightArm();
    }

    public void lowerArmSleep(){
        lowerArm();
        sleep(250);
    }

    public void raiseArm(){
        base.arms.raiseRightArm();
    }

    public void grabStone(){
        base.arms.clampRightClamp();
        sleep(600);
    }

    public void releaseStone(){
        base.arms.openRightClamp();
    }

    public void getFirstStoneLeft(){
        lowerArm();

        //drives forward and right after seeing stones
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 9, 25.2, initialAngle);
        log("next to first stone encoders");

        straightenOut();
        log("straighten out after near first stone encoders");

        //drives to specific distance from both walls
        frontRangeDriveToDistance(FAR_LEFT_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment first stone encoders");

        grabStone();

        raiseArm();
    }

    public void depositFirstStoneLeft(){
        straightenOut();
        log("straighten out after grabbing first stone");

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -10, -10, initialAngle);
        log("drives left after grabbing first stone");

        straightenOut();
        log("straightens out before driving to foundation");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -70, 10, initialAngle);
        log("drives near foundation");

        lowerArmSleep();

        releaseStone();

        raiseArm();
    }

    public void getSecondStoneLeft(){
        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 11, -11, initialAngle);
        log("drives left after depositing first stone");

        base.arms.shutRightClamp();

        straightenOut();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 84, 0, initialAngle);
        log("drives forward to second stone");

        base.arms.openRightClamp();

        lowerArm();

        straightenOut();
        log("straighten out after driving forward to second stone");

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 9.5, 9.5, initialAngle);
        log("drives next to second stone encoders");

        straightenOut();
        log("straightens out after next to second stone encoders");

        //drives to distance from both walls
        frontRangeDriveToDistance(LEFT_SECOND_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment for second stone");

        grabStone();

        raiseArm();
    }

    public void depositSecondStoneLeft(){
        //drives left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drives left to deposit second stone");

        straightenOut();
        log("straightens out after moving left after second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -75, 0, initialAngle);
        log("drives back to deposit second stone");
    }

    public void getThirdStoneLeft(){

    }

    public void depositThirdStoneLeft(){

    }

    public void leftPark(){
        //turns to face gate side of line
        base.drivetrain.gyroTurn(0.3, MAX_TURN_SPEED, 150, 5);
        log("turns to face parking location");

        //releases and parks
        lowerArmSleep();

        releaseStone();

        raiseArm();

        sleep(250);
        base.arms.shutRightClamp();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());
        log("driving forward for parking");
    }

    public void getFirstStoneMiddle(){
        lowerArm();

        //strafes right next to the stones
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 2.5, 25, initialAngle);
        log("encoders next to first stone");

        straightenOut();
        log("straighten out after encoders first stone");

        //drives to specific distance from both walls
        frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("first stone sensor adjustment");

        grabStone();

        raiseArm();
    }

    public void depositFirstStoneMiddle(){
        straightenOut();
        log("straighten out after grabbing first stone");

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -10, -10, initialAngle);
        log("drive left after grabbing first stone");

        straightenOut();
        log("straighten out before driving to foundation");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -65, 10, initialAngle);
        log("drive to foundation");

        lowerArmSleep();

        releaseStone();

        raiseArm();
    }

    public void getSecondStoneMiddle(){
        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 11, -11, initialAngle);
        log("drive right after deposit first stone");

        straightenOut();

        base.arms.shutRightClamp();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 80.5, 0, initialAngle);
        log("drive straight to second stone");

        base.arms.openRightClamp();

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 9.5, 9.5, initialAngle);
        log("drive next to second stone encoders");

        straightenOut();
        log("straighten out after next to second stone encoders");

        //drives to distance from both walls
        frontRangeDriveToDistance(MIDDLE_SECOND_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment second stone");

        grabStone();

        raiseArm();
    }

    public void depositSecondStoneMiddle(){
        straightenOut();
        log("straighten out after grabbing second stone");

        //drives left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drives left before depositing second stone");

        straightenOut();
        log("straightens out before depositing second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -45, 0, initialAngle);
        log("drive to deposit second stone");
    }

    public void getThirdStoneMiddle(){

    }

    public void depositThirdStoneMiddle(){

    }

    public void middlePark(){
        //turns to face gate side of line
        base.drivetrain.gyroTurn(0.3, MAX_TURN_SPEED, 150, 5);
        log("turns to park");

        lowerArmSleep();

        releaseStone();

        raiseArm();

        sleep(250);
        base.arms.shutRightClamp();

        //park
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());
        log("park");
    }

    public void getFirstStoneRight(){
        lowerArm();

        //drives back and right after seeing stones
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -4, 25.2, initialAngle);
        log("drives next to first stone encoders");

//        straightenOut();
//        log("straightens out after near first stone encoders");

        //drives to specific distance from both walls
        frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment first stone");

        grabStone();

        raiseArm();
    }

    public void depositFirstStoneRight(){
        straightenOut();

        //drive left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -10, -10, initialAngle);
        log("drive left after grabbing first stone");

        straightenOut();
        log("straighten out after driving left after collecting first stone");

        //drive to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -60,10, initialAngle);
        log("drive to foundation");

        lowerArmSleep();

        releaseStone();

        raiseArm();
    }

    public void getSecondStoneRight(){

        straightenOut();

        //drives to second stone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  11, -11, initialAngle);
        log("drive left after depositing first stone");

        straightenOut();
        base.arms.shutRightClamp();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  77, 0, initialAngle);
        log("drives forward after depositing first stone");

        base.arms.openRightClamp();

        lowerArm();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED,  9.5, 9.5, initialAngle);
        log("drives next to second stone encoders");

        straightenOut();
        log("straightens out after near second stone encoders");

        //drives to distance from both walls
        frontRangeDriveToDistance(RIGHT_SECOND_DISTANCE_TO_WALL);
        leftRangeDriveToDistance(COLLECTING_DISTANCE);
        log("sensor adjustment for second stone");

        grabStone();

        raiseArm();
    }

    public void depositSecondStoneRight(){
        straightenOut();
        log("straighten out after grabbing second stone");

        //drives left to go to building zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -9, -9, initialAngle);
        log("drive left before depositing second stone");

        straightenOut();
        log("straightens after driving left with second stone");

        //drives back to go to other zone
        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -45, 0, initialAngle);
        log("drives to deposit second stone");
    }

    public void getThirdStoneRight(){}

    public void depositThirdStoneRight(){

    }

    public void rightPark(){
        //turns to face gate side of line
        base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 160, 5);
        log("turns to park");

        lowerArmSleep();

        releaseStone();

        raiseArm();

        sleep(250);
        base.arms.shutRightClamp();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());
        log("park");
    }


}
