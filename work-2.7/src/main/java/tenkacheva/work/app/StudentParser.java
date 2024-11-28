package tenkacheva.work.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StudentParser {
    /**
     * Parses a single string line to student
     * @param       student a string in format "id; firstName; lastName; age"
     * @return      parsed student
     * @throws      StudentParserException when the student parameter is invalid
     *              or when a student property is empty or property is illegal
     * @see         Student
     */
    public Student parseOne(String student) {
        final int propertyCount = 4;

        String[] data = student.split(";", propertyCount);
        if (data.length != propertyCount) {
            var format = "Student must be in format: id; firstName; lastName; age";
            var msg = String.format(format, propertyCount, data.length);
            throw new StudentParserException(msg);
        }

        long id;
        try {
            id = Long.parseLong(data[0].trim());
        } catch (NumberFormatException exception) {
            throw new StudentParserException("Id must be a number.");
        }

        String firstName = data[1].trim();
        if (firstName.isBlank()) {
            throw new StudentParserException("First Name cannot be empty.");
        }

        String lastName = data[2].trim();
        if (lastName.isBlank()) {
            throw new StudentParserException("Last Name cannot be empty.");
        }

        int age;
        try {
            age = Integer.parseInt(data[3].trim());
        } catch (NumberFormatException exception) {
            throw new StudentParserException("Age must be a number." + data[3]);
        }

        return new Student(id, firstName, lastName, age);
    }

    /**
     * Parses a stream of students to list of students
     * @param       inputStream a stream of student
     * @return      list of students
     * @throws      StudentParserException when the student parameter is invalid
     *              or when a student property is empty or a student property is invalid
     * @see         Student
     */
    public List<Student> parse(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        List<Student> students = new ArrayList<>();
        reader.lines().forEach((line) -> students.add(parseOne(line)));

        return students;
    }
}
