package Entity;

import javafx.beans.property.*;

public class Customer {
    private final IntegerProperty id;
    private final StringProperty account;
    private final StringProperty name;
    private final StringProperty phone;
    private final StringProperty address;
    private final DoubleProperty balance;

    public Customer(int id, String account, String name, String phone, String address, double balance) {
        this.id = new SimpleIntegerProperty(id);
        this.account = new SimpleStringProperty(account);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
        this.balance = new SimpleDoubleProperty(balance);
    }

    public IntegerProperty idProperty() { return id; }
    public StringProperty accountProperty() { return account; }
    public StringProperty nameProperty() { return name; }
    public StringProperty phoneProperty() { return phone; }
    public StringProperty addressProperty() { return address; }
    public DoubleProperty balanceProperty() { return balance; }
}
