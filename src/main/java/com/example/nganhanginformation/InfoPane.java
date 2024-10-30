package com.example.nganhanginformation;

import Entity.Employee;  // Import the Employee class
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class InfoPane extends AnchorPane {

    private TableView<Employee> tableView;
    private ObservableList<Employee> dataList;

    public InfoPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/table.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn. InfoPane");
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

        TableColumn<Employee, String> idCol = new TableColumn<>("Employee ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setMinWidth(100);

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setMinWidth(100);

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setMinWidth(100);

        TableColumn<Employee, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderCol.setMinWidth(80);

        TableColumn<Employee, String> phoneCol = new TableColumn<>("Phone #");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setMinWidth(100);

        TableColumn<Employee, String> positionCol = new TableColumn<>("Position");
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        positionCol.setMinWidth(150);

        TableColumn<Employee, String> dateMemberCol = new TableColumn<>("Date Member");
        dateMemberCol.setCellValueFactory(new PropertyValueFactory<>("dateMember"));
        dateMemberCol.setMinWidth(100);

        tableView.getColumns().addAll(idCol, firstNameCol, lastNameCol, genderCol, phoneCol, positionCol, dateMemberCol);

        dataList = FXCollections.observableArrayList(
                new Employee("1", "Marco", "Man", "Male", "2131223", "Web Developer (Back End)", "2022-09-21"),
                new Employee("2", "Check", "King", "Female", "231232", "Marketer Coordinator", "2022-09-21"),
                new Employee("3", "Sharit", "Mbual", "Male", "123235", "App Developer", "2022-09-21")
        );
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
        ObservableList<Employee> filteredData = FXCollections.observableArrayList();
        for (Employee employee : dataList) {
            if (employee.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    employee.getLastName().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredData.add(employee);
            }
        }
        tableView.setItems(filteredData);
    }
}
