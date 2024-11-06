package BusinessLogicLayer;

import DataAccessLayer.DALThongKe;

import java.util.Map;

public class BLLThongKe {
    private DALThongKe dalThongKe;

    public BLLThongKe() {
        dalThongKe = new DALThongKe();
    }

    public int getTotalAccountsThisMonth() throws Exception {
        return dalThongKe.getTotalAccountsThisMonth();
    }

    public int getTotalTransactionsThisMonth() throws Exception {
        return dalThongKe.getTotalTransactionsThisMonth();
    }

    public double getTotalRevenueThisMonth() throws Exception {
        return dalThongKe.getTotalRevenueThisMonth();
    }
    public Map<String, Double> getMonthlyRevenue() throws Exception {
        return dalThongKe.getMonthlyRevenue();
    }

}
