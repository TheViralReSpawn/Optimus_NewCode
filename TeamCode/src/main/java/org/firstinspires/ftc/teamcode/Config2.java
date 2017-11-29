package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Michael on 14/09/2017.
 */
public class Config2 {

    public ElapsedTime runtime = new ElapsedTime();

    private HardwareMap hardwareMap;
    private LinearOpMode opMode;
    private Telemetry telemetry;



    Servo bgl; //These are actuall for the guy grabber.
    Servo bgr;

    Servo ogl;
    Servo ogr;



    DcMotor blockGrabber;

    DigitalChannel touchBottom;
    DigitalChannel touchTop;



    DcMotor guyGrabberLeft;
    DcMotor guyGrabberRight;


    DcMotor driveLeft;
    DcMotor driveRight;

    Servo atl;
    Servo atr;

    double          bglPos;                  // Servo safe position
    double          bgrPos;                  // Servo safe position

    double oglPos;
    double ogrPos;

    double atlPos;
    double atrPos;

    public Config2(final LinearOpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.telemetry = opMode.telemetry;
    }

    public void init() throws InterruptedException {

        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        driveRight = hardwareMap.dcMotor.get("driveRight");
        driveLeft.setDirection(DcMotorSimple.Direction.FORWARD);


        guyGrabberLeft = hardwareMap.dcMotor.get("guyGrabberLeft");
        guyGrabberLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        guyGrabberRight = hardwareMap.dcMotor.get("guyGrabberRight");
        guyGrabberRight.setDirection(DcMotorSimple.Direction.FORWARD);

        bgl = hardwareMap.servo.get("bgl");
        bgl.setDirection(Servo.Direction.FORWARD);
        bglPos = 0.1;

        bgr = hardwareMap.servo.get("bgr");
        bgr.setDirection(Servo.Direction.REVERSE);
        bgrPos = 0.95;

        /////////////////////////////

        ogl = hardwareMap.servo.get("ogl");
        ogl.setDirection(Servo.Direction.FORWARD);
        oglPos = 0.65;

        ogr = hardwareMap.servo.get("ogr");
        ogr.setDirection(Servo.Direction.FORWARD);
        ogrPos = 0.09;

        ///////////////////////

        atl = hardwareMap.servo.get("atl");
        atl.setDirection(Servo.Direction.FORWARD);
        atlPos = 0.1;

        atr = hardwareMap.servo.get("atr");
        atr.setDirection(Servo.Direction.REVERSE);
        atrPos = 0.95;

        ///////////////////////

        blockGrabber = hardwareMap.dcMotor.get("blockGrabber");
        blockGrabber.setDirection(DcMotorSimple.Direction.FORWARD);

        touchBottom = hardwareMap.get(DigitalChannel.class, "touchBottom");
        touchBottom.setMode(DigitalChannel.Mode.INPUT);

        touchTop = hardwareMap.get(DigitalChannel.class, "touchTop");
        touchTop.setMode(DigitalChannel.Mode.INPUT);
        touchBottom.setState(true);


    }

    public void waitForStart() throws InterruptedException {

        opMode.waitForStart();
        runtime.reset();
        telemetry.update();

    }

    public void fullTelemetry() {

        telemetry.addData("Say", "Hello Driver");

        telemetry.addData("Block Grabber Servo Left", ogl.getPosition());
        telemetry.addData("Block Grabber Servo Right", ogr.getPosition());

        telemetry.addData("Left Drive Motors", driveLeft.getPower());
        telemetry.addData("Right Drive Motors", driveRight.getPower());

        telemetry.addData("Guy Grabber Left", guyGrabberLeft.getPower());
        telemetry.addData("Guy Grabber Right", guyGrabberRight.getPower());

        telemetry.addData("Guy Grabber Servo Left", bgl.getPosition());
        telemetry.addData("Guy Grabber Servo Right", bgr.getPosition());

        telemetry.addData("Block Grabber Servo Left", atl.getPosition());
        telemetry.addData("Block Grabber Servo Right", atr.getPosition());

        //telemetry.addData("Block Grabber Motor", blockGrabber.getPower());

        //telemetry.addData("Gamepad 2 Right Stick Y",);

        telemetry.update();
    }

}
