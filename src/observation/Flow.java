package observation;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import myLib.utils.FileIO;

/**
 *
 * @author tadaki
 */
public class Flow extends AbstractObservation {

    public Flow(int numCells, int maxSpeed) {
        super(numCells, maxSpeed);
    }

    @Override
    public double calcValue(int tmax) {
        int num = 0;
        for (int t = 0; t < tmax; t++) {
            sys.update();
            List<Integer> speedList = sys.getSpeeds();
            for (int s : speedList) {
                num += s;
            }
        }
        return (double) num / (tmax * sys.getNumCell());
    }

    public static void main(String args[]) throws IOException {
        int numCells = 1000;
        int maxSpeed = 3;
        Flow flow = new Flow(numCells, maxSpeed);
        List<Point2D.Double> pList = flow.calcValues(20, 100, numCells);
        try (BufferedWriter out = FileIO.openWriter("Flow-output.txt")) {
            for (Point2D.Double p : pList) {
                FileIO.writeSSV(out, p.x, p.y);
            }
        }
    }
}
