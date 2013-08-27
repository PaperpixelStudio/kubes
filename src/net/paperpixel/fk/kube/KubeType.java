package net.paperpixel.fk.kube;

import net.paperpixel.fk.core.FKConstants;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum KubeType {
    INACTIVE_KUBE("inactive", InactiveKube.class.getName(), FKConstants.INACTIVE_KUBE_COLOR),
    EFFECT_KUBE("effect", EffectKube.class.getName(), FKConstants.EFFECT_KUBE_COLOR),
    NOTE_KUBE("note", NoteKube.class.getName(), FKConstants.NOTE_KUBE_COLOR),
  //  IDLE_KUBE("idle", IdleKube.class.getName(), FKConstants.IDLE_KUBE_COLOR),
    LOOP_KUBE("loop", LoopKube.class.getName(), FKConstants.LOOP_KUBE_COLOR);

    String name;
    String className;
    Color color;

    KubeType(String theName, String theClassName, Color theColor) {
        name = theName;
        className = theClassName;
        color = theColor;
    }

    // Joli !
    public AbstractKube getInstance(int theLine, int theCol) {
        try {
            Class myClass = (Class) Class.forName(className);
            Constructor myConstructor = myClass.getDeclaredConstructor(int.class, int.class);
            AbstractKube myKube = (AbstractKube) myConstructor.newInstance(theLine, theCol);
            myKube.setType(this);
            myKube.setColor(KubeWall.getRandomColor());
            return myKube;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color theColor) {
        color = theColor;
    }

    public String getName() {
        return name;
    }
}
