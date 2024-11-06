package com.example.nganhanginformation;

import BusinessLogicLayer.BLLAccount;
import Entity.EntityAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RutTienPane extends AnchorPane {

    private TableView<EntityAccount> tableView;
    private ObservableList<EntityAccount> dataList;
    private BLLAccount dalAccount = new BLLAccount();

    public RutTienPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/ruttien.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn.  Rút Tiền");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        this.getChildren().add(welcomeLabel);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.getStyleClass().add("text-field");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));

        tableView = new TableView<>();
        tableView.getStyleClass().add("table-view");

        setupTableColumns();

        dataList = FXCollections.observableArrayList(dalAccount.getAllCustomers());
        tableView.setItems(dataList);

        VBox tableContainer = new VBox(searchField, tableView);
        tableContainer.setSpacing(10);
        tableContainer.setPadding(new Insets(10));
        tableContainer.getStyleClass().add("table-container"); // Thêm class

        HBox container = new HBox();
        container.setSpacing(20);
        container.getChildren().addAll(tableContainer, createWithdrawalSection());

        this.getChildren().add(container);
        AnchorPane.setTopAnchor(container, 60.0);
        AnchorPane.setLeftAnchor(container, 20.0);
        AnchorPane.setRightAnchor(container, 20.0);
        AnchorPane.setBottomAnchor(container, 150.0);
    }

    private void setupTableColumns() {
        TableColumn<EntityAccount, Integer> idCol = new TableColumn<>("Mã tài khoản");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        idCol.setMinWidth(100);

        TableColumn<EntityAccount, String> fullnameCol = new TableColumn<>("Tên khách hàng");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fullnameCol.setMinWidth(165);

        TableColumn<EntityAccount, Double> balanceCol = new TableColumn<>("Số dư hiện tại");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        balanceCol.setMinWidth(165);

        TableColumn<EntityAccount, String> addressCol = new TableColumn<>("Địa chỉ");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setMinWidth(165);

        TableColumn<EntityAccount, String> statusCol = new TableColumn<>("Trạng thái tài khoản");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setMinWidth(165);

        tableView.getColumns().addAll(idCol, fullnameCol, balanceCol, addressCol, statusCol);
    }

    private GridPane createWithdrawalSection() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.getStyleClass().add("grid-pane"); // Thêm class

        Label accountNumberLabel = new Label("Số tài khoản:");
        TextField accountNumberField = new TextField();

        Label accountHolderLabel = new Label("Tên chủ tài khoản:");
        TextField accountHolderField = new TextField();

        Label withdrawalAmountLabel = new Label("Số tiền giao dich:");
        TextField withdrawalAmountField = new TextField();

        Label pinLabel = new Label("Mã PIN/Mật khẩu:");
        PasswordField pinField = new PasswordField();

        Label withdrawalMethodLabel = new Label("Hình thức giao dịch:");
        ComboBox<String> withdrawalMethodComboBox = new ComboBox<>();
        withdrawalMethodComboBox.getItems().addAll("Tiền mặt", "Chuyển khoản");

        Label reasonLabel = new Label("Nội dung giao dịch:");
        TextField reasonField = new TextField();

        Label contactInfoLabel = new Label("Thông tin liên lạc:");
        TextField contactInfoField = new TextField();

        Label withdrawalDateLabel = new Label("Thời gian giao dịch:");
        DatePicker withdrawalDateField = new DatePicker();

        // Nút Xác nhận rút tiền
        Button withdrawalButton = new Button("Thanh Toán");
        withdrawalButton.getStyleClass().add("transfer-button");
        withdrawalButton.setOnAction(event -> {
            handleWithdrawal(
                    accountNumberField.getText(),
                    withdrawalAmountField.getText(),
                    pinField.getText(),
                    withdrawalMethodComboBox.getValue(),
                    reasonField.getText(),
                    contactInfoField.getText()
            );
        });

        // Thêm các trường vào GridPane
        gridPane.add(accountNumberLabel, 0, 0);
        gridPane.add(accountNumberField, 1, 0);
        gridPane.add(accountHolderLabel, 0, 1);
        gridPane.add(accountHolderField, 1, 1);
        gridPane.add(withdrawalAmountLabel, 0, 2);
        gridPane.add(withdrawalAmountField, 1, 2);
        gridPane.add(pinLabel, 0, 3);
        gridPane.add(pinField, 1, 3);
        gridPane.add(withdrawalMethodLabel, 0, 4);
        gridPane.add(withdrawalMethodComboBox, 1, 4);
        gridPane.add(reasonLabel, 0, 5);
        gridPane.add(reasonField, 1, 5);
        gridPane.add(contactInfoLabel, 0, 6);
        gridPane.add(contactInfoField, 1, 6);
        gridPane.add(withdrawalDateLabel, 0, 7);
        gridPane.add(withdrawalDateField, 1, 7);
        gridPane.add(withdrawalButton, 1, 8);


        Image printIcon = new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/printer.png")); // Đường dẫn đến icon
        ImageView printIconView = new ImageView(printIcon);
        printIconView.setFitWidth(20);
        printIconView.setFitHeight(20);


        Button printReceiptButton = new Button("In hóa đơn", printIconView);
        gridPane.add(printReceiptButton, 1, 9);


        GridPane.setHalignment(withdrawalButton, HPos.RIGHT);
        GridPane.setHalignment(printReceiptButton, HPos.RIGHT);

        return gridPane;
    }

    private void filterData(String searchTerm) {
        if (searchTerm.isEmpty()) {
            tableView.setItems(dataList);
            return;
        }
        ObservableList<EntityAccount> filteredData = FXCollections.observableArrayList();
        for (EntityAccount customer : dataList) {
            if (customer.getFullname().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    customer.getAccountType().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredData.add(customer);
            }
        }
        tableView.setItems(filteredData);
    }

    private void handleWithdrawal(String accountNumber, String amount, String pin, String method, String reason, String contactInfo) {
        System.out.println("Rút tiền từ tài khoản " + accountNumber + " với số tiền " + amount + ". Mã PIN: " + pin + ", Hình thức: " + method + ", Lý do: " + reason + ", Thông tin liên lạc: " + contactInfo);
    }
}
