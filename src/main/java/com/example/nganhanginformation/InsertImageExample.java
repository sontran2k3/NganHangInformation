package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class InsertImageExample extends Application {

    private static final String INSERT_IMAGE_SQL = "INSERT INTO images (name, image) VALUES (?, ?)";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Tạo nút để mở FileChooser chọn ảnh
        Button btnChooseImage = new Button("Chọn ảnh");
        btnChooseImage.setOnAction(e -> chooseImage(primaryStage));

        // Layout
        StackPane root = new StackPane();
        root.getChildren().add(btnChooseImage);

        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Chọn ảnh để lưu vào CSDL");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void chooseImage(Stage stage) {
        // Khởi tạo FileChooser để chọn ảnh
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            String imageName = selectedFile.getName(); // Lấy tên file ảnh

            insertImageToDatabase(imagePath, imageName);
        } else {
            showAlert("Lỗi", "Không có file nào được chọn.", AlertType.ERROR);
        }
    }

    private void insertImageToDatabase(String imagePath, String imageName) {
        // Thông tin kết nối cơ sở dữ liệu
        String jdbcURL = "jdbc:mysql://localhost:3306/nganhang"; // Tên CSDL: nganhang
        String dbUser = "root";
        String dbPassword = "1234";

        // Kiểm tra file ảnh
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            System.err.println("File không tồn tại: " + imagePath);
            return;
        }

        // Đọc file ảnh vào mảng byte
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(imageFile.toPath());
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
            showAlert("Lỗi", "Không thể đọc file ảnh.", AlertType.ERROR);
            return;
        }

        // Kết nối và chèn ảnh vào cơ sở dữ liệu
        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_IMAGE_SQL)) {

            // Thiết lập tham số cho câu lệnh SQL
            preparedStatement.setString(1, imageName);
            preparedStatement.setBytes(2, imageBytes);

            // Thực thi câu lệnh SQL
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " dòng đã được chèn thành công.");
            showAlert("Thông báo", "Ảnh đã được lưu vào cơ sở dữ liệu.", AlertType.INFORMATION);

        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            showAlert("Lỗi", "Không thể kết nối cơ sở dữ liệu.", AlertType.ERROR);
        }
    }

    // Hàm hiển thị thông báo
    private void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
