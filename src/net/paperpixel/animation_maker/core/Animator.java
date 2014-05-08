package net.paperpixel.animation_maker.core;

import net.paperpixel.animation_maker.kube.AMKube;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Animator extends AMProcessing {

    private ArrayList<Frame> frames;
    private Frame activeFrame;
    private boolean animationPlaying;
    private int stepMillis;

    public static final String PLAY_STEP_UP = "frame_step_up";
    public static final String PLAY_STEP_DOWN = "frame_step_down";

    public static final String ADD_FRAME = "add_frame";
    public static final String ADD_FRAME_BEFORE = "add_frame_before";
    public static final String ADD_FRAME_AFTER = "add_frame_after";

    public static final String FRAME_MOVE_UP = "frame_move_up";
    public static final String FRAME_MOVE_DOWN = "frame_move_down";
    public static final String PLAY_FIRST = "play_first";
    public static final String PLAY_LAST = "play_last";


    public Animator() {
        frames = new ArrayList<Frame>();

        addFrame();
    }

    public void draw() {
        if (animationPlaying) {
            makePlay();
        } else {
            frames.get(getIndexBefore(true)).drawOnionSkin();
        }
        activeFrame.draw();
    }


    /*ANIMATION*/

    public void play() {
        setActiveFrame(0);
        stepMillis = p5.millis();
        animationPlaying = true;
    }

    public void stop() {
        animationPlaying = false;
//        setActiveFrame(0);
    }

    private void makePlay() {
        if (canDoNextStep()) {
            setActiveFrame(PLAY_STEP_DOWN);
        }
    }

    private boolean canDoNextStep() {
        int stepDelay = p5.getAnimationStepDelay();
        if (p5.millis() - stepMillis > stepDelay) {
            stepMillis = p5.millis();
            return true;
        }
        return false;
    }


    /*FRAME HANDLING*/

    public void addFrame() {
        addFrame(ADD_FRAME);
    }

    public void addFrame(String framePosition) {
        Frame newFrame = new Frame();

        addFrame(newFrame, framePosition);
    }

    public void addFrame(Frame newFrame, String framePosition) {
        if (framePosition == ADD_FRAME) {
            frames.add(newFrame);
        } else if (framePosition == ADD_FRAME_BEFORE) {
            frames.add(getIndexBefore(true), newFrame);
        } else if (framePosition == ADD_FRAME_AFTER) {
            int index = getIndexAfter(true);
            if (frames.size() - 1 > index) {
                frames.add(index, newFrame);
            } else {
                frames.add(newFrame);
            }
        }

        p5.getControls().populateFrameList(frames);
        setActiveFrame(newFrame);
    }

    public void duplicateFrame() {
        Frame newFrame = activeFrame.clone();
        addFrame(newFrame, ADD_FRAME_AFTER);
    }

    public void removeFrame() {
        if (frames.size() > 1) {
            int indexToRemove = frames.indexOf(activeFrame);
            removeFrame(indexToRemove);
        }
    }

    private void removeFrame(int index) {
        frames.remove(index);
        p5.getControls().populateFrameList(frames);
        setActiveFrame(index - 1 < 0 ? 0 : index - 1);
    }

    public void moveFrame(String moveUpOrDown) {
        int index = -1;
        if (moveUpOrDown == FRAME_MOVE_UP) {
            index = getIndexBefore(true);
        } else if (moveUpOrDown == FRAME_MOVE_DOWN) {
            index = getIndexAfter(true);
        }
        if (index >= 0) {
            Collections.swap(frames, frames.indexOf(activeFrame), index);
            setActiveFrame(index);
        }
    }

    public void setActiveFrame(Frame newActiveFrame) {
        activeFrame = newActiveFrame;
        p5.getControls().getFrameCount().setText("Frame " + frames.indexOf(activeFrame));
        p5.getControls().setFrameListItemActive(frames.indexOf(activeFrame));
    }

    public void setActiveFrame(int frameIndex) {
        try {
            Frame newActiveFrame = frames.get(frameIndex);
            setActiveFrame(newActiveFrame);
        } catch (NullPointerException e) {
            PApplet.println("there's no frame with index " + frameIndex);
        }
    }

    public void setActiveFrame(String upOrDown) {
        setActiveFrame(upOrDown, false);
    }


    public void setActiveFrame(String upOrDown, boolean keepBoundaries) {
        int index = 0;
        if (upOrDown == PLAY_STEP_DOWN) {
            index = getIndexAfter(keepBoundaries);
        } else if (upOrDown == PLAY_STEP_UP) {
            index = getIndexBefore(keepBoundaries);
        } else if (upOrDown == PLAY_FIRST) {
            index = 0;
        } else if (upOrDown == PLAY_LAST) {
            index = frames.size() - 1;
        }
        setActiveFrame(index);
    }

    /*LOAD / SAVE HANDLING*/

    public String getJSON() {
        JSONArray json = new JSONArray();

        try {
            for (int i = 0; i < frames.size(); i++) {
                json.put(i, frames.get(i).getKubeWall().getStates());
            }
        } catch (JSONException e) {
            PApplet.println("JSON exception. Malformed ?");
        }

        return json.toString();
    }

    /*
    frame
        line
            column
                color
     */
    public String getArray() {
        String result = "{";
        for(int h = 0; h < frames.size(); h++) {
            AMKube[][] kubes = frames.get(h).getKubeWall().getKubes();
            result += "{";
            for(int i = 0; i < kubes.length; i++) {
                result += "{";
                for(int j = 0; j < kubes[i].length; j++) {

                    if(kubes[i][j].isActive()) {
                        result += kubes[i][j].getAMColor().getId();
                    } else {
                        result += 0;
                    }

                    if(j < kubes[i].length - 1) {
                        result += ",";
                    }
                }
                result += "}";

                if(i < kubes.length - 1) {
                    result += ",";
                }
            }
            result += "}";

            if(h < frames.size() - 1) {
                result += ",";
            }
        }

        return result + "}";
    }

    public boolean load(String jsonString) {
        try {
            JSONArray json = new JSONArray(jsonString);
            ArrayList<Frame> newFrames = new ArrayList<Frame>(); 

            for (int i = 0; i < json.length(); i++) {
                Frame newFrame = new Frame();
                JSONObject states = json.getJSONObject(i);
                HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
                for (int j = 0; j < states.length(); j++) {
                    map.put(j, states.getInt(String.valueOf(j)));
                }
                newFrame.getKubeWall().setStates(map);
                newFrames.add(newFrame);
            }

            frames.clear();
            for (Frame myFrame : newFrames) {
                addFrame(myFrame, ADD_FRAME);
            }

            return true;
        } catch (JSONException e) {
            return false;
        }
    }


    /*UTILS*/

    private int getIndexAfter(boolean keepBoundaries) {
        int index = frames.indexOf(activeFrame) + 1;
        if (index > frames.size() - 1) {
            if (keepBoundaries) {
                index = frames.size() - 1;
            } else {
                index = 0;
            }
        }
        return index;
    }

    private int getIndexBefore(boolean keepBoundaries) {
        int index = frames.indexOf(activeFrame) - 1;
        if (index < 0) {
            if (keepBoundaries) {
                index = 0;
            } else {
                index = frames.size() - 1;
            }
        }
        return index;
    }


    /*GETTERS / SETTERS*/

    public Frame getActiveFrame() {
        return activeFrame;
    }

    public boolean isAnimationPlaying() {
        return animationPlaying;
    }
}
