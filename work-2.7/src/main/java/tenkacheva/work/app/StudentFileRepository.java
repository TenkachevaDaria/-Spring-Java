package tenkacheva.work.app;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentFileRepository implements StudentRepository {
    private final List<Student> students;

    private final String path;

    /**
     * @param path          a path where students are stored and loaded
     * @throws IOException  when the path is invalid
     */
    @Autowired
    public StudentFileRepository(@Value("${app.students.path}") String path) throws IOException {
        this.path = path;

        Path currentPath = Path.of(path);
        try {
            Files.createFile(currentPath);
        } catch (FileAlreadyExistsException ignored) {
        }

        try (InputStream stream = Files.newInputStream(currentPath)) {
            this.students = load(stream);
        }
    }

    @Override
    public List<Student> getAll() {
        return new ArrayList<>(students);
    }

    @Override
    public boolean add(Student student) {
        return students.add(student);
    }

    @Override
    public boolean remove(long id) {
        return students.removeIf(student -> student.id() == id);
    }

    private List<Student> load(InputStream inputStream) {
        return new StudentParser().parse(inputStream);
    }

    @PreDestroy
    private void saveChanges() {
        try(var writer = new PrintWriter(new FileOutputStream(path, false))) {
            students.forEach(writer::println);
            writer.flush();
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
