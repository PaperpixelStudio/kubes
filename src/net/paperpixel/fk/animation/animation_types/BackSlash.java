package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

public class BackSlash extends AbstractAnimation{

    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {
        return addSteps(
                createStep(2, 5, 6, 9, 13, 16),
                createStep(1, 4, 8, 11, 12, 15),
                createStep(0, 3, 7, 10, 14, 17),
                createStep(2, 5, 6, 9, 13, 16),
                createStep(1, 4, 8, 11, 12, 15),
                createStep(0, 3, 7, 10, 14, 17)
        );
    }

    @Override
    public String getName() {
        return "Backslash";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
