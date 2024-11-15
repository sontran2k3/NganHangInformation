package DataAccessLayer;

import Entity.EntityEmployee;
import Context.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALEmployee {
    public List<EntityEmployee> getAllEmployees() {
        List<EntityEmployee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EntityEmployee employee = new EntityEmployee(
                        rs.getInt("employee_id"),
                        rs.getString("fullname"),
                        rs.getString("cccd"),
                        rs.getDate("birthday"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBoolean("gender"),
                        rs.getString("role")
                );
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    public boolean addEmployee(EntityEmployee employee) {
        String sql = "INSERT INTO employee (employee_id, fullname, cccd, birthday, address, phone, gender, branch_id, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employee.getEmployeeId());
            ps.setString(2, employee.getFullname());
            ps.setString(3, employee.getCccd());
            ps.setDate(4, employee.getBirthday());
            ps.setString(5, employee.getAddress());
            ps.setString(6, employee.getPhone());
            ps.setBoolean(7, employee.isGender());
            ps.setString(8, employee.getBranchId());
            ps.setString(9, employee.getRole());

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void updateEmployee(EntityEmployee employee) {
        String sql = "UPDATE employee SET fullname = ?, cccd = ?, birthday = ?, address = ?, phone = ?, gender = ? WHERE employee_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getFullname());
            stmt.setString(2, employee.getCccd());
            stmt.setDate(3, employee.getBirthday());
            stmt.setString(4, employee.getAddress());
            stmt.setString(5, employee.getPhone());
            stmt.setBoolean(6, employee.isGender());
            stmt.setInt(7, employee.getEmployeeId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
