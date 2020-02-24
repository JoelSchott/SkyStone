package org.firstinspires.ftc.teamcode.Sky_Stone_Components;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcontroller.internal.Core.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.Core.RobotComponent;

public class Foundation extends RobotComponent {

    public CRServo rightFoundationServo;
    public CRServo leftFoundationServo;

    public Foundation(RobotBase base){
        super(base);
        rightFoundationServo = base().getMapper().mapCRServo("rightFoundation", CRServo.Direction.FORWARD);
        leftFoundationServo = base().getMapper().mapCRServo("leftFoundation", CRServo.Direction.FORWARD);
    }

    public void moveRightServo(double power){
        rightFoundationServo.setPower(power);
    }
    public void moveLeftServo(double power){
        leftFoundationServo.setPower(power);
    }

    public void stop(){
        rightFoundationServo.setPower(0);
        leftFoundationServo.setPower(0);
    }

}
