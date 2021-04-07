package observation;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import myLib.utils.FileIO;

/**
 *
 * @author tadaki
 */
public class Flow extends AbstractObservation {

    public Flow(int numCells, int maxSpeed, Random random) {
        super(numCells, maxSpeed, random);
    }

    @Override
    public double calcValue() {
        //全車両の速度の和を求める
        
        
        //速度の和をセルの総数で除したものを返す

        return 0.;
    }

    public static void main(String args[]) throws IOException {
        int numCells = 1000;
        int maxSpeed = 3;
        Flow flow = new Flow(numCells, maxSpeed, new Random(48L));
        List<Point2D.Double> pList = flow.calcValues(20, numCells);
        try (BufferedWriter out = FileIO.openWriter("Flow-output.txt")) {
            for (Point2D.Double p : pList) {
                FileIO.writeSSV(out, p.x, p.y);
            }
        }
    }
}
