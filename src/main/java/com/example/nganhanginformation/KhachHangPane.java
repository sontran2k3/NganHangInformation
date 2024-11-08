package com.example.nganhanginformation;

import Entity.EntityKhachHang;
import BusinessLogicLayer.BLLKhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class KhachHangPane extends AnchorPane {

    private TableView<EntityKhachHang> tableView;
    private ObservableList<EntityKhachHang> dataList;
    private BLLKhachHang bllKhachHang = new BLLKhachHang();

    private ImageView profileImageView;
    private ImageView signImageView;
    private ImageView profileIcon;
    private ImageView signIcon;

    public KhachHangPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/table.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn.  Quản lý khách hàng");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        this.getChildren().add(welcomeLabel);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        VBox container = new VBox();
        container.setSpacing(10);
        container.setPadding(new Insets(10));

        GridPane grid = new GridPane();
        grid.setVgap(10);  // Giảm khoảng cách giữa các hàng
        grid.setHgap(20); // Giảm khoảng cách giữa các cột
        grid.setPadding(new Insets(10));

        // Cột 1: Thông tin cá nhân
        TextField fullNameField = new TextField();
        TextField cccdField = new TextField();
        DatePicker birthdayPicker = new DatePicker();
        TextField addressField = new TextField();

        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(fullNameField, 1, 0);
        grid.add(new Label("CCCD:"), 0, 1);
        grid.add(cccdField, 1, 1);
        grid.add(new Label("Birthday:"), 0, 2);
        grid.add(birthdayPicker, 1, 2);
        grid.add(new Label("Address:"), 0, 3);
        grid.add(addressField, 1, 3);

        // Cột 2: Thông tin bổ sung
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        TextField occupationField = new TextField();
        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female", "Other");

        grid.add(new Label("Phone:"), 2, 0);
        grid.add(phoneField, 3, 0);
        grid.add(new Label("Email:"), 2, 1);
        grid.add(emailField, 3, 1);
        grid.add(new Label("Occupation:"), 2, 2);
        grid.add(occupationField, 3, 2);
        grid.add(new Label("Gender:"), 2, 3);
        grid.add(genderBox, 3, 3);

        // Cột 3: Hình ảnh và chữ ký
        Button chooseProfileImageButton = new Button("Ảnh đại diện");
        chooseProfileImageButton.setOnAction(e -> chooseImage("Profile Picture"));
        profileImageView = new ImageView();
        profileImageView.setPreserveRatio(true);
        profileImageView.setFitHeight(100);
        profileImageView.setFitWidth(100);

        profileIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/image.png")));
        profileIcon.setFitHeight(30);
        profileIcon.setFitWidth(30);

        StackPane profileImageContainer = new StackPane();
        profileImageContainer.setPrefSize(100, 100);
        profileImageContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        profileImageContainer.getChildren().addAll(profileImageView, profileIcon);
        grid.add(chooseProfileImageButton, 4, 0);
        grid.add(profileImageContainer, 5, 0);

        Button chooseSignImageButton = new Button("Chữ kỹ");
        chooseSignImageButton.setOnAction(e -> chooseImage("Signature"));
        signImageView = new ImageView();
        signImageView.setPreserveRatio(true);
        signImageView.setFitHeight(100);
        signImageView.setFitWidth(100);

        signIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/image.png")));
        signIcon.setFitHeight(30);
        signIcon.setFitWidth(30);

        StackPane signImageContainer = new StackPane();
        signImageContainer.setPrefSize(100, 100);
        signImageContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        signImageContainer.getChildren().addAll(signImageView, signIcon);
        grid.add(chooseSignImageButton, 4, 1);
        grid.add(signImageContainer, 5, 1);

        // Bảng khách hàng
        tableView = new TableView<>();
        TableColumn<EntityKhachHang, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
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

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));

        container.getChildren().addAll(grid, searchField, tableView);
        this.getChildren().add(container);
        AnchorPane.setTopAnchor(container, 70.0);
        AnchorPane.setLeftAnchor(container, 20.0);

        this.setPrefWidth(800);
        this.setPrefHeight(600);
    }

    private void chooseImage(String imageType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();
            if (imageType.equals("Profile Picture")) {
                profileImageView.setImage(new Image(imagePath));
                profileIcon.setVisible(false);
            } else if (imageType.equals("Signature")) {
                signImageView.setImage(new Image(imagePath));
                signIcon.setVisible(false);
            }
        }
    }

    private void filterData(String query) {
        ObservableList<EntityKhachHang> filteredList = FXCollections.observableArrayList();
        for (EntityKhachHang customer : dataList) {
            if (customer.getFullname().toLowerCase().contains(query.toLowerCase()) ||
                    customer.getEmail().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(customer);
            }
        }
        tableView.setItems(filteredList);
    }
}
