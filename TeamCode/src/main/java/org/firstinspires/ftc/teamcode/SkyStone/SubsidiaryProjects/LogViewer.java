package org.firstinspires.ftc.teamcode.SkyStone.SubsidiaryProjects;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LogViewer extends LinearOpMode {


    @Override
    public void runOpMode(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "log");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = br.readLine();

            br.close();
            telemetry.addLine("Successfully read file");
            telemetry.update();
        }
        catch(Exception e){
            telemetry.addLine("problem with i/o");
            telemetry.addLine(e.getMessage());
            telemetry.update();
        }
    }

}
