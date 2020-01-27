package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CustomPhoneCameraSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Sky_Stone_Components.FourWheelMecanum;
import org.firstinspires.ftc.teamcode.SkyStone.MainBaseWebcam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

@Autonomous(name = "Red Double Stone Encoders", group = "Autonomous")
public class RedDoubleStoneEncoders extends LinearOpMode {

    private MainBaseWebcam base;

    private final static double DRIVE_SPEED = 1.0;
    private static final double MAX_TURN_SPEED = 0.5;
    private final static double MINIMUM_TURN_SPEED = 0.1;
    private final static double DISTANCE_ADJUSTMENT_SPEED = 0.15;

    private final static double FIRST_DISTANCE = 26.80;
    private final static double FIRST_LEFT_DISTANCE = 16.84;
    private static final double COLLECTING_DISTANCE = 27.33;

    private static final double LEFT_BRIDGE_DISTANCE = 40;
    private static final double MIDDLE_BRIDGE_DISTANCE = 32;
    private static final double RIGHT_BRIDGE_DISTANCE =  24;

    private static final double LEFT_FIRST_DISTANCE_TO_WALL = 20.86;
    private static final double LEFT_SECOND_DISTANCE_TO_WALL = 1.32;

    private static final double MIDDLE_FIRST_DISTANCE_TO_WALL = 27;
    private static final double MIDDLE_SECOND_DISTANCE_TO_WALL = 8.2;

    private static final double RIGHT_FIRST_DISTANCE_TO_WALL = 34.4;
    private static final double RIGHT_SECOND_DISTANCE_TO_WALL = 14.3;

    private static final double AROUND_GATE_DISTANCE = 13;
    private static final double RIGHT_PARKING_DISTANCE = 5;
    private static final double PARKING_DISTANCE = 15;


    private CustomPhoneCameraSkyStone.SkyStonePosition position = CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN;

    @Override
    public void runOpMode(){

        base = new MainBaseWebcam(hardwareMap,telemetry,this);
        base.init();

        base.drivetrain.setInitalAngle(180);
        int initialAngle = base.gyro.gyro.getIntegratedZValue();

        telemetry.clearAll();
        telemetry.addLine("May the Force be with us");
        telemetry.update();

        while (!opModeIsActive()){
            telemetry.addData("front distance is ", base.frontRange.customDistanceInInches());
            telemetry.update();
        }

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 10.5, initialAngle);

        base.getTelemetry().addData("front distance is ", base.frontRange.customDistanceInInches());
        base.getTelemetry().addData("left distance is ", base.leftRange.customDistanceInInches());
        base.getTelemetry().update();

        frontRangeDriveToDistance(FIRST_DISTANCE);
        leftRangeDriveToDistance(FIRST_LEFT_DISTANCE);

        base.drivetrain.setPowers(0);

        sleep(800);

        List<Recognition> stones = base.webcam.getObjects();
        position = CustomPhoneCameraSkyStone.REDTwoStonesGetPosition(stones);
        if (position == CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN){
            position = CustomPhoneCameraSkyStone.SkyStonePosition.MIDDLE;
        }

        for (Recognition stone : stones){
            double midPoint = (stone.getRight() + stone.getLeft())/2.0;
            telemetry.addLine("Saw " + stone.getLabel() + " with confidence " + stone.getConfidence() + " with midpoint " + midPoint);
        }

        telemetry.addData("Position is ",  position);
        telemetry.update();

        switch(position){
            case LEFT:

                //drives forward and right after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 4, 14, initialAngle);

                //drives to specific distance from both walls
                frontRangeDriveToDistance(LEFT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -LEFT_BRIDGE_DISTANCE, 0, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(LEFT_BRIDGE_DISTANCE + 16), AROUND_GATE_DISTANCE - 1.5, initialAngle);

                //drives to distance from both walls
                frontRangeDriveToDistance(LEFT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                base.drivetrain.gyroEncoderDrive(0.2, 1.2, 0, initialAngle);

                grabBlock();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(LEFT_BRIDGE_DISTANCE + 18), 0, initialAngle);

                //turns to face gate side of line
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 160, 5);

                //releases and parks
                base.arms.setRightPosition(0.1);
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case MIDDLE:

                //strafes right next to the stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14, initialAngle);

                //drives to specific distance from both walls
                frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -MIDDLE_BRIDGE_DISTANCE, 0, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, MIDDLE_BRIDGE_DISTANCE + 16, AROUND_GATE_DISTANCE - 1, initialAngle);

                //drives to distance from both walls
                frontRangeDriveToDistance(MIDDLE_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(MIDDLE_BRIDGE_DISTANCE + 16), 0, initialAngle);

                //turns to face gate side of line
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 160, 5);

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case RIGHT:

                //drives back and right after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -4.5, 14, initialAngle);

                //drives to specific distance from both walls
                frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, - RIGHT_BRIDGE_DISTANCE,0, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, RIGHT_BRIDGE_DISTANCE + 16, AROUND_GATE_DISTANCE - 3, initialAngle);

                //drives to distance from both walls
                frontRangeDriveToDistance(RIGHT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(RIGHT_BRIDGE_DISTANCE + 18), 0, initialAngle);

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
            File file = new File(Environment.getExternalStorageDirectory(), "angle");
            FileOutputStream outputStream = new FileOutputStream(file);
            PrintStream printStream = new PrintStream(outputStream);
            printStream.flush();
            printStream.println(base.gyro.heading());
            printStream.close();
            outputStream.close();
        }
        catch(Exception e){
            telemetry.addLine("problem with i/o");
            telemetry.update();
            e.printStackTrace();
        }

    }

    private void frontRangeDriveToDistance(double distance){
        double error = Math.abs(base.frontRange.customDistanceInInches() - distance);
        double buffer = 1.5;
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
    }

    private void leftRangeDriveToDistance(double distance){
        double error = Math.abs(base.leftRange.customDistanceInInches() - distance);
        double buffer = 1.5;
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

    private void grabBlock(){
        base.arms.setRightPosition(0.75);
        sleep(300);
    }

    private void releaseBlock(){
        base.arms.setRightPosition(0.1);
        sleep(300);
    }
}
