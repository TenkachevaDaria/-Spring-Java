package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ContactDaoImpl implements ContactDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Contact select(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM contacts WHERE id = ?",
                (resultSet, rowNumber) -> mapContact(resultSet),
                id
        );
    }

    @Override
    public List<Contact> selectAll() {
        return jdbcTemplate.query(
                "SELECT * FROM contacts",
                (resultSet, rowNumber) -> mapContact(resultSet)
        );
    }

    @Override
    public boolean insert(Contact contact) {
        return jdbcTemplate.update(
                "INSERT INTO contacts(first_name, last_name, email, phone)"
                        + "VALUES (?, ?, ?, ?)",
                contact.firstName(),
                contact.lastName(),
                contact.email(),
                contact.phone()
        ) > 0;
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(
                "DELETE FROM contacts WHERE id = ?",
                id
        ) > 0;
    }

    @Override
    public boolean update(Contact contact) {
        return jdbcTemplate.update(
                "UPDATE contacts SET first_name = ?, "
                        + "last_name = ?, "
                        + "email = ?, "
                        + "phone = ?"
                        + "WHERE id = ?",
                contact.firstName(),
                contact.lastName(),
                contact.email(),
                contact.phone(),
                contact.id()
        ) > 0;
    }

    private Contact mapContact(ResultSet resultSet) throws SQLException {
        return new Contact(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("phone")
        );
    }
}
