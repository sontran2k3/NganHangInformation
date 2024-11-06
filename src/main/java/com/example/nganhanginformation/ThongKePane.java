package com.example.nganhanginformation;

import BusinessLogicLayer.BLLThongKe;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Map;

public class ThongKePane extends AnchorPane {
    private BLLThongKe bllThongKe;

    public ThongKePane() {
        bllThongKe = new BLLThongKe();

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn. Báo cáo thống kê");
        welcomeLabel.getStyleClass().add("welcome-label");
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        HBox topPanel = new HBox(20);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER);

        String accountCount = String.valueOf(getMonthlyStatistics("account"));
        String transactionCount = String.valueOf(getMonthlyStatistics("transaction"));
        String revenue = String.format("%.2f", getMonthlyStatistics("revenue"));

        Label totalAccounts = createInfoLabel(accountCount, "Tài khoản/Tháng", Color.web("#4a90e2"));
        Label totalTransactions = createInfoLabel(transactionCount, "Giao dịch/Tháng", Color.web("#50e3c2"));
        Label totalRevenue = createInfoLabel(revenue, "Doanh Thu/Tháng", Color.web("#f5a623"));

        topPanel.getChildren().addAll(totalAccounts, totalTransactions, totalRevenue);

        BarChart<String, Number> totalEnrolledChart = createBarChart("Doanh thu trong năm");
        LineChart<String, Number> femaleEnrolledChart = createLineChart("Enrolled Female Chart", Color.DEEPPINK);
        LineChart<String, Number> maleEnrolledChart = createLineChart("Enrolled Male Chart", Color.DODGERBLUE);

        totalEnrolledChart.getStyleClass().add("chart");
        femaleEnrolledChart.getStyleClass().add("chart");
        maleEnrolledChart.getStyleClass().add("chart");

        GridPane chartGrid = new GridPane();
        chartGrid.getStyleClass().add("chart-grid");
        chartGrid.add(totalEnrolledChart, 0, 0, 1, 2);
        chartGrid.add(femaleEnrolledChart, 1, 0);
        chartGrid.add(maleEnrolledChart, 1, 1);
        BarChart<String, Number> revenueChart = createBarChart("");
        chartGrid.add(revenueChart, 0, 0, 1, 2);


        VBox mainLayout = new VBox(20, welcomeLabel, topPanel, chartGrid);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_LEFT);

        this.getChildren().add(mainLayout);

        this.getStylesheets().add(getClass().getResource("Styles/thongke.css").toExternalForm());
    }

    private double getMonthlyStatistics(String type) {
        try {
            switch (type) {
                case "account":
                    return bllThongKe.getTotalAccountsThisMonth();
                case "transaction":
                    return bllThongKe.getTotalTransactionsThisMonth();
                case "revenue":
                    return bllThongKe.getTotalRevenueThisMonth();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Label createInfoLabel(String number, String text, Color backgroundColor) {
        Label numberLabel = new Label(number);
        numberLabel.getStyleClass().add("info-label");

        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("info-text");

        VBox box = new VBox(5, numberLabel, textLabel);
        box.setStyle("-fx-background-color: " + toHex(backgroundColor) + "; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        box.getStyleClass().add("info-box");

        return new Label("", box);
    }

    private BarChart<String, Number> createBarChart(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        xAxis.setLabel("Năm");
        yAxis.setLabel("Doanh thu ");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Doanh thu");

        try {
            Map<String, Double> monthlyRevenue = bllThongKe.getMonthlyRevenue();
            for (Map.Entry<String, Double> entry : monthlyRevenue.entrySet()) {
                dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(dataSeries);
        barChart.setLegendVisible(true);
        barChart.setCategoryGap(10);

        return barChart;
    }

    private LineChart<String, Number> createLineChart(String title, Color lineColor) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle(title);
        xAxis.setLabel("Date");
        yAxis.setLabel("Doanh thu trong năm");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.getData().add(new XYChart.Data<>("2023-01-07", 2));
        dataSeries.getData().add(new XYChart.Data<>("2023-02-10", 3));
        dataSeries.getData().add(new XYChart.Data<>("2023-03-15", 4));
        dataSeries.getData().add(new XYChart.Data<>("2023-04-20", 5));

        lineChart.getData().add(dataSeries);
        lineChart.setLegendVisible(false);

        for (XYChart.Series<String, Number> series : lineChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                data.getNode().setStyle("-fx-background-color: " + toHex(lineColor) + ";");
            }
        }

        return lineChart;
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
