package org.firstinspires.ftc.teamcode.Sky_Stone_Components;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcontroller.internal.Core.RobotBase;
import org.firstinspires.ftc.robotcontroller.internal.Core.RobotComponent;

public class Foundation extends RobotComponent {
    public CRServo foundationServo;


    public Foundation(RobotBase base){
        super(base);
        foundationServo = base().getMapper().mapCRServo("foundation", CRServo.Direction.FORWARD);
    }

    public void moveServo(double power){

        foundationServo.setPower(power);
    }


    public void stop(){
        foundationServo.setPower(0);
    }

}
