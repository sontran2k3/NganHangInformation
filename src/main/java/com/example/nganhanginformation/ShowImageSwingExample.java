package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowImageSwingExample extends Application {

    private static final String SELECT_NAMES_SQL = "SELECT name FROM images";
    private static final String SELECT_IMAGE_SQL = "SELECT image FROM images WHERE name = ?";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TableView<ImageData> tableView = new TableView<>();
        ImageView imageView = new ImageView(); // Dùng để hiển thị ảnh

        // Cột tên ảnh
        TableColumn<ImageData, String> nameColumn = new TableColumn<>("Tên ảnh");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.getColumns().add(nameColumn);

        // Lấy dữ liệu từ cơ sở dữ liệu và thêm vào TableView
        List<ImageData> imageDataList = fetchImageDataFromDatabase();
        tableView.getItems().addAll(imageDataList);

        // Thêm sự kiện khi click vào bảng
        tableView.setOnMouseClicked(event -> {
            ImageData selectedItem = tableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Image image = fetchImageFromDatabase(selectedItem.getName());
                if (image != null) {
                    imageView.setImage(image);
                }
            }
        });

        // Đặt kích thước cho ImageView
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        // Bố cục
        StackPane root = new StackPane(tableView, imageView);
        StackPane.setAlignment(imageView, javafx.geometry.Pos.BOTTOM_CENTER);

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("Danh sách tên ảnh");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<ImageData> fetchImageDataFromDatabase() {
        List<ImageData> imageDataList = new ArrayList<>();

        // Thông tin kết nối cơ sở dữ liệu
        String jdbcURL = "jdbc:mysql://localhost:3306/nganhang";
        String dbUser = "root";
        String dbPassword = "1234";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             java.sql.Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_NAMES_SQL)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                imageDataList.add(new ImageData(name));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }

        return imageDataList;
    }

    private Image fetchImageFromDatabase(String name) {
        // Thông tin kết nối cơ sở dữ liệu
        String jdbcURL = "jdbc:mysql://localhost:3306/nganhang";
        String dbUser = "root";
        String dbPassword = "1234";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_IMAGE_SQL)) {

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("image");
                return new Image(new ByteArrayInputStream(imageData));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        return null;
    }

    public static class ImageData {
        private final String name;

        public ImageData(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
