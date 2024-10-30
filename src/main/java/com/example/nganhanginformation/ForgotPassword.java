package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ForgotPassword extends Application {

    @Override
    public void start(Stage primaryStage) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(500.0);
        anchorPane.setPrefWidth(600.0);
        anchorPane.getStyleClass().add("login_container");

        VBox logoContainer = new VBox();
        logoContainer.setPrefWidth(250.0);
        logoContainer.setPrefHeight(500.0);
        logoContainer.setAlignment(Pos.CENTER);
        logoContainer.getStyleClass().add("login_logo_container");

        Image logoImage = new Image(getClass().getResource("/com/example/nganhanginformation/Image/logo.png").toExternalForm());
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(200.0);
        logoImageView.setFitWidth(200.0);
        logoImageView.setPreserveRatio(true);
        logoContainer.getChildren().add(logoImageView);

        GridPane formContainer = new GridPane();
        formContainer.setLayoutX(270);
        formContainer.setLayoutY(10);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setHgap(10);
        formContainer.setVgap(10);
        formContainer.setPadding(new Insets(20));

        // Tiêu đề cho giao diện
        Label titleLabel = new Label("Khôi Phục Mật Khẩu");
        titleLabel.setFont(Font.font("Arial", 24));
        titleLabel.setStyle("-fx-text-fill: #2980B9;");
        titleLabel.setPadding(new Insets(10, 0, 20, 0));
        formContainer.add(titleLabel, 0, 0);

        // Tạo HBox cho Tên đăng nhập
        HBox usernameContainer = createInputContainer("Tên đăng nhập:", "/com/example/nganhanginformation/Image/user.png");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Tên Tài Khoản");
        usernameField.getStyleClass().add("custom-textfield");

        // Tạo HBox cho Email
        HBox emailContainer = createInputContainer("Email:", "/com/example/nganhanginformation/Image/gmail.png");
        TextField emailField = new TextField();
        emailField.setPromptText("Email của bạn");
        emailField.getStyleClass().add("custom-textfield");

        // Tạo HBox cho Số điện thoại
        HBox phoneContainer = createInputContainer("Số điện thoại:", "/com/example/nganhanginformation/Image/phone.png");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Số điện thoại của bạn");
        phoneField.getStyleClass().add("custom-textfield");

        // Thêm thông báo cho người dùng
        Label feedbackLabel = new Label();
        feedbackLabel.setFont(Font.font("Arial", 12));
        feedbackLabel.setStyle("-fx-text-fill: #E74C3C;");

        // Thêm nút gửi yêu cầu khôi phục
        Label submitLabel = new Label("Gửi yêu cầu khôi phục");
        submitLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #1ABC9C; -fx-padding: 12px; -fx-background-radius: 10px; -fx-font-weight: bold;");
        submitLabel.setOnMouseClicked(event -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                feedbackLabel.setText("Vui lòng điền đầy đủ thông tin.");
            } else {
                feedbackLabel.setText("Yêu cầu khôi phục mật khẩu đã được gửi!");
            }
        });

        // Tạo "Quay lại" mà không sử dụng button
        Text backText = new Text("Quay lại");
        backText.setFont(Font.font("Arial", 14));
        backText.setStyle("-fx-fill: #2980B9; -fx-underline: true; -fx-cursor: hand;"); // Để nó trông giống như một liên kết
        backText.setOnMouseClicked(event -> {
            // Quay lại giao diện đăng nhập
            Login login = new Login();
            login.start(primaryStage);
        });

        // Thêm HBox và TextField vào formContainer
        formContainer.add(usernameContainer, 0, 1);
        formContainer.add(usernameField, 0, 2);
        formContainer.add(emailContainer, 0, 3);
        formContainer.add(emailField, 0, 4);
        formContainer.add(phoneContainer, 0, 5);
        formContainer.add(phoneField, 0, 6);
        formContainer.add(submitLabel, 0, 7);
        formContainer.add(feedbackLabel, 0, 8); // Thêm feedbackLabel vào formContainer
        formContainer.add(backText, 0, 9); // Thêm backText vào formContainer
        anchorPane.getChildren().addAll(logoContainer, formContainer);

        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add(getClass().getResource("Styles/Loginaccount.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Khôi Phục Mật Khẩu");
        primaryStage.show();
    }

    private HBox createInputContainer(String labelText, String iconPath) {
        HBox container = new HBox();
        ImageView icon = new ImageView(new Image(getClass().getResource(iconPath).toExternalForm()));
        icon.setFitHeight(20);
        icon.setFitWidth(20);
        Label label = new Label(labelText);
        label.setFont(Font.font("Arial", 14));
        label.setStyle("-fx-text-fill: #2C3E50;");
        container.getChildren().addAll(icon, label);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(10);
        return container;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
