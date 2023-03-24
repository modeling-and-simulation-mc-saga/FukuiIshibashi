package model;

/**
 * Car class for Fukui-Ishibashi model
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
        this(position,0,maxSpeed);
    }

    /**
     * Decide speed from the front gap
     *
     * @param gap empty cells to the preceding car
     * @return speed
     */
    public int evalSpeed(int gap) {
        speed = Math.min(gap, maxSpeed);
        return speed;
    }

    /**
     * Move
     *
     * @param length the length of the system
     * @return
     */
    public int move(int length) {
        position = (position + speed) % length;
        return position;
    }

    //*** getters 
    
    public int getPosition() {
        return position;
    }

    public int getSpeed() {
        return speed;
    }
}
