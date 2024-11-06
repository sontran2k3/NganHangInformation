package com.example.nganhanginformation;

import Entity.EntityKhachHang;
import BusinessLogicLayer.BLLKhachHang; // Import BLL class
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class KhachHangPane extends AnchorPane {

    private TableView<EntityKhachHang> tableView;
    private ObservableList<EntityKhachHang> dataList;
    private BLLKhachHang bllKhachHang = new BLLKhachHang();

    public KhachHangPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/table.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn.  Quản lý khách hàng");
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

        TableColumn<EntityKhachHang, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId")); // Chỉnh sửa để trùng với tên biến
        idCol.setMinWidth(50);

        TableColumn<EntityKhachHang, String> fullnameCol = new TableColumn<>("Họ Tên");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fullnameCol.setMinWidth(150);

        TableColumn<EntityKhachHang, String> cccdCol = new TableColumn<>("CMND/CCCD");
        cccdCol.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        cccdCol.setMinWidth(100);

        TableColumn<EntityKhachHang, String> genderCol = new TableColumn<>("Giới Tính");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderCol.setMinWidth(50);

        TableColumn<EntityKhachHang, String> phoneCol = new TableColumn<>("Số Điện Thoại");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setMinWidth(100);

        TableColumn<EntityKhachHang, String> birthdayCol = new TableColumn<>("Ngày Sinh");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        birthdayCol.setMinWidth(100);

        TableColumn<EntityKhachHang, String> addressCol = new TableColumn<>("Địa Chỉ");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setMinWidth(150);

        TableColumn<EntityKhachHang, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setMinWidth(150);

        TableColumn<EntityKhachHang, String> occupationCol = new TableColumn<>("Nghề Nghiệp");
        occupationCol.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        occupationCol.setMinWidth(150);



        tableView.getColumns().addAll(idCol, fullnameCol, cccdCol, genderCol, phoneCol, birthdayCol, addressCol, emailCol, occupationCol);

        dataList = FXCollections.observableArrayList(bllKhachHang.getAllCustomers());
        tableView.setItems(dataList);

        VBox container = new VBox(searchField, tableView);
        container.setSpacing(10);
        container.setPadding(new Insets(10));
        this.getChildren().add(container);
        AnchorPane.setTopAnchor(container, 60.0);
        AnchorPane.setLeftAnchor(container, 20.0);
        AnchorPane.setRightAnchor(container, 20.0);
        AnchorPane.setBottomAnchor(container, 20.0);

        AnchorPane.setTopAnchor(tableView, 0.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
    }

    private void filterData(String searchTerm) {
        if (searchTerm.isEmpty()) {
            tableView.setItems(dataList);
            return;
        }
        ObservableList<EntityKhachHang> filteredData = FXCollections.observableArrayList();
        for (EntityKhachHang customer : dataList) {
            if (customer.getCccd().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredData.add(customer);
            }
        }
        tableView.setItems(filteredData);
    }

}
