package tenkacheva.work.app;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StudentEventsListener {
    @EventListener
    private void handleAddingStudentEvent(StudentAddingEvent event) {
        Student student = event.student();
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student cannot be created.");
        }
    }

    @EventListener
    private void handleRemovingStudentEvent(StudentRemovingEvent event) {
        Long id = event.id();
        if (id != null) {
            System.out.println("id => " + id);
        } else {
            System.out.println("student cannot be deleted.");
        }
    }
}
