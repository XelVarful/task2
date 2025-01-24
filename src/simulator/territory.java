package simulator;

import java.util.ArrayList;
import java.util.Random;

public class territory {
    private final int width;
    private final int height;
    private final ArrayList<Person> people;

    public territory(int width, int height, int initialPeopleCount) {
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
                        && random.nextDouble() < 1) { // 50% шанс размножения

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
