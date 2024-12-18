package DataAccessLayer;

import Context.DBContext;
import Entity.EntityAccount;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DALAccount {

    public List<EntityAccount> getAllCustomers() {
        List<EntityAccount> customerList = new ArrayList<>();
        String sql = "SELECT a.account_id, c.fullname, a.account_type, a.balance, a.create_date, a.status , a.pin " +
                "FROM customer c " +
                "JOIN account a ON c.customer_id = a.customer_id";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EntityAccount customer = new EntityAccount();
                customer.setAccountId(rs.getInt("account_id"));
                customer.setFullname(rs.getString("fullname"));
                customer.setAccountType(rs.getString("account_type"));
                customer.setBalance(rs.getBigDecimal("balance"));
                customer.setCreatedate(rs.getDate("create_date"));
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

            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateSender)) {
                ps.setDouble(1, totalAmount); // trừ số tiền bao gồm cả phí giao dịch
                ps.setString(2, senderAccount);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateReceiver)) {
                ps.setDouble(1, amount);
                ps.setString(2, receiverAccount);
                ps.executeUpdate();
            }

            String referenceCode = generateReferenceCode();
            try (PreparedStatement ps = conn.prepareStatement(sqlInsertTransaction)) {
                ps.setString(1, senderAccount);
                ps.setString(2, receiverAccount);
                ps.setDouble(3, amount);
                ps.setString(4, description);
                ps.setString(5, referenceCode);
                ps.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Transfer failed
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private double calculateTransactionFee(double amount) {
        return amount * 0.01; // Giả định phí là 1% số tiền giao dịch
    }

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
        return null;
    }


    public void addAccount(EntityAccount account) {
        String sql = "INSERT INTO account (account_id, customer_id, employee_id, account_type, balance, status, pin, create_date, validation_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Sử dụng customer_id làm account_id
            ps.setInt(1, account.getCustomerId()); // Gán account_id = customer_id
            ps.setInt(2, account.getCustomerId()); // customer_id
            ps.setInt(3, account.getEmployee_id());
            ps.setString(4, account.getAccountType());
            ps.setBigDecimal(5, account.getBalance());
            ps.setString(6, account.getStatus());
            ps.setInt(7, account.getPin());
            ps.setDate(8, account.getCreatedate());
            ps.setDate(9, account.getValidationdate());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(EntityAccount account) {
        String sql = "UPDATE account SET  employee_id = ?, account_type = ?, balance = ?, status = ?, pin = ?, create_date = ?, validation_date = ? WHERE customer_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, account.getEmployee_id());
            ps.setString(2, account.getAccountType());
            ps.setBigDecimal(3, account.getBalance());
            ps.setString(4, account.getStatus());
            ps.setInt(5, account.getPin());
            ps.setDate(6, account.getCreatedate());
            ps.setDate(7, account.getValidationdate());
            ps.setInt(8, account.getCustomerId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccountById(int customerId) {
        String query = "DELETE FROM Account WHERE customer_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean processWithdrawal(String accountNumber, BigDecimal amount, String pin, String method, String reason, String contactInfo) {
        String updateBalanceQuery = "UPDATE account SET balance = balance - ? WHERE account_id = ? AND pin = ?";
        String insertTransactionQuery = "INSERT INTO transaction (transaction_date, transaction_type, sender_account_id, amount, method, description, contact, status, reference_code) " +
                "VALUES (CURRENT_DATE, 'Rút tiền', ?, ?, ?, ?, ?, 'Thành công', ?)";

        try (Connection connection = DBContext.getConnection();
             PreparedStatement updateBalanceStmt = connection.prepareStatement(updateBalanceQuery);
             PreparedStatement insertTransactionStmt = connection.prepareStatement(insertTransactionQuery)) {

            // Kiểm tra và cập nhật số dư tài khoản
            updateBalanceStmt.setBigDecimal(1, amount);
            updateBalanceStmt.setString(2, accountNumber);
            updateBalanceStmt.setString(3, pin);
            int rowsAffected = updateBalanceStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Giao dịch thất bại: Sai thông tin tài khoản hoặc mã PIN!");
            }
            String referenceCode = generateReferenceCode();

            insertTransactionStmt.setString(1, accountNumber);
            insertTransactionStmt.setBigDecimal(2, amount);
            insertTransactionStmt.setString(3, method);
            insertTransactionStmt.setString(4, reason);
            insertTransactionStmt.setString(5, contactInfo);
            insertTransactionStmt.setString(6, referenceCode); // Thêm mã tham chiếu
            insertTransactionStmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deposit(String accountNumber, double amount, String description, String method, String contactInfo) {
        String sqlCheckBalance = "SELECT balance FROM account WHERE account_id = ?";
        String sqlUpdateAccount = "UPDATE account SET balance = balance + ? WHERE account_id = ?";
        String sqlInsertTransaction = "INSERT INTO transaction (transaction_date, transaction_type, sender_account_id, receiver_account_id, amount, description, status, reference_code, method, contact) VALUES (CURRENT_DATE, 'Nạp tiền', ?, ?, ?, ?, 'Thành công', ?, ?, ?)";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement psCheckBalance = conn.prepareStatement(sqlCheckBalance);
             PreparedStatement psUpdateAccount = conn.prepareStatement(sqlUpdateAccount);
             PreparedStatement psInsertTransaction = conn.prepareStatement(sqlInsertTransaction)) {

            psCheckBalance.setString(1, accountNumber);
            ResultSet rs = psCheckBalance.executeQuery();
            if (rs.next()) {
                double balance = rs.getDouble("balance");

                psUpdateAccount.setDouble(1, amount);
                psUpdateAccount.setString(2, accountNumber);
                psUpdateAccount.executeUpdate();

                psInsertTransaction.setString(1, accountNumber);
                psInsertTransaction.setString(2, accountNumber);
                psInsertTransaction.setDouble(3, amount);
                psInsertTransaction.setString(4, description);
                psInsertTransaction.setString(5, UUID.randomUUID().toString());
                psInsertTransaction.setString(6, method);
                psInsertTransaction.setString(7, contactInfo);
                psInsertTransaction.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
