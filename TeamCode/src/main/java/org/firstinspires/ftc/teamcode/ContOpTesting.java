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

    static final double oMINr = 0.09;
    static final double oMAXr = 0.95;

    static final double oMINl = 0.01;
    static final double oMAXl = 0.65;

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

    public void setDefaults()  {

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

        ////////////////////////////Guy Grabber Motors///////////////////////////////////////////

        float velocity = -gamepad2.right_stick_y;

        config.guyGrabberLeft.setPower(velocity);
        config.guyGrabberRight.setPower(velocity);


        ////////////////////////////Block Grabber & Tilting Servos///////////////////////////////

        if (gamepad1.left_trigger > 0.0) {
            config.oglPos += ogSpeed * (double) gamepad1.left_trigger;
            config.ogrPos += ogSpeed * (double) gamepad1.left_trigger;
        } else if (gamepad1.right_trigger > 0.0) {
            config.oglPos -= ogSpeed * (double) gamepad1.right_trigger;
            config.ogrPos -= ogSpeed * (double) gamepad1.right_trigger;
        } else {
            config.oglPos = config.oglPos;
            config.oglPos = config.oglPos;
        }


         if (gamepad1.left_bumper) {                //Opens
            config.oglPos = oMINl;
            config.ogrPos = oMAXr;
        } else if (gamepad1.right_bumper) {          //Closes
            config.oglPos = oMAXl;
            config.ogrPos = oMINr;
        } else {
            config.oglPos = config.oglPos;
            config.ogrPos = config.ogrPos;
        }

        //-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~//

        //float tilt = gamepad2.left_stick_x;

        ////////////////////////////Guy Grabber Servo Left & Right////////////////////////////////

        if (gamepad2.right_trigger > 0.0) {
            config.bglPos += bgSpeed * gamepad2.right_trigger;
            config.bgrPos -= bgSpeed * gamepad2.right_trigger;
        } else if (gamepad2.left_trigger > 0.0) {
            config.bglPos -= bgSpeed * gamepad2.left_trigger;
            config.bgrPos += bgSpeed * gamepad2.left_trigger;
        } else {
            config.bglPos = config.bglPos;
            config.bgrPos = config.bgrPos;
        }


        ///////////////////////////Block Grabber Motor /////////////////////////////////////////

        float blockGrab = gamepad2.left_stick_y;

        //////////////////////////Touch Sensor ////////////////////////////////////////////////

        if (!config.touchBottom.getState()) {
            telemetry.addData("Bottom touch sensor is: ", " TRUE!");
            telemetry.addData("Top touch sensor is: ", " FALSE!");

            blockGrabCode(1,0);
        } else if (!config.touchTop.getState()) {
            telemetry.addData("Top touch sensor is: ", " TRUE!");
            telemetry.addData("Bottom touch sensor is: ", " FALSE!");

            blockGrabCode(0,-1);
        }  else {
            telemetry.addData("Top touch sensor is: ", " FALSE!");
            telemetry.addData("Bottom touch sensor is: ", " FALSE!");

            blockGrabCode(1,-1);
        }


        //config.blockGrabber.setPower(blockGrab);

        //////////////////////////////////////////////////////////////////////////////////////

        // Move both servos to new position.
        config.bglPos = Range.clip(config.bglPos, MIN, MAX);
        config.bgl.setPosition(config.bglPos);

        config.bgrPos = Range.clip(config.bgrPos, MIN, MAX);
        config.bgr.setPosition(config.bgrPos);


        ///////////////////////////////////////////////////////////////////////////////////////

        config.oglPos = Range.clip(config.oglPos, oMINl, oMAXl);
        config.ogl.setPosition(config.oglPos);

        config.ogrPos = Range.clip(config.ogrPos, oMINr, oMAXr);
        config.ogr.setPosition(config.ogrPos);

        //telemetry.addData("Bottom touch sensor state", myState);
        config.fullTelemetry();

        /*------------------------------------------------------------------------------*/


    }

    public void blockGrabCode(int a, int b) {
        if (gamepad1.dpad_down) {
            config.blockGrabber.setPower( a );
        } else if (gamepad1.dpad_up) {
            config.blockGrabber.setPower( b );
        } else {
            config.blockGrabber.setPower(0);
        }
    }

}
