package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner, Navigation<Screen> {
    private Screen currentScreen;

    @Autowired
    public Application(ContactRepository contactRepository) {
        currentScreen = new HomeScreen(contactRepository, this);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (currentScreen != null) {
            try {
                System.out.println(currentScreen);
                System.out.println(currentScreen.input(scanner.nextLine()));
                System.out.println();
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    @Override
    public void navigate(Screen current) {
        this.currentScreen = current;
    }
}
