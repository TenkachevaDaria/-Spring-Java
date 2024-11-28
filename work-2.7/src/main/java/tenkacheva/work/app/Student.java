package tenkacheva.work.app;

public record Student(long id, String firstName, String lastName, int age) {

    @Override
    public String toString() {
        return String.format("%d; %s; %s; %d", id, firstName, lastName, age);
    }
}
