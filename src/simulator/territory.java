package simulator;

import java.util.ArrayList;
import java.util.Random;

public class territory {
    private final int width;
    private final int height;
    private final ArrayList<Person> people;
    private final ArrayList<simulator.bacteria> bacteria;

    public territory(int width, int height, int initialPeopleCount, int initialBacteriaCount) {
        this.width = width;
        this.height = height;
        this.people = new ArrayList<>();
        this.bacteria = new ArrayList<>();
        initializePeople(initialPeopleCount);
        initializeBacteria(initialBacteriaCount);
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
    } // инитиализация людей
    private void initializeBacteria(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            bacteria.add(new bacteria(x, y));
        }
    }// Инициализация бактерий
    public void moveEntities() { // перемещение людей и бактерии
        for (Person person : people) {
            person.move(width, height);
        }
        for (simulator.bacteria bact : bacteria) {
            bact.move(width, height);
        }
    }
    public void agePeople() { // смерть людей по возрасту
        people.removeIf(person -> {
            if (person.getAge() >= 75 && new Random().nextDouble() < 0.7) {
                System.out.println(person + " умер от старости");
                return true;
            }
            person.incrementAge();
            return false;
        });
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
                        && random.nextDouble() < 0.5) { // для примера стоит 50 но можно для примера поставить значение 100

                    String gender = random.nextBoolean() ? "Мужчина" : "Женщина"; // рандомно выбирает пол при рождении
                    int x = p1.getX();
                    int y = p1.getY();
                    newPeople.add(new Person(gender, 0, x, y));
                }
            }
        }

        people.addAll(newPeople);
        if (!newPeople.isEmpty()) {
            System.out.println(newPeople.size() + " новых людей родилось");
        }
    }

    // Логика бактерий: убивают людей и размножаются
    public void simulateBacteriaEffects() {
        Random random = new Random();

        bacteria.addAll(reproduceBacteria());

        people.removeIf(person -> {
            for (simulator.bacteria bact : bacteria) {
                if (bact.kills(person) && random.nextDouble() < 0.4) { // 40% того что убьет
                    System.out.println(person + " убит бактерией");
                    return true;
                }
            }
            return false;
        });
    }
    private ArrayList<simulator.bacteria> reproduceBacteria() {
        Random random = new Random();
        ArrayList<simulator.bacteria> newBacteria = new ArrayList<>();

        for (simulator.bacteria bact : bacteria) {
            if (random.nextDouble() < 0.1) {  // размножение бактерии стоит на 10 но можно также изменить для примера
                int newX = bact.getX();
                int newY = bact.getY();
                newBacteria.add(new bacteria(newX, newY));
            }
        }
        if (!newBacteria.isEmpty()) {
            System.out.println(newBacteria.size() + " новых бактерий размножилось");
        }

        return newBacteria;
    }
    public void displayPeople() {
        System.out.println("Количество людей: " + people.size());
        for (Person person : people) {
            System.out.println(person);// вывод информации о человеке
        }
    }
    public void displayBacteria() {
        System.out.println("Количество бактерий: " + bacteria.size());
        for (simulator.bacteria bact : bacteria) {
            System.out.println(bact); // вывод информации о бактериях
        }
    }
}
