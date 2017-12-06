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

    Servo ogl;
    Servo ogr;

    DcMotor rollerLeft;
    DcMotor rollerRight;

    DcMotor driveLeft;
    DcMotor driveRight;

    DcMotor raiserRight;
    DcMotor raiserLeft;

    Servo atl;
    Servo atr;

    double oglPos;
    double ogrPos;

    double atlPos;
    double atrPos;

    static final double aMINr = 0.1;
    static final double aMAXr = 0.7;

    static final double aMINl = 0.1;
    static final double aMAXl = 0.7;


    static final double oMINr = 0.09;
    static final double oMAXr = 0.95;

    static final double oMINl = 0.01;
    static final double oMAXl = 0.65;

    public Config2 (final LinearOpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.telemetry = opMode.telemetry;
    }

    public void init() throws InterruptedException {

        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        driveRight = hardwareMap.dcMotor.get("driveRight");
        driveLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        rollerLeft = hardwareMap.dcMotor.get("rollerLeft");
        rollerLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        rollerRight = hardwareMap.dcMotor.get("rollerRight");
        rollerRight.setDirection(DcMotorSimple.Direction.REVERSE);

        raiserLeft = hardwareMap.dcMotor.get("raiserLeft");
        raiserLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        raiserRight = hardwareMap.dcMotor.get("raiserRight");
        raiserRight.setDirection(DcMotorSimple.Direction.REVERSE);

        /////////////////////////////

        ogl = hardwareMap.servo.get("ogl");
        ogl.setDirection(Servo.Direction.FORWARD);
        oglPos = 0.65;

        ogr = hardwareMap.servo.get("ogr");
        ogr.setDirection(Servo.Direction.REVERSE);
        ogrPos = 0.09;

        ///////////////////////

        atl = hardwareMap.servo.get("atl");
        atl.setDirection(Servo.Direction.REVERSE);

        atr = hardwareMap.servo.get("atr");
        atr.setDirection(Servo.Direction.FORWARD);

        ///////////////////////

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

        telemetry.addData("ATL Servo Left", atl.getPosition());
        telemetry.addData("ATR Servo Right", atr.getPosition());

        //telemetry.addData("Block Grabber Motor", blockGrabber.getPower());

        //telemetry.addData("Gamepad 2 Right Stick Y",);

        telemetry.update();
    }

}
