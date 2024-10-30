package DataAccessLayer;

import Context.DBContext;
import Entity.EntityLogin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DALLogin {
    public EntityLogin login(String username, String password) {
        try (Connection conn = DBContext.getConnection()) {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                return new EntityLogin(username, password, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
