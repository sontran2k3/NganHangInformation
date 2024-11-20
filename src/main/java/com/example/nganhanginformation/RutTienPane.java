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

import java.math.BigDecimal;

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

    private GridPane createWithdrawalSection() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.getStyleClass().add("grid-pane");

        // Các thành phần giao diện
        Label accountNumberLabel = new Label("Số tài khoản:");
        TextField accountNumberField = new TextField();

        Label accountHolderLabel = new Label("Tên chủ tài khoản:");
        TextField accountHolderField = new TextField();
        accountHolderField.setEditable(false);

        Label withdrawalAmountLabel = new Label("Số tiền giao dịch:");
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
        accountNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                String senderName = dalAccount.getCustomerNameByAccount(newValue.trim());
                accountHolderField.setText(senderName != null ? senderName : "Không tìm thấy");
            } else {
                accountHolderField.clear();
            }
        });
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

        return gridPane;
    }

    private void handleWithdrawal(String accountNumber, String amountStr, String pin, String method, String reason, String contactInfo) {
        try {
            BigDecimal amount = new BigDecimal(amountStr);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Số tiền phải lớn hơn 0!");
            }

            boolean success = dalAccount.processWithdrawal(accountNumber, amount, pin, method, reason, contactInfo);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Giao dịch thành công!", ButtonType.OK);
                alert.showAndWait();
                refreshTableData(); // Cập nhật lại dữ liệu trên bảng
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Giao dịch thất bại!", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Số tiền không hợp lệ!", ButtonType.OK);
            alert.showAndWait();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
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
    private void refreshTableData() {
        dataList.clear();
        dataList.addAll(dalAccount.getAllCustomers());
        tableView.refresh();
    }


}
