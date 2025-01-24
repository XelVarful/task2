package simulator;
import java.util.Random;
public class Person {
    private final String gender;
    private int age;
    private int x;
    private int y;

    public Person(String gender, int age, int x, int y) {
        this.gender = gender;
        this.age = age;
        this.x = x;
        this.y = y;
    }

    public Person(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        age++;
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


    public String toString() {
        return "Пол: " + gender + ", Возраст: " + age + ", Координаты: (" + x + ", " + y + ")";
    }
}
