package simulator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class lifesimulator {
    public static void main(String[] args) {
        final int width = 100;
        final int height = 100;
        final int initialPeopleCount = new Random().nextInt(6) + 10; // создать от 10 до 15 людей

        territory territory = new territory(width, height, initialPeopleCount);

        Timer timer = new Timer();

        // Задача для перемещения людей каждые 2 миллисекунды
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                territory.movePeople();
                territory.simulateReproduction();
            }
        }, 0, 200);

        // Задача для старения людей каждую минуту
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                territory.agePeople();
            }
        }, 0, 60000); // но для теста можно устновить значение меньше

        // Основное меню
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.println("\nСимуляция жизни");
            System.out.println("1. Показать всех людей");
            System.out.println("2. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();

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
