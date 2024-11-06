package DataAccessLayer;

import Context.DBContext;
import Entity.EntityAccount;
import Entity.EntityTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DALAccount {

    // Phương thức lấy danh sách tất cả khách hàng
    public List<EntityAccount> getAllCustomers() {
        List<EntityAccount> customerList = new ArrayList<>();
        String sql = "SELECT c.customer_id, c.fullname, a.account_type, a.balance, c.address, a.status , a.pin " +
                "FROM customer c " +
                "JOIN account a ON c.customer_id = a.customer_id";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Lặp qua kết quả truy vấn và thêm vào danh sách
            while (rs.next()) {
                EntityAccount customer = new EntityAccount();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setFullname(rs.getString("fullname"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setBalance(rs.getBigDecimal("balance"));
                customer.setAddress(rs.getString("address"));
                customer.setStatus(rs.getString("status"));
                customer.setPin(rs.getInt("pin"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

    // Phương thức tìm tên khách hàng dựa vào số tài khoản
    public String getCustomerNameByAccount(String accountNumber) {
        String query = "SELECT fullname FROM customer WHERE customer_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("fullname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Phương thức lưu giao dịch
    public boolean validatePin(String accountNumber, int pin) {
        String query = "SELECT pin FROM account WHERE account_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("pin") == pin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean transfer(String senderAccount, String receiverAccount, double amount, String description) {
        String sqlCheckBalance = "SELECT balance FROM account WHERE account_id = ?";
        String sqlUpdateSender = "UPDATE account SET balance = balance - ? WHERE account_id = ?";
        String sqlUpdateReceiver = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
        String sqlInsertTransaction = "INSERT INTO transaction (transaction_date, transaction_type, sender_account_id, receiver_account_id, amount, description, status, reference_code) VALUES (CURRENT_DATE, 'Chuyển khoản', ?, ?, ?, ?, 'Thành công', ?)";

        double transactionFee = calculateTransactionFee(amount); // Tính phí giao dịch nếu có
        double totalAmount = amount + transactionFee;

        try (Connection conn = DBContext.getConnection()) {
            // Check sender's balance
            try (PreparedStatement ps = conn.prepareStatement(sqlCheckBalance)) {
                ps.setString(1, senderAccount);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (currentBalance < totalAmount) {
                        return false; // Không đủ số dư
                    }
                }
            }

            // Update sender's balance
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateSender)) {
                ps.setDouble(1, totalAmount); // trừ số tiền bao gồm cả phí giao dịch
                ps.setString(2, senderAccount);
                ps.executeUpdate();
            }

            // Update receiver's balance
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateReceiver)) {
                ps.setDouble(1, amount); // chỉ cộng số tiền gốc
                ps.setString(2, receiverAccount);
                ps.executeUpdate();
            }

            // Log the transaction
            String referenceCode = generateReferenceCode(); // Tạo mã tham chiếu
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertTransaction)) {
                ps.setString(1, senderAccount);
                ps.setString(2, receiverAccount);
                ps.setDouble(3, amount);
                ps.setString(4, description);
                ps.setString(5, referenceCode);
                ps.executeUpdate();
            }

            return true; // Transfer successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Transfer failed
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method để tính phí giao dịch (tùy chỉnh theo quy định)
    private double calculateTransactionFee(double amount) {
        return amount * 0.01; // Giả định phí là 1% số tiền giao dịch
    }

    // Method để tạo mã tham chiếu giao dịch
    private String generateReferenceCode() {
        return "TXN-" + System.currentTimeMillis();
    }
    public String getLastReferenceCode() {
        String sql = "SELECT reference_code FROM transaction ORDER BY transaction_date DESC LIMIT 1";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getString("reference_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null; // Trả về null nếu không tìm thấy mã tham chiếu
    }






}
