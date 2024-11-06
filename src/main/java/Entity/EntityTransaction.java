package Entity;

import java.util.Date;

public class EntityTransaction {
    private String transactionId;
    private Date transactionDate;
    private String transactionType;
    private String senderAccountId;
    private String receiverAccountId;
    private double amount;
    private double transactionFee; // Thêm phí giao dịch
    private String description;
    private String referenceCode; // Mã tham chiếu

    // Constructor, Getters và Setters cho các thuộc tính
    public double getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
}
