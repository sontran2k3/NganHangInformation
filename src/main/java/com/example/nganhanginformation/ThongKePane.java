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

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn.  Báo cáo thống kê");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        this.getChildren().add(welcomeLabel);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        HBox topPanel = new HBox(20);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER);

        String accountCount = String.valueOf(getMonthlyStatistics("account"));
        String transactionCount = String.valueOf(getMonthlyStatistics("transaction"));
        String revenue = String.format("%.2f", getMonthlyStatistics("revenue"));

        Label totalAccounts = createInfoLabel(accountCount, "Số tài khoản trong tháng", Color.web("#4a90e2"));
        Label totalTransactions = createInfoLabel(transactionCount, "Số giao dịch trong tháng", Color.web("#50e3c2"));
        Label totalRevenue = createInfoLabel(revenue + " VND", "Doanh thu trong tháng", Color.web("#b0b0b0"));

        topPanel.getChildren().addAll(totalAccounts, totalRevenue, totalTransactions);

        BarChart<String, Number> revenueChart = createBarChart("Doanh thu theo tháng");

        Region separator = new Region();
        separator.setStyle("-fx-background-color: #ccc; -fx-min-height: 1px; -fx-pref-height: 1px;");

        VBox dataContainer = new VBox(20, topPanel, separator, revenueChart);
        dataContainer.setPadding(new Insets(20));
        dataContainer.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-width: 2px; -fx-border-radius: 15px;");

        AnchorPane.setTopAnchor(dataContainer, 80.0);

        VBox mainLayout = new VBox(20, welcomeLabel, dataContainer);

        CalendarBoxApp calendarBoxApp = new CalendarBoxApp();
        VBox calendarBox = calendarBoxApp.createCalendarBox();


        calendarBox.setMaxHeight(350);

        calendarBox.getStyleClass().add("calendar-box-container");

        dataContainer.heightProperty().addListener((observable, oldValue, newValue) -> {
            double dataContainerHeight = dataContainer.getHeight();
            calendarBox.setPrefHeight(dataContainerHeight);
        });

        HBox layoutWithCalendar = new HBox(20, mainLayout, calendarBox);
        layoutWithCalendar.setPadding(new Insets(20));
        layoutWithCalendar.setAlignment(Pos.TOP_LEFT);

        VBox calendarBoxContainer = new VBox();
        calendarBoxContainer.getChildren().add(calendarBox);
        calendarBoxContainer.setPadding(new Insets(35, 0, 0, 0));

        layoutWithCalendar.getChildren().add(calendarBoxContainer);

        this.getChildren().addAll(layoutWithCalendar);
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

        numberLabel.setStyle("-fx-font-size: 20px;");

        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("info-text");

        textLabel.setStyle("-fx-font-size: 16px;");

        VBox box = new VBox(10, numberLabel, textLabel);
        box.setStyle("-fx-background-color: " + toHex(backgroundColor) + "; -fx-padding: 20; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.getStyleClass().add("info-box");

        box.setPrefWidth(250);
        box.setPrefHeight(75);

        return new Label("", box);
    }

    private BarChart<String, Number> createBarChart(String title) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        xAxis.setLabel("Tháng");
        yAxis.setLabel("Doanh thu (VND)");

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

        barChart.setPrefWidth(800);
        barChart.setPrefHeight(400);

        return barChart;
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}
