package net.paperpixel.fk.core;

import net.paperpixel.fk.util.Range;

import java.awt.*;

public class FKConstants {

    /* SETUP */

    public static final int KINECT_DRAW_STEPS = 5;
    public static final int KINECT_DISTANCE = -1800;


    /* KUBES */

    public static final float KUBE_MAX_ALPHA = 255.0f;
    public static final int GROUND_SIZE = 50000;


    /*Ranges*/

    public static final Range<Integer> KUBE_SIZE_RANGE = new Range<Integer>(100, 1500);
    public static final Range<Integer> TOTAL_ROWS_RANGE = new Range<Integer>(1, 6);
    public static final Range<Integer> TOTAL_COLUMNS_RANGE = new Range<Integer>(1, 6);
    public static final Range<Integer> KUBE_MARGIN_RANGE = new Range<Integer>(0, 200);
    public static final Range<Integer> KUBE_POINTS_RANGE = new Range<Integer>(20, 70);
    public static final Range<Integer> KUBE_THRESHOLD_RANGE = new Range<Integer>(0, 100);
    public static final Range<Integer> GROUND_POSITION_RANGE = new Range<Integer>(-1000, -3000);

    public static final Range<Integer> KUBE_POSITION_X = new Range<Integer>(-2000, 2000);
    public static final Range<Integer> KUBE_POSITION_Y = new Range<Integer>(-1000, 500);
    public static final Range<Integer> KUBE_POSITION_Z = new Range<Integer>(300, 5000);
    public static final Range<Integer> BPM = new Range<Integer>(60, 180);
    public static final int PITCH_SCALE_MIN = 0;


    public static final int IDLE_DELAY =  5 * 1000;

    public static final Color NOTE_KUBE_COLOR = new Color(209, 44, 44);
    public static final Color IDLE_KUBE_COLOR = new Color(50, 100, 200);
    public static final Color EFFECT_KUBE_COLOR = new Color(103, 209, 17);
    public static final Color LOOP_KUBE_COLOR = new Color(18, 130, 255);
    public static final Color XA_KUBE_COLOR = new Color(185, 52, 255);
    public static final Color INACTIVE_KUBE_COLOR = new Color(0, 0, 0);

    /* OSC */
    public static final String IP_ADDRESS = "192.168.0.195";

    public static final int SEND_PORT = 9000;

    public static final int RECEIVE_PORT = 9000;

    public static final String OSC_PLAY_NOTE = "/playNote";


    /* METHODE D'ENVOI */
    public static final int COM_OSC = 1;

    public static final int COM_MIDI = 2;
    public static final int COM_DMX = 3;


    /*ELSE*/

    /*matte color for alpha blending (for the lighting we need to convert rgba to rgb)*/
    public static final Color MATTE_COLOR = new Color(0, 0, 0);
}
