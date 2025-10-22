package org.firstinspires.ftc.teamcode.subSystems;

import dev.frozenmilk.dairy.core.wrapper.Wrapper;

public class Constants {
    public static boolean ifOpMode(Wrapper OpMode) {
        return OpMode.getState() == Wrapper.OpModeState.STOPPED;
    }

    public static final class claw {
        public enum clawState {
            CLOSED,
            OPEN
        }

        public static final String CLAW = "claw";
        public static final double closePos = 0.3;
        public static final double openPos = 0.7;
    }
}