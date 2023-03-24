package observation;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import model.FI;

/**
 * Abstract class for observation
 * 
 * Observe by changing density
 *
 * @author tadaki
 */
public abstract class AbstractObservation {

    protected final FI sys;

    public AbstractObservation(int numCells, int maxSpeed, Random random) {
        sys = new FI(numCells, maxSpeed, 0, random);
    }

    /**
     * Get average value
     *
     * @return
     */
    abstract public double calcValue();

    /**
     * Get list of average values
     *
     * @param dn step for changing the number of cars
     * @param tRelax relaxing time
     * @return
     */
    public List<Point2D.Double> calcValues(int dn, int tRelax) {
        List<Point2D.Double> pList = Collections.synchronizedList(new ArrayList<>());
        int numCells = sys.getNumCell();
        int numCar = dn;
        while (numCar < numCells) {
            initializeAndRelax(numCar, tRelax);//relaxing
            double value = calcValue();//get average value
            pList.add(new Point2D.Double((double) numCar / numCells, value));
            numCar += dn;
        }
        return pList;
    }

    /**
     * initialize and relax
     *
     * @param numCar the number of cars
     * @param tRelax relaxing time
     */
    protected void initializeAndRelax(int numCar, int tRelax) {
        sys.setNumCar(numCar);
        for (int t = 0; t < tRelax; t++) {
            sys.update();
        }
    }
}
