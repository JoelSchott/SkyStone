package org.firstinspires.ftc.teamcode.SkyStone.SubsidiaryProjects;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class LogTestWriter extends LinearOpMode {

    private File file;
    private FileOutputStream outputStream;
    private PrintStream printStream;

    @Override
    public void runOpMode(){
        try{
            file = new File(Environment.getExternalStorageDirectory(), "number");
            outputStream = new FileOutputStream(file);
            printStream = new PrintStream(outputStream);
        }
        catch(Exception e){
            telemetry.addLine(e.getMessage());
            telemetry.update();
        }
        printStream.println(3);
        telemetry.addLine("sent message");
        telemetry.update();
    }
}
