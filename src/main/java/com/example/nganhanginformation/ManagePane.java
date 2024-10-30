package com.example.nganhanginformation;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ManagePane extends AnchorPane {

    public ManagePane() {
        // Nhãn tiêu đề chính
        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn. ManagePane");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        this.getChildren().add(welcomeLabel);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        VBox pieChartContainer = new VBox();
        pieChartContainer.setSpacing(5);

        this.getChildren().add(pieChartContainer);
    }
}
