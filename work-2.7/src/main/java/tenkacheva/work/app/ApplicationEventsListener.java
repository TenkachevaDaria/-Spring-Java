package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEventsListener {
    private final StudentRepository studentRepository;

    @Autowired
    public ApplicationEventsListener(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @EventListener
    private void handleApplicationReadyEvent(ApplicationReadyEvent event) {
        for (Student student : studentRepository.getAll()) {
            studentRepository.remove(student.id());
        }

        studentRepository.add(new Student(1, "first1", "last1", 1));
        studentRepository.add(new Student(2, "first2", "last2", 2));
        studentRepository.add(new Student(3, "first3", "last3", 3));
    }
}
