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
    public boolean isCccdExist(String cccd) {
        String query = "SELECT COUNT(*) FROM customer WHERE cccd = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cccd);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu có ít nhất 1 bản ghi
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean addCustomer(EntityKhachHang customer) {
        if (isCccdExist(customer.getCccd())) {
            // Trả về false nếu CCCD đã tồn tại
            throw new IllegalArgumentException("CCCD đã tồn tại trong cơ sở dữ liệu.");
        }

        String query = "INSERT INTO customer (customer_id, fullname, cccd, birthday, address, phone, email, occupation, sign_sample, profile_picture, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customer.getCustomerId());
            pstmt.setString(2, customer.getFullname());
            pstmt.setString(3, customer.getCccd());
            pstmt.setDate(4, customer.getBirthday());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getPhone());
            pstmt.setString(7, customer.getEmail());
            pstmt.setString(8, customer.getOccupation());
            pstmt.setBytes(9, customer.getSignSample());
            pstmt.setBytes(10, customer.getProfilePicture());
            pstmt.setString(11, customer.getGender());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean updateCustomer(EntityKhachHang customer) {
        // Lệnh SQL để cập nhật thông tin khách hàng
        String query = "UPDATE customer SET fullname = ?, cccd = ?, birthday = ?, address = ?, phone = ?, email = ?, occupation = ?, gender = ?, profile_picture = ?, sign_sample = ? WHERE customer_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Gán giá trị cho các tham số trong câu lệnh SQL
            stmt.setString(1, customer.getFullname());
            stmt.setString(2, customer.getCccd());
            stmt.setDate(3, customer.getBirthday());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getEmail());
            stmt.setString(7, customer.getOccupation());
            stmt.setString(8, customer.getGender());
            stmt.setBytes(9, customer.getProfilePicture());
            stmt.setBytes(10, customer.getSignSample());
            stmt.setInt(11, customer.getCustomerId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setInt(1, customerId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}