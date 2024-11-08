package com.example.nganhanginformation;

import BusinessLogicLayer.BLLAccount;
import BusinessLogicLayer.BLLKhachHang;
import Entity.EntityAccount;
import Entity.EntityKhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

public class AccountPane extends AnchorPane {

    private TableView<EntityAccount> tableView;
    private ObservableList<EntityAccount> dataList;
    private BLLAccount dalAccount = new BLLAccount();
    private BLLKhachHang bllKhachHang = new BLLKhachHang(); // Thêm đối tượng BLLKhachHang


    public AccountPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/account.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn.  Quản lý tài khoản");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        this.getChildren().add(welcomeLabel);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.getStyleClass().add("text-field");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));

        tableView = new TableView<>();
        tableView.getStyleClass().add("table-view");

        TableColumn<EntityAccount, Integer> idCol = new TableColumn<>("Mã tài khoản");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        idCol.setMinWidth(165);

        TableColumn<EntityAccount, String> fullnameCol = new TableColumn<>("Tên khách hàng");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fullnameCol.setMinWidth(165);

        TableColumn<EntityAccount, String> cccdCol = new TableColumn<>("Loại tài khoản");
        cccdCol.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        cccdCol.setMinWidth(165);

        TableColumn<EntityAccount, Double> balanceCol = new TableColumn<>("Số dư hiện tại");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        balanceCol.setMinWidth(165);

        TableColumn<EntityAccount, String> addressCol = new TableColumn<>("Địa chỉ");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setMinWidth(165);

        TableColumn<EntityAccount, String> statusCol = new TableColumn<>("Trạng thái tài khoản");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setMinWidth(165);

        TableColumn<EntityAccount, String> pinCol = new TableColumn<>("Mã Pin");
        pinCol.setCellValueFactory(new PropertyValueFactory<>("pin"));
        pinCol.setMinWidth(165);

        tableView.getColumns().addAll(idCol, fullnameCol, cccdCol, balanceCol, addressCol, statusCol, pinCol);

        dataList = FXCollections.observableArrayList(dalAccount.getAllCustomers());
        tableView.setItems(dataList);

        VBox tableContainer = new VBox(searchField, tableView);
        tableContainer.setSpacing(10);
        tableContainer.setPadding(new Insets(10));



        // Thêm form quản lý tài khoản dưới bảng
        VBox accountManagementContainer = createAccountManagementForm();

        VBox mainContainer = new VBox(tableContainer, accountManagementContainer);
        mainContainer.setSpacing(20);

        this.getChildren().add(mainContainer);
        AnchorPane.setTopAnchor(mainContainer, 60.0);
        AnchorPane.setLeftAnchor(mainContainer, 20.0);
        AnchorPane.setRightAnchor(mainContainer, 20.0);
        AnchorPane.setBottomAnchor(mainContainer, 20.0);
    }

    private VBox createAccountManagementForm() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(15);
        grid.setHgap(20);

        Label titleLabel = new Label("Thông Tin Tài Khoản");
        titleLabel.getStyleClass().add("title-label");
        grid.add(titleLabel, 0, 0, 2, 1);

        Label accountIdLabel = new Label("Mã khách hàng:");
        TextField accountIdField = new TextField();
        grid.add(accountIdLabel, 0, 1);
        grid.add(accountIdField, 1, 1);

        Label customerIdLabel = new Label("Tên khách hàng:");
        TextField customerField = new TextField();
        customerField.setEditable(false);  // Tên khách hàng sẽ không thể chỉnh sửa
        grid.add(customerIdLabel, 0, 2);
        grid.add(customerField, 1, 2);

        Label employeeIdLabel = new Label("Mã nhân viên:");
        TextField employeeIdField = new TextField();
        grid.add(employeeIdLabel, 0, 3);
        grid.add(employeeIdField, 1, 3);

        Label createDateLabel = new Label("Ngày tạo:");
        DatePicker createDatePicker = new DatePicker();
        grid.add(createDateLabel, 0, 4);
        grid.add(createDatePicker, 1, 4);

        Label validationDateLabel = new Label("Ngày xác thực:");
        DatePicker validationDatePicker = new DatePicker();
        grid.add(validationDateLabel, 2, 1);
        grid.add(validationDatePicker, 3, 1);

        Label accountTypeLabel = new Label("Loại tài khoản:");
        ComboBox<String> accountTypeComboBox = new ComboBox<>();
        accountTypeComboBox.getItems().addAll("Ghi nợ", "Tiết kiệm", "");
        grid.add(accountTypeLabel, 2, 2);
        grid.add(accountTypeComboBox, 3, 2);

        Label statusLabel = new Label("Trạng thái:");
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Hoạt động", "Ngừng hoạt động", "Khóa");
        grid.add(statusLabel, 2, 3);
        grid.add(statusComboBox, 3, 3);

        Label balanceLabel = new Label("Số dư:");
        TextField balanceField = new TextField();
        balanceField.setText("0.00");
        grid.add(balanceLabel, 2, 4);
        grid.add(balanceField, 3, 4);

        Button addButton = new Button("Thêm tài khoản");
        Button updateButton = new Button("Cập nhật tài khoản");
        Button deleteButton = new Button("Xóa tài khoản");

        accountIdField.setOnKeyReleased(event -> {
            try {
                int customerId = Integer.parseInt(accountIdField.getText().trim());
                EntityKhachHang customer = bllKhachHang.getCustomerById(customerId);
                if (customer != null) {
                    customerField.setText(customer.getFullname());
                } else {
                    customerField.clear();
                }
            } catch (NumberFormatException e) {
                customerField.clear(); // Nếu không phải số hợp lệ, xóa tên
            }
        });


        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        buttonBox.getStyleClass().add("hbox-buttons");
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);

        accountIdField.setPrefWidth(200);
        customerField.setPrefWidth(200);
        employeeIdField.setPrefWidth(200);
        createDatePicker.setPrefWidth(200);
        validationDatePicker.setPrefWidth(200);
        accountTypeComboBox.setPrefWidth(200);
        statusComboBox.setPrefWidth(200);
        balanceField.setPrefWidth(200);

        VBox formContainer = new VBox(50);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f4f4f9; -fx-border-radius: 15; -fx-border-color: #cccccc; -fx-border-width: 2; -fx-background-radius: 15;");
        formContainer.getChildren().add(grid);

        grid.add(buttonBox, 0, 5, 4, 1);
        Scene scene = new Scene(formContainer, 800, 500);



        return formContainer;
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
}
