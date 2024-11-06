package DataAccessLayer;

import Context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class DALThongKe {

    public int getTotalAccountsThisMonth() throws Exception {
        String query = "SELECT COUNT(*) FROM account WHERE MONTH(create_date) = MONTH(CURRENT_DATE()) AND YEAR(create_date) = YEAR(CURRENT_DATE())";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }

    public int getTotalTransactionsThisMonth() throws Exception {
        String query = "SELECT COUNT(*) FROM transaction WHERE MONTH(transaction_date) = MONTH(CURRENT_DATE()) AND YEAR(transaction_date) = YEAR(CURRENT_DATE())";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }

    public double getTotalRevenueThisMonth() throws Exception {
        String query = "SELECT SUM(amount) FROM transaction WHERE MONTH(transaction_date) = MONTH(CURRENT_DATE()) AND YEAR(transaction_date) = YEAR(CURRENT_DATE())";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
        }
        return 0.0;
    }
    public Map<String, Double> getMonthlyRevenue() throws Exception {
        String query = "SELECT MONTH(transaction_date) AS month, SUM(amount) AS totalRevenue " +
                "FROM transaction " +
                "WHERE YEAR(transaction_date) = YEAR(CURRENT_DATE()) " +
                "GROUP BY MONTH(transaction_date)";

        Map<String, Double> monthlyRevenue = new HashMap<>();

        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String month = String.format("Tháng %02d", resultSet.getInt("month"));
                Double revenue = resultSet.getDouble("totalRevenue");
                monthlyRevenue.put(month, revenue);
            }
        }
        return monthlyRevenue;
    }

}
