package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Competition.Blue;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcontroller.internal.Core.Utility.CustomPhoneCameraSkyStone;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Sky_Stone_Components.FourWheelMecanum;
import org.firstinspires.ftc.teamcode.SkyStone.MainBaseWebcam;

import java.util.List;


//@Autonomous(name = "Blue Double Stone Arms", group = "Autonomous")
public class BlueDoubleStoneArms extends LinearOpMode {

    private MainBaseWebcam base;

    private final static double DRIVE_SPEED = 1.0;
    private static final double MAX_TURN_SPEED = 0.5;
    private final static double MINIMUM_TURN_SPEED = 0.1;
    private final static double DISTANCE_ADJUSTMENT_SPEED = 0.09;

    private final static double FIRST_DISTANCE = 29.2;
    private final static double FIRST_LEFT_DISTANCE = 13.23;
    private static final double COLLECTING_DISTANCE = 3.11;

    private static final double RIGHT_BRIDGE_DISTANCE = 40;
    private static final double MIDDLE_BRIDGE_DISTANCE = 32;
    private static final double LEFT_BRIDGE_DISTANCE =  24;

    private static final double RIGHT_FIRST_DISTANCE_TO_WALL = 22.2;
    private static final double RIGHT_SECOND_DISTANCE_TO_WALL = 1.71;

    private static final double MIDDLE_FIRST_DISTANCE_TO_WALL = 28.8;
    private static final double MIDDLE_SECOND_DISTANCE_TO_WALL = 9;

    private static final double LEFT_FIRST_DISTANCE_TO_WALL = 35.64;
    private static final double LEFT_SECOND_DISTANCE_TO_WALL = 15.4;

    private static final double AROUND_GATE_DISTANCE = 13;
    private static final double RIGHT_PARKING_DISTANCE = 5;
    private static final double PARKING_DISTANCE = 15;


    private CustomPhoneCameraSkyStone.SkyStonePosition position = CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN;

    @Override
    public void runOpMode(){

        base = new MainBaseWebcam(hardwareMap,telemetry,this);
        base.init();
        base.drivetrain.setInitalAngle(0);

        telemetry.clearAll();
        telemetry.addLine("May the Force be with us");
        telemetry.update();

        waitForStart();

        base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.LEFT, 13.5, 4);

        base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 0, 2);

        base.getTelemetry().addData("front distance is " , base.frontRange.customDistanceInInches());
        base.getTelemetry().addData("left distance is ", base.leftRange.customDistanceInInches());
        base.getTelemetry().update();

        frontRangeDriveToDistance(FIRST_DISTANCE);
        leftRangeDriveToDistance(FIRST_LEFT_DISTANCE);

        base.drivetrain.setPowers(0);

        sleep(800);

        List<Recognition> stones = base.webcam.getObjects();
        position = CustomPhoneCameraSkyStone.BLUETwoStonesGetPosition(stones);
        if (position == CustomPhoneCameraSkyStone.SkyStonePosition.UNKNOWN){
            position = CustomPhoneCameraSkyStone.SkyStonePosition.MIDDLE;
        }

        for (Recognition stone : stones){
            double midPoint = (stone.getRight() + stone.getLeft())/2.0;
            telemetry.addLine("Saw " + stone.getLabel() + " with confidence " + stone.getConfidence() + " with midpoint " + midPoint);
        }
        telemetry.addData("State is ", position);
        telemetry.update();

        switch(position){
            case LEFT:

                //drives back after seeing stones
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, 6.5, 4);

                //strafes left next to the stones
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.LEFT, 12, 6);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to specific distance from both walls
                //frontRangeDriveToDistance(FAR_LEFT_DISTANCE_TO_WALL);

                //rotates to initial angle
                //base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.RIGHT, 10, 4);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drive to other zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, LEFT_BRIDGE_DISTANCE, 6);

                releaseBlock();

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to second stone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.FORWARD, LEFT_BRIDGE_DISTANCE + 17.5, 8);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives left next to blocks
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.LEFT, 9, 5);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to distance from both walls
                frontRangeDriveToDistance(LEFT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.RIGHT, 10, 6);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives back to go to other zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, LEFT_BRIDGE_DISTANCE + 18, 8);

                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20, 3);

                releaseBlock();

                //park
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.FORWARD, 12, 5);


                break;

            case MIDDLE:

                //drives back after seeing stones
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, 1, 4);

                //strafes left next to the stones
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.LEFT, 12, 6);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to specific distance from both walls
                frontRangeDriveToDistance(MIDDLE_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.RIGHT, 10, 4);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drive to other zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, MIDDLE_BRIDGE_DISTANCE, 6);

                releaseBlock();

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to second stone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.FORWARD, MIDDLE_BRIDGE_DISTANCE + 18, 8);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives left next to blocks
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.LEFT, 8.7, 5);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to distance from both walls
                frontRangeDriveToDistance(MIDDLE_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.RIGHT, 10, 6);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives back to go to other zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, MIDDLE_BRIDGE_DISTANCE + 17.5, 8);

                //turns to face parking location
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20, 3);

                releaseBlock();

                //park
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.FORWARD, 12, 5);

                break;

            case RIGHT:

                //drives forward after seeing stones
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.FORWARD, 6, 4);

                //strafes left next to the stones
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.LEFT, 12, 6);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to specific distance from both walls
                frontRangeDriveToDistance(RIGHT_FIRST_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                grabBlock();

                //drive right to go to building zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.RIGHT, 10, 4);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drive to other zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, RIGHT_BRIDGE_DISTANCE, 6);

                releaseBlock();

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to second stone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.FORWARD, RIGHT_BRIDGE_DISTANCE + 18, 8);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives left next to blocks
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.LEFT, 8, 5);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives to distance from both walls
                frontRangeDriveToDistance(RIGHT_SECOND_DISTANCE_TO_WALL);
                leftRangeDriveToDistance(COLLECTING_DISTANCE);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                grabBlock();

                //drives right to go to building zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.RIGHT, 9.5, 6);

                //rotates to initial angle
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED,MAX_TURN_SPEED, 0, 2);

                //drives back to go to other zone
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.BACK, RIGHT_BRIDGE_DISTANCE + 17, 8);

                //turns to face parking location
                base.drivetrain.gyroTurn(MINIMUM_TURN_SPEED, MAX_TURN_SPEED, 20 ,3);

                releaseBlock();

                //park
                base.drivetrain.encoderDrive(DRIVE_SPEED, FourWheelMecanum.Direction.FORWARD, 13, 5);

                break;
        }

    }

    private void frontRangeDriveToDistance(double distance){
        if (base.frontRange.customDistanceInInches() > distance){
            while (base.frontRange.customDistanceInInches() > distance){
                base.drivetrain.setPowers(DISTANCE_ADJUSTMENT_SPEED);
            }
        }
        else if (base.frontRange.customDistanceInInches() < distance){
            while (base.frontRange.customDistanceInInches() < distance){
                base.drivetrain.setPowers(-DISTANCE_ADJUSTMENT_SPEED);
            }
        }
    }

    private void leftRangeDriveToDistance(double distance){
//        if (base.leftRange.customDistanceInInches() > distance){
//            while (base.leftRange.customDistanceInInches() > distance){
//                base.drivetrain.frontRight.setPower(DISTANCE_ADJUSTMENT_SPEED);
//                base.drivetrain.frontLeft.setPower(-DISTANCE_ADJUSTMENT_SPEED);
//                base.drivetrain.backLeft.setPower(DISTANCE_ADJUSTMENT_SPEED);
//                base.drivetrain.backRight.setPower(-DISTANCE_ADJUSTMENT_SPEED);
//            }
//        }
//        else if (base.leftRange.customDistanceInInches() < distance){
//            while (base.leftRange.customDistanceInInches() < distance){
//                base.drivetrain.frontRight.setPower(-DISTANCE_ADJUSTMENT_SPEED);
//                base.drivetrain.frontLeft.setPower(DISTANCE_ADJUSTMENT_SPEED);
//                base.drivetrain.backLeft.setPower(-DISTANCE_ADJUSTMENT_SPEED);
//                base.drivetrain.backRight.setPower(DISTANCE_ADJUSTMENT_SPEED);
//            }
//        }
    }

    private void grabBlock(){
//        base.arms.setLeftPower(1);
//        sleep(350);
//        base.arms.setLeftPower(0.3);
    }

    private void releaseBlock(){
//        try{
//            wait(2000);}
////        catch(Exception e){
////            e.printStackTrace();
////        }
////        base.arms.setLeftPower(-1);
////        sleep(600);
////        base.arms.setLeftPower(0);
    }
}
