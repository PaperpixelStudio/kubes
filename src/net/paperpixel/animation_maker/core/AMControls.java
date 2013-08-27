package net.paperpixel.animation_maker.core;

import controlP5.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class AMControls extends AMProcessing {

    private ControlP5 cp5;
    private ControlGroup bottom_controls;
    private ControlFont font;
    private Textlabel frameCount;
    private Group right_controls;
    private Group save_controls;
    private static final String BUTTON_PLAY = "play";
    private static final String BUTTON_STOP = "stop";
    private ListBox frameList;
    private Textarea save_textarea;

    public AMControls() {
        cp5 = new ControlP5(p5);
        font = new ControlFont(p5.createFont("Arial", 11, true));


        /*GROUPS*/

        bottom_controls = cp5.addGroup("bottom_controls")
                .setPosition(getControlsBottom())
                .hideBar();

        right_controls = cp5.addGroup("right_controls")
                .setPosition(getControlsRight())
                .hideBar();

        save_controls = cp5.addGroup("save_controls")
                .setPosition(getControlsRightBottom().x - 200, getControlsRightBottom().y + 60)
                .hideBar();


        /*BOTTOM CONTROLS*/

        frameCount = cp5.addTextlabel("frame_count")
                .setText("Frame 0")
                .moveTo(bottom_controls)
                .setPosition(0, 0);

        cp5.addButton("toggle_active")
                .setCaptionLabel("Toggle kubes")
                .moveTo(bottom_controls)
                .addListener(new ToggleActiveListener())
                .setPosition(60, -5);

        cp5.addButton("play_button")
                .setCaptionLabel("PLAY")
                .moveTo(bottom_controls)
                .addListener(new PlayStopListener())
                .setStringValue(BUTTON_PLAY)
                .setPosition(180, -10)
                .setSize(60, 30);

        cp5.addButton("stop_button")
                .setCaptionLabel("STOP")
                .moveTo(bottom_controls)
                .addListener(new PlayStopListener())
                .setStringValue(BUTTON_STOP)
                .setPosition(250, -10)
                .setSize(60, 30);


        cp5.addButton("show_up_button")
                .moveTo(bottom_controls)
                .setCaptionLabel("UP 1 FRAME")
                .addListener(new StepUpDownListener())
                .setStringValue(Animator.PLAY_STEP_UP)
                .setPosition(350, -5)
                .setSize(60, 20);


        cp5.addButton("show_down_button")
                .moveTo(bottom_controls)
                .setCaptionLabel("DOWN 1 FRAME")
                .addListener(new StepUpDownListener())
                .setStringValue(Animator.PLAY_STEP_DOWN)
                .setPosition(420, -5)
                .setSize(70, 20);


        addShortcutText(0, 60);




        /*RIGHT CONTROLS*/

        frameList = cp5.addListBox("frame_list")
                .moveTo(right_controls)
                .setPosition(0, 0)
                .setSize(150, 320)
                .setItemHeight(30)
                .hideBar()
                .addListener(new FrameListListener());
        

        cp5.addButton("add_frame_button")
                .moveTo(right_controls)
                .setPosition(160, 0)
                .setSize(120, 20)
                .setCaptionLabel("Add frame")
                .setStringValue(Animator.ADD_FRAME)
                .addListener(new AddFrameListener());

        cp5.addButton("add_frame_before_button")
                .moveTo(right_controls)
                .setPosition(160, 30)
                .setSize(120, 20)
                .setCaptionLabel("Add frame before")
                .setStringValue(Animator.ADD_FRAME_BEFORE)
                .addListener(new AddFrameListener());

        cp5.addButton("add_frame_after_button")
                .moveTo(right_controls)
                .setPosition(160, 60)
                .setSize(120, 20)
                .setCaptionLabel("Add frame after")
                .setStringValue(Animator.ADD_FRAME_AFTER)
                .addListener(new AddFrameListener());


        cp5.addButton("duplicate_frame")
                .moveTo(right_controls)
                .setPosition(160, 110)
                .setSize(120, 20)
                .setCaptionLabel("Duplicate frame")
                .addListener(new DuplicateFrameListener());



        cp5.addButton("remove_frame")
                .moveTo(right_controls)
                .setPosition(160, 160)
                .setSize(120, 20)
                .setCaptionLabel("Remove frame")
                .addListener(new RemoveFrameListener());




        cp5.addButton("move_frame_up")
                .moveTo(right_controls)
                .setPosition(160, 210)
                .setSize(120, 20)
                .setCaptionLabel("Move frame up")
                .setStringValue(Animator.FRAME_MOVE_UP)
                .addListener(new MoveFrameListener());

        cp5.addButton("move_frame_down")
                .moveTo(right_controls)
                .setPosition(160, 240)
                .setSize(120, 20)
                .setCaptionLabel("Move frame down")
                .setStringValue(Animator.FRAME_MOVE_DOWN)
                .addListener(new MoveFrameListener());



        /*SAVE CONTROLS*/

        save_textarea = cp5.addTextarea("save_textarea")
                .moveTo(save_controls)
                .setSize(400, 150)
                .setPosition(0, 0)
                .setFont(font)
                .setColorBackground(p5.color(50))
                .setColorForeground(p5.color(50));

        cp5.addButton("load_button")
                .setCaptionLabel("Load from clipboard")
                .moveTo(save_controls)
                .setSize(110, 30)
                .setPosition(0, 160)
                .addListener(new LoadListener());

        cp5.addButton("save_button")
                .setCaptionLabel("Save to clipboard")
                .moveTo(save_controls)
                .setSize(100, 30)
                .setPosition(120, 160)
                .addListener(new SaveListener());
    }

    private void addShortcutText(int posX, int posY) {
        ArrayList<String> text = new ArrayList<String>();

        text.add("[A-Y] toggle top kubes");
        text.add("[Q-H] toggle middle kubes");
        text.add("[W-N] toggle bottom kubes");
        text.add("[TAB] toggle all kubes");
        text.add("[SPACE - ENTER - RETURN] play / stop");
        text.add("[+] add a frame after");
        text.add("[-] delete current frame");
        text.add("[UP] show frame before");
        text.add("[DOWN] show frame after");
        text.add("[$] show first frame");
        text.add("[µ] show last frame");
        text.add("[LEFT] move frame before");
        text.add("[RIGHT] move frame after");

        for (int i = 0; i < text.size(); i++) {
            cp5.addTextlabel("shortcut_text_" + i)
                    .setText(text.get(i))
                    .setFont(font)
                    .moveTo(bottom_controls)
                    .setPosition(posX, posY)
                    .setSize(300, 10);

            posY += 15;
        }
    }


    public void populateFrameList(ArrayList<Frame> frames) {
        frameList.clear();

        for (int i = 0; i < frames.size(); i++) {
            frameList.addItem("Frame " + i, i);
        }
    }

    public void setFrameListItemActive(int index) {
        frameList.setColorBackground(70);
        try {
            frameList.getItem(index).setColorBackground(p5.color(1, 108, 158));

//            Buggué, dommage
//            frameList.scroll(index);
        } catch (IndexOutOfBoundsException e) {
            PApplet.println("Could not activate frame in frameList: index " + index + " not found");
        }
    }



    /*UTILS*/

    public static PVector getControlsBottom() {
        int posX = (int) p5.getKubeWallPosition().x;
        int posY = (int) (p5.getKubeWallPosition().x + (p5.getKubeSize() * p5.getTotalLines()) + (p5.getKubeMargin() * p5.getTotalLines()) + 30);

        return new PVector(posX, posY);
    }

    public static PVector getControlsRight() {
        int posX = (int) p5.getKubeWallPosition().x + (p5.getKubeSize() * p5.getTotalColumns()) + (p5.getKubeMargin() * p5.getTotalColumns()) + 30;
        int posY = (int) p5.getKubeWallPosition().x;

        return new PVector(posX, posY);
    }

    public static PVector getControlsRightBottom() {
        int posX = (int) getControlsRight().x;
        int posY = (int) getControlsBottom().y;

        return new PVector(posX, posY);
    }



    /*LISTENERS*/

    private class ToggleActiveListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getAnimator().getActiveFrame().toggleKubes();
        }
    }

    private class PlayStopListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            if (theEvent.getStringValue() == BUTTON_PLAY) {
                p5.getAnimator().play();
            } else if(theEvent.getStringValue() == BUTTON_STOP) {
                p5.getAnimator().stop();
            }
        }
    }

    private class FrameListListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getAnimator().setActiveFrame((int) theEvent.getValue());
        }
    }

    private class AddFrameListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getAnimator().addFrame(theEvent.getStringValue());
        }
    }


    /*GETTERS / SETTERS*/

    public Textlabel getFrameCount() {
        return frameCount;
    }

    private class StepUpDownListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getAnimator().setActiveFrame(theEvent.getStringValue());
        }
    }

    private class RemoveFrameListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getAnimator().removeFrame();
        }
    }

    private class MoveFrameListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getAnimator().moveFrame(theEvent.getStringValue());
        }
    }

    private class DuplicateFrameListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getAnimator().duplicateFrame();
        }
    }

    private class LoadListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            String text = p5.getClipboard().getClipboardContents();
            if (text != "") {
                save_textarea.setText("ANIMATION LOADED");
                if(!p5.getAnimator().load(text)) {
                    save_textarea.setText("Couldn't load from clipboard. Are you sure you copied a valid JSON string ?");
                }
            }
        }
    }

    private class SaveListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            String json = p5.getAnimator().getJSON();
            save_textarea.setText("ANIMATION SAVED IN CLIPBOARD");
            p5.getClipboard().setClipboardContents(json);
        }
    }
}
