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
                customer.setGender(rs.getString("gender"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customers;
    }
    public EntityKhachHang getCustomerById(int customerId) {
        EntityKhachHang customer = null;
        String query = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                customer = new EntityKhachHang();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullname(rs.getString("fullname"));
                customer.setCccd(rs.getString("cccd"));
                customer.setBirthday(rs.getDate("birthday"));
                customer.setAddress(rs.getString("address"));
                customer.setPhone(rs.getString("phone"));
                customer.setEmail(rs.getString("email"));
                customer.setOccupation(rs.getString("occupation"));
                customer.setGender(rs.getString("gender"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

}
