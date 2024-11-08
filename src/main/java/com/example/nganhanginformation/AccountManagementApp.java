package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AccountManagementApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quản lý Tài khoản");

        // Tạo lưới bố cục cho giao diện
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(15);
        grid.setHgap(20);

        // Tạo tiêu đề cho giao diện
        Label titleLabel = new Label("Thông Tin Tài Khoản");
        titleLabel.getStyleClass().add("title-label");

        grid.add(titleLabel, 0, 0, 2, 1);

        // account_id (Mã tài khoản)
        Label accountIdLabel = new Label("Mã tài khoản:");
        TextField accountIdField = new TextField();
        accountIdField.setEditable(false);
        grid.add(accountIdLabel, 0, 1);
        grid.add(accountIdField, 1, 1);

        // customer_id (Mã khách hàng)
        Label customerIdLabel = new Label("Mã khách hàng:");
        TextField customerIdField = new TextField();
        grid.add(customerIdLabel, 0, 2);
        grid.add(customerIdField, 1, 2);

        // employee_id (Mã nhân viên)
        Label employeeIdLabel = new Label("Mã nhân viên:");
        TextField employeeIdField = new TextField();
        grid.add(employeeIdLabel, 0, 3);
        grid.add(employeeIdField, 1, 3);

        // create_date (Ngày tạo tài khoản)
        Label createDateLabel = new Label("Ngày tạo:");
        DatePicker createDatePicker = new DatePicker();
        grid.add(createDateLabel, 0, 4);
        grid.add(createDatePicker, 1, 4);

        // validation_date (Ngày xác thực)
        Label validationDateLabel = new Label("Ngày xác thực:");
        DatePicker validationDatePicker = new DatePicker();
        grid.add(validationDateLabel, 2, 1);
        grid.add(validationDatePicker, 3, 1);

        // account_type (Loại tài khoản)
        Label accountTypeLabel = new Label("Loại tài khoản:");
        ComboBox<String> accountTypeComboBox = new ComboBox<>();
        accountTypeComboBox.getItems().addAll("Saving", "Checking");
        grid.add(accountTypeLabel, 2, 2);
        grid.add(accountTypeComboBox, 3, 2);

        // status (Trạng thái tài khoản)
        Label statusLabel = new Label("Trạng thái:");
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Active", "Inactive", "Suspended");
        grid.add(statusLabel, 2, 3);
        grid.add(statusComboBox, 3, 3);

        // balance (Số dư tài khoản)
        Label balanceLabel = new Label("Số dư:");
        TextField balanceField = new TextField();
        balanceField.setText("0.00");
        grid.add(balanceLabel, 2, 4);
        grid.add(balanceField, 3, 4);

        // Tạo HBox chứa các nút chức năng
        Button addButton = new Button("Thêm tài khoản");
        Button updateButton = new Button("Cập nhật tài khoản");
        Button deleteButton = new Button("Xóa tài khoản");

        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        buttonBox.getStyleClass().add("hbox-buttons");
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);

        // Thiết lập chiều rộng cố định cho các trường
        accountIdField.setPrefWidth(200);
        customerIdField.setPrefWidth(200);
        employeeIdField.setPrefWidth(200);
        createDatePicker.setPrefWidth(200);
        validationDatePicker.setPrefWidth(200);
        accountTypeComboBox.setPrefWidth(200);
        statusComboBox.setPrefWidth(200);
        balanceField.setPrefWidth(200);

        // Tạo một VBox để bao bọc các trường nhập liệu
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #f4f4f9; -fx-border-radius: 15; -fx-border-color: #cccccc; -fx-border-width: 2; -fx-background-radius: 15;");

        // Thêm GridPane vào VBox
        formContainer.getChildren().add(grid);

        // Bố trí HBox vào lưới
        grid.add(buttonBox, 0, 5, 4, 1);

        // Tạo scene và thêm CSS
        Scene scene = new Scene(formContainer, 800, 600);
        scene.getStylesheets().add(getClass().getResource("Styles/account.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
