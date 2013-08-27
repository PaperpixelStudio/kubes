package net.paperpixel.fk.pointcloud;

import net.paperpixel.fk.core.FKConstants;
import processing.core.PApplet;
import processing.core.PVector;

/**
 * Created with IntelliJ IDEA.
 * User: MacBookPro17
 * Date: 18/11/12
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public class PCLinesDisplay extends PointCloudDiplayer {

    private PVector oldRealWorldPoint;

    public void drawUniquePoint(PVector realWorldPoint){

        p5.pushMatrix();
        p5.translate(0, 0, FKConstants.KINECT_DISTANCE);
        if (realWorldPoint.z < 3000) { //KINECT_MAX_DEPTH
            if (oldRealWorldPoint == null || realWorldPoint.dist(oldRealWorldPoint) > super.minimumDistanceToTrace) {
                oldRealWorldPoint = new PVector(realWorldPoint.x - 1, realWorldPoint.y - 1, realWorldPoint.z - 1);
            }
            int orientation = (int) Math.abs(oldRealWorldPoint.y - realWorldPoint.y);
            p5.stroke(PApplet.map(orientation, 0, 20, 10, 255));

            p5.line(realWorldPoint.x, realWorldPoint.y, realWorldPoint.z, oldRealWorldPoint.x, oldRealWorldPoint.y, oldRealWorldPoint.z);
        }
        p5.popMatrix();
    }

    public PVector drawUniquePoint(PVector realWorldPoint, PVector oldPoint){
        oldRealWorldPoint = oldPoint;
        drawUniquePoint(realWorldPoint);
        return oldRealWorldPoint;
    }

    public void drawPointCloud(PVector[] depthMap){

    }
}
