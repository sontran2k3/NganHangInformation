package BusinessLogicLayer;

import DataAccessLayer.DALTransaction;
import Entity.EntityTransaction;

import java.util.List;

public class BLLTransaction {
    private final DALTransaction dalTransaction = new DALTransaction();

    public List<EntityTransaction> getTransactionsByAccountId(int accountId) {
        return dalTransaction.getTransactionsByAccountId(accountId);
    }
}
