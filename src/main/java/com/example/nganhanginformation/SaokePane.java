package com.example.nganhanginformation;

import BusinessLogicLayer.BLLTransaction;
import DataAccessLayer.DALTransaction;
import Entity.EntityAccount;
import Entity.EntityTransaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SaokePane extends HBox {
    private final BLLTransaction bllTransaction = new BLLTransaction();
    private TableView<EntityTransaction> tableView;

    public SaokePane(EntityAccount selectedAccount) {
        setSpacing(50);
        setPadding(new Insets(20));

        VBox leftPane = new VBox(10);
        ImageView avatarImageView = new ImageView(new Image(getClass().getResource("/com/example/nganhanginformation/Image/avata.png").toExternalForm()));
        avatarImageView.setFitWidth(175);
        avatarImageView.setFitHeight(175);
        Label accountNameLabel = new Label("Tài khoản:  " + selectedAccount.getFullname());
        accountNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; ");
        Label accountIdLabel = new Label("Mã tài khoản:  " + selectedAccount.getAccountId());
        accountIdLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        leftPane.getChildren().addAll(avatarImageView, accountNameLabel, accountIdLabel);

        VBox rightPane = new VBox(10);
        rightPane.setAlignment(Pos.TOP_CENTER);

        Label saoKeLabel = new Label("SAO KÊ GIAO DỊCH");
        saoKeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        tableView = new TableView<>();
        tableView.setPrefWidth(850);
        tableView.setPrefHeight(700);

        TableColumn<EntityTransaction, String> transactionIdColumn = new TableColumn<>("Mã GD");
        transactionIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTransactionId()));
        transactionIdColumn.setMinWidth(75);

        TableColumn<EntityTransaction, String> transactionDateColumn = new TableColumn<>("Ngày GD");
        transactionDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTransactionDate().toString()));
        transactionDateColumn.setMinWidth(125);

        TableColumn<EntityTransaction, String> transactionTypeColumn = new TableColumn<>("Loại GD");
        transactionTypeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTransactionType()));

        TableColumn<EntityTransaction, Double> amountColumn = new TableColumn<>("Số tiền");
        amountColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());

        TableColumn<EntityTransaction, String> descriptionColumn = new TableColumn<>("Mô tả");
        descriptionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));

        TableColumn<EntityTransaction, Double> Column = new TableColumn<>("Giao dịch");
        Column.setMinWidth(100);

        Column.setCellFactory(param -> new TableCell<EntityTransaction, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    EntityTransaction transaction = getTableRow().getItem();

                    String transactionType = transaction.getTransactionType();
                    ImageView imageView = new ImageView();

                    if ("Nạp tiền".equals(transactionType)) {
                        imageView.setImage(new Image(getClass().getResource("/com/example/nganhanginformation/Image/left-arrow.png").toExternalForm()));
                    } else if ("Chuyển khoản".equals(transactionType) || "Rút tiền".equals(transactionType)) {
                        imageView.setImage(new Image(getClass().getResource("/com/example/nganhanginformation/Image/right-arrow.png").toExternalForm()));
                    }

                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    setGraphic(imageView);
                }
            }
        });
        tableView.getColumns().addAll(transactionIdColumn, transactionDateColumn, transactionTypeColumn, amountColumn, descriptionColumn, Column);
        loadTransactions(selectedAccount.getAccountId());
        rightPane.getChildren().addAll(saoKeLabel, tableView);
        getChildren().addAll(leftPane, rightPane);
        getStylesheets().add(getClass().getResource("Styles/table.css").toExternalForm());


    }

    private void loadTransactions(int accountId) {
        DALTransaction dalTransaction = new DALTransaction();
        ObservableList<EntityTransaction> transactions = FXCollections.observableArrayList(dalTransaction.getTransactionsByAccountId(accountId));
        tableView.setItems(transactions);
    }

    public static void openSaokeWindow(EntityAccount selectedAccount) {
        SaokePane saokePane = new SaokePane(selectedAccount);
        Scene saokeScene = new Scene(saokePane, 1000, 500);
        Stage saokeStage = new Stage();
        saokeStage.setTitle("Sao kê tài khoản");
        saokeStage.setScene(saokeScene);
        saokeStage.show();
    }
}
