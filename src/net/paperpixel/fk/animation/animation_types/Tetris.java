package net.paperpixel.fk.animation.animation_types;

import net.paperpixel.fk.animation.AbstractAnimation;

import java.util.ArrayList;

public class Tetris extends AbstractAnimation {
    @Override
    protected ArrayList<ArrayList<Integer>> getAnimationSteps() {
        return addSteps(
                createStep(12, 13),
                createStep(6, 7, 12),
                createStep(0, 1, 6, 12),
                createStep(0, 1, 6, 12, 14, 15),
                createStep(0, 1, 6, 12, 8, 9, 13, 14),
                createStep(0, 1, 6, 12, 2, 3, 7, 8),
                createStep(0, 1, 6, 12, 2, 3, 7, 8, 16),
                createStep(0, 1, 6, 12, 2, 3, 7, 8, 10, 15, 16),
                createStep(0, 1, 6, 12, 2, 3, 7, 8, 4, 9, 10, 16),
                createStep(0, 1, 6, 12, 2, 3, 7, 8, 4, 9, 10, 16, 17),
                createStep(0, 1, 6, 12, 2, 3, 7, 8, 4, 9, 10, 16, 11, 17),
                createStep(0, 1, 6, 12, 2, 3, 7, 8, 4, 9, 10, 16, 5, 11, 17),
                getAllKubeIds(),
                getAllKubeIds(),
                createStep(),
                getAllKubeIds(),
                createStep(),
                getAllKubeIds(),
                createStep(),
                getAllKubeIds()
        );
    }

    @Override
    public String getName() {
        return "Tetris";
    }

    @Override
    public boolean hasRandom() {
        return false;
    }
}
