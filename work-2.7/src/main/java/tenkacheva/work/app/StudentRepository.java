package tenkacheva.work.app;

import java.util.List;

public interface StudentRepository {
    /**
     * Gets all students
     * @return      list of students
     * @see         Student
     */
    List<Student> getAll();

    /**
     * Adds student
     * @param       student a student to be added
     * @return      true when operation is success otherwise false
     * @see         Student
     */
    boolean add(Student student);

    /**
     * Removes student by id
     * @param       id an id of a student to be removed
     * @return      true when operation is success otherwise false
     * @see         Student
     */
    boolean remove(long id);
}
