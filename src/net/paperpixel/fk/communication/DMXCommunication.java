package net.paperpixel.fk.communication;

import dmxP512.*;
import net.paperpixel.fk.core.FKProcessing;
import net.paperpixel.fk.kube.AbstractKube;
//import net.paperpixel.fk.util.Pair;
import processing.core.PApplet;

import java.awt.*;

public class DMXCommunication extends FKProcessing {
    private static DmxP512 dmxOutput;

    private boolean LANBOX=false;
    private String LANBOX_IP="192.168.1.77";
    private static String serialPort = "COM1";

    public static final int UNIVERSE_SIZE = 128;
    public static final int DMXPRO_BAUDRATE = 115000;

    public static void setup() {
        setup(serialPort);
    }

    public static void setup(String myPort) {
        dmxOutput = new DmxP512(p5, UNIVERSE_SIZE, false);
        dmxOutput.setupDmxPro(myPort, DMXPRO_BAUDRATE);
        serialPort = myPort;
    }

    public static void send(int theDmxChannel, Color theColor) {
        if(p5.isSendDmx()) {
            int[] myColorArray = new int[3];

            myColorArray[0] = theColor.getRed();
            myColorArray[1] = theColor.getGreen();
            myColorArray[2] = theColor.getBlue();

            try {
                dmxOutput.set(theDmxChannel, myColorArray);
            } catch(NullPointerException e) {
//                if(p5.isDebug())
//                    PApplet.println("WARNING: DMXPRO is not recognized.");
            }

            if(p5.isDebug()) {
                PApplet.println("[DMX: sent color rgb("+ theColor.getRed() + ", " + theColor.getGreen() + ", " +
                        theColor.getBlue() + ") on DMX channel " + theDmxChannel + " (port " + DMXCommunication.serialPort + ")]");
            }
        }
    }

    public static void setAllChannels(int[][] theChannels) {
        try {
            for (int i = 0; i < p5.getTotalLines(); i++) {
                for (int j = 0; j < p5.getTotalColumns(); j++) {
                    AbstractKube myKube = p5.getKubeWall().getKubeFromLineCol(i, j);
                    myKube.setDmxChannel(theChannels[i][j]);
                }
            }
        } catch(NullPointerException e) {
            if(p5.isDebug())
                PApplet.println("WARNING: could not load dmx channels");
        }
    }
}
