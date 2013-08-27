package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

public class JohnnySundae extends AbstractAnimation {
    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {
        return addSteps(
                createStep(7, 10),
                createStep(1, 4, 6, 8, 9, 11, 13, 16),
                createStep(0, 2, 3, 5, 7, 10, 12, 14, 15, 17),
                createStep(0, 2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17),
                createStep(1, 2, 4, 6, 8, 9, 11, 13, 15, 16),
                createStep(2, 5, 12, 15),
                createStep(1, 2, 4, 6, 8, 9, 11, 13, 15, 16),
                createStep(0, 2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17),
                createStep(0, 2, 3, 5, 7, 10, 12, 14, 15, 17),
                createStep(1, 4, 6, 8, 9, 11, 13, 16),
                createStep(4, 13)
);
    }

    @Override
    public String getName() {
        return "Johnny Sundae";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
