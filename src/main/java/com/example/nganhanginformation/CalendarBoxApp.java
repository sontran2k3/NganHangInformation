package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class CalendarBoxApp extends Application {

    private LocalDate selectedDate = LocalDate.now();
    private Label monthLabel;

    @Override
    public void start(Stage primaryStage) {
        VBox calendarBox = createCalendarBox();

        Scene scene = new Scene(calendarBox, 250, 350);
        scene.getStylesheets().add(getClass().getResource("Styles/styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("CalendarBox");
        primaryStage.show();
    }

    VBox createCalendarBox() {
        Label yearLabel = new Label(String.valueOf(selectedDate.getYear()));
        Label dateLabel = new Label(formatDate(selectedDate));
        yearLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        dateLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        VBox headerBox = new VBox(yearLabel, dateLabel);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10, 0, 5, 0));

        Button prevButton = new Button("<");
        Button nextButton = new Button(">");
        prevButton.setOnAction(e -> changeMonth(-1));
        nextButton.setOnAction(e -> changeMonth(1));

        monthLabel = new Label(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + selectedDate.getYear());
        HBox monthNavigation = new HBox(10, prevButton, monthLabel, nextButton);
        monthNavigation.setAlignment(Pos.CENTER);

        GridPane daysGrid = createDaysGrid();

        VBox calendarBox = new VBox(10, headerBox, monthNavigation, daysGrid);
        calendarBox.setAlignment(Pos.TOP_CENTER);
        calendarBox.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-radius: 10; -fx-effect: dropshadow(three-pass-box, gray, 10, 0, 0, 2); -fx-padding: 15;");
        return calendarBox;
    }

    private GridPane createDaysGrid() {
        GridPane daysGrid = new GridPane();
        daysGrid.setVgap(5);
        daysGrid.setHgap(5);
        daysGrid.setPadding(new Insets(10));

        String[] dayNames = {"S", "M", "T", "W", "T", "F", "S"};
        for (int i = 0; i < dayNames.length; i++) {
            Label dayNameLabel = new Label(dayNames[i]);
            dayNameLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-alignment: center;");
            dayNameLabel.setMinSize(30, 30);
            daysGrid.add(dayNameLabel, i, 0);
        }

        LocalDate firstDayOfMonth = selectedDate.withDayOfMonth(1);
        int daysInMonth = selectedDate.lengthOfMonth();
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

        int col = firstDayOfWeek.getValue() % 7;
        int row = 1;

        for (int day = 1; day <= daysInMonth; day++) {
            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-font-size: 14;");
            dayLabel.setMinSize(30, 30);
            dayLabel.setAlignment(Pos.CENTER);

            if (day == selectedDate.getDayOfMonth()) {
                dayLabel.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-background-radius: 50%; -fx-alignment: center;");
            }

            daysGrid.add(dayLabel, col, row);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }

        return daysGrid;
    }

    private void changeMonth(int delta) {
        selectedDate = selectedDate.plusMonths(delta);
        monthLabel.setText(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + selectedDate.getYear());
        updateDaysGrid();
    }

    private void updateDaysGrid() {
        VBox calendarBox = createCalendarBox();
        Stage stage = (Stage) monthLabel.getScene().getWindow();
        stage.getScene().setRoot(calendarBox);
    }

    private String formatDate(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String month = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        return String.format("%s, %s %d", dayOfWeek, month, date.getDayOfMonth());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
