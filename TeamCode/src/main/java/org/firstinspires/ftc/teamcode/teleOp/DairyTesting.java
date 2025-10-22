package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Constants.claw.clawState;
import org.firstinspires.ftc.teamcode.teleOp.robotFunctions.RobotControl;
import dev.frozenmilk.mercurial.Mercurial;
import dev.frozenmilk.mercurial.bindings.BoundGamepad;

// MUST ADD THE ATTACH FOR EACH SUBSYSTEM
@Mercurial.Attach
@Claw.Attach
@TeleOp
public class DairyTesting extends OpMode {
    /*
     The class goes through another class because init must ONLY done in init() method from OpMode class
     e.i. HardwareMap or Telemetry don't exist before init
     (you can try to obtain them before init but i don't remember if we tried to)

     This is how we used to make the robot's functions through RobotControl
     However with Dairy, you can run them independently and in a reusable way as subsystems
     rather than having to call them as classes where they aren't unified

     The drivetrain isn't a subsystem yet, because i'm not doing all that, you guys got that 🙏
     */
    private final RobotControl bot = new RobotControl();

    @Override
    public void init() {
        BoundGamepad Driver = Mercurial.gamepad1(); // Initialize gamepad 1
        // When b is pressed (onTrue) run setState command to clawState.OPEN (enum)
        Driver.b().onTrue(Claw.INSTANCE.setState(clawState.OPEN));
        // When a is pressed (onTrue) run setState command to clawState.CLOSED (enum)
        Driver.a().onTrue(Claw.INSTANCE.setState(clawState.CLOSED));

        bot.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        bot.run(gamepad1);

        // Print to the device's console
        telemetry.addData("currentPos: ", Claw.INSTANCE.getPos());
        telemetry.addData("targetPos: ", Claw.INSTANCE.getTargetPos());
        // Must include .update() otherwise you will be looking at nothing
        telemetry.update();
    }
}