package observation;

import java.awt.geom.Point2D;
import java.util.List;
import model.FI;
import myLib.utils.Utils;

/**
 * 観測を行う抽象クラス 密度を変えながらシミュレーションを行い、その結果を得る
 *
 * @author tadaki
 */
public abstract class AbstractObservation {

    protected final FI sys;

    public AbstractObservation(int numCells, int maxSpeed) {
        sys = new FI(numCells, maxSpeed, 0);
    }

    /**
     * 平均量の取得
     *
     * @param tmax 観測時間
     * @return
     */
    abstract public double calcValue(int tmax);

    /**
     * 平均量の取得
     *
     * @param dn 車両数の変化
     * @param tmax 観測時間
     * @param tRelax 緩和時間
     * @return
     */
    public List<Point2D.Double> calcValues(int dn, int tmax, int tRelax) {
        List<Point2D.Double> pList = Utils.createList();
        int numCells = sys.getNumCell();
        int numCar = dn;
        while (numCar < numCells) {
            initializeAndRelax(numCar, tRelax);
            double value = calcValue(tmax);
            pList.add(new Point2D.Double((double) numCar / numCells, value));
            numCar += dn;
        }
        return pList;
    }

    /**
     * 初期化して緩和
     *
     * @param numCar 車両数
     * @param tmax 緩和させる時間
     */
    protected void initializeAndRelax(int numCar, int tmax) {
        sys.setNumCar(numCar);
        for (int t = 0; t < tmax; t++) {
            sys.update();
        }
    }
}
