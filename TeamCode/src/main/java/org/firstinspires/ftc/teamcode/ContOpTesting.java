/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Config2;

import static org.firstinspires.ftc.teamcode.Config2.aMAXl;
import static org.firstinspires.ftc.teamcode.Config2.aMAXr;
import static org.firstinspires.ftc.teamcode.Config2.aMINl;
import static org.firstinspires.ftc.teamcode.Config2.aMINr;
import static org.firstinspires.ftc.teamcode.Config2.oMAXl;
import static org.firstinspires.ftc.teamcode.Config2.oMAXr;
import static org.firstinspires.ftc.teamcode.Config2.oMINl;
import static org.firstinspires.ftc.teamcode.Config2.oMINr;
//potato

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

//yay

@TeleOp(name="Pushbot: Testing Config", group="Pushbot")
//@Disabled
public class ContOpTesting extends LinearOpMode {

    Config2 config;

    private ElapsedTime runtime = new ElapsedTime();

    static final double MIN = 0.1;
    static final double MAX = 0.95;

    final double    bgSpeed      = 0.02 ;                            // sets rate to move servo

    ///////////////////////////////////


    final double    ogSpeed      = 0.05 ;                            // sets rate to move servo

    final double    bgmSpeed     = 0.10 ;

    @Override
    public void runOpMode() throws InterruptedException {

        config = new Config2(this);
        config.init();

        config.waitForStart();

        while (opModeIsActive()) {

            telemetry.update();

            setDefaults();

            telemetry.update();

        }

    }

    public void setDefaults() {

        ////////////////////////////Drive Motors/////////////////////////////////////////////////

        float throttle = -gamepad1.left_stick_y;
        float direction = gamepad1.right_stick_x;
        float left = throttle + direction;
        float right = throttle - direction;

        left = Range.clip(left, -1.0f, 1.0f);
        right = Range.clip(right, -1.0f, 1.0f);

        //config.driveLeft.setPower(left);
        //config.driveRight.setPower(right);

        config.driveLeft.setPower(-gamepad1.left_stick_y);
        config.driveRight.setPower(gamepad1.right_stick_y);

        ////////////////////////////Roller Grabber Motors///////////////////////////////////////////

        if (!gamepad1.y) {
            config.rollerLeft.setPower(1.0);
            config.rollerRight.setPower(1.0);
        } else if (!gamepad1.x) {
            config.rollerLeft.setPower(-1.0);
            config.rollerRight.setPower(-1.0);
        } else {
            config.rollerLeft.setPower(0.0);
            config.rollerRight.setPower(0.0);
        }

        if (gamepad1.right_stick_y > 0.0) {
            config.raiserLeft.setPower(gamepad1.right_stick_y);
            config.raiserLeft.setPower(gamepad1.right_stick_y);
        } else if (gamepad1.right_stick_y < 0.0) {
            config.raiserLeft.setPower(gamepad1.right_stick_y);
            config.raiserLeft.setPower(gamepad1.right_stick_y);
        } else {
            config.raiserLeft.setPower(0.0);
            config.raiserLeft.setPower(0.0);
        }


        ////////////////////////////Block Grabber & Tilting Servos///////////////////////////////

        if (gamepad1.left_bumper) {
            config.oglPos = 1;
            config.ogrPos = 1;
        } else if (gamepad1.right_bumper) {
            config.oglPos = 0;
            config.ogrPos = 0;
        }

        //-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~//

        //float tilt = gamepad2.left_stick_x;

        ////////////////////////////Guy Grabber Servo Left & Right////////////////////////////////


        ///////////////////////////Block Grabber Motor /////////////////////////////////////////


        //////////////////////////Touch Sensor ////////////////////////////////////////////////

        //config.blockGrabber.setPower(blockGrab);

        //////////////////////////////////////////////////////////////////////////////////////

        if (gamepad1.b) {                //Opens
            config.atlPos = 1;
            config.atrPos = 1;
        } else if (gamepad1.a) {          //Closes
            config.atlPos = 0;
            config.atrPos = 0;
        }

        //////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////

        config.oglPos = Range.clip(config.oglPos, oMINl, oMAXl);
        config.ogl.setPosition(config.oglPos);

        config.ogrPos = Range.clip(config.ogrPos, oMINr, oMAXr);
        config.ogr.setPosition(config.ogrPos);



        config.atlPos = Range.clip(config.atlPos, aMINl, aMAXl);
        config.atl.setPosition(config.atlPos);

        config.atrPos = Range.clip(config.atrPos, aMINr, aMAXr);
        config.atr.setPosition(config.atrPos);


        //telemetry.addData("Bottom touch sensor state", myState);
        config.fullTelemetry();

        /*------------------------------------------------------------------------------*/

    }

}
