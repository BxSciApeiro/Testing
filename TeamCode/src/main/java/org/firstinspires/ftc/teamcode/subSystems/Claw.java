package org.firstinspires.ftc.teamcode.subSystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subSystems.Constants.claw;
import org.firstinspires.ftc.teamcode.subSystems.Constants.claw.clawState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.frozenmilk.dairy.core.dependency.Dependency;
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation;
import dev.frozenmilk.dairy.core.wrapper.Wrapper;
import dev.frozenmilk.mercurial.commands.Lambda;
import dev.frozenmilk.mercurial.subsystems.SDKSubsystem;
import dev.frozenmilk.mercurial.subsystems.Subsystem;
import dev.frozenmilk.util.cell.Cell;

/* Subsystem Example:
https://github.com/Dairy-Foundation/Dairy/blob/master/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/examples/mercurial/JavaSubsystem.java
 */
public class Claw extends SDKSubsystem {
    // the instance line is for kotlin object class
    // Only change these two lines for each subsystem
    public static final Claw INSTANCE = new Claw();
    private Claw() { }

    /*
        Below are notes for how Dairy identifies and runs the subsystem
        Should not touch unless you want to break something
    */

    // the annotation class we use to attach this subsystem
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface Attach { }

    // Subsystems use the core Feature system of Dairy to be attached to OpModes
    // we need to set up the dependencies, which at its simplest looks like this
    private Dependency<?> dependency = Subsystem.DEFAULT_DEPENDENCY.and(new SingleAnnotation<>(Attach.class));

    // we need to have the getter, rather than the field,
    // but if we actually constructed the dependency every time we ran this, it would slow the program down
    @NonNull
    @Override
    public Dependency<?> getDependency() {
        return dependency;
    }

    @Override
    public void setDependency(@NonNull Dependency<?> dependency) {
        this.dependency = dependency;
    }

    /*
        Below is where the real code starts
    */

    private static Wrapper currentMode;
    private static double targetPos;

    // https://docs.dairy.foundation/Core/templating/using_opmode_lazy_cell
    // Cells are used to keep an object's data up to date
    private final Cell<Servo> clawServo = subsystemCell(() -> getHardwareMap().get(Servo.class, claw.CLAW));

    // Method to obtaining clawState and setting the position of the servo
    public void setTarget(clawState state) {
        switch (state) {
            case OPEN:
                targetPos = claw.openPos;
                break;
            case CLOSED:
                targetPos = claw.closePos;
                break;
        }
        // Must use .get() to get the object from the cell
        clawServo.get().setPosition(targetPos);
    }

    /*
        a hook that runs init code
        there are more hooks that handle the subsystem before teleop runs but they are many
        https://docs.dairy.foundation/Core/features/writing_using_features#writing-using-features
     */
    @Override
    public void preUserInitHook(@NonNull Wrapper opMode) {
        currentMode = opMode;
    }

    /*
        How commands are written
        https://docs.dairy.foundation/Mercurial/commands/lambda
     */
    public Lambda setState(clawState clawState) {
        return new Lambda("setClaw")
                .setInit(() -> setTarget(clawState));
    }

    // Simple getter methods
    public double getPos() {
        return (clawServo.get().getPosition());
    }

    public double getTargetPos() {
        return targetPos;
    }
}