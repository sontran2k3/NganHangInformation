package com.example.nganhanginformation;

import BusinessLogicLayer.BLLEmployee;
import Entity.EntityEmployee;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.sql.Date;

public class ManagePane extends AnchorPane {

    private TableView<EntityEmployee> tableView;
    private ObservableList<EntityEmployee> dataList;
    private BLLEmployee dalEmployee = new BLLEmployee();

    private TextField fullNameField;
    private TextField cccdField;
    private DatePicker birthdayPicker;
    private TextField addressField;
    private TextField phoneField;
    private ComboBox<String> genderComboBox;

    public ManagePane(Scene scene) {
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

        TableColumn<EntityEmployee, Integer> idCol = new TableColumn<>("Mã nhân viên");
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        idCol.setMinWidth(165);

        TableColumn<EntityEmployee, String> fullnameCol = new TableColumn<>("Tên nhân viên");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fullnameCol.setMinWidth(165);

        TableColumn<EntityEmployee, String> cccdCol = new TableColumn<>("Cccd/CMND");
        cccdCol.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        cccdCol.setMinWidth(165);

        TableColumn<EntityEmployee, Date> birthdayCol = new TableColumn<>("Ngày sinh");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        birthdayCol.setMinWidth(165);

        TableColumn<EntityEmployee, String> addressCol = new TableColumn<>("Địa chỉ");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setMinWidth(165);

        TableColumn<EntityEmployee, String> phoneCol = new TableColumn<>("Số điện thoại");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setMinWidth(165);

        TableColumn<EntityEmployee, String> genderCol = new TableColumn<>("Giới tính");
        genderCol.setCellValueFactory(cellData -> {
            boolean gender = cellData.getValue().isGender();
            return new SimpleStringProperty(gender ? "Nam" : "Nữ");


        });
        genderCol.setMinWidth(165);

        tableView.getColumns().addAll(idCol, fullnameCol, cccdCol, birthdayCol, addressCol, phoneCol, genderCol);
        tableView.setPrefHeight(300);

        dataList = FXCollections.observableArrayList(dalEmployee.getAllEmployees());
        tableView.setItems(dataList);

        tableView.setOnMouseClicked(event -> {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                EntityEmployee selectedEmployee = tableView.getSelectionModel().getSelectedItem();

                fullNameField.setText(selectedEmployee.getFullname());
                cccdField.setText(selectedEmployee.getCccd());
                birthdayPicker.setValue(selectedEmployee.getBirthday().toLocalDate());
                addressField.setText(selectedEmployee.getAddress());
                phoneField.setText(selectedEmployee.getPhone());
                genderComboBox.setValue(selectedEmployee.isGender() ? "Nam" : "Nữ");
            }
        });

        VBox tableContainer = new VBox(searchField, tableView);
        tableContainer.setSpacing(10);
        tableContainer.setPadding(new Insets(10));

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
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(30);

        Label titleLabel = new Label("Thông Tin Tài Khoản");
        titleLabel.getStyleClass().add("title-label");
        grid.add(titleLabel, 0, 0, 4, 1);

        // Các trường thông tin nhân viên
        Label fullNameLabel = new Label("Họ và tên: ");
        fullNameField = new TextField();
        fullNameField.setPrefWidth(180);
        grid.add(fullNameLabel, 0, 1);
        grid.add(fullNameField, 1, 1);

        Label cccdLabel = new Label("CCCD/CMND: ");
        cccdField = new TextField();
        cccdField.setPrefWidth(180);
        grid.add(cccdLabel, 0, 2);
        grid.add(cccdField, 1, 2);

        Label birthdayLabel = new Label("Ngày sinh: ");
        birthdayPicker = new DatePicker();
        birthdayPicker.setPrefWidth(180);
        grid.add(birthdayLabel, 0, 3);
        grid.add(birthdayPicker, 1, 3);

        Label addressLabel = new Label("Địa chỉ: ");
        addressField = new TextField();
        addressField.setPrefWidth(180);
        grid.add(addressLabel, 2, 1);
        grid.add(addressField, 3, 1);

        Label phoneLabel = new Label("Số điện thoại: ");
        phoneField = new TextField();
        phoneField.setPrefWidth(180);
        grid.add(phoneLabel, 2, 2);
        grid.add(phoneField, 3, 2);

        Label genderLabel = new Label("Giới tính: ");
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Nam", "Nữ");
        genderComboBox.setPrefWidth(180);
        grid.add(genderLabel, 2, 3);
        grid.add(genderComboBox, 3, 3);

        Button addButton = new Button("Thêm tài khoản");
        addButton.setOnAction(event -> {addEmployee();});

        Button updateButton = new Button("Cập nhật tài khoản");
        updateButton.setOnAction(event -> {updateEmployee();});

        Button deleteButton = new Button("Xóa tài khoản");
        deleteButton.setOnAction(event -> {deleteEmployee();});


        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.getStyleClass().add("hbox-buttons");
        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton);
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(10));
        formContainer.setStyle("-fx-background-color: #f4f4f9; -fx-border-radius: 15; -fx-border-color: #cccccc; -fx-border-width: 2; -fx-background-radius: 15;");
        formContainer.getChildren().add(grid);

        grid.add(buttonBox, 0, 4, 4, 1);
        Scene scene = new Scene(formContainer, 800, 400);

        return formContainer;
    }
    private void addEmployee() {
        if (fullNameField.getText().isEmpty() || cccdField.getText().isEmpty() || birthdayPicker.getValue() == null ||
                addressField.getText().isEmpty() || phoneField.getText().isEmpty() || genderComboBox.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Vui lòng nhập đầy đủ thông tin");
            alert.setContentText("Các trường thông tin không thể để trống.");
            alert.showAndWait();
        } else {
            int randomEmployeeId = generateRandomEmployeeId();
            String fullname = fullNameField.getText();
            String cccd = cccdField.getText();
            Date birthday = Date.valueOf(birthdayPicker.getValue());
            String address = addressField.getText();
            String phone = phoneField.getText();
            boolean gender = genderComboBox.getValue().equals("Nam");
            String role = "nhansu";

            EntityEmployee newEmployee = new EntityEmployee(randomEmployeeId, fullname, cccd, birthday, address, phone, gender, role);
            dalEmployee.addEmployee(newEmployee);
            dataList.add(newEmployee);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Thành công");
            successAlert.setHeaderText("Thêm nhân viên thành công");
            successAlert.setContentText("Nhân viên đã được thêm vào hệ thống.");
            successAlert.showAndWait();
        }
    }

    private void updateEmployee() {
        EntityEmployee updatedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (updatedEmployee != null) {
            updatedEmployee.setFullname(fullNameField.getText());
            updatedEmployee.setCccd(cccdField.getText());
            updatedEmployee.setBirthday(Date.valueOf(birthdayPicker.getValue()));
            updatedEmployee.setAddress(addressField.getText());
            updatedEmployee.setPhone(phoneField.getText());
            updatedEmployee.setGender(genderComboBox.getValue().equals("Nam"));

            dalEmployee.updateEmployee(updatedEmployee);

            int index = dataList.indexOf(updatedEmployee);
            if (index != -1) {
                dataList.set(index, updatedEmployee);
            }
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Cập nhật thành công");
            successAlert.setHeaderText("Cập nhật thông tin nhân viên");
            successAlert.setContentText("Thông tin nhân viên đã được cập nhật thành công.");
            successAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Chưa chọn nhân viên");
            alert.setContentText("Vui lòng chọn nhân viên cần cập nhật.");
            alert.showAndWait();
        }
    }

    private void deleteEmployee() {
        EntityEmployee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Xóa nhân viên");
            confirmationAlert.setHeaderText("Bạn có chắc chắn muốn xóa nhân viên này?");
            confirmationAlert.setContentText("Nhân viên sẽ bị xóa khỏi hệ thống.");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    dalEmployee.deleteEmployee(selectedEmployee.getEmployeeId());

                    dataList.remove(selectedEmployee);

                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Xóa thành công");
                    successAlert.setHeaderText("Nhân viên đã được xóa");
                    successAlert.setContentText("Nhân viên đã được xóa khỏi hệ thống.");
                    successAlert.showAndWait();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Cảnh báo");
            alert.setHeaderText("Chưa chọn nhân viên");
            alert.setContentText("Vui lòng chọn nhân viên cần xóa.");
            alert.showAndWait();
        }
    }



    private int generateRandomEmployeeId() {
        return (int) (Math.random() * 900000) + 100000;
    }

    private void filterData(String searchTerm) {
        if (searchTerm.isEmpty()) {
            tableView.setItems(dataList);
            return;
        }
        ObservableList<EntityEmployee> filteredData = FXCollections.observableArrayList();
        for (EntityEmployee employee : dataList) {
            if (employee.getFullname().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    employee.getCccd().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredData.add(employee);
            }
        }
        tableView.setItems(filteredData);
    }
}
