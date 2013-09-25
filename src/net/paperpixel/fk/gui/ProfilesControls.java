package net.paperpixel.fk.gui;

import controlP5.*;
import net.paperpixel.fk.communication.DMXCommunication;
import net.paperpixel.fk.communication.MidiCommunication;
import processing.core.PApplet;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class ProfilesControls extends AbstractControlGroup {

    private static final String CONFIG_PATH = "config";

    private String profileName = "";

    private DropdownList profileList;
    private Textfield saveAsTextfield;

    public ProfilesControls(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        super(theName, theCp5, theControlWindow);
    }

    @Override
    public ControlGroup setup() {
        int pos_x = 0;

        /*Title*/

        FKControls.createGroupTitle(controlGroup, "Profile");


        /*Label for profile load*/

        Textlabel load_label = cp5.addTextlabel(name + "_load_label")
                .setText("Profile: ")
                .setPosition(pos_x, FKControls.GROUP_TITLE_HEIGHT)
                .setGroup(controlGroup);

        pos_x += 50 + FKControls.CONTROL_PADDING;


        /*Dropdown list for profiles */

        profileList = FKControls.createDropdownList(name + "_load_list")
                .setLabel("-- Profiles")
                .setPosition(pos_x, FKControls.GROUP_TITLE_HEIGHT + 12)
                .setSize(150, 200)
                .setGroup(controlGroup)
                .addListener(new LoadProfileListener());

        _populateProfileList();

        pos_x += 150 + FKControls.CONTROL_PADDING;


        /*Button to load profiles*/

        cp5.addButton(name + "_load_button")
                .setCaptionLabel("Save profile")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(pos_x, FKControls.GROUP_TITLE_HEIGHT - 8)
                .setSize(80, 19)
                .setGroup(controlGroup)
                .addListener(new SaveProfileListener())
                .setBroadcast(true);

        pos_x += 80 + (FKControls.CONTROL_PADDING * 2);


        /*Textfield to save file as*/

        saveAsTextfield = cp5.addTextfield(name + "_save_as_textfield")
                .setText("")
                .setLabelVisible(false)
                .setPosition(pos_x, FKControls.GROUP_TITLE_HEIGHT - 8)
                .setSize(90, 20)
                .setGroup(controlGroup);

        pos_x += 90 + FKControls.CONTROL_PADDING;


        /*Button to save file as*/

        Button save_as_button = cp5.addButton(name + "save_as_button")
                .setCaptionLabel("Save profile as")
                .setBroadcast(false)
                .setValue(0)
                .setPosition(pos_x, FKControls.GROUP_TITLE_HEIGHT - 8)
                .setSize(100, 20)
                .setGroup(controlGroup)
                .setBroadcast(true)
                .addListener(new SaveProfileAsListener());

        return controlGroup;
    }

    private void _populateProfileList() {
        profileList.clear();
        File[] configFiles = _listFileNames();

        if(configFiles != null) {
            int incr = 0;
            for(int i = 0; i < configFiles.length -1; i++) {
                String fileName = configFiles[i].getName();
                int extensionDot = fileName.lastIndexOf(".");
                if (extensionDot > 0) {
                    String name = fileName.substring(0, extensionDot);
                    String extension = fileName.substring(extensionDot + 1);

                    /*checks if extension is .ser*/
                    if (extension.equals("ser")) {
                        profileList.addItem(name, incr);

                        /*sets dropdown value to profile just loaded*/
                        if (name.equals(profileName)) {
                            profileList.setValue(incr);
                        }
                        incr++;
                    }
                }
            }
        }
    }


    public void loadConfigFile(int theIndex) {
        ListBoxItem profileNameItem = profileList.getItem(theIndex);
        if (profileNameItem != null) {
            profileName = profileNameItem.getName();
            cp5.loadProperties(profileName);

            /*For enttec DMX USB PRO we use DMXCommunication class to send info through serial*/
//            MidiCommunication.populateMidiDevices(p5.getControls().getLightingMappingControls().getLightingDeviceDDL());
            MidiCommunication.populateMidiDevices(p5.getControls().getAudioMappingControls().getAudioDeviceDDL());
            p5.getControls().getDMXMappingControls().populateSerialPorts();
            _populateProfileList();
            DMXCommunication.setup(p5.getControls().getDMXMappingControls().getSerialPortName());
        }
    }


    /*Getters and setters*/


    public DropdownList getProfileList() {
        return profileList;
    }

    public String getProfileName() {
        return profileName;
    }

    /*Private methods*/

    private static File[] _listFileNames() {
        File file = new File(p5.sketchPath);
        if (file.isDirectory()) {
            String[] fileList = file.list();
            File[] files = file.listFiles();
            Arrays.sort( files, new Comparator() {
                public int compare(Object o1, Object o2) {

                    if (((File)o1).lastModified() > ((File)o2).lastModified()) {
                        return -1;
                    } else if (((File)o1).lastModified() < ((File)o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }
            });
            return files;
        } else {
            return null;
        }
    }


    /*Listeners*/

    class LoadProfileListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            loadConfigFile((int) profileList.getValue());
            p5.getControls().init();
        }
    }
    class SaveProfileListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            cp5.saveProperties(profileName);
        }
    }

    class SaveProfileAsListener implements ControlListener {
        public void controlEvent(ControlEvent theEvent) {
            String myProfileName = saveAsTextfield.getText();

            if (myProfileName.length() > 0) {
                profileName = myProfileName;
                saveAsTextfield.setText("");
                cp5.saveProperties(profileName);
                _populateProfileList();
            }
        }
    }
}
