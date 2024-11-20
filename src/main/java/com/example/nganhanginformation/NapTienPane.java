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

public class NapTienPane extends AnchorPane {

    private TableView<EntityAccount> tableView;
    private ObservableList<EntityAccount> dataList;
    private BLLAccount dalAccount = new BLLAccount();

    public NapTienPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/ruttien.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn.  Nạp Tiền");
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
        tableContainer.getStyleClass().add("table-container");

        HBox container = new HBox();
        container.setSpacing(20);
        container.getChildren().addAll(tableContainer, createTransactionSection());

        this.getChildren().add(container);
        AnchorPane.setTopAnchor(container, 60.0);
        AnchorPane.setLeftAnchor(container, 20.0);
        AnchorPane.setRightAnchor(container, 20.0);
        AnchorPane.setBottomAnchor(container, 150.0);
    }

    private void setupTableColumns() {
        TableColumn<EntityAccount, Integer> idCol = new TableColumn<>("Mã tài khoản");
        idCol.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        idCol.setMinWidth(100);

        TableColumn<EntityAccount, String> fullnameCol = new TableColumn<>("Tên khách hàng");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fullnameCol.setMinWidth(165);

        TableColumn<EntityAccount, Double> balanceCol = new TableColumn<>("Số dư hiện tại");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        balanceCol.setMinWidth(165);

        TableColumn<EntityAccount, String> addressCol = new TableColumn<>("Ngày kích hoạt");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("createdate"));
        addressCol.setMinWidth(165);
        TableColumn<EntityAccount, String> statusCol = new TableColumn<>("Trạng thái tài khoản");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setMinWidth(165);

        tableView.getColumns().addAll(idCol, fullnameCol, balanceCol, addressCol, statusCol);
    }

    private GridPane createTransactionSection() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.getStyleClass().add("grid-pane");

        Label accountNumberLabel = new Label("Số tài khoản:");
        TextField accountNumberField = new TextField();

        Label accountHolderLabel = new Label("Tên chủ tài khoản:");
        TextField accountHolderField = new TextField();

        Label transactionAmountLabel = new Label("Số tiền giao dịch:");
        TextField transactionAmountField = new TextField();

        Label pinLabel = new Label("Mã PIN/Mật khẩu:");
        PasswordField pinField = new PasswordField();

        Label transactionMethodLabel = new Label("Hình thức giao dịch:");
        ComboBox<String> transactionMethodComboBox = new ComboBox<>();
        transactionMethodComboBox.getItems().addAll("Tiền mặt", "Chuyển khoản");

        Label reasonLabel = new Label("Nội dung giao dịch:");
        TextField reasonField = new TextField();

        Label contactInfoLabel = new Label("Thông tin liên lạc:");
        TextField contactInfoField = new TextField();

        Label transactionDateLabel = new Label("Thời gian giao dịch:");
        DatePicker transactionDateField = new DatePicker();

        // Nút Xác nhận nạp tiền
        Button transactionButton = new Button("Nạp Tiền");
        transactionButton.getStyleClass().add("transfer-button");
        transactionButton.setOnAction(event -> {
            handleDeposit(
                    accountNumberField.getText(),
                    transactionAmountField.getText(),
                    pinField.getText(),
                    transactionMethodComboBox.getValue(),
                    reasonField.getText(),
                    contactInfoField.getText()
            );
        });

        // Lắng nghe sự kiện nhập "Số tài khoản"
        accountNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                String accountHolderName = dalAccount.getCustomerNameByAccount(newValue.trim());
                accountHolderField.setText(accountHolderName != null ? accountHolderName : "Không tìm thấy");
            } else {
                accountHolderField.clear();
            }
        });

        gridPane.add(accountNumberLabel, 0, 0);
        gridPane.add(accountNumberField, 1, 0);
        gridPane.add(accountHolderLabel, 0, 1);
        gridPane.add(accountHolderField, 1, 1);
        gridPane.add(transactionAmountLabel, 0, 2);
        gridPane.add(transactionAmountField, 1, 2);
        gridPane.add(pinLabel, 0, 3);
        gridPane.add(pinField, 1, 3);
        gridPane.add(transactionMethodLabel, 0, 4);
        gridPane.add(transactionMethodComboBox, 1, 4);
        gridPane.add(reasonLabel, 0, 5);
        gridPane.add(reasonField, 1, 5);
        gridPane.add(contactInfoLabel, 0, 6);
        gridPane.add(contactInfoField, 1, 6);
        gridPane.add(transactionDateLabel, 0, 7);
        gridPane.add(transactionDateField, 1, 7);
        gridPane.add(transactionButton, 1, 8);

        // Tạo Image và ImageView cho icon
        Image printIcon = new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/printer.png"));
        ImageView printIconView = new ImageView(printIcon);
        printIconView.setFitWidth(20);
        printIconView.setFitHeight(20);

        // Tạo nút In hóa đơn và thêm icon
        Button printReceiptButton = new Button("In hóa đơn", printIconView);
        gridPane.add(printReceiptButton, 1, 9);

        // Thiết lập căn chỉnh
        GridPane.setHalignment(transactionButton, HPos.RIGHT);
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

    private void handleDeposit(String accountNumber, String amount, String pin, String method, String reason, String contactInfo) {
        boolean isPinValid = dalAccount.validatePin(accountNumber, Integer.parseInt(pin));
        if (!isPinValid) {
            showError("Mã PIN không hợp lệ.");
            return;
        }

        boolean success = dalAccount.deposit(accountNumber, Double.parseDouble(amount), reason, method, contactInfo);
        if (success) {
            showMessage("Nạp tiền thành công.");
            refreshTableData(); // Cập nhật lại dữ liệu trên bảng

        } else {
            showError("Nạp tiền thất bại.");
        }
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.showAndWait();
    }
    private void refreshTableData() {
        dataList.clear();
        dataList.addAll(dalAccount.getAllCustomers());
        tableView.refresh();
    }


}
