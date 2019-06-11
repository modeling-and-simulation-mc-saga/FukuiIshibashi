package model;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import myLib.utils.Utils;

/**
 * Fukui-Ishibashi交通流モデル
 *
 * @author tadaki
 */
public class FI {

    private final int numCell;//セル数
    private final int maxSpeed;//最高速度
    private int numCar;//車両数
    private List<Car> carList;

    public FI(int numCell, int maxSpeed, int numCar) {
        if (numCell < numCar) {
            throw new IllegalArgumentException();
        }
        this.numCell = numCell;
        this.maxSpeed = maxSpeed;
        this.numCar = numCar;
        initialize();
    }

    /**
     * 初期化：停止した車両をランダムな間隔で配置する
     */
    private void initialize() {
        carList = Utils.createList();
        //0からL-1の中から、numCar個の整数をランダムに選択
        int[] positions = Utils.createRandomNumberList(numCell, numCar);
        Arrays.sort(positions);//昇順に整列
        for (int i = 0; i < numCar; i++) {
            int x = positions[numCar - 1 - i];
            carList.add(new Car(x, maxSpeed));
        }
    }

    /**
     * 状態更新
     */
    public void update() {
        //各車両の速度を計算
        for (int i = 0; i < numCar; i++) {
            int j = (i - 1 + numCar) % numCar;//先行車両
            int gap = carList.get(j).getPosition()
                    - carList.get(i).getPosition() - 1;
            gap = (gap + numCell) % numCell;
            carList.get(i).evalSpeed(gap);
        }
        //全車両を移動
        carList.stream().forEach(car -> car.move(numCell));
    }

    /**
     * 全ての車両の位置のリスト
     *
     * @return
     */
    public List<Integer> getPositions() {
        List<Integer> list = Utils.createList();
        carList.stream().forEachOrdered((car) -> {
            list.add(car.getPosition());
        });
        return list;
    }

    /**
     * 全ての車両の速度のリスト
     *
     * @return
     */
    public List<Integer> getSpeeds() {
        List<Integer> list = Utils.createList();
        carList.stream().forEachOrdered((car) -> {
            list.add(car.getSpeed());
        });
        return list;
    }

    /**
     * 系の状態を印刷
     * @param out 
     */
    public void printState(PrintStream out) {
        List<Integer> positions = getPositions();
        int p[] = new int[numCell];
        positions.stream().forEach((i) -> {
            p[i] = 1;
        });
        for (int i = 0; i < p.length; i++) {
            switch (p[i]) {
                case 0:
                    out.print(" ");
                    break;
                default:
                    out.print("*");
                    break;
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
     * 指定した場所から後方（座標の小さい方向）に停止した車両を配置
     * @param x0 
     */
    public void  forceInitial(int x0){
        carList = Utils.createList();
        for (int i = 0; i < numCar; i++) {
            int x = x0-i;
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
        int vmax=2;
        FI sys = new FI(numCells,vmax,n);
        for(int t=0;t<tMax;t++){
            sys.printState(System.out);
            sys.update();
        }
    }  
    
}
