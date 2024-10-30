package com.example.nganhanginformation;

import BusinessLogicLayer.BLLLogin;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

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
        formContainer.setLayoutY(120);
        formContainer.setAlignment(Pos.CENTER);
        formContainer.setHgap(10);
        formContainer.setVgap(10);
        formContainer.setPadding(new Insets(20));

        HBox usernameContainer = new HBox();
        ImageView usernameIcon = new ImageView(new Image(getClass().getResource("/com/example/nganhanginformation/Image/user.png").toExternalForm()));
        usernameIcon.setFitHeight(20);
        usernameIcon.setFitWidth(20);
        Label usernameLabel = new Label("Tên đăng nhập:");
        usernameLabel.setFont(Font.font("Arial", 14));
        usernameLabel.setStyle("-fx-text-fill: #2C3E50;");
        usernameContainer.getChildren().addAll(usernameIcon, usernameLabel);
        usernameContainer.setAlignment(Pos.CENTER_LEFT);
        usernameContainer.setSpacing(10);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Tên Tài Khoản");
        usernameField.getStyleClass().add("custom-textfield");

        HBox passwordContainer = new HBox();
        ImageView passwordIcon = new ImageView(new Image(getClass().getResource("/com/example/nganhanginformation/Image/password.png").toExternalForm()));
        passwordIcon.setFitHeight(20);
        passwordIcon.setFitWidth(20);
        Label passwordLabel = new Label("Mật khẩu:");
        passwordLabel.setFont(Font.font("Arial", 14));
        passwordLabel.setStyle("-fx-text-fill: #2C3E50;");
        passwordContainer.getChildren().addAll(passwordIcon, passwordLabel);
        passwordContainer.setAlignment(Pos.CENTER_LEFT);
        passwordContainer.setSpacing(10);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mật Khẩu");
        passwordField.getStyleClass().add("custom-textfield");

        Button loginButton = new Button("Đăng Nhập");
        loginButton.setPrefWidth(250);
        loginButton.getStyleClass().add("custom-button");

        // Xử lý sự kiện đăng nhập
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            BLLLogin bllLogin = new BLLLogin();
            String role = bllLogin.validateLogin(username, password);

            if (role != null) {
                try {
                    if (role.equals("nhanvien")) {
                        NhanSu nhanSuScreen = new NhanSu();
                        Stage newStage = new Stage();
                        nhanSuScreen.start(newStage);
                        primaryStage.close();
                    } else if (role.equals("admin")) {
                        Admin adminScreen = new Admin();
                        Stage newStage = new Stage();
                        adminScreen.start(newStage);
                        primaryStage.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Tên đăng nhập hoặc mật khẩu không đúng");
            }
        });

        Label forgotPasswordLabel = new Label("Quên mật khẩu?");
        forgotPasswordLabel.setFont(Font.font("Arial", 13));
        forgotPasswordLabel.setStyle("-fx-text-fill: #2980B9; -fx-underline: true;");

        forgotPasswordLabel.setOnMouseClicked(event -> {
            ForgotPassword forgotPasswordScreen = new ForgotPassword();
            try {
                forgotPasswordScreen.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        formContainer.add(usernameContainer, 0, 0);
        formContainer.add(usernameField, 0, 1);
        formContainer.add(passwordContainer, 0, 2);
        formContainer.add(passwordField, 0, 3);
        formContainer.add(loginButton, 0, 4);
        formContainer.add(forgotPasswordLabel, 0, 5);

        anchorPane.getChildren().addAll(logoContainer, formContainer);

        Scene scene = new Scene(anchorPane);
        scene.getStylesheets().add(getClass().getResource("Styles/Loginaccount.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ngân Hàng App");
        primaryStage.show();
    }
}
