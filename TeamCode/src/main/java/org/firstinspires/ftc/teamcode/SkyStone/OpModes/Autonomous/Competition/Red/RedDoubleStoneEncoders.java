package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Red;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CustomPhoneCameraSkyStone;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.SkyStone.MainBaseWebcam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

@Autonomous(name = "Red Double Stone Encoders", group = "Autonomous")
public class RedDoubleStoneEncoders extends LinearOpMode {

    public MainBaseWebcam base;

    public final static double DRIVE_SPEED = 1.0;
    public static final double MAX_TURN_SPEED = 0.5;
    public final static double MINIMUM_TURN_SPEED = 0.1;
    public final static double DISTANCE_ADJUSTMENT_SPEED = 0.15;

    public final static double FIRST_DISTANCE = 26.80;
    public final static double FIRST_LEFT_DISTANCE = 16.84;
    public static final double COLLECTING_DISTANCE = 27.33;

    public static final double LEFT_BRIDGE_DISTANCE = 56;
    public static final double MIDDLE_BRIDGE_DISTANCE = 48;
    public static final double RIGHT_BRIDGE_DISTANCE =  40;

    public static final double LEFT_FIRST_DISTANCE_TO_WALL = 20.86;
    public static final double LEFT_SECOND_DISTANCE_TO_WALL = 1.32;

    public static final double MIDDLE_FIRST_DISTANCE_TO_WALL = 27.7;
    public static final double MIDDLE_SECOND_DISTANCE_TO_WALL = 8.2;

    public static final double RIGHT_FIRST_DISTANCE_TO_WALL = 34.4;
    public static final double RIGHT_SECOND_DISTANCE_TO_WALL = 14.3;

    public static final double AROUND_GATE_DISTANCE = 13;
    public static final double RIGHT_PARKING_DISTANCE = 5;
    public static final double PARKING_DISTANCE = 15;


    private CustomPhoneCameraSkyStone.SkyStonePosition position = CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN;

    public int initialAngle;

    @Override
    public void runOpMode(){

        base = new MainBaseWebcam(hardwareMap,telemetry,this);
        base.init();

        base.drivetrain.setInitalAngle(180);

        base.gyro.gyro.resetZAxisIntegrator();
        initialAngle = base.gyro.gyro.getIntegratedZValue();

        telemetry.clearAll();
        telemetry.addLine("May the Force be with us");
        telemetry.update();

        waitForStart();

        base.gyro.gyro.resetZAxisIntegrator();

        base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, 14, initialAngle);
        straightenOut();

        base.getTelemetry().addData("front distance is ", base.frontRange.customDistanceInInches());
        base.getTelemetry().addData("left distance is ", base.leftRange.customDistanceInInches());
        base.getTelemetry().update();

        frontRangeDriveToDistance(FIRST_DISTANCE);
        leftRangeDriveToDistance(FIRST_LEFT_DISTANCE);

        base.drivetrain.setPowers(0);

        sleep(800);

        List<Recognition> stones = base.webcam.getObjects();
        position = CustomPhoneCameraSkyStone.REDTwoStonesGetPosition(stones);


        for (Recognition stone : stones){
            double midPoint = (stone.getRight() + stone.getLeft())/2.0;
            telemetry.addLine("Saw " + stone.getLabel() + " with confidence " + stone.getConfidence() + " with midpoint " + midPoint);
        }

        telemetry.addData("Position is ",  position);
        telemetry.update();

        if (position == CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN){
            position = CustomPhoneCameraSkyStone.SkyStonePosition.LEFT;
        }

        switch(position){
            case LEFT:

                //drives forward and right after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 10.5, 12.5, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(LEFT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -LEFT_BRIDGE_DISTANCE, 0, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, (LEFT_BRIDGE_DISTANCE + 22), AROUND_GATE_DISTANCE - 3, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(LEFT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                base.drivetrain.gyroEncoderDrive(0.2, 1.2, 0, initialAngle);

                grabBlock();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(LEFT_BRIDGE_DISTANCE + 19), 0, initialAngle);

                getThirdStoneLeft();

                //turns to face gate side of line
                base.drivetrain.gyroTurn(0.3, MAX_TURN_SPEED, 150, 5);

                //releases and parks
                base.arms.setRightArmPosition(0.1);
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case MIDDLE:

                //strafes right next to the stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 2.75, 12.5, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -MIDDLE_BRIDGE_DISTANCE - 4, 0, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, MIDDLE_BRIDGE_DISTANCE + 26, AROUND_GATE_DISTANCE - 3, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(MIDDLE_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(MIDDLE_BRIDGE_DISTANCE + 28), 0, initialAngle);

                getThirdStoneMiddle();

                //turns to face gate side of line
                base.drivetrain.gyroTurn(0.3, MAX_TURN_SPEED, 150, 5);

                releaseBlock();

                //park
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 15, 0, base.gyro.gyro.getIntegratedZValue());

                break;

            case RIGHT:

                //drives back and right after seeing stones
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -7, 14.2, initialAngle);

                straightenOut();

                //drives to specific distance from both walls
                frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drive left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                straightenOut();

                //drive to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, - RIGHT_BRIDGE_DISTANCE,0, initialAngle);

                releaseBlock();

                //drives to second stone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, RIGHT_BRIDGE_DISTANCE + 20.2, AROUND_GATE_DISTANCE - 4, initialAngle);

                straightenOut();

                //drives to distance from both walls
                frontRangeDriveToDistance(RIGHT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                grabBlock();

                //drives left to go to building zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, 0, -AROUND_GATE_DISTANCE, initialAngle);

                straightenOut();

                //drives back to go to other zone
                base.drivetrain.gyroEncoderDrive(DRIVE_SPEED, -(RIGHT_BRIDGE_DISTANCE + 23), 0, initialAngle);

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
            File file = new File(Environment.getExternalStorageDirectory(), "angle");
            FileOutputStream outputStream = new FileOutputStream(file);
            PrintStream printStream = new PrintStream(outputStream);
            printStream.flush();
            int angle = base.gyro.heading() - 180;
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

    public void straightenOut(){
        base.drivetrain.gyroTurn(0.2, 0.8, 180, 2);
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
        base.arms.setRightArmPosition(0.75);
        sleep(300);
    }

    public void releaseBlock(){
        base.arms.setRightArmPosition(0.1);
        sleep(300);
    }

    public void getThirdStoneLeft(){ }
    public void getThirdStoneMiddle(){}
    public void getThirdStoneRight(){}
}
