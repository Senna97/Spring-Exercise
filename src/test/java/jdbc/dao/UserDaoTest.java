package jdbc.dao;

import jdbc.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext applicationContext;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        this.userDao = applicationContext.getBean("userDao", UserDao.class);
        userDao.deleteAll();
        this.user1 = new User("1", "seohyeon", "1111");
        this.user2 = new User("2", "seohyeon", "2222");
        this.user3 = new User("3", "seohyeon", "3333");
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);

        User result = userDao.findById("3");
        assertThat("3").isEqualTo(result.getId());
    }

    @Test
    public void find() {
        userDao.add(user1);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            userDao.findById("3");
        });
    }

    @Test
    @DisplayName("없으면 빈 리스트를, 있으면 개수만큼 리턴")
    void getAllTest() {
        List<User> users = userDao.getAll();

        assertEquals(0, users.size());

        userDao.add(user1);
        userDao.add(user2);
        userDao.add(user3);
        users = userDao.getAll();
        assertEquals(3, users.size());
    }
}