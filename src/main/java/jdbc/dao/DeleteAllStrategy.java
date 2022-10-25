package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy {
    @Override
    public PreparedStatement makePs(Connection c) throws SQLException {
        return c.prepareStatement("DELETE FROM `likelion-db`.users");
    }
}
