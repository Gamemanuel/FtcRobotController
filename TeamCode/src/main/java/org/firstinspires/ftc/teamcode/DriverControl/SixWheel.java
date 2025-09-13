package org.firstinspires.ftc.teamcode.DriverControl;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

import java.util.List;
import java.util.Stack;


// adds to list of teleop programs as SixWheelDrive
@TeleOp(name = "SixWheelDrive", group = "driving")
public class SixWheel extends LinearOpMode {

    RobotClass robot;
    Limelight3A limelight;
    RobotUtils utils;

    String MOTIF;

    // makes it so I can access the the motors from different children classes
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);

        // This Creates A new Utils object. it thinks that the robot is null but that is because it has not been created yet.
        // you must initialize this object otherwise it thinks that the function is static and the function errors  .
        utils = new RobotUtils(robot, telemetry);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // Set to your AprilTag pipeline
        limelight.start();

        // waits for you to click the start button
        waitForStart();

        // while the program is running
        while (opModeIsActive()) {
            utils.drive(gamepad1.left_stick_y, gamepad1.right_stick_x);

            // get the april tag ID
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                // Get list of detected AprilTags
                List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

                for (LLResultTypes.FiducialResult fr : fiducials) {
                    int tagId = fr.getFiducialId();  // Correct usage
                    telemetry.addData("AprilTag ID", tagId);
                    // You can also access fr.getFamily(), fr.getTargetXDegrees(), etc.

                    // convert tag ID to corresponding MOTIF pattern
                    // 21 = GPP, 22 = PGP, 23 = PPG
//                    if (tagId == 21) {
//                        MOTIF = "GPP";
//                    }
//                    if (tagId == 22) {
//                        MOTIF = "PGP";
//                    }
//                    if (tagId == 23) {
//                        MOTIF = "PGG";
//                    }

                    // testing new idea should work the same as above /\
                    switch (tagId) {
                        case 21:
                            MOTIF = "GPP";
                            break;
                        case 22:
                            MOTIF = "PGP";
                            break;
                        case 23:
                            MOTIF = "PGG";
                            break;
                        default:
                            telemetry.addData("Unexpected tagId", tagId);
                    }

                    // display the telemetry value
                    telemetry.addData("motif", MOTIF);
                }
            } else {
                telemetry.addData("Limelight", "No valid AprilTag result");
            }
            telemetry.update();


        }
    }
}
