package com.example.nganhanginformation;

import BusinessLogicLayer.BLLAccount;
import Entity.EntityAccount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AccountPane extends AnchorPane {

    private TableView<EntityAccount> tableView;
    private ObservableList<EntityAccount> dataList;
    private BLLAccount dalAccount = new BLLAccount();

    public AccountPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/table.css").toExternalForm());

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

        VBox container = new VBox(searchField, tableView);
        container.setSpacing(10);
        container.setPadding(new Insets(10));
        this.getChildren().add(container);
        AnchorPane.setTopAnchor(container, 60.0);
        AnchorPane.setLeftAnchor(container, 20.0);
        AnchorPane.setRightAnchor(container, 20.0);
        AnchorPane.setBottomAnchor(container, 20.0);
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
