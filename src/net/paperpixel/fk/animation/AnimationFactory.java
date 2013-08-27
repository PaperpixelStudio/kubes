package net.paperpixel.fk.animation;

import net.paperpixel.fk.animation.animation_types.*;
import net.paperpixel.fk.core.FKProcessing;
import processing.core.PApplet;

import javax.swing.*;
import java.util.*;

public class AnimationFactory extends FKProcessing {

    static ArrayList<AbstractAnimation> animations;
    static int sequenceCount = 0;
    static HashMap<Class, AbstractAnimation> animationClasses;

    public static void createAnimations() {
        animationClasses = new HashMap<Class, AbstractAnimation>();
        animations = new ArrayList<AbstractAnimation>();
        
        resetAnimations();

        addAnimation(VerticalSweep.class);
    }
    
    private static void addAnimation(Class theClass) {
        addAnimation(theClass, 1);
    }

    private static void addAnimation(Class theClass, int theSequenceNumber) {
        if (animationClasses.get(theClass) == null) {
            try {
                animationClasses.put(theClass, (AbstractAnimation) theClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        
        for (int i = 0; i < theSequenceNumber; i++) {
            animations.add(animationClasses.get(theClass));
        }
    }

    public static ArrayList<ArrayList<Integer>> getAnimation() {
        int nextSequence = getNextSequence();
        return animations.get(nextSequence).getAnimation();
    }
    
    public static ArrayList<ArrayList<Integer>> getAnimation(Class theClass) {
        try {
            AbstractAnimation theAnimation = (AbstractAnimation) theClass.newInstance();
            return theAnimation.getAnimation();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static int getNextSequence() {
        if (sequenceCount >= animations.size()) {
            sequenceCount = 0;
        }
        return sequenceCount++;
    }

    private static void resetAnimations() {
        for (Map.Entry<Class, AbstractAnimation> entry : animationClasses.entrySet()) {
            entry.getValue().reset();
        }
    }
}
