package net.paperpixel.fk.gui;

import controlP5.*;

import net.paperpixel.fk.core.FKProcessing;


public class FKControls extends FKProcessing {

    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;
    public static final int WINDOW_POSITION_X = 100;
    public static final int WINDOW_POSITION_Y = 100;

    public static final int WINDOW_PADDING = 20;
    public static final int GROUP_PADDING = 40;
    public static final int CONTROL_PADDING = 10;
    public static final int GROUP_TITLE_HEIGHT = 40;

    private ControlWindow controlWindow;
    private static ControlP5 cp5;

    /*Groups*/
    private ControlGroup profilesGroup;
    private ControlGroup miscGroup;
    private ControlGroup kubeWallGroup;
    private ControlGroup midiMappingGroup;
    private ControlGroup lightingMappingGroup;
    private ControlGroup animationGroup;
    private ControlGroup networkingGroup;

    private MiscControls miscControls;
    private KubeWallControls kubeWallControls;
    private ProfilesControls profilesControls;
    private AudioMappingControls audioMappingControls;
    private LightingMappingControls lightingMappingControls;
    private AnimationControls animationControls;
    private NetworkingControls networkingControls;


    public FKControls() {
        cp5 = new ControlP5(p5);
        cp5.setMoveable(false);

        controlWindow = cp5
                .addControlWindow("FKControls", WINDOW_POSITION_X, WINDOW_POSITION_Y, WINDOW_WIDTH, WINDOW_HEIGHT)
                .hideCoordinates()
                .setBackground(40);

        kubeWallControls = new KubeWallControls("kubeWallGroup", cp5, controlWindow);
        kubeWallGroup = kubeWallControls.setup();
        kubeWallGroup.setPosition(WINDOW_PADDING, 120);

        miscControls = new MiscControls("miscGroup", cp5, controlWindow);
        miscGroup = miscControls.setup();
        miscGroup.setPosition(350, 120);

        audioMappingControls = new AudioMappingControls("midiMappingGroup", cp5, controlWindow);
        midiMappingGroup = audioMappingControls.setup();
        midiMappingGroup.setPosition(550, 120);

        lightingMappingControls = new LightingMappingControls("lightingMappingGroup", cp5, controlWindow);
        lightingMappingGroup = lightingMappingControls.setup();
        lightingMappingGroup.setPosition(550, 340);

        profilesControls = new ProfilesControls("profile_group", cp5, controlWindow);
        profilesGroup = profilesControls.setup();
        profilesGroup.setPosition(WINDOW_PADDING, WINDOW_PADDING);

        animationControls = new AnimationControls("animation_group", cp5, controlWindow);
        animationGroup = animationControls.setup();
        animationGroup.setPosition(350, 450);

        networkingControls = new NetworkingControls("networkingGroup", cp5, controlWindow);
        networkingGroup = networkingControls.setup();
        networkingGroup.setPosition(550, 520);
        networkingGroup.hide();
    }


    public void init() {
        networkingControls.getUpdateButton().update();
        animationControls.populateAnimationModeDDL();
        p5.getKubeWall().setAnimationMode(animationControls.getAnimationMode().getItem((int) animationControls.getAnimationMode().getValue()).getName());
        p5.getKubeWall().setKubeWallPosition();

        p5.recreateKubeWall();
    }


    /*Utils methods*/

    public static Textlabel createGroupTitle(ControlGroup theGroup, String theTitle) {
        return cp5.addTextlabel(theGroup.getName() + "_title")
                .setText(theTitle)
                .setPosition(0, 0)
                .setGroup(theGroup)
                .setFont(p5.createFont("Georgia", 18));
    }

    public static DropdownList createDropdownList(String theName) {
        DropdownList list = cp5.addDropdownList(theName);
        list.setBackgroundColor(p5.color(190));
        list.setItemHeight(20);
        list.setBarHeight(17);
        list.captionLabel().getStyle().marginTop = 3;
        list.captionLabel().getStyle().marginLeft = 3;
        list.captionLabel().getStyle().marginTop = 3;
        //list.scroll(0);
        list.setColorBackground(p5.color(60));
        list.setColorActive(p5.color(255, 128));

        return list;
    }



    /*Getters and setters*/

    public ControlWindow getControlWindow() {
        return controlWindow;
    }

    public static ControlP5 getCp5() {
        return cp5;
    }

    public MiscControls getMiscControls() {
        return miscControls;
    }

    public KubeWallControls getKubeWallControls() {
        return kubeWallControls;
    }

    public ProfilesControls getProfilesControls() {
        return profilesControls;
    }

    public LightingMappingControls getLightingMappingControls() {
        return lightingMappingControls;
    }

    public AudioMappingControls getAudioMappingControls() {
        return audioMappingControls;
    }

    public AnimationControls getAnimationControls() {
        return animationControls;
    }

    public NetworkingControls getNetworkingControls() {
        return networkingControls;
    }
}