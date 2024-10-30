package com.example.nganhanginformation;

import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SettingsPane extends AnchorPane {
    private boolean isDarkMode = false;

    public SettingsPane() {
        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn. Dang Tesst");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");


        ToggleButton toggleButton = new ToggleButton("Chế độ tối");
        toggleButton.setOnAction(e -> toggleDarkMode());

        this.getChildren().addAll(welcomeLabel, toggleButton);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);
        AnchorPane.setTopAnchor(toggleButton, 60.0);
        AnchorPane.setLeftAnchor(toggleButton, 20.0);

        VBox pieChartContainer = new VBox();
        pieChartContainer.setSpacing(5);
        this.getChildren().add(pieChartContainer);
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        if (isDarkMode) {
            this.getScene().getRoot().setStyle("-fx-background-color: #333; -fx-text-fill: white;");
        } else {
            this.getScene().getRoot().setStyle("-fx-background-color: white; -fx-text-fill: black;");
        }
    }
}
