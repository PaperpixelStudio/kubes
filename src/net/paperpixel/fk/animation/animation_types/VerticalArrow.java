package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

public class VerticalArrow extends AbstractAnimation{
    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {
        return addSteps(
                createStep(0, 3, 8, 11, 12, 15),
                createStep(2, 5, 7, 10, 14, 17),
                createStep(1, 4, 6, 9, 13, 16),
                createStep(0, 3, 8, 11, 12, 15),
                createStep(2, 5, 7, 10, 14, 17),
                createStep(1, 4, 6, 9, 13, 16)
        );
    }

    @Override
    public String getName() {
        return "Vertical Arrow";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
