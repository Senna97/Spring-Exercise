package jdbc.dao;

import jdbc.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User findById(String id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM `likelion-db`.users WHERE id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;

        if (rs.next()) {
            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM `likelion-db`.users");
        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM `likelion-db`.users");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }
}
