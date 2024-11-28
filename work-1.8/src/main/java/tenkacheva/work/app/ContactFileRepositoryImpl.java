package tenkacheva.work.app;

import jakarta.annotation.PreDestroy;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ContactFileRepositoryImpl implements ContactRepository {
    private final List<Contact> contacts;

    private final String path;

    /**
     * @param path          a path where contacts are stored and loaded
     * @throws IOException  when the path is invalid
     */
    public ContactFileRepositoryImpl(String path) throws IOException {
        this.path = path;

        Path currentPath = Path.of(path);
        try {
            Files.createFile(currentPath);
        } catch (FileAlreadyExistsException ignored) {
        }

        try (InputStream stream = Files.newInputStream(currentPath)) {
            this.contacts = load(stream);
        }
    }

    /**
     * @param from  a path where contacts are loaded
     * @param to    a path where contacts are stored
     */
    public ContactFileRepositoryImpl(InputStream from, String to) {
        this.path = to;
        this.contacts = load(from);
    }

    @Override
    public List<Contact> getAll() {
        return new ArrayList<>(contacts);
    }

    @Override
    public boolean add(Contact contact) {
        return contacts.add(contact);
    }

    @Override
    public boolean remove(String email) {
        return contacts.removeIf(contact -> contact.email().equals(email));
    }

    private List<Contact> load(InputStream inputStream) {
        return new ContactParser().parse(inputStream);
    }

    @PreDestroy
    private void saveChanges() {
        try(var writer = new PrintWriter(new FileOutputStream(path, false))) {
            contacts.forEach(writer::println);
            writer.flush();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
