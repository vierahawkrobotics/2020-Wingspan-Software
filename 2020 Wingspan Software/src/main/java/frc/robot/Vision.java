/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * Add your docs here.
 */
public class Vision {
    public Vision() {

    }

    Thread m_visionThread;

    public void initTargetOverlay() {
        m_visionThread = new Thread(() -> {

            // Get the UsbCamera from CameraServer and set resolution
            UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setResolution(320, 240);

            // Get a CvSink. This will capture Mats from the camera
            // Setup a CvSource. This will send images back to the Dashboard
            CvSink cvSink = CameraServer.getInstance().getVideo();
            CvSource outputStream = CameraServer.getInstance().putVideo("BWrectangle", 320, 240);

            // Mats are very memory expensive. Lets reuse this Mat.
            Mat source = new Mat();
            Mat output = new Mat();

            // This cannot be 'true'. The program will never exit if it is. This
            // lets the robot stop this thread when restarting robot code or
            // deploying.
            // Tell the CvSink to grab a frame from the camera and put it
            // in the source mat. If there is an error notify the output.
            // Send the output the error.
            // skip the rest of the current iteration

            while (!Thread.interrupted()) {
                if (cvSink.grabFrame(source) == 0) {
                    outputStream.notifyError(cvSink.getError());
                    continue;
                }
                // make the image grayscale
                // Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);

                // Put a target-shaped overlay on the camera output
                Imgproc.line(output, new Point(108, 80), new Point(134, 119), new Scalar(255, 255, 255), 2);
                Imgproc.line(output, new Point(134, 114), new Point(186, 119), new Scalar(255, 255, 255), 2);
                Imgproc.line(output, new Point(212, 80), new Point(186, 119), new Scalar(255, 255, 255), 2);

                // Give the output stream a new image to display
                outputStream.putFrame(output);
            }
        });
        m_visionThread.setDaemon(true);
        m_visionThread.start();
    }
}
