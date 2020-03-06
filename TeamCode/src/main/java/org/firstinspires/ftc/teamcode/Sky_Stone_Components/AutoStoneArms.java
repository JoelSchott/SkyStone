package org.firstinspires.ftc.teamcode.Sky_Stone_Components;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.internal.Core.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.Core.RobotComponent;

public class AutoStoneArms extends RobotComponent{

    public Servo leftArm;
    public Servo leftClamp;
    public Servo rightArm;
    public Servo rightClamp;

    public final static double RIGHT_ARM_UP_POSITION = 0.3;
    public final static double RIGHT_ARM_DOWN_POSITION = 0.64;

    public final static double LEFT_ARM_UP_POSITION = 0.2;
    public final static double LEFT_ARM_DOWN_POSITION = 0.68;

    public final static double RIGHT_CLAMP_OPEN_POSITION = 0.10;
    public final static double RIGHT_CLAMP_GRAB_POSITION = 0.9;
    public final static double RIGHT_CLAMP_SHUT_POSITION = 1;

    public final static double LEFT_CLAMP_OPEN_POSITION = 0;
    public final static double LEFT_CLAMP_GRAB_POSITION = 0.85;
    public final static double LEFT_CLAMP_SHUT_POSITION = 1;

    public AutoStoneArms(RobotBase base){
        super(base);
        leftArm = base.getMapper().mapServo("leftArm", Servo.Direction.FORWARD);
        rightArm = base.getMapper().mapServo("rightArm", Servo.Direction.REVERSE);
        leftClamp = base.getMapper().mapServo("leftClamp", Servo.Direction.FORWARD);
        rightClamp = base.getMapper().mapServo("rightClamp", Servo.Direction.REVERSE);
        raiseLeftArm();
        raiseRightArm();
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

    public void clampLeftClamp(){
        leftClamp.setPosition(LEFT_CLAMP_GRAB_POSITION);
    }
    public void openLeftClamp(){leftClamp.setPosition(LEFT_CLAMP_OPEN_POSITION);}
    public void shutLeftClamp(){leftClamp.setPosition(LEFT_CLAMP_SHUT_POSITION);}

    public void clampRightClamp(){ rightClamp.setPosition(RIGHT_CLAMP_GRAB_POSITION); }
    public void openRightClamp(){rightClamp.setPosition(RIGHT_CLAMP_OPEN_POSITION);}
    public void shutRightClamp(){rightClamp.setPosition(RIGHT_CLAMP_SHUT_POSITION);}

    public void stop(){
    }
}

