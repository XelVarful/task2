package simulator;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class Territory {
    private final int width;
    private final int height;
    private final ArrayList<Person> people;
    private final ArrayList<Bacteria> bacteria;

    public Territory(int width, int height, int initialPeopleCount, int initialBacteriaCount) {
        this.width = width;
        this.height = height;
        this.people = new ArrayList<>();
        this.bacteria = new ArrayList<>();
        initializePeople(initialPeopleCount);
        initializeBacteria(initialBacteriaCount);
    }

    private void initializePeople(int count) {
        for (int i = 0; i < count; i++) {
            String gender = ThreadLocalRandom.current().nextBoolean() ? "Мужчина" : "Женщина";
            int age = ThreadLocalRandom.current().nextInt(80);
            int x = ThreadLocalRandom.current().nextInt(width);
            int y = ThreadLocalRandom.current().nextInt(height);
            people.add(new Person(gender, age, x, y));
        }
    }

    private void initializeBacteria(int count) {
        for (int i = 0; i < count; i++) {
            int x = ThreadLocalRandom.current().nextInt(width);
            int y = ThreadLocalRandom.current().nextInt(height);
            bacteria.add(new Bacteria(x, y));
        }
    }

    public void moveEntities() {
        people.forEach(person -> person.move(width, height));
        bacteria.forEach(bact -> bact.move(width, height));
    }

    public void agePeople() {
        Predicate<Person> isOldAndDies = person -> {
            if (person.getAge() >= 75 && ThreadLocalRandom.current().nextDouble() < 0.7) {
                System.out.println(person + " умер от старости");
                return true;
            }
            person.incrementAge();
            return false;
        };
        people.removeIf(isOldAndDies);
    }

    public void simulateReproduction() {
        ArrayList<Person> newPeople = new ArrayList<>();

        for (int i = 0; i < people.size(); i++) {
            for (int j = i + 1; j < people.size(); j++) {
                Person p1 = people.get(i);
                Person p2 = people.get(j);

                if (p1.getX() == p2.getX() && p1.getY() == p2.getY()
                        && !p1.getGender().equals(p2.getGender())
                        && p1.getAge() >= 16 && p2.getAge() >= 16
                        && ThreadLocalRandom.current().nextDouble() < 0.7) {

                    String gender = ThreadLocalRandom.current().nextBoolean() ? "Мужчина" : "Женщина";
                    newPeople.add(new Person(gender, 0, p1.getX(), p1.getY()));
                }
            }
        }

        people.addAll(newPeople);
        if (!newPeople.isEmpty()) {
            System.out.println(newPeople.size() + " новых людей родилось");
        }
    }

    public void simulateBacteriaEffects() {
        // Убийство бактерий, если они находятся в одной клетке
        ArrayList<Bacteria> bacteriaToRemove = new ArrayList<>();
        for (Bacteria bact1 : bacteria) {
            for (Bacteria bact2 : bacteria) {
                if (bact1 != bact2 && bact1.getX() == bact2.getX() && bact1.getY() == bact2.getY()) {
                    bacteriaToRemove.add(bact1);
                    System.out.println("Бактерия " + bact1 + " умерла от конкуренции с " + bact2);
                    break;
                }
            }
        }
        bacteria.removeAll(bacteriaToRemove);

        // Размножение бактерий
        bacteria.addAll(reproduceBacteria());

        // Взаимодействие бактерий и людей
        ArrayList<Person> peopleToRemove = new ArrayList<>();
        ArrayList<Bacteria> bacteriaKilledByPeople = new ArrayList<>();

        for (Person person : people) {
            for (Bacteria bact : bacteria) {
                if (person.getX() == bact.getX() && person.getY() == bact.getY()) {
                    // 50% шанс, что бактерия убьет человека
                    if (ThreadLocalRandom.current().nextDouble() < 0.4) {
                        peopleToRemove.add(person);
                        System.out.println("Человек " + person + " убит бактерией ");
                    }
                    // 50% шанс, что человек убьет бактерию
                    else if (ThreadLocalRandom.current().nextDouble() < 0.6) {
                        bacteriaKilledByPeople.add(bact);
                        System.out.println("Бактерия " + bact + " убита человеком ");
                    }
                }
            }
        }

        people.removeAll(peopleToRemove);
        bacteria.removeAll(bacteriaKilledByPeople);
    }

    private ArrayList<Bacteria> reproduceBacteria() {
        ArrayList<Bacteria> newBacteria = new ArrayList<>();

        bacteria.forEach(bact -> {
            if (ThreadLocalRandom.current().nextDouble() < 0.01) { // 1% шанс размножения
                newBacteria.add(new Bacteria(bact.getX(), bact.getY()));
            }
        });

        if (!newBacteria.isEmpty()) {
            System.out.println(newBacteria.size() + " новых бактерий размножилось");
        }

        return newBacteria;
    }

    public void displayPeople() {
        System.out.println("Количество людей: " + people.size());
        people.forEach(System.out::println);
    }

    public void displayBacteria() {
        System.out.println("Количество бактерий: " + bacteria.size());
        bacteria.forEach(System.out::println);
    }
}