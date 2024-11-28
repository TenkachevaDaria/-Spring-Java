package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.stream.Collectors;

@ShellComponent
public class StudentCommands {
    private final ApplicationEventPublisher eventPublisher;
    private final StudentRepository repository;

    @Autowired
    public StudentCommands(ApplicationEventPublisher eventPublisher, StudentRepository repository) {
        this.eventPublisher = eventPublisher;
        this.repository = repository;
    }

    @ShellMethod(key = "add-student")
    public void onAddStudentCommand(
            @ShellOption String firstName,
            @ShellOption String lastName,
            @ShellOption int age
    ) {
        long id = System.currentTimeMillis();
        Student student = new Student(id, firstName, lastName, age);

        if (repository.add(student)) {
            eventPublisher.publishEvent(new StudentAddingEvent(student));
        } else {
            eventPublisher.publishEvent(new StudentAddingEvent(null));
        }
    }

    @ShellMethod(key = "remove-student")
    public void onRemoveStudentCommand(@ShellOption long id) {
        if (repository.remove(id)) {
            eventPublisher.publishEvent(new StudentRemovingEvent(id));
        } else {
            eventPublisher.publishEvent(new StudentRemovingEvent(null));
        }
    }

    @ShellMethod(key = "list-students")
    public String onListStudentCommand() {
        return repository
                .getAll()
                .stream()
                .map(Student::toString)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "clear-students")
    public String onClearStudentCommand() {
        for (Student student : repository.getAll()) {
            repository.remove(student.id());
        }
        return "students have cleared.";
    }
}
