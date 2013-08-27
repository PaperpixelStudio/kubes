package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

public class Simple extends AbstractAnimation {

    public ArrayList<ArrayList<Integer>> getAnimationSteps() {
        return addSteps(
                createStep(1, 3),
                createStep(7, 10),
                createStep(11, 12, 13, 14)
        );
    }

    @Override
    public String getName() {
        return "Simple";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
