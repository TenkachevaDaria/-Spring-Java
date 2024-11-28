package tenkacheva.work.app;

import java.util.List;

public interface ContactRepository {
    /**
     * Adds contact
     * @param       contact a contact to be added
     * @return      true when operation is success otherwise false
     * @see         Contact
     */
    boolean add(Contact contact);

    /**
     * Removes contact by email
     * @param       email an email of a contact to be removed
     * @return      true when operation is success otherwise false
     * @see         Contact
     */
    boolean remove(String email);

    /**
     * Gets all contacts
     * @return      list of contacts
     * @see         Contact
     */
    List<Contact> getAll();
}
