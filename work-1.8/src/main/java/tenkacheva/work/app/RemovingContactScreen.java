package tenkacheva.work.app;

public class RemovingContactScreen implements Screen {
    private final ContactRepository repository;
    private final Navigation<Screen> navigation;

    public RemovingContactScreen(ContactRepository repository, Navigation<Screen> navigation) {
        this.repository = repository;
        this.navigation = navigation;
    }

    @Override
    public String input(String command) {
        navigation.navigate(new HomeScreen(repository, navigation));

        if (repository.remove(command.trim())) {
            return "OK";
        }

        return "Not Found";
    }

    @Override
    public String toString() {
        return "Enter an email: ";
    }
}
