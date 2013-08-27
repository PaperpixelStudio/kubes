package net.paperpixel.fk.pointcloud;

import processing.core.PApplet;
import processing.core.PVector;
import quickhull3d.Point3d;
import quickhull3d.QuickHull3D;

import java.util.ArrayList;

public class MeshDisplay extends PointCloudDiplayer {

    private QuickHull3D hull;

    public MeshDisplay() {
        hull = new QuickHull3D();
    }

    @Override
    public void drawUniquePoint(PVector point) {
    }

    @Override
    public PVector drawUniquePoint(PVector point, PVector oldPoint) {
        return null;
    }

    @Override
    public void drawPointCloud(PVector[] depthMap) {

        /*DO NOT USE : beaauuucoup trop lent*/

        ArrayList<QuickHull3D> hulls = new ArrayList<QuickHull3D>();
        ArrayList<Point3d> points = new ArrayList<Point3d>();
        PVector oldVector = depthMap[0];
        QuickHull3D myHull = new QuickHull3D();

        for (int i = 1; i < depthMap.length; i++) {
            PVector myVector = depthMap[i];

            if (myVector.dist(oldVector) > 500) {
                if (points.size() > 10) {
                    try {
                        myHull.build(points.toArray(new Point3d[points.size()]));
                    } catch(IllegalArgumentException e) {

                    }
                    hulls.add(myHull);
                    myHull = new QuickHull3D();
                }
                points.clear();
            }

            if (myVector.z < 3000) {
                points.add(new Point3d(myVector.x, myVector.y, myVector.z));

                oldVector = myVector;
            }
        }

//        Point3d[] points = new Point3d[depthMap.length];
//        for (int i = 0; i < depthMap.length; i++) {
//            PVector vector = depthMap[i];
//            points[i] = new Point3d(vector.x, vector.y, vector.z);
//        }
//        hull.build(points);

        p5.fill(255, 50);
        p5.stroke(255, 70);

        for (QuickHull3D hull : hulls) {
            int[][] faceIndices = hull.getFaces();
            for (int i = 0; i < faceIndices.length; i++) {
                p5.beginShape();
                for (int k = 0; k < faceIndices[i].length; k++) {
                    Point3d point = hull.getVertices()[faceIndices[i][k]];
                    p5.vertex((float) point.x, (float) point.y, (float) point.z);
                }
                p5.endShape();
            }
        }

//        PApplet.println(hull.getNumFaces());
    }
}
