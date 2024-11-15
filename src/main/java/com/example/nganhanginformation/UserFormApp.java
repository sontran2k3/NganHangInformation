package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;

public class UserFormApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 20px;");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.RIGHT);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHalignment(HPos.RIGHT);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(col1, col2, col3, col4);

        TextField fullNameField = new TextField();
        TextField cccdField = new TextField();
        DatePicker birthdayPicker = new DatePicker();
        TextField addressField = new TextField();
        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        TextField occupationField = new TextField();

        // Trường radio cho giới tính
        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleRadio = new RadioButton("Male");
        RadioButton femaleRadio = new RadioButton("Female");
        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(20); // 5 là khoảng cách giữa các RadioButton
        genderBox.getChildren().addAll(maleRadio, femaleRadio);

        // Thêm các trường vào GridPane
        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(fullNameField, 1, 0);

        grid.add(new Label("CCCD:"), 0, 1);
        grid.add(cccdField, 1, 1);

        grid.add(new Label("Birthday:"), 0, 2);
        grid.add(birthdayPicker, 1, 2);

        grid.add(new Label("Address:"), 0, 3);
        grid.add(addressField, 1, 3);

        // Cột 2
        grid.add(new Label("Phone:"), 2, 0);
        grid.add(phoneField, 3, 0);

        grid.add(new Label("Email:"), 2, 1);
        grid.add(emailField, 3, 1);

        grid.add(new Label("Occupation:"), 2, 2);
        grid.add(occupationField, 3, 2);

        grid.add(new Label("Gender:"), 2, 3);
        grid.add(genderBox, 3, 3); // Thêm HBox vào cột 3 và hàng 3

        // Tạo các ImageView để hiển thị ảnh chân dung và chữ ký
        ImageView profileImageView = new ImageView();
        profileImageView.setPreserveRatio(true);  // Giữ tỉ lệ hình ảnh
        profileImageView.setFitHeight(100);  // Đặt chiều cao ô vuông
        profileImageView.setFitWidth(100);   // Đặt chiều rộng ô vuông

        ImageView signImageView = new ImageView();
        signImageView.setPreserveRatio(true);
        signImageView.setFitHeight(100);
        signImageView.setFitWidth(100);

        // Tạo các ImageView cho biểu tượng chọn ảnh
        ImageView profileIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/image.png")));
        profileIcon.setFitHeight(30);
        profileIcon.setFitWidth(30);

        ImageView signIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/image.png")));
        signIcon.setFitHeight(30);
        signIcon.setFitWidth(30);

        // Thêm ô vuông để hiển thị ảnh chân dung và chữ ký với biểu tượng
        StackPane profileImageContainer = new StackPane();
        profileImageContainer.setPrefSize(100, 100);  // Đặt kích thước cố định cho ô vuông
        profileImageContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        profileImageContainer.getChildren().addAll(profileImageView, profileIcon);  // Chồng ảnh và icon

        StackPane signImageContainer = new StackPane();
        signImageContainer.setPrefSize(100, 100);  // Đặt kích thước cố định cho ô vuông
        signImageContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        signImageContainer.getChildren().addAll(signImageView, signIcon);  // Chồng ảnh và icon

        // Chọn hình ảnh cho ảnh chân dung và chữ ký
        Button chooseProfileImageButton = new Button("Choose Profile Picture");
        Button chooseSignImageButton = new Button("Choose Signature");

        // Đổi kiểu cho nút bấm
        chooseProfileImageButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px;");
        chooseSignImageButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 10px;");

        chooseProfileImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                // Hiển thị ảnh chân dung
                Image profileImage = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(profileImage);
                profileIcon.setVisible(false);  // Ẩn icon khi ảnh được chọn
            }
        });

        chooseSignImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                // Hiển thị chữ ký
                Image signImage = new Image(selectedFile.toURI().toString());
                signImageView.setImage(signImage);
                signIcon.setVisible(false);  // Ẩn icon khi ảnh được chọn
            }
        });


        // Thêm ImageView vào GridPane và các ô vuông vào bên phải các nút
        grid.add(chooseProfileImageButton, 0, 4);
        grid.add(chooseSignImageButton, 2, 4);
        grid.add(profileImageContainer, 1, 4);  // Hiển thị ảnh chân dung trong ô vuông
        grid.add(signImageContainer, 3, 4);     // Hiển thị chữ ký trong ô vuông

        // Tạo nút submit
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #ff5722; -fx-text-fill: white; -fx-padding: 12px;");
        submitButton.setOnAction(e -> {
            // Xử lý khi nhấn submit
        });
        grid.add(submitButton, 1, 5, 3, 1);

        // Thiết lập và hiển thị Scene
        Scene scene = new Scene(grid, 700, 500);
        primaryStage.setTitle("User Information Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
