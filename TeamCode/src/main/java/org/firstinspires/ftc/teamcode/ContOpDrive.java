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

package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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

@TeleOp(name="ContOpDrive", group="Pushbot")
//@Disabled
public class ContOpDrive extends LinearOpMode {

    Config config;

    DcMotor guyGrabberLeft;
    DcMotor guyGrabberRight;

    DcMotor blockGrabber;

    DcMotor leftMotors;
    DcMotor rightMotors;

    TouchSensor blockSenseTop;
    TouchSensor blockSenseBottom;

    static final double MIN = 0.1;                                   /// Sets max range of the guyGrabbber servos.
    static final double MAX = 0.95;                                   /// Sets minimum range of the guyGrabbber servos.

    final double    bgSpeed      = 0.02 ;                            // Sets velocity of the guyGrabber servos.


    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        config = new Config(this);
        config.init();

        config.waitForStart();

        while (opModeIsActive()) {

            telemetry.update();

            setDefaults();

            telemetry.update();

        }

    }

    public void setDefaults() {

        /////////////////////////////Drive Base Code//////////////////////////////////////

        float throttle = -gamepad1.left_stick_y;            // These read the value of the joysticks to move the robot base.
        float direction = gamepad1.right_stick_x;           //
        float left = throttle + direction;                  //
        float right = throttle - direction;                 //

        right = Range.clip(right, -1.0f, 1.0f);             // These set the max and minimum power of the robot base motors in each direction.
        left = Range.clip(left, -1.0f, 1.0f);               //

        config.leftMotors.setPower(left);                   // These just assign the motor powers to the motor base.
        config.rightMotors.setPower(right);

        /*-----------------------------------------------------------------------------*/

        /////////////////////////////Guy Grabber Code///////////////////////////////////

        float guyGrab = gamepad2.left_stick_y;

        guyGrab = Range.clip(guyGrab, -1.0f, 1.0f);


        config.guyGrabberLeft.setPower(guyGrab);
        config.guyGrabberRight.setPower(guyGrab);

        /*-----------------------------------------------------------------------------*/

        //////////////////////////////Block Grabber Code///////////////////////////////////

        float blockGrab = gamepad2.right_stick_y;

        if ((config.blockSenseTop.isPressed() || config.blockSenseBottom.isPressed())) {               //
            if (config.blockSenseTop.isPressed()) {                                             //
                blockGrab = Range.clip(blockGrab, 0.0f, 1.0f);                           //
                config.blockGrabber.setPower(blockGrab);                                        //
            }

            if (config.blockSenseBottom.isPressed()) {
                blockGrab = Range.clip(blockGrab, -1.0f, 0.0f);
                config.blockGrabber.setPower(blockGrab);
            }
        } else {
            blockGrab = Range.clip(blockGrab, -1.0f, 1.0f);
            config.blockGrabber.setPower(blockGrab);
        }

        ////////////////////////////Block Grabber Left & Right/////////////////////////////

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

        // Move both servos to new position.
        config.bglPos = Range.clip(config.bglPos, MIN, MAX);
        config.bgl.setPosition(config.bglPos);

        config.bgrPos = Range.clip(config.bgrPos, MIN, MAX);
        config.bgr.setPosition(config.bgrPos);

        /*------------------------------------------------------------------------------*/


    }

}
