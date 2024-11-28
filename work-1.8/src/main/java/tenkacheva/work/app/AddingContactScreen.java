package tenkacheva.work.app;

public class AddingContactScreen implements Screen {
    private final ContactRepository repository;
    private final Navigation<Screen> navigation;

    public AddingContactScreen(ContactRepository repository, Navigation<Screen> navigation) {
        this.repository = repository;
        this.navigation = navigation;
    }

    @Override
    public String input(String command) {
        navigation.navigate(new HomeScreen(repository, navigation));
        ContactParser parser = new ContactParser();

        try {
            Contact contact = parser.parseOne(command);
            if (!repository.add(contact)) {
                return "Contact cannot be added";
            }
        } catch (ContactParserException exception) {
            return exception.getMessage();
        }

        return "OK";
    }

    @Override
    public String toString() {
        return "Enter a contact: ";
    }
}
