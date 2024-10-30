package com.example.nganhanginformation;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.chart.PieChart;

public class HomePane extends AnchorPane {

    public HomePane() {
        // Nhãn tiêu đề chính
        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");


        this.getChildren().add(welcomeLabel);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        VBox pieChartContainer = new VBox();
        pieChartContainer.setSpacing(5);


        PieChart pieChart = createPieChart();
        pieChart.setPrefSize(350, 350);

        pieChartContainer.getChildren().addAll(pieChart);

        AnchorPane.setTopAnchor(pieChartContainer, 80.0); // Dưới tiêu đề
        AnchorPane.setLeftAnchor(pieChartContainer, 20.0); // Bên trái

        // Thêm container vào HomePane
        this.getChildren().add(pieChartContainer);
    }

    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();

        PieChart.Data slice1 = new PieChart.Data("Phần A", 30);
        PieChart.Data slice2 = new PieChart.Data("Phần B", 25);
        PieChart.Data slice3 = new PieChart.Data("Phần C", 20);
        PieChart.Data slice4 = new PieChart.Data("Phần D", 15);
        PieChart.Data slice5 = new PieChart.Data("Phần E", 10);

        pieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5);
        pieChart.setTitle("Doanh Thu");

        return pieChart;
    }
}
