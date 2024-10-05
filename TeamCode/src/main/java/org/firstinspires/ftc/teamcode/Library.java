package org.firstinspires.ftc.teamcode;

public class Library {
    double[] wheelSpeeds = new double[2];

    //index 0 == leftSide power
    //index 1 == rightSide power
    public double[] getWheelSpeeds() {
        return wheelSpeeds;
    }
    //sets motor controllers

    public void regularDrive(double forward, double turn) {
        turn *= .75; //slows the turning down
        wheelSpeeds[0] = forward - turn;
        wheelSpeeds[1] = forward + turn;
    }

    public void tankDrive(double left, double right) {
        wheelSpeeds[0] = left;
        wheelSpeeds[1] = right;
    }


}