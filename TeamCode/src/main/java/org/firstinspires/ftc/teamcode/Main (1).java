package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
// adds to list of teleop programs
@TeleOp(name = "Drivetrains", group = "driving")

public class Main extends LinearOpMode {
    // makes it so i can access the the motors from different children classes
        DcMotor left1;
        DcMotor left2;
        DcMotor right1;
        DcMotor right2;
    @Override
    public void runOpMode() throws InterruptedException {
        // sets the different motors
        left1 = hardwareMap.get(DcMotor.class, "left1");
        left2 = hardwareMap.get(DcMotor.class, "left2");
        right1 = hardwareMap.get(DcMotor.class, "right1");
        right2 = hardwareMap.get(DcMotor.class, "right2");
        //use the following bit of code ONLY of your motors do not all go forwards
            //left1.setDirection(DcMotor.Direction.REVERSE);
            //left2.setDirection(DcMotor.Direction.REVERSE);
            //right1.setDirection(DcMotor.Direction.FORWARD);
            //right2.setDirection(DcMotor.Direction.FORWARD);

        //tank drive setting
        //spinleft(gamepad1.left_stick_y);
        // the power has to be between -1 and 1
        // this spins the 2 left motors for the same power
        //spinright(gamepad1.right_stick_y);
    // one joystick controls turning and going straight
        //straight and backwards
        wholedrivetrain((-1 * gamepad1.left_stick_y)); // Note: pushing stick forward gives negative value
        //turning
            //left
        spinleft((-1 * gamepad1.left_stick_x));
            //right
        spinright(gamepad1.left_stick_x);
    // strafing ( mecanum only )
        strafeleft(gamepad1.left_bumper);
        straferight(gamepad1.right_bumper);
    }
    public void spinleft ( double leftpower) {
        // turns the 2 individual motors into a motor group
        left1.setPower(leftpower);
        left2.setPower(leftpower);
    }
    public void spinright ( double rightpower) {
        // turns the 2 individual motors into a motor group ( right side )
        right1.setPower(rightpower);
        right2.setPower(rightpower);
    }
    public void wholedrivetrain ( double power) {
        spinright(power);
        spinleft(power);
    }
    // the following functions are for mecanums strafing
    public void strafeleft (boolean straifleft) {
        boolean True = true;
        if (straifleft == True); {
           straifmotion(1);
        }

    }
    public void straferight (boolean straifright) {
        boolean True = true;
        if (straifright == True); {
            straifmotion(1);
        }

    }
    //  note this function should only be used with -1, or 1
    // the defalt way is also left
    public void straifmotion (int posorneg ) {
        left1.setPower(-1 * posorneg);
        left2.setPower(posorneg);
        right1.setPower(posorneg);
        right2.setPower(-1 * posorneg);

    }

}
