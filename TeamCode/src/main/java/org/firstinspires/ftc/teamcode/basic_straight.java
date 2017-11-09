
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "basic_straight", group = "Concept")
public class basic_straight extends LinearOpMode {

    private autoConfig config;

    public void runOpMode() throws InterruptedException {
        config.init();
        config.grab("c");
        if (opModeIsActive()) {
            config.moveTime(.5, 3000);
            config.grab("o");
        }
    }
    }

