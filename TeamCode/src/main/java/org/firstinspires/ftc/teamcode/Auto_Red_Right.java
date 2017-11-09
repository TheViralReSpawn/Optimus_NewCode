
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name = "auto_Red_Right ", group = "Concept")
public class Auto_Red_Right extends LinearOpMode {

    private autoConfig config;
    final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer Vuforia;

    public void runOpMode() throws InterruptedException {

        config = new autoConfig(this);
        config.init();
        final String TAG = "Vuforia VuMark Sample";
        OpenGLMatrix lastLocation = null;
        VuforiaLocalizer vuforia;
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ASH0TEr/////AAAAGdzmhIJKn0ZlnYqNlsv14n5//eDerEzaogq8MWGyVV3KQSlsuPDZw/rDejjj1XAXUTLP/TsDJKAEvPKYWzM3EnOEQyCLnFyOP23fI6eNaTdXTGc56vjzWApNw2iYdDzsOdRhFuq6rGZL+++ofFmXUdbohKI4umpRGeKN2zel9JW/PUnByV7homJ8AbBPWe8hvyYBvvlS78OraW04kvoZJkOY9HLpzLA2eYz5mSEcbql30fMgEyk+xogGfrJj0uwjhvkNOYvkmr4/YTdLzqgLyLkF/JT1w3cUbS4B95vzfP0P/VDlAs6GUIvYAq3JwtnOasgYnJWZLq3ygi3GKvxaV2QaSvqGiyzacBxjjkRIOdqT\n";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.Vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.Vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        relicTrackables.activate();
        waitForStart();

        while (opModeIsActive()){
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            config.grab("c");

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                telemetry.addData("say", "Left move");
                telemetry.update();
                config.grab("c");
                config.encoderMove(2750,.5);
                config.encoderRotate(360);
                config.encoderMove(500,.5);
                config.grab("o");

            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                telemetry.addData("say", "Center move");
                telemetry.update();
                config.grab("c");
                config.encoderMove(2750,.5);
                config.encoderRotate(360);
                config.encoderMove(1375,.5);
                config.grab("o");

            }else{
                telemetry.addData("say", "Right move");
                telemetry.update();
                config.grab("c");
                config.encoderMove(2750,.5);
                config.encoderRotate(360);
                config.encoderMove(2290,.5);
                config.grab("o");

            }
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}
