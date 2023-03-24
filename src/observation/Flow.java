package observation;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Random;

/**
 *
 * @author tadaki
 */
public class Flow extends AbstractObservation {

    public Flow(int numCells, int maxSpeed, Random random) {
        super(numCells, maxSpeed, random);
    }

    /**
     * Calculate average flow
     */
    @Override
    public double calcValue() {
        int speedSum = 0;
        List<Integer> speedList = sys.getSpeeds();
        for (int s : speedList) {
            speedSum += s;
        }
        return (double) speedSum / (sys.getNumCell());
    }

    public static void main(String args[]) throws IOException {
        int numCells = 1000;
        int maxSpeed = 3;
        Flow flow = new Flow(numCells, maxSpeed, new Random(48L));
        List<Point2D.Double> pList = flow.calcValues(20, numCells);
        try (PrintStream out = new PrintStream("Flow-output.txt")) {
            pList.forEach(p->out.println(p.x+" "+p.y));
        }
    }
}
