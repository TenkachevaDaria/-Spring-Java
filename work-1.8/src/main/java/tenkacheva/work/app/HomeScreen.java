package tenkacheva.work.app;

import java.util.stream.Collectors;

public class HomeScreen implements Screen {
    private final ContactRepository repository;
    private final Navigation<Screen> navigation;

    public HomeScreen(ContactRepository repository, Navigation<Screen> navigation) {
        this.repository = repository;
        this.navigation = navigation;
    }

    @Override
    public String input(String command) {
        int number;
        try {
            number = Integer.parseInt(command);
        } catch (NumberFormatException exception) {
            return "The command must be a number.";
        }

        return switch (number) {
            case 1 -> onAddContact();
            case 2 -> onGetAllContacts();
            case 3 -> onRemoveContactByEmail();
            case 4 -> onExit();
            default -> "Please enter a right command.";
        };
    }

    @Override
    public String toString() {
        return """
                1) Add Contact
                2) Get All Contacts
                3) Remove Contact By Email
                4) Exit
                """;
    }

    private String onAddContact() {
        navigation.navigate(new AddingContactScreen(repository, navigation));
        return "";
    }

    private String onGetAllContacts() {
        return repository
                .getAll()
                .stream()
                .map(Contact::toString)
                .collect(Collectors.joining("\n"))
                .replace(";", " |");
    }

    private String onRemoveContactByEmail() {
        navigation.navigate(new RemovingContactScreen(repository, navigation));
        return "";
    }

    private String onExit() {
        navigation.navigate(null);
        return "";
    }
}
