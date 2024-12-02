package tenkacheva.work.app;

import java.util.List;

public interface ContactDao {

    Contact select(long id);

    List<Contact> selectAll();

    boolean insert(Contact contact);

    boolean delete(long id);

    boolean update(Contact contact);
}
