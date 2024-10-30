package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EmployeeTableApp extends Application {

    private TableView<Employee> tableView;
    private ObservableList<Employee> dataList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Table");

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.getStyleClass().add("text-field");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));

        tableView = new TableView<>();
        tableView.getStyleClass().add("table-view");

        TableColumn<Employee, String> idCol = new TableColumn<>("Employee ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Employee, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableColumn<Employee, String> phoneCol = new TableColumn<>("Phone #");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<Employee, String> positionCol = new TableColumn<>("Position");
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Employee, String> dateMemberCol = new TableColumn<>("Date Member");
        dateMemberCol.setCellValueFactory(new PropertyValueFactory<>("dateMember"));

        tableView.getColumns().addAll(idCol, firstNameCol, lastNameCol, genderCol, phoneCol, positionCol, dateMemberCol);

        dataList = FXCollections.observableArrayList(
                new Employee("1", "Marco", "Man", "Male", "2131223", "Web Developer (Back End)", "2022-09-21"),
                new Employee("2", "Check", "King", "Female", "231232", "Marketer Coordinator", "2022-09-21"),
                new Employee("3", "Sharit", "Mbual", "Male", "123235", "App Developer", "2022-09-21")
        );
        tableView.setItems(dataList);


        HBox searchBox = new HBox(searchField);
        searchBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(searchBox);
        root.setCenter(tableView);

        Scene scene = new Scene(root, 800, 400);
        scene.getStylesheets().add(getClass().getResource("Styles/table.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
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

    public static class Employee {
        private final String id;
        private final String firstName;
        private final String lastName;
        private final String gender;
        private final String phone;
        private final String position;
        private final String dateMember;

        public Employee(String id, String firstName, String lastName, String gender, String phone, String position, String dateMember) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.phone = phone;
            this.position = position;
            this.dateMember = dateMember;
        }

        public String getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getGender() {
            return gender;
        }

        public String getPhone() {
            return phone;
        }

        public String getPosition() {
            return position;
        }

        public String getDateMember() {
            return dateMember;
        }
    }
}
