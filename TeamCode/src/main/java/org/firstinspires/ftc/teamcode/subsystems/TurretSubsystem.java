package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.SubsystemBase;

@Config
public class TurretSubsystem extends SubsystemBase {

    CRServo Turret;

    public static PIDFCoefficients llPidfCoeffs = new PIDFCoefficients(0.017, 0, 0.001, 0);
    public static double Minimum = 0.043;


    public TurretSubsystem(HardwareMap hMap) {
        Turret = hMap.get(CRServo.class, "turntable");
    }

    public void setTurretPower(double power) {
        Turret.setPower(power);
    }

    @Override
    public void periodic() {
        FtcDashboard.getInstance().getTelemetry().addData("turret power", Turret.getPower());
    }

}
