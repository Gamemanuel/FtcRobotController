package org.firstinspires.ftc.teamcode.DriverControl;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.RobotClass;
import org.firstinspires.ftc.teamcode.RobotUtils;

// json imports for limelight
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// adds to list of teleop programs as SixWheelDrive
@TeleOp(name = "SixWheelDrive", group = "driving")
public class SixWheel extends LinearOpMode {

    RobotClass robot;

    // Replace with the Limelight's static IP address
    private static final String LIMELIGHT_IP = "192.168.43.11";
    private static final String JSON_URL = "http://" + LIMELIGHT_IP + "/json/results.json";

    // makes it so I can access the the motors from different children classes
    @SuppressLint("DefaultLocale")
    @Override
    public void runOpMode() {
        // gets hardware mapping from RobotClass.java
        robot = new RobotClass(hardwareMap);

        // This Creates A new Utils object. it thinks that the robot is null but that is because it has not been created yet.
        // you must initialize this object otherwise it thinks that the function is static and the function errors  .
        RobotUtils utils = new RobotUtils(robot, telemetry);


        // TODO: ADD a limelight check for april tags on the obelisk
        telemetry.addData("Status", "Initialized. Look for a tag.");
        telemetry.update();

        // waits for you to click the start button
        waitForStart();

        // while the program is running
        while (opModeIsActive()) {
            utils.drive(gamepad1.left_stick_y, gamepad1.right_stick_x);

            String jsonResponse = getJsonFromLimelight();

            if (jsonResponse != null) {
                // Find the index of the tag ID ("tid")
                int tidIndex = jsonResponse.indexOf("\"tid\":");
                if (tidIndex != -1) {
                    // Extract the numerical ID
                    int start = tidIndex + "\"tid\":".length();
                    int end = jsonResponse.indexOf(",", start);
                    if (end == -1) end = jsonResponse.indexOf("}", start);

                    try {
                        String idString = jsonResponse.substring(start, end).trim();
                        double tagID = Double.parseDouble(idString);
                        telemetry.addData("AprilTag ID", tagID);
                    } catch (NumberFormatException e) {
                        telemetry.addData("Error", "Could not parse tag ID");
                    }
                } else {
                    telemetry.addData("Status", "No AprilTag found");
                }
            } else {
                telemetry.addData("Status", "Connection error or no data");
            }
            telemetry.update();
            sleep(100); // Check for a new tag every 100ms
        }
    }
    private String getJsonFromLimelight() {
        try {
            URL url = new URL(JSON_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();
            return content.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
