package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.Optional;

public class NhanSu extends Application {

    private AnchorPane mainContent;
    private Stage primaryStage;

    // Khai báo các pane cho các chức năng
    private BankOfInformationUI logo;
    private KhachHangPane homePane;
    private AccountPane infoPane;
    private ChuyenKhoanPane chuyenkhoanPane;
    private NapTienPane naptienPane;
    private RutTienPane ruttienPane;
    private ManagePane managePane;
    private ThongKePane settingsPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Quản lý thông tin ngân hàng");
        primaryStage.show();

        VBox logoContainer = new VBox(15);
        logoContainer.setPrefWidth(200.0);
        logoContainer.setAlignment(Pos.TOP_CENTER);
        logoContainer.getStyleClass().add("nhansu");

        Image logoImage = new Image(getClass().getResource("/com/example/nganhanginformation/Image/logonhansu.png").toExternalForm());
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        Line lineBelowLogo = new Line(0, 0, 200, 0);
        lineBelowLogo.setStrokeWidth(1);
        lineBelowLogo.setStyle("-fx-stroke: #ffffff;");

        Button btnHome = createStyledButton("Quản lý khách hàng", "/com/example/nganhanginformation/Image/home.png");
        Button btnInfo = createStyledButton("Quản lý tài khoản", "/com/example/nganhanginformation/Image/user.png");
        Button btnManage = createStyledButton("Giao dịch tài chính", "/com/example/nganhanginformation/Image/management.png");
        Button btnSettings = createStyledButton("Báo cáo, Thống kê", "/com/example/nganhanginformation/Image/settings.png");
        Button btnLogout = createStyledButton("Đăng xuất", "/com/example/nganhanginformation/Image/logout.png");

        btnLogout.setOnAction(e -> handleLogout());

        Line separator = new Line(0, 0, 200, 0);
        separator.setStrokeWidth(1);
        separator.setStyle("-fx-stroke: #ffffff;");

        StackPane outerFrame = new StackPane();
        outerFrame.getStyleClass().add("outer-frame");
        outerFrame.getChildren().add(logoContainer);

        logoContainer.getChildren().addAll(logoView, lineBelowLogo, btnHome, btnInfo, createTransactionMenu(btnManage), btnSettings);

        Region spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        logoContainer.getChildren().addAll(spacer, separator, btnLogout);

        mainContent = new AnchorPane();
        AnchorPane.setTopAnchor(mainContent, 20.0);
        AnchorPane.setLeftAnchor(mainContent, 250.0);

        logo = new BankOfInformationUI(scene);
        homePane = new KhachHangPane(scene);
        infoPane = new AccountPane(scene);
        chuyenkhoanPane = new ChuyenKhoanPane(scene);
        naptienPane = new NapTienPane(scene);
        ruttienPane = new RutTienPane(scene);
        managePane = new ManagePane(scene);
        settingsPane = new ThongKePane();

        mainContent.getChildren().addAll(logo, homePane, infoPane, chuyenkhoanPane, naptienPane, ruttienPane, managePane, settingsPane);

        // Thiết lập sự kiện cho các nút

        logoView.setOnMouseClicked(e -> {
            // Mở trang BankOfInformationUI
            BankOfInformationUI bankUI = new BankOfInformationUI(scene);
            try {
                switchPane(logo);  // Khởi tạo và mở BankOfInformationUI
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        btnHome.setOnAction(e -> switchPane(homePane));
        btnInfo.setOnAction(e -> switchPane(infoPane));
        btnManage.setOnAction(e -> switchPane(managePane));
        btnSettings.setOnAction(e -> switchPane(settingsPane));

        anchorPane.getChildren().addAll(outerFrame, mainContent);
        AnchorPane.setTopAnchor(outerFrame, 0.0);
        AnchorPane.setLeftAnchor(outerFrame, 0.0);
        AnchorPane.setBottomAnchor(outerFrame, 0.0);

        switchPane(logo);

        scene.getStylesheets().add(getClass().getResource("Styles/nhansu.css").toExternalForm());
    }

    private TitledPane createTransactionMenu(Button btnManage) {
        VBox transactionMenu = new VBox(10);
        transactionMenu.setStyle("-fx-padding: 10;");

        Button btnChuyenKhoan = createStyledButton("Chuyển khoản", "/com/example/nganhanginformation/Image/transfer.png");
        Button btnNapTien = createStyledButton("Nạp tiền", "/com/example/nganhanginformation/Image/Deposit.png");
        Button btnRutTien = createStyledButton("Rút tiền", "/com/example/nganhanginformation/Image/Withdraw.png");

        // Khởi tạo sự kiện cho các button giao dịch
        btnChuyenKhoan.setOnAction(e -> switchPane(chuyenkhoanPane));
        btnNapTien.setOnAction(e -> switchPane(naptienPane));
        btnRutTien.setOnAction(e -> switchPane(ruttienPane));

        transactionMenu.getChildren().addAll(btnChuyenKhoan, btnNapTien, btnRutTien);

        TitledPane titledPane = new TitledPane("Giao dịch tài chính", transactionMenu);
        titledPane.setExpanded(false);
        titledPane.setTextFill(Color.WHITE);
        titledPane.setId("titled-pane");

        btnManage.setOnAction(e -> titledPane.setExpanded(!titledPane.isExpanded()));

        return titledPane;
    }

    private void switchPane(AnchorPane paneToShow) {
        for (Node node : mainContent.getChildren()) {
            node.setVisible(false);
        }
        paneToShow.setVisible(true);
    }

    private void handleLogout() {
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
