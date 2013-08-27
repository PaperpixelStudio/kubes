package net.paperpixel.fk.gui;

import controlP5.ControlGroup;
import controlP5.ControlP5;
import controlP5.ControlWindow;
import net.paperpixel.fk.core.FKProcessing;

public abstract class AbstractControlGroup extends FKProcessing {

    protected ControlP5 cp5;
    protected ControlWindow controlWindow;
    protected ControlGroup controlGroup;
    protected String name;

    public AbstractControlGroup(String theName, ControlP5 theCp5, ControlWindow theControlWindow) {
        name = theName;
        cp5 = theCp5;
        controlWindow = theControlWindow;
        controlGroup = createControlGroup(name);
    }

    public abstract ControlGroup setup();
    
    protected ControlGroup createControlGroup(String theName) {
        return cp5.addGroup(theName)
                .hideBar()
                .moveTo(controlWindow);
    }

    public ControlGroup getControlGroup() {
        return controlGroup;
    }

    protected void setControlGroup(ControlGroup theControlGroup) {
        controlGroup = theControlGroup;
    }
}
