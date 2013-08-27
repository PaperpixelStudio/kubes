package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.core.FKConstants;
import net.paperpixel.fk.gui.wall_controller.SetupWallController;
import net.paperpixel.fk.kube.AbstractKube;
import net.paperpixel.fk.kube.KubeType;
import net.paperpixel.fk.kube.KubeWall;
import processing.core.PApplet;

import java.util.ArrayList;

public class KubeWallControls extends AbstractControlGroup {

    /*Controllers*/

    private Slider kubeSizeSlider;
    private Slider totalRowsSlider;
    private Slider totalColumnsSlider;
    private Slider positionSliderX;
    private Slider positionSliderY;
    private Slider positionSliderZ;
    private Slider groundPositionSlider;
    private Toggle displayGroundToggle;
    private SetupWallController wallController;
    private Textlabel wallLabel;

    private ArrayList<ColorPicker> typeColorPickers = new ArrayList<ColorPicker>();

    public KubeWallControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {
        int pos_y = FKControls.GROUP_TITLE_HEIGHT;

        /*TITLE*/

        FKControls.createGroupTitle(controlGroup, "KubeWall setup");


        /*KUBE SIZE*/

        kubeSizeSlider = cp5.addSlider("kubeSize")
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(100, 30)
                .setCaptionLabel("Kube size")
                .setRange(FKConstants.KUBE_SIZE_RANGE.getMin(), FKConstants.KUBE_SIZE_RANGE.getMax())
                .setBroadcast(true)
                .setGroup(controlGroup);


        /*KUBE MARGIN*/

        kubeSizeSlider = cp5.addSlider("kubeMargin")
                .setBroadcast(false)
                .setPosition(160, pos_y)
                .setSize(60, 30)
                .setCaptionLabel("Kube margin")
                .setRange(FKConstants.KUBE_MARGIN_RANGE.getMin(), FKConstants.KUBE_MARGIN_RANGE.getMax())
                .setDefaultValue(FKConstants.KUBE_MARGIN_RANGE.getMin())
                .setBroadcast(true)
                .addListener(new KubeMarginListener())
                .setGroup(controlGroup);

        pos_y += 35;


        /*TOTAL ROWS*/

        totalRowsSlider = cp5.addSlider("totalLines")
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(60, 30)
                .setNumberOfTickMarks(FKConstants.TOTAL_ROWS_RANGE.getMax())
                .setCaptionLabel("Total lines")
                .setRange(FKConstants.TOTAL_ROWS_RANGE.getMin(), FKConstants.TOTAL_ROWS_RANGE.getMax())
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new ChangeRowColumnListener());


        /*TOTAL COLUMNS*/

        totalColumnsSlider = cp5.addSlider("totalColumns")
                .setBroadcast(false)
                .setPosition(130, pos_y)
                .setSize(60, 30)
                .setNumberOfTickMarks(FKConstants.TOTAL_COLUMNS_RANGE.getMax())
                .setCaptionLabel("Total columns")
                .setRange(FKConstants.TOTAL_COLUMNS_RANGE.getMin(), FKConstants.TOTAL_COLUMNS_RANGE.getMax())
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new ChangeRowColumnListener());

        pos_y += 45;


        /*X POSITION*/

        positionSliderX = cp5.addSlider(name + "_pos_x_2")
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(40, 30)
                .setCaptionLabel("X rowCol")
                .setRange(FKConstants.KUBE_POSITION_X.getMin(), FKConstants.KUBE_POSITION_X.getMax())
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new ChangePositionListener());


        /*Y POSITION*/

        positionSliderY = cp5.addSlider(name + "_pos_y")
                .setBroadcast(false)
                .setPosition(110, pos_y)
                .setSize(40, 30)
                .setCaptionLabel("Y rowCol")
                .setRange(FKConstants.KUBE_POSITION_Y.getMin(), FKConstants.KUBE_POSITION_Y.getMax())
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new ChangePositionListener());


        /*Z POSITION*/

        positionSliderZ = cp5.addSlider(name + "_pos_z")
                .setBroadcast(false)
                .setPosition(220, pos_y)
                .setSize(40, 30)
                .setCaptionLabel("Z rowCol")
                .setRange(FKConstants.KUBE_POSITION_Z.getMin(), FKConstants.KUBE_POSITION_Z.getMax())
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new ChangePositionListener());

        pos_y += 45;


        /*DISPLAY GROUND*/

        displayGroundToggle = cp5.addToggle("displayGround")
                .setBroadcast(false)
                .setCaptionLabel("Display ground")
                .setPosition(0, pos_y)
                .setSize(40, 15)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .setMode(ControlP5.SWITCH);


        /*GROUND POSITION*/

        groundPositionSlider = cp5.addSlider("groundPosition")
                .setBroadcast(false)
                .setPosition(100, pos_y)
                .setSize(100, 30)
                .setCaptionLabel("Ground position")
                .setRange(FKConstants.GROUND_POSITION_RANGE.getMin(), FKConstants.GROUND_POSITION_RANGE.getMax())
                .setBroadcast(true)
                .setGroup(controlGroup);


        pos_y += 45;



        /*KUBE TYPE*/

        wallLabel = cp5.addTextlabel("wall_label")
                .setGroup(controlGroup)
                .setPosition(160, pos_y);

        wallController = new SetupWallController(cp5, p5, "setup_wall_controller", wallLabel)
                .setBroadcast(false)
                .setPosition(0, pos_y)
                .setSize(150, 120)
                .setBroadcast(true)
                .moveTo(controlGroup);

        pos_y += 100;


        /*KUBE TYPE COLORS*/

        for (int i = 0; i < KubeType.values().length; i++) {
            KubeType myType = KubeType.values()[i];

            if(myType != KubeType.INACTIVE_KUBE) {
                cp5.addTextlabel("color_picker_label_" + myType.getName())
                        .setText("Color for the " + myType.getName() + " kubes")
                        .setPosition(0, pos_y)
                        .setGroup(controlGroup);
                pos_y += 15;
            }

            typeColorPickers.add(
                    myType.ordinal(),
                    cp5.addColorPicker("color_picker_" + myType.getName())
                            .setPosition(0, pos_y)
                            .setLabel("Color of " + myType.getName() + " kube")
                            .setGroup(controlGroup)
//                            .setColorValue(myType.getColor().getRGB())
            );

            if(myType == KubeType.INACTIVE_KUBE) {
                typeColorPickers.get(i).hide();
            } else {
                pos_y += 75;
            }
        }


        return controlGroup;
    }



    /*LISTENERS*/

    private class ChangeRowColumnListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
//            p5.setKubeWall(new KubeWall((int) totalRowsSlider.getValue(), (int) totalColumnsSlider.getValue(), wallController.getKubeTypeIndexes()));
            p5.recreateKubeWall();
        }
    }

    private class ChangePositionListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getKubeWallPosition().x = positionSliderX.getValue();
            p5.getKubeWallPosition().y = positionSliderY.getValue();
            p5.getKubeWallPosition().z = positionSliderZ.getValue();

            p5.getKubeWall().setKubeWallPosition();
        }
    }


    /*GETTERS & SETTERS*/

    public SetupWallController getWallController() {
        return wallController;
    }

    public ArrayList<ColorPicker> getTypeColorPickers() {
        return typeColorPickers;
    }

    private class KubeMarginListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            p5.getKubeWall().setKubeWallPosition();
        }
    }
}
