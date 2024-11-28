package tenkacheva.work.app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ContactParser {

    /**
     * Parses a single string line to contact
     * @param       contact a string in format "fullName; phone; email"
     * @return      parsed contact
     * @throws      ContactParserException when the contact parameter is invalid
     *              or when a contact property is empty
     * @see         Contact
     */
    public Contact parseOne(String contact) {
        final int propertyCount = 3;

        String[] data = contact.split(";", propertyCount);
        if (data.length != propertyCount) {
            var format = "Contact must be in format: fullName; phone; email";
            var msg = String.format(format, propertyCount, data.length);
            throw new ContactParserException(msg);
        }

        String fullName = data[0].trim();
        if (fullName.isBlank()) {
            throw new ContactParserException("Full Name cannot be empty.");
        }

        String phone = data[1].trim();
        if (phone.isBlank()) {
            throw new ContactParserException("Phone cannot be empty.");
        }

        String email = data[2].trim();
        if (email.isBlank()) {
            throw new ContactParserException("Email cannot be empty.");
        }

        return new Contact(fullName, phone, email);
    }

    /**
     * Parses a stream of contacts to list of contacts
     * @param       inputStream a stream of contacts
     * @return      list of contacts
     * @throws      ContactParserException when the contact parameter is invalid
     *              or when a contact property is empty
     * @see         Contact
     */
    public List<Contact> parse(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        List<Contact> contacts = new ArrayList<>();
        reader.lines().forEach((line) -> contacts.add(parseOne(line)));

        return contacts;
    }
}
