package org.firstinspires.ftc.teamcode.teleOp.robotFunctions;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotControl {
    private DriveTrain drive;

    public void init(HardwareMap hwMap, Telemetry tele) {
        drive = new DriveTrain(hwMap, tele);
    }

    public void run(Gamepad gamePad) {
        drive.move(gamePad);
    }
}