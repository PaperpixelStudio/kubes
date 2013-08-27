package net.paperpixel.fk.pointcloud;

import net.paperpixel.fk.core.FKProcessing;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 18/11/12
 * Time: 16:12
 * To change this template use File | Settings | File Templates.
 */
public abstract class PointCloudDiplayer extends FKProcessing{

    protected float minimumDistanceToTrace = 50;

    public abstract void drawUniquePoint(PVector point);
    public abstract PVector drawUniquePoint(PVector point, PVector oldPoint);
    public abstract void drawPointCloud(PVector[] depthMap);
}
