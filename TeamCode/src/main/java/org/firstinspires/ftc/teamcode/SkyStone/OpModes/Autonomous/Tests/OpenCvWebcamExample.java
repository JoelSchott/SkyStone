package org.firstinspires.ftc.teamcode.SkyStone.OpModes.Autonomous.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class OpenCvWebcamExample extends LinearOpMode {

    OpenCvCamera webcam;

    @Override
    public void runOpMode(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam"), cameraMonitorViewId);
        webcam.openCameraDevice();

        webcam.setPipeline(new CustomPipeline());

        webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("Frame Count", webcam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
            telemetry.addData("Total frame time ms", webcam.getTotalFrameTimeMs());
            telemetry.addData("Pipeline time ms", webcam.getPipelineTimeMs());
            telemetry.addData("Overhead time ms", webcam.getOverheadTimeMs());
            telemetry.addData("Theoretical max FPS", webcam.getCurrentPipelineMaxFps());
            telemetry.update();

            if (gamepad1.a){
                webcam.stopStreaming();
            }
            else if(gamepad1.x)
            {
                webcam.pauseViewport();
            }
            else if(gamepad1.y)
            {
                webcam.resumeViewport();
            }

            sleep(100);
        }
    }

    class CustomPipeline extends OpenCvPipeline{

        @Override
        public Mat processFrame(Mat input){

            Point topLeft = new Point(input.cols()/4,input.rows()/4);
            Point bottomRight = new Point(input.cols()*(3f/4f), input.rows()*(3f/4f));
            Imgproc.rectangle(input, topLeft, bottomRight, new Scalar(0,255,0), 4);

            return input;
        }

    }

}
