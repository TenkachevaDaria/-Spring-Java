package tenkacheva.work.app;

public record Contact(String fullName, String phone, String email) {

    @Override
    public String toString() {
        return String.format("%s; %s; %s", fullName, phone, email);
    }
}
