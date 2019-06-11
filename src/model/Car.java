package model;

/**
 * Fukui-Ishibashiモデル中の車両クラス
 *
 * @author tadaki
 */
public class Car {

    private int position;
    private int speed;
    private final int maxSpeed;

    public Car(int position, int speed, int maxSpeed) {
        this.position = position;
        this.speed = speed;
        this.maxSpeed = maxSpeed;
    }

    public Car(int position, int maxSpeed) {
        this.position = position;
        this.speed = 0;
        this.maxSpeed = maxSpeed;
    }

    /**
     * 速度の決定
     *
     * @param gap 先行車両との間の空きセル数
     * @return 決定した速度
     */
    public int evalSpeed(int gap) {
        speed = Math.min(gap, maxSpeed);
        return speed;
    }

    /**
     * 移動
     *
     * @param length システムのセル数
     * @return
     */
    public int move(int length) {
        position = (position + speed) % length;
        return position;
    }

    public int getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }
}
