package observation;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;
import model.FI;
import myLib.utils.Utils;

/**
 * 観測を行う抽象クラス 密度を変えながらシミュレーションを行い、その結果を得る
 *
 * @author tadaki
 */
public abstract class AbstractObservation {

    protected final FI sys;

    public AbstractObservation(int numCells, int maxSpeed, Random random) {
        sys = new FI(numCells, maxSpeed, 0, random);
    }

    /**
     * 平均量の取得
     *
     * @param tmax 観測時間
     * @return
     */
    abstract public double calcValue();

    /**
     * 平均量の取得
     *
     * @param dn 車両数の変化
     * @param tRelax 緩和時間
     * @return
     */
    public List<Point2D.Double> calcValues(int dn, int tRelax) {
        List<Point2D.Double> pList = Utils.createList();
        int numCells = sys.getNumCell();
        int numCar = dn;
        while (numCar < numCells) {//各車両数に対して
            initializeAndRelax(numCar, tRelax);//初期状態からの緩和
            double value = calcValue();//観測量を取得
            pList.add(new Point2D.Double((double) numCar / numCells, value));
            numCar += dn;
        }
        return pList;
    }

    /**
     * 初期化して緩和
     *
     * @param numCar 車両数
     * @param tRelax 緩和させる時間
     */
    protected void initializeAndRelax(int numCar, int tRelax) {
        sys.setNumCar(numCar);
        for (int t = 0; t < tRelax; t++) {
            sys.update();
        }
    }
}
