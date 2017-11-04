package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static com.sun.tools.javac.util.Constants.format;

public class autoConfig {



    public ElapsedTime runtime = new ElapsedTime();
    private HardwareMap hardwareMap;
    private LinearOpMode opMode;
    private Telemetry telemetry;

    //hardware decleration

    Servo bgl;
    Servo bgr;
    //DcMotor blockGrabber;
    //DigitalChannel touchBottom;
    //DigitalChannel touchTop;
    //DcMotor guyGrabberLeft;
    //DcMotor guyGrabberRight;
    DcMotor driveLeft;
    DcMotor driveRight;


    String          grabPos;
    long            time;
    double          bglPos;                  // Servo safe position
    double          bgrPos;                  // Servo safe position
    double          power;
    double          left;
    double          right;
    int             distance;
        //some basic distances
    int matDistMid = 4118;          //36"
    int matDistLeft = 5040;         //43.62"
    int matDistRight = 3250;        //28.37"
    int matInBox = 2291;            //20"

    static final double MIN = 0.1;
    static final double MAX = 0.95;
    final double    bgSpeed      = 0.02 ;                            // sets rate to move servo

    private VuforiaLocalizer vuforia;



    public autoConfig(LinearOpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.telemetry = opMode.telemetry;
    }

    public void init() throws InterruptedException {

        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        driveRight = hardwareMap.dcMotor.get("driveRight");
        driveLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        driveLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        driveRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    //    guyGrabberLeft = hardwareMap.dcMotor.get("guyGrabberLeft");
     //   guyGrabberLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    //    guyGrabberRight = hardwareMap.dcMotor.get("guyGrabberRight");
    //    guyGrabberRight.setDirection(DcMotorSimple.Direction.FORWARD);

        bgl = hardwareMap.servo.get("bgl");
        bgl.setDirection(Servo.Direction.FORWARD);
        bglPos = 0.1;

        bgr = hardwareMap.servo.get("bgr");
        bgr.setDirection(Servo.Direction.REVERSE);
        bgrPos = 0.95;


    }

    public void waitForStart() throws InterruptedException {

        opMode.waitForStart();
        runtime.reset();
        telemetry.update();

    }

    public void fullTelemetry() {

        telemetry.addData("Say", "Hello Driver");

        //telemetry.addData("Left Drive Motors", driveLeft.getPower());
        //telemetry.addData("Right Drive Motors", driveRight.getPower());

   //     telemetry.addData("Guy Grabber Left", guyGrabberLeft.getPower());
   //     telemetry.addData("Guy Grabber Right", guyGrabberRight.getPower());

        telemetry.addData("Block Grabber Servo Left", bgl.getPosition());
        telemetry.addData("Block Grabber Servo Right", bgr.getPosition());

        //telemetry.addData("Block Grabber Motor", blockGrabber.getPower());

        //telemetry.addData("Gamepad 2 Right Stick Y",);

        telemetry.update();
    }

//    updatepublic static void VuStart() {
//
//        final String TAG = "Vuforia VuMark Sample";
//        OpenGLMatrix lastLocation = null;
//        VuforiaLocalizer vuforia;
//
//    }

    public void VuData() throws InterruptedException {

        final String TAG = "Vuforia VuMark Sample";
        OpenGLMatrix lastLocation = null;
        VuforiaLocalizer vuforia;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ASH0TEr/////AAAAGdzmhIJKn0ZlnYqNlsv14n5//eDerEzaogq8MWGyVV3KQSlsuPDZw/rDejjj1XAXUTLP/TsDJKAEvPKYWzM3EnOEQyCLnFyOP23fI6eNaTdXTGc56vjzWApNw2iYdDzsOdRhFuq6rGZL+++ofFmXUdbohKI4umpRGeKN2zel9JW/PUnByV7homJ8AbBPWe8hvyYBvvlS78OraW04kvoZJkOY9HLpzLA2eYz5mSEcbql30fMgEyk+xogGfrJj0uwjhvkNOYvkmr4/YTdLzqgLyLkF/JT1w3cUbS4B95vzfP0P/VDlAs6GUIvYAq3JwtnOasgYnJWZLq3ygi3GKvxaV2QaSvqGiyzacBxjjkRIOdqT\n";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        while (opMode.opModeIsActive()) {

            RelicRecoveryVuMark vuMark = null;
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));

                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
                telemetry.update();
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
                if (vuMark == RelicRecoveryVuMark.LEFT) ;
                {

                    telemetry.update();
                }
            }
        }

    }


    public void movePower(double power) {

        driveLeft.setPower(power);
        driveRight.setPower(power);

    }

    public void powerRotate(double power) {

        driveLeft.setPower(power);
        driveRight.setPower(-power);

    }

    public void encoderMove(int distance, double power)  {

        driveLeft.setTargetPosition(distance);
        driveRight.setTargetPosition(distance);
        driveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        movePower(power);

    }

    public void encoderRotate(int distance) {
        driveLeft.setTargetPosition(distance);
        driveRight.setTargetPosition(-distance);
        driveLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void grab(String grabPos) throws InterruptedException {
        if (grabPos == "o") {
            bgl.setPosition(MAX);
            bgr.setPosition(MAX);
        } else if (grabPos == "c") {
            bgl.setPosition(MIN);
            bgr.setPosition(MIN);
        } else {

        }
    }

    void moveTime(double power, long time) throws InterruptedException {
            movePower(power);
            Thread.sleep(time);
        }
    }


