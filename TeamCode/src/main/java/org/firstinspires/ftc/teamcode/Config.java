package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Michael on 14/09/2017.
 */
public class Config {

    public ElapsedTime runtime = new ElapsedTime();

    private HardwareMap hardwareMap;
    private LinearOpMode opMode;
    private Telemetry telemetry;

    DcMotor guyGrabberLeft;
    DcMotor guyGrabberRight;

    DcMotor blockGrabber;

    DcMotor leftMotors;
    DcMotor rightMotors;

    Servo bgl;
    Servo bgr;

    TouchSensor blockSenseTop;
    TouchSensor blockSenseBottom;

    double          bglPos;                  // Servo safe position
    double          bgrPos;                  // Servo safe position

    static final double MIN = 0.1;
    static final double MAX = 0.95;

    final double    bgSpeed      = 0.02 ;                            // sets rate to move servo

    public Config(final LinearOpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.telemetry = opMode.telemetry;
    }

    public void init() throws InterruptedException {

        //////////////////////Assigning Hardware Names///////////////////////////

        guyGrabberLeft = hardwareMap.dcMotor.get("guyGrabberLeft");
        guyGrabberRight = hardwareMap.dcMotor.get("guyGrabberRight");

        blockGrabber = hardwareMap.dcMotor.get("blockGrabber");

        leftMotors = hardwareMap.dcMotor.get("leftMotors");
        rightMotors = hardwareMap.dcMotor.get("rightMotors");

        bgl = hardwareMap.servo.get("bgl");
        bgl.setDirection(Servo.Direction.FORWARD);
        bglPos = 0.1;

        bgr = hardwareMap.servo.get("bgr");
        bgr.setDirection(Servo.Direction.REVERSE);
        bgrPos = 0.95;

        blockSenseTop = hardwareMap.get(TouchSensor.class, "blockSenseTop");
        blockSenseBottom = hardwareMap.get(TouchSensor.class, "blockSenseBottom");

        //////////////////////Setting Direction of Motors////////////////////////

        guyGrabberLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        guyGrabberRight.setDirection(DcMotorSimple.Direction.FORWARD);

        blockGrabber.setDirection(DcMotorSimple.Direction.FORWARD);

        leftMotors.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotors.setDirection(DcMotorSimple.Direction.FORWARD);

        //////////////////////Initiating Encoder Functions////////////////////////

        leftMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        guyGrabberLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        guyGrabberRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        blockGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        leftMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotors.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        guyGrabberLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        guyGrabberRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        blockGrabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //////////////////////////////////////////////////////////////////////////

    }

    public void waitForStart() throws InterruptedException {

        opMode.waitForStart();
        runtime.reset();
        telemetry.update();

    }

    public void fullTelemetry() {

        telemetry.addData("Say", "Hello Driver");

        telemetry.addData("Guy Grabber Base Motor", guyGrabberLeft.getCurrentPosition());
        telemetry.addData("Guy Grabber Mid Motor", guyGrabberRight.getCurrentPosition());

        telemetry.addData("Block Grabber Motor", blockGrabber.getCurrentPosition());
        telemetry.addData("Block Grabber Servo Left", bgl.getPosition());
        telemetry.addData("Block Grabber Servo Right", bgr.getPosition());

        telemetry.addData("Left Motors", leftMotors.getCurrentPosition());
        telemetry.addData("Right Motors", rightMotors.getCurrentPosition());

        telemetry.update();
    }

    public void stopBot() {

        guyGrabberLeft.setPower(0.0);
        guyGrabberRight.setPower(0.0);

        blockGrabber.setPower(0.0);

        leftMotors.setPower(0.0);
        rightMotors.setPower(0.0);
    }
}

