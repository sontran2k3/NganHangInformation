package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Optional;

public class Admin extends Application {

    private AnchorPane mainContent;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Quản lý ngân hang, Nhân viên");
        primaryStage.show();

        VBox logoContainer = new VBox(15);
        logoContainer.setPrefWidth(200.0);
        logoContainer.setAlignment(Pos.TOP_CENTER);
        logoContainer.getStyleClass().add("nhansu");

        Image logoImage = new Image(getClass().getResource("/com/example/nganhanginformation/Image/logonhansu.png").toExternalForm());
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        // Create a line below the logo
        Line lineBelowLogo = new Line(0, 0, 200, 0);
        lineBelowLogo.setStrokeWidth(1);
        lineBelowLogo.setStyle("-fx-stroke: gray;");

        Button btnHome = createStyledButton("Trang chủ", "/com/example/nganhanginformation/Image/home.png");
        Button btnInfo = createStyledButton("Người dùng", "/com/example/nganhanginformation/Image/user.png");
        Button btnManage = createStyledButton("Quản lý", "/com/example/nganhanginformation/Image/management.png");
        Button btnSettings = createStyledButton("Cài đặt", "/com/example/nganhanginformation/Image/settings.png");

        Button btnLogout = createStyledButton("Đăng xuất", "/com/example/nganhanginformation/Image/logout.png");
        btnLogout.setOnAction(e -> handleLogout());

        Line separator = new Line(0, 0, 200, 0);
        separator.setStrokeWidth(1);
        separator.setStyle("-fx-stroke: gray;");

        StackPane outerFrame = new StackPane();
        outerFrame.getStyleClass().add("outer-frame");
        outerFrame.getChildren().add(logoContainer);

        logoContainer.getChildren().addAll(logoView, lineBelowLogo, btnHome, btnInfo, btnManage, btnSettings);

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        logoContainer.getChildren().addAll(spacer, separator, btnLogout);

        mainContent = new AnchorPane();
        AnchorPane.setTopAnchor(mainContent, 20.0);
        AnchorPane.setLeftAnchor(mainContent, 250.0);

        HomePane homePane = new HomePane();
        InfoPane infoPane = new InfoPane(scene);
        ManagePane managePane = new ManagePane();
        SettingsPane settingsPane = new SettingsPane();

        mainContent.getChildren().addAll(homePane, infoPane, managePane, settingsPane);

        btnHome.setOnAction(e -> switchPane(homePane));
        btnInfo.setOnAction(e -> switchPane(infoPane));
        btnManage.setOnAction(e -> switchPane(managePane));
        btnSettings.setOnAction(e -> switchPane(settingsPane));

        anchorPane.getChildren().addAll(outerFrame, mainContent);
        AnchorPane.setTopAnchor(outerFrame, 0.0);
        AnchorPane.setLeftAnchor(outerFrame, 0.0);
        AnchorPane.setBottomAnchor(outerFrame, 0.0);

        switchPane(homePane);

        scene.getStylesheets().add(getClass().getResource("Styles/nhansu.css").toExternalForm());
    }

    private void switchPane(AnchorPane paneToShow) {
        for (Node node : mainContent.getChildren()) {
            node.setVisible(false);
        }
        paneToShow.setVisible(true);
    }

    private void handleLogout() {
        // Tạo một hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có muốn đăng xuất không?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Login loginScreen = new Login();
            try {
                loginScreen.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Button createStyledButton(String text, String iconPath) {
        Button button = new Button(text);
        button.getStyleClass().add("styled-button");

        Image icon = new Image(getClass().getResource(iconPath).toExternalForm());
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);
        button.setGraphic(iconView);
        button.setContentDisplay(ContentDisplay.LEFT);
        button.setAlignment(Pos.CENTER_LEFT);

        return button;
    }
}
