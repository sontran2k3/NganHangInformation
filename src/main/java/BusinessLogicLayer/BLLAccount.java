package BusinessLogicLayer;

import DataAccessLayer.DALAccount;
import Entity.EntityAccount;
import Entity.EntityTransaction;

import java.math.BigDecimal;
import java.util.List;

public class BLLAccount {
    private DALAccount dalAccount = new DALAccount();

    public List<EntityAccount> getAllCustomers() {
        return dalAccount.getAllCustomers();
    }

    public String getCustomerNameByAccount(String accountNumber) {
        return dalAccount.getCustomerNameByAccount(accountNumber);
    }

    public boolean validatePin(String accountNumber, int pin) {
        return dalAccount.validatePin(accountNumber, pin);
    }

    public boolean transfer(String senderAccount, String receiverAccount, double amount, String description) {
        return dalAccount.transfer(senderAccount, receiverAccount, amount, description);
    }

    public String getLastReferenceCode() {
        return dalAccount.getLastReferenceCode();
    }

    public void addAccount(EntityAccount account) {
        DALAccount dalAccount = new DALAccount();
        dalAccount.addAccount(account);
    }

    public void updateAccount(EntityAccount account) {
        dalAccount.updateAccount(account);
    }

    public void deleteAccount(int customerId) {
        dalAccount.deleteAccountById(customerId);
    }

    public boolean processWithdrawal(String accountNumber, BigDecimal amount, String pin, String method, String reason, String contactInfo) {
        DALAccount dal = new DALAccount();
        return dal.processWithdrawal(accountNumber, amount, pin, method, reason, contactInfo);
    }

    public boolean deposit(String accountNumber, double amount, String description, String method, String contactInfo) {
        {
            DALAccount dal = new DALAccount();
            return dal.deposit(accountNumber, amount, description, method, contactInfo);
        }
    }
}
