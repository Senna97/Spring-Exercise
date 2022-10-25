package jdbc.dao;

import jdbc.domain.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;


class UserDaoTest {

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        UserDao userDao = new DaoFactory().userDao();
        User user = new User("3", "seohyeon", "3333");
        userDao.add(user);

        User result = userDao.findById("3");
        assertThat("3").isEqualTo(result.getId());
    }
}