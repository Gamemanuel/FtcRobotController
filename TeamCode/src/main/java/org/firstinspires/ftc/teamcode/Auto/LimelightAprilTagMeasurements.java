package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous; // Added annotation for visibility in Driver Station

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

// Uncommented the Autonomous annotation so it appears in the list
@Autonomous(name="Limelight MT1 Distance Calc")
public class LimelightAprilTagMeasurements extends OpMode {
    RobotClass robot;
    RobotUtils utils;
    public double distance;

    @Override
    public void init() {
        robot = new RobotClass(hardwareMap);
        utils = new RobotUtils(robot, telemetry, null, null, null);
    }

    @Override
    public void start() {
        robot.limelight.start();
        // Ensure the Limelight is set to use the default MegaTag pipeline in its web interface (Pipeline 0 is default)
    }

    public void loop() {
        // --- REMOVED: Orientation update not needed for MT1 ---
        // YawPitchRollAngles orientation = robot.imu.getRobotYawPitchRollAngles();
        // robot.limelight.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

        LLResult llResult = robot.limelight.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            // Changed from getBotpose_MT2() to getBotpose_MT1()
            Pose3D botpose = llResult.getBotpose();

            // The Pose3D z value (tz) represents the distance from the camera to the target.
            // Depending on the camera mounting direction, you might need to use getX() or getY(),
            // but the Z axis usually points "forward" from the lens in Limelight's coordinate system.
//            distance = botpose.getTz();

            telemetry.addData("Calculated Distance (tz)", distance);
            telemetry.addData("Target x (tx)", llResult.getTx());
            telemetry.addData("Target Area (ta)", llResult.getTa());
            telemetry.addData("Botpose MT1", botpose.toString());
            telemetry.update();
        }
    }
}
