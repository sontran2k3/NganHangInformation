package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StudentEnrollmentDashboard extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Tạo panel trên cùng với các nhãn
        HBox topPanel = new HBox(20);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER);

        Label totalStudents = createInfoLabel("9", "Total Enrolled Students", Color.web("#4a90e2"));
        Label femaleStudents = createInfoLabel("5", "Enrolled Female Students", Color.web("#50e3c2"));
        Label maleStudents = createInfoLabel("4", "Enrolled Male Students", Color.web("#f5a623"));

        topPanel.getChildren().addAll(totalStudents, femaleStudents, maleStudents);

        // Tạo biểu đồ
        BarChart<String, Number> totalEnrolledChart = createBarChart("Total Enrolled Chart");
        LineChart<String, Number> femaleEnrolledChart = createLineChart("Enrolled Female Chart", Color.DEEPPINK);
        LineChart<String, Number> maleEnrolledChart = createLineChart("Enrolled Male Chart", Color.DODGERBLUE);

        // Tổ chức biểu đồ trong lưới
        GridPane chartGrid = new GridPane();
        chartGrid.setPadding(new Insets(10));
        chartGrid.setHgap(10);
        chartGrid.setVgap(10);

        chartGrid.add(totalEnrolledChart, 0, 0, 1, 2); // Đặt biểu đồ thanh chiếm hai hàng
        chartGrid.add(femaleEnrolledChart, 1, 0);
        chartGrid.add(maleEnrolledChart, 1, 1);

        // Bố cục chính
        VBox mainLayout = new VBox(20, topPanel, chartGrid);
        mainLayout.setPadding(new Insets(20));

        Scene scene = new Scene(mainLayout, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Enrollment Dashboard");
        primaryStage.show();
    }

    private Label createInfoLabel(String number, String text, Color backgroundColor) {
        Label numberLabel = new Label(number);
        numberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        numberLabel.setTextFill(Color.WHITE);

        Label textLabel = new Label(text);
        textLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        textLabel.setTextFill(Color.WHITE);

        VBox box = new VBox(5, numberLabel, textLabel);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);
        box.setBackground(new Background(new BackgroundFill(backgroundColor, new CornerRadii(8), Insets.EMPTY)));
        box.setPrefSize(250, 80);

        return new Label("", box);
    }

    private BarChart<String, Number> createBarChart(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        barChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Enrollments");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<>("2023-01-07", 9));
        dataSeries.getData().add(new XYChart.Data<>("2023-02-10", 4));
        dataSeries.getData().add(new XYChart.Data<>("2023-03-15", 7));
        dataSeries.getData().add(new XYChart.Data<>("2023-04-20", 6));

        barChart.getData().add(dataSeries);
        barChart.setLegendVisible(false);
        barChart.setCategoryGap(10);

        return barChart;
    }

    private LineChart<String, Number> createLineChart(String title, Color lineColor) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Enrollments");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<>("2023-01-07", 2));
        dataSeries.getData().add(new XYChart.Data<>("2023-02-10", 3));
        dataSeries.getData().add(new XYChart.Data<>("2023-03-15", 4));
        dataSeries.getData().add(new XYChart.Data<>("2023-04-20", 5));

        lineChart.getData().add(dataSeries);
        lineChart.setLegendVisible(false);

        // Đặt màu đường kẻ
        for (XYChart.Series<String, Number> series : lineChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                data.getNode().setStyle("-fx-background-color: " + toHex(lineColor) + ";");
            }
        }

        return lineChart;
    }

    // Chuyển đổi Color sang mã Hex
    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
