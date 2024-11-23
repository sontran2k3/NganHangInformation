package DataAccessLayer;

import Context.DBContext;
import Entity.EntityTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DALTransaction {
    public List<EntityTransaction> getTransactionsByAccountId(int accountId) {
        List<EntityTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE sender_account_id = ? OR receiver_account_id = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EntityTransaction transaction = new EntityTransaction();
                transaction.setTransactionId(rs.getString("transaction_id"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setSenderAccountId(rs.getString("sender_account_id"));
                transaction.setReceiverAccountId(rs.getString("receiver_account_id"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionFee(rs.getDouble("transaction_fee"));
                transaction.setDescription(rs.getString("description"));
                transaction.setReferenceCode(rs.getString("reference_code"));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
