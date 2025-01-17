package simulator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


// класс отвечающий за людей (у каждого присвоенно свои значения)
class Person {
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

    @Override
    public String toString() {
        return "Пол: " + gender + ", Возраст: " + age + ", Координаты: (" + x + ", " + y + ")";
    }
}

// класс отвечавющий за территорию
class Territory {
    private final int width;
    private final int height;
    private final ArrayList<Person> people;

    public Territory(int width, int height, int initialPeopleCount) {
        this.width = width;
        this.height = height;
        this.people = new ArrayList<>();
        initializePeople(initialPeopleCount);
    }

    private void initializePeople(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String gender = random.nextBoolean() ? "Мужчина" : "Женщина";
            int age = random.nextInt(80);
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            people.add(new Person(gender, age, x, y));
        }
    }

    public void movePeople() {
        for (Person person : people) {
            person.move(width, height);
        }
    }

    public void agePeople() {
        for (Person person : people) {
            person.incrementAge();
        }
    }

    public void simulateReproduction() {
        Random random = new Random();
        ArrayList<Person> newPeople = new ArrayList<>();

        for (int i = 0; i < people.size(); i++) {
            for (int j = i + 1; j < people.size(); j++) {
                Person p1 = people.get(i);
                Person p2 = people.get(j);

                if (p1.getX() == p2.getX() && p1.getY() == p2.getY()
                        && !p1.getGender().equals(p2.getGender())
                        && p1.getAge() >= 16 && p2.getAge() >= 16
                        && random.nextDouble() < 1) { // 100% шанс размножения для примера, потом изменю

                    String gender = random.nextBoolean() ? "Мужчина" : "Женщина";
                    int x = p1.getX();
                    int y = p1.getY();
                    newPeople.add(new Person(gender, 0, x, y));
                }
            }
        }

        people.addAll(newPeople);
        if (!newPeople.isEmpty()) {
            System.out.println(newPeople.size() + " новых людей родилось.");
        }
    }

    public void displayPeople() {
        System.out.println("Количество людей: " + people.size());
        for (Person person : people) {
            System.out.println(person);
        }
    }
}

// класс за который отвечает логику программы
public class LifeSimulator {
    public static void main(String[] args) {
        final int width = 100;
        final int height = 100;
        final int initialPeopleCount = new Random().nextInt(6) + 10; // создать от 10 до 16 людей

        Territory territory = new Territory(width, height, initialPeopleCount);

        Timer timer = new Timer();

        // Задача для перемещения людей каждые 20 секунд
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                territory.movePeople();
                territory.simulateReproduction();
            }
        }, 0, 200);

        // Задача для старения людей каждую минуту
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                territory.agePeople();
            }
        }, 0, 60000);

        // Основное меню
        while (true) {
            System.out.println("\nСимуляция жизни");
            System.out.println("1. Показать всех людей");
            System.out.println("2. Выход");
            System.out.print("Выберите действие: ");

            int choice = new java.util.Scanner(System.in).nextInt();

            switch (choice) {
                case 1 -> territory.displayPeople();
                case 2 -> {
                    System.out.println("Выход из программы.");
                    timer.cancel();
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}
