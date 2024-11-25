package com.example.nganhanginformation;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BankOfInformationUI extends AnchorPane {

    public BankOfInformationUI(Scene scene) {

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn");
        welcomeLabel.setFont(Font.font("Arial", 40));
        welcomeLabel.setStyle("-fx-font-weight: bold;");

        ImageView logoView = new ImageView(new Image(getClass().getResource("/com/example/nganhanginformation/Image/img.png").toExternalForm()));
        logoView.setFitWidth(200);
        logoView.setFitHeight(200);

        Line goldenLine = new Line(0, 0, 0, 140);
        goldenLine.setStroke(Color.BLACK);
        goldenLine.setStrokeWidth(4);

        Text bankText = new Text("BANK OF");
        bankText.setFont(Font.font("Arial", 48));
        bankText.getStyleClass().add("text-animation");

        Text infoText = new Text("INFORMATION");
        infoText.setFont(Font.font("Arial", 48));
        infoText.getStyleClass().add("text-animation");

        VBox textBox = new VBox(-5, bankText, infoText);
        textBox.setAlignment(Pos.CENTER_LEFT);

        HBox root = new HBox(20, logoView, goldenLine, textBox);
        root.setAlignment(Pos.CENTER);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);
        container.setStyle("-fx-background-color: #ffffff; -fx-padding: 20;");

        container.getChildren().add(root);

        StackPane background = new StackPane();
        background.setStyle("-fx-background-color: #ffffff;");

        AnchorPane overlay = new AnchorPane();
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);
        overlay.getChildren().add(welcomeLabel);

        background.getChildren().addAll(container, overlay);
        background.setAlignment(Pos.CENTER);

        background.prefWidthProperty().bind(scene.widthProperty().multiply(0.9));
        background.prefHeightProperty().bind(scene.heightProperty().multiply(0.9));

        this.getChildren().add(background);
    }
}
