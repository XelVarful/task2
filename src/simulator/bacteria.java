package simulator;

import java.util.Random;

public class bacteria {
    private int x;
    private int y;

    public bacteria(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int maxX, int maxY) {
        Random random = new Random();
        x = Math.max(0, Math.min(maxX - 1, x + random.nextInt(3) - 1));
        y = Math.max(0, Math.min(maxY - 1, y + random.nextInt(3) - 1));
    }

    public boolean kills(Person person) {
        return this.x == person.getX() && this.y == person.getY();
    }
    public String toString() {
        return "Бактерия на координатах: (" + x + ", " + y + ")";
    }
}
