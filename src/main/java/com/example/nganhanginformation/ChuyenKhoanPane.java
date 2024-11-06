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
import javafx.stage.Stage;

public class ChuyenKhoanPane extends AnchorPane {

    private TableView<EntityAccount> tableView;
    private ObservableList<EntityAccount> dataList;
    private BLLAccount dalAccount = new BLLAccount();
    private PasswordField pinField;  // Declare pinField as a class-level variable

    public ChuyenKhoanPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/chuyenkhoan.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn. Chuyển Khoản");
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
        container.getChildren().addAll(tableContainer, createTransferSection());

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

    private GridPane createTransferSection() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.getStyleClass().add("grid-pane");

        Label senderAccountLabel = new Label("Số tài khoản người gửi:");
        TextField senderAccountField = new TextField();

        Label senderNameLabel = new Label("Tên người gửi:");
        TextField senderNameField = new TextField();
        senderNameField.setEditable(false);

        Label receiverAccountLabel = new Label("Số tài khoản người nhận:");
        TextField receiverAccountField = new TextField();

        Label receiverNameLabel = new Label("Tên người nhận:");
        TextField receiverNameField = new TextField();
        receiverNameField.setEditable(false);

        Label amountLabel = new Label("Số tiền giao dịch:");
        TextField amountField = new TextField();

        Label descriptionLabel = new Label("Nội dung giao dịch:");
        TextField descriptionField = new TextField();

        Label dateLabel = new Label("Thời gian giao dịch:");
        DatePicker dateField = new DatePicker();

        Label pinLabel = new Label("Mã PIN/Mật khẩu:");
        pinField = new PasswordField();

        Button transferButton = new Button("Thanh Toán");
        transferButton.getStyleClass().add("transfer-button");
        transferButton.setOnAction(event -> {
            handleTransfer(senderAccountField.getText(), receiverAccountField.getText(), amountField.getText(), descriptionField.getText());
        });

        senderAccountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                String senderName = dalAccount.getCustomerNameByAccount(newValue);
                senderNameField.setText(senderName != null ? senderName : "Không tìm thấy");
            } else {
                senderNameField.clear();
            }
        });

        receiverAccountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                String receiverName = dalAccount.getCustomerNameByAccount(newValue);
                receiverNameField.setText(receiverName != null ? receiverName : "Không tìm thấy");
            } else {
                receiverNameField.clear();
            }
        });

        gridPane.add(senderAccountLabel, 0, 0);
        gridPane.add(senderAccountField, 1, 0);
        gridPane.add(senderNameLabel, 0, 1);
        gridPane.add(senderNameField, 1, 1);
        gridPane.add(receiverAccountLabel, 0, 2);
        gridPane.add(receiverAccountField, 1, 2);
        gridPane.add(receiverNameLabel, 0, 3);
        gridPane.add(receiverNameField, 1, 3);
        gridPane.add(amountLabel, 0, 4);
        gridPane.add(amountField, 1, 4);
        gridPane.add(descriptionLabel, 0, 5);
        gridPane.add(descriptionField, 1, 5);
        gridPane.add(dateLabel, 0, 6);
        gridPane.add(dateField, 1, 6);
        gridPane.add(pinLabel, 0, 7);
        gridPane.add(pinField, 1, 7);
        gridPane.add(transferButton, 0, 8, 2, 1);
        GridPane.setHalignment(transferButton, HPos.CENTER);

        Image printIcon = new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/printer.png"));
        ImageView printIconView = new ImageView(printIcon);
        printIconView.setFitWidth(20);
        printIconView.setFitHeight(20);

        Button printReceiptButton = new Button("In hóa đơn", printIconView);
        printReceiptButton.setOnAction(event -> {
            String referenceCode = dalAccount.getLastReferenceCode();
            if (referenceCode != null) {
                printReceipt(senderAccountField.getText(), receiverAccountField.getText(),
                        Double.parseDouble(amountField.getText()), descriptionField.getText(), referenceCode);
            } else {
                showAlert("Lỗi", "Không tìm thấy mã tham chiếu giao dịch. Vui lòng thử lại.");
            }
        });
        gridPane.add(printReceiptButton, 1, 9);
        GridPane.setHalignment(printReceiptButton, HPos.RIGHT);

        return gridPane;
    }

    private void handleTransfer(String senderAccount, String receiverAccount, String amount, String description) {
        String pinInput = pinField.getText();
        if (pinInput.isEmpty()) {
            showAlert("Lỗi", "Vui lòng nhập mã PIN.");
            return;
        }

        double transferAmount;
        try {
            transferAmount = Double.parseDouble(amount);
            if (transferAmount <= 0) {
                showAlert("Lỗi", "Số tiền phải lớn hơn 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Lỗi", "Số tiền không hợp lệ.");
            return;
        }

        if (senderAccount.isEmpty() || receiverAccount.isEmpty()) {
            showAlert("Lỗi", "Vui lòng điền đầy đủ thông tin tài khoản.");
            return;
        }

        boolean isPinValid = dalAccount.validatePin(senderAccount, Integer.parseInt(pinInput));
        if (!isPinValid) {
            showAlert("Lỗi", "Mã PIN không hợp lệ.");
            return;
        }

        boolean success = dalAccount.transfer(senderAccount, receiverAccount, transferAmount, description);
        if (success) {
            showAlert("Thành công", "Chuyển khoản thành công.\nMã tham chiếu giao dịch: " + dalAccount.getLastReferenceCode());
            dataList.setAll(dalAccount.getAllCustomers());
        } else {
            showAlert("Lỗi", "Chuyển khoản thất bại.");
        }
    }

    private void printReceipt(String senderAccount, String receiverAccount, double amount, String description, String referenceCode) {
        String receiptContent = "----- HÓA ĐƠN GIAO DỊCH -----\n"
                + "Ngân hàng: Ngân hàng XYZ\n"
                + "Mã tham chiếu: " + referenceCode + "\n"
                + "Ngày giao dịch: " + java.time.LocalDate.now() + "\n\n"
                + "Người gửi: " + senderAccount + "\n"
                + "Người nhận: " + receiverAccount + "\n"
                + "Số tiền: " + amount + "\n"
                + "Nội dung: " + description + "\n\n"
                + "----- CẢM ƠN QUÝ KHÁCH -----";

        TextArea receiptArea = new TextArea(receiptContent);
        receiptArea.setEditable(false);

        Stage printStage = new Stage();
        printStage.setTitle("Hóa đơn giao dịch");
        printStage.setScene(new Scene(new VBox(receiptArea), 400, 300));
        printStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void filterData(String searchQuery) {
        if (searchQuery.isEmpty()) {
            tableView.setItems(dataList);
        } else {
            ObservableList<EntityAccount> filteredList = FXCollections.observableArrayList();
            for (EntityAccount account : dataList) {
                if (account.getFullname().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredList.add(account);
                }
            }
            tableView.setItems(filteredList);
        }
    }
}
