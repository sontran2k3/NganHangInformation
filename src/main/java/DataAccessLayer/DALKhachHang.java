package DataAccessLayer;

import Context.DBContext;
import Entity.EntityKhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DALKhachHang {

    public List<EntityKhachHang> getAllCustomers() {
        List<EntityKhachHang> customers = new ArrayList<>();
        String query = "SELECT * FROM customer";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EntityKhachHang customer = new EntityKhachHang();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullname(rs.getString("fullname"));
                customer.setCccd(rs.getString("cccd"));
                customer.setBirthday(rs.getDate("birthday"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setOccupation(rs.getString("occupation"));

                int genderCode = rs.getInt("gender");
                customer.setGender(genderCode);

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
}
