package org.firstinspires.ftc.teamcode.SkyStone.SubsidiaryProjects;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LogTestReader extends LinearOpMode {

    @Override
    public void runOpMode(){
        int angle = -1;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "number");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            telemetry.addLine("line is " + line);
            telemetry.update();
            angle = Integer.parseInt(line);
            br.close();
            telemetry.addData("Successfully set angle to ", angle);
            telemetry.update();
        }
        catch(Exception e){
            telemetry.addLine("problem with i/o");
            telemetry.addLine(e.getMessage());
            telemetry.update();
        }
        telemetry.addData("angle is ", angle);
        telemetry.update();

    }

}
