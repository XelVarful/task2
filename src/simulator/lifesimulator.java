package simulator;

import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask; // Абстрактый класс

public class lifesimulator {
    public static void main(String[] args) {
        final int width = 100;
        final int height = 100;
        final int initialPeopleCount = new Random().nextInt(17) + 10; // от 10 до 15
        final int initialBacteriaCount = new Random().nextInt(7) + 4; // от 4 до 8

        Territory territory = new Territory(width, height, initialPeopleCount, initialBacteriaCount);

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                territory.moveEntities();
                territory.simulateReproduction();
            }
        }, 0, 200); // каждые 20 секунд также можно сделать значение меньше для примера
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                territory.moveEntities();
            }
        }, 0, 200);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                territory.simulateBacteriaEffects();}
        }, 0, 2000);
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                territory.agePeople();
            }
        }, 0, 30000); // каждая минута для примера можно поставить знанчение меньше

        while (true) {
            System.out.println("\nСимуляция жизни");
            System.out.println("1. Показать всех людей");
            System.out.println("2. Показать всех бактерий");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> territory.displayPeople();
                case 2 -> territory.displayBacteria();
                case 3 -> {
                    System.out.println("Выход из программы.");
                    timer.cancel();
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}