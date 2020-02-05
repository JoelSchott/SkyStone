package org.firstinspires.ftc.teamcode.SkyStone.SubsidiaryProjects;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

@TeleOp(name = "log writer")
public class LogTestWriter extends LinearOpMode {

    private File file;

    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;

    private PrintWriter pw;

    private FileOutputStream outputStream;
    private PrintStream printStream;

    @Override
    public void runOpMode(){

        waitForStart();

        try{
            file = new File(Environment.getExternalStorageDirectory(), "number");
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
            pw.println("anish is a lumberjack");
            pw.close();
        }
        catch(Exception e){
            telemetry.addLine(e.getMessage());
            telemetry.update();
        }

        telemetry.addLine("sent message");
        telemetry.update();

        while(opModeIsActive()){

        }

    }
}
