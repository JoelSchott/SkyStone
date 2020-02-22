package org.firstinspires.ftc.teamcode.Sky_Stone_Components;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.Core.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.Core.RobotComponent;

public class AutoStoneArms extends RobotComponent{

    public Servo leftArm;
    public CRServo leftClamp;
    public Servo rightArm;
    public CRServo rightClamp;

    public final static double RIGHT_ARM_UP_POSITION = 0.7;
    public final static double RIGHT_ARM_DOWN_POSITION = 0.3;
    public final static double LEFT_ARM_UP_POSITION = 0.52;
    public final static double LEFT_ARM_DOWN_POSITION = 0.85;


    public AutoStoneArms(RobotBase base){
        super(base);
        leftArm = base.getMapper().mapServo("leftArm", Servo.Direction.FORWARD);
        rightArm = base.getMapper().mapServo("rightArm", Servo.Direction.REVERSE);
        leftClamp = base.getMapper().mapCRServo("leftClamp", CRServo.Direction.FORWARD);
        rightClamp = base.getMapper().mapCRServo("rightClamp", CRServo.Direction.FORWARD);
        raiseRightArm();
        raiseLeftArm();
    }

    public void raiseRightArm(){
        rightArm.setPosition(RIGHT_ARM_UP_POSITION);
    }
    public void lowerRightArm(){
        rightArm.setPosition(RIGHT_ARM_DOWN_POSITION);
    }
    public void setLeftArmPosition(double position){
        leftArm.setPosition(position);
    }
    public void raiseLeftArm(){
        leftArm.setPosition(LEFT_ARM_UP_POSITION);
    }
    public void lowerLeftArm(){
        leftArm.setPosition(LEFT_ARM_DOWN_POSITION);
    }
    public void setRightArmPosition(double position){rightArm.setPosition(position);}
    public void leftClampInPower(double power){
        leftClamp.setPower(Math.abs(power));
    }
    public void leftClampOutPower(double power){
        leftClamp.setPower(-Math.abs(power));
    }
    public void rightClampInPower(double power){
        rightClamp.setPower(Math.abs(power));
    }
    public void rightClampOutPower(double power){
        rightClamp.setPower(-Math.abs(power));
    }

    public void stop(){
        leftClamp.setPower(0);
        rightClamp.setPower(0);
    }
}

