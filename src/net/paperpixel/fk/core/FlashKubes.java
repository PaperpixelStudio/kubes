package net.paperpixel.fk.core;

import SimpleOpenNI.SimpleOpenNI;
import controlP5.ColorPicker;
import controlP5.ControlEvent;
import net.paperpixel.fk.gui.FKControls;
import net.paperpixel.fk.kube.KubeType;
import net.paperpixel.fk.kube.KubeWall;
import net.paperpixel.fk.pointcloud.MeshDisplay;
import net.paperpixel.fk.pointcloud.PCDisplayerFactory;
import net.paperpixel.fk.pointcloud.PCLinesDisplay;
import oscP5.OscP5;
import peasy.PeasyCam;
import processing.core.*;
import processing.opengl.*;
// import themidibus.MidiBus;

import javax.media.opengl.GL;
import java.awt.*;
import java.util.ArrayList;

public final class FlashKubes extends PApplet {
    private SimpleOpenNI kinect;
    private PeasyCam cam;
    private PGraphicsOpenGL pgl;
    private GL gl;
    private FKControls controls;


    private float minimumDistanceToTrace = 100;
    private int kubeSize;
    private PVector kubeWallPosition = new PVector();
    private int totalLines = 3;
    private int totalColumns = 3;
    private int idleSpeed = 200;

    private boolean mouse_test = false;
    private boolean draw_kubes = true;
    private boolean send_audio_midi = false;
    private boolean send_light_midi = false;
    private boolean debug = false;
    private int kubeMargin = 0;
    private float bpm = 120;
    private int notesPerKube = FKConstants.PITCH_SCALE_MIN;
    private int groundPosition = 0;
    private boolean displayGround = false;
    private boolean randomKubeColors = false;
    private boolean slave = false;
    private int masterKubeNumber = 0;
    private int idleDelay = 30;
    private boolean idleSync = false;


    private KubeWall kubeWall;

    private boolean isKinectConnected = false;
    private PVector oldRealWorldPoint;

    private static OscP5 oscP5;

    static public void main(String args[]) {
        PApplet.main(new String[] { "net.paperpixel.fk.core.FlashKubes" });
    }


    public void setup() {
        size(1024, 768, OPENGL);
        smooth();

        FKProcessing.setup(this);

        controls = new FKControls();

        cam = new PeasyCam(this, 1800);
        cam.setYawRotationMode();

        /*remove clipping plane (object dissapearing after zooming out a bit)*/
        frustum(-width / 2, width / 2, -height / 2, height/2, 500, 50000);

        pgl = (PGraphicsOpenGL) g;
        gl = pgl.gl;

        try {
            kinect = new SimpleOpenNI(this, SimpleOpenNI.RUN_MODE_MULTI_THREADED);
            isKinectConnected = kinect.enableDepth() ? true : false;
            kinect.setMirror(true);
        } catch (Throwable e) {
            isKinectConnected = false;
        }


        /*Loads the first config file found in sketchPath*/
        try {
            controls.getProfilesControls().loadConfigFile(0);
        } catch (NullPointerException e) {
            int[][] kubeTypeIndexes = controls.getKubeWallControls().getWallController().getKubeTypeIndexes();

            if (kubeTypeIndexes != null && kubeTypeIndexes.length > 0) {
                kubeWall = new KubeWall(getTotalLines(), getTotalColumns(), kubeTypeIndexes);
            } else {
                kubeWall = new KubeWall(getTotalLines(), getTotalColumns());
            }
        }
    }

    public void draw() {
        background(0);

        directionalLight(126, 126, 126, 0, -100, -1);
        ambientLight(102, 102, 102, 0, -100, -1);

        /*Augmente les fps en temporisant l'update de la depthMap*/
//        if (millis() % 20 < 10) {
        kinect.update();
//        }

        pgl.beginGL();
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
        pgl.endGL();

        // on retourne l'image pcq les valeurs du cloud 3d kinect sont inversées par rapport à l'espace opengl
        rotateX(radians(180));


        if (isDrawKubes()) {
            getKubeWall().checkIdle();
            getKubeWall().draw();
        }


        // le point cloud de la kinect
        if(isKinectConnected && !isMouseTest()) {
            _drawAndAnalysePointCloud();
        } else {
            _analyseMouseMoves();
        }

        if(displayGround)
            _drawGround();

        if (isDrawKubes()) {
            getKubeWall().act();
        }

        _showFrameRate();
    }


    /**
     * Show / hide the control window
     */
    public void keyPressed() {
        if (key=='h') controls.getControlWindow().hide();
        if (key=='s') controls.getControlWindow().show();
    }


    /**
     * Called when closing the sketch.
     * Stops all playing clips
     */
    public void stop() {
        try {
            getKubeWall().resetAllKubes();
        } catch (NullPointerException e) {
            if(isDebug())
                PApplet.println("Couldn't reset all kubes");
        }
        super.stop();
    }




    private void  _drawAndAnalysePointCloud() {

        int[] depthMap = kinect.depthMap();
        int depthMapLength = depthMap.length;
        int index;
        PVector realWorldPoint;

        stroke(255);
        strokeWeight(1);


        for (int x = 0; x < kinect.depthHeight(); x += FKConstants.KINECT_DRAW_STEPS) {
            for (int y = 0; y < kinect.depthWidth(); y += FKConstants.KINECT_DRAW_STEPS) {
                index = x + y * kinect.depthWidth();

                if (index < depthMapLength && depthMap[index] > 0) {
                    realWorldPoint = kinect.depthMapRealWorld()[index];
                    getKubeWall().check(realWorldPoint);
                    PCDisplayerFactory.getDisplayer(PCLinesDisplay.class).drawUniquePoint(realWorldPoint, oldRealWorldPoint);

                    oldRealWorldPoint = realWorldPoint;
                }
            }
        }
    }


    private void _analyseMouseMoves() {
        float posZ;

        if(keyPressed) {
            posZ = getKubeWallPosition().z;
            float posX = map(mouseX, 0, width, -width, width);
            float posY = map(mouseY, 0, height, height, -2000);
        } else {
            posZ = getKubeWallPosition().z - (2 * getKubeSize());
        }

        fill(255);
        stroke(255);
        strokeWeight(1);

        for (int z = 0; z < getKubeSize() / 2; z++) {
            for (int x = 0; x < 2; x++) {
                for (int y = 0; y < 2; y++) {
                    float posX = map(mouseX, 0, width, -width, width);
                    float posY = map(mouseY, 0, height, height, -2000);
                    PVector myPoint = new PVector(posX + x, posY + y, posZ + z);
                    try {
                        getKubeWall().check(myPoint);
                    } catch (NullPointerException e) {

                    }

                    pushMatrix();
                    translate(0, 0, FKConstants.KINECT_DISTANCE);
                    point(myPoint.x, myPoint.y, myPoint.z);
                    popMatrix();
                }
            }
        }
    }

    private void _drawGround() {
        pushMatrix();
        translate(-1000, groundPosition, FKConstants.KINECT_DISTANCE);
        fill(255, 30);
        noStroke();
        box(FKConstants.GROUND_SIZE, 2, FKConstants.GROUND_SIZE);
        popMatrix();
    }


    private void _showFrameRate() {
        fill(100);
        cam.beginHUD();
        text("frameRate: " + str(round(frameRate)) + " fps", 10, 20);
        cam.endHUD();
    }



    /* Getters and setters */

    public KubeWall getKubeWall() {
        if (kubeWall == null) {
            recreateKubeWall();
        }
        return kubeWall;
    }

    public void setKubeWall(KubeWall kubeWall) {
        this.kubeWall = kubeWall;
    }

    public void recreateKubeWall() {
        this.kubeWall = null;
        this.kubeWall = new KubeWall(totalLines, totalColumns, controls.getKubeWallControls().getWallController().getKubeTypeIndexes());
    }



    /*controlP5 events*/

    public void controlEvent(ControlEvent theEvent) {
        if(debug)
            println("controlEvent : "+theEvent);


        /* Change type colors (controlP5 bug: doesn't work with a listener)
       * TODO: find a way to do this in KubeWallControls
       * */
        int i = 0;
        ArrayList<ColorPicker> myCPList = controls.getKubeWallControls().getTypeColorPickers();
        for(ColorPicker myCP : myCPList) {
            if(theEvent.isFrom(myCP)) {
                KubeType myType = KubeType.values()[i];

                if (myType != KubeType.INACTIVE_KUBE) {
                    myType.setColor(new Color(
                            (int) theEvent.getArrayValue(0),
                            (int) theEvent.getArrayValue(1),
                            (int) theEvent.getArrayValue(2)
                    ));
                }
            }

            i++;
        }
    }




    /* variables ControlP5 */

    public FKControls getControls() {
        return controls;
    }

    public float getBpm() {
        return bpm;
    }

    public boolean isSendLightMidi() {
        return send_light_midi;
    }

    public boolean isRandomKubeColors() {
        return randomKubeColors;
    }

    public int getKubeSize() {
        return kubeSize;
    }

    public boolean isMouseTest() {
        return mouse_test;
    }

    public boolean isSendAudioMidi() {
        return send_audio_midi;
    }

    public boolean isDebug() {
        return debug;
    }

    public int getKubeMargin() {
        return kubeMargin;
    }

    public float getMinimumDistanceToTrace() {
        return minimumDistanceToTrace;
    }

    public PVector getKubeWallPosition() {
        return kubeWallPosition;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public int getNotesPerKube() {
        return notesPerKube;
    }

    public int getIdleSpeed() {
        return idleSpeed;
    }

    public boolean isSlave() {
        return slave;
    }

    public int getMasterKubeNumber() {
        return masterKubeNumber;
    }

    public int getIdleDelay() {
        return idleDelay;
    }

    public boolean isIdleSync() {
        return idleSync;
    }

    public boolean isDrawKubes() {
        return draw_kubes;
    }
}