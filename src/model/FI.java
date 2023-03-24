package model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Fukui-Ichibashi traffic flow model
 *
 * @author tadaki
 */
public class FI {

    private final int numCell;//the number of cells
    private final int maxSpeed;//maximum speed
    private int numCar;//the number of cars
    private List<Car> carList;
    private final Random random;

    public FI(int numCell, int maxSpeed, int numCar, Random random) {
        this.random = random;
        if (numCell < numCar) {
            throw new IllegalArgumentException();
        }
        this.numCell = numCell;
        this.maxSpeed = maxSpeed;
        this.numCar = numCar;
        initialize();
    }

    /**
     * Initalize: place stopped cars with random spacing
     */
    private void initialize() {
        carList = Collections.synchronizedList(new ArrayList<>());
        //Select random positions
        int[] positions = createRandomPosition(numCell, numCar);
        Arrays.sort(positions);//sorting positions
        for (int i = 0; i < numCar; i++) {
            int x = positions[numCar - 1 - i];
            carList.add(new Car(x, maxSpeed));
        }
    }

    /**
     * generate n random numbers in [0,nCell) Each random numbers appears once.
     *
     * @param nCell
     * @param n
     * @return
     */
    private int[] createRandomPosition(int nCell, int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nCell; i++) {
            list.add(i);
        }
        int count = 0;
        int c[] = new int[n];
        while (count < n) {
            int k = random.nextInt(list.size());
            int x = list.remove(k);
            c[count] = x;
            count++;
        }
        return c;
    }

    /**
     * update
     */
    public void update() {
        //Evaluate speeds of all cars
        for (int i = 0; i < numCar; i++) {
            int j = (i - 1 + numCar) % numCar;//preceding car
            int gap = carList.get(j).getPosition()
                    - carList.get(i).getPosition() - 1;
            gap = (gap + numCell) % numCell;
            carList.get(i).evalSpeed(gap);
        }
        //move all cars
        carList.forEach(car -> car.move(numCell));
    }

    /**
     * Getting positions of all cars
     *
     * @return
     */
    public List<Integer> getPositions() {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        carList.stream().forEachOrdered(car -> list.add(car.getPosition()));
        return list;
    }

    /**
     * Getting speeds of all cars
     *
     * @return
     */
    public List<Integer> getSpeeds() {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        carList.stream().forEachOrdered(car -> list.add(car.getSpeed()));
        return list;
    }

    /**
     * Print state to out
     *
     * @param out
     */
    public void printState(PrintStream out) {
        List<Integer> positions = getPositions();
        int p[] = new int[numCell];
        positions.forEach(i -> {
            p[i] = 1;
        });
        for (int i = 0; i < p.length; i++) {
            switch (p[i]) {
                case 0 -> out.print(" ");
                default -> out.print("*");
            }
        }
        out.println();
    }

    public void setNumCar(int numCar) {
        this.numCar = numCar;
        initialize();
    }

    public int getNumCell() {
        return numCell;
    }

    /**
     * Placing stopped cars backwards from specifyed position
     *
     * @param x0
     */
    public void forceInitial(int x0) {
        carList = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < numCar; i++) {
            int x = x0 - i;
            carList.add(new Car(x, maxSpeed));
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int tMax = 10;
        int numCells = 15;
        int n = 6;
        int vmax = 2;
        FI sys = new FI(numCells, vmax, n, new Random(48L));
        for (int t = 0; t < tMax; t++) {
            sys.printState(System.out);
            sys.update();
        }
    }

}
