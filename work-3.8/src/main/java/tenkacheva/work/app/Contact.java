package tenkacheva.work.app;

public record Contact(
        long id,
        String firstName,
        String lastName,
        String email,
        String phone) {

    public Contact(
            long id,
            String firstName,
            String lastName,
            String email,
            String phone) {
        this.id = id;

        if (firstName.length() > 32 || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName cannot be empty or the length cannot be greater than 32 symbols");
        }
        this.firstName = firstName;

        if (lastName.length() > 32 || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be empty or the length cannot be greater than 32 symbols");
        }
        this.lastName = lastName;

        if (email.length() > 320 || email.isBlank()) {
            throw new IllegalArgumentException("email cannot be empty or the length cannot be greater than 320 symbols");
        }
        this.email = email;

        if (phone.length() > 32 || phone.isBlank()) {
            throw new IllegalArgumentException("phone cannot be empty or the length cannot be greater than 32 symbols");
        }
        this.phone = phone;
    }
}
