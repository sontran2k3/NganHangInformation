package com.example.nganhanginformation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PaymentOrderForm extends Application {

    private String senderAccount;
    private String senderName;
    private String receiverAccount;
    private String receiverName;
    private double amount;
    private String amountInWords;

    public PaymentOrderForm(String senderAccount, String senderName, String receiverAccount,
                            String receiverName, double amount, String amountInWords) {
        this.senderAccount = senderAccount;
        this.senderName = senderName;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
        this.amount = amount;
        this.amountInWords = amountInWords;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Payment Order Form");
        primaryStage.setResizable(false);

        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setStyle("-fx-background-color: #f3f4f6;");

        HBox header = new HBox();
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: linear-gradient(to right, #4A90E2, #50C9C3);");

        Label titleLabel = new Label(" HÓA ĐƠN  (PAYMENT ORDER)");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");
        header.getChildren().add(titleLabel);

        TitledPane accountInfoGroup = createGroupPane("Thông tin tài khoản (Account Information)", new String[][]{
                {"Tên TK người gửi (A/C Name):", senderName},
                {"Số TK người gửi (A/C No.):", senderAccount},
                {"Người nhận (Beneficiary):", receiverName},
                {"Số TK người nhận:", receiverAccount}
        });

        TitledPane bankInfoGroup = createGroupPane("Thông tin ngân hàng (Bank Information)", new String[][]{
                {"Tại NH/At Bank:", "BANK OF INFORMATION"},
                {"Chi nhánh/Branch:", "HA NOI"}
        });

        TitledPane amountInfoGroup = createGroupPane("Thông tin số tiền (Amount Information)", new String[][]{
                {"Số tiền bằng số (Amount in figures):", String.format("%.2f", amount)},
                {"Số tiền bằng chữ (Amount in words):", amountInWords}
        });

        HBox footer = new HBox(100);
        footer.setPadding(new Insets(20, 0, 0, 0));
        footer.setStyle("-fx-alignment: center;");

        VBox customerSignature = new VBox(5);
        customerSignature.setAlignment(Pos.CENTER);
        customerSignature.getChildren().addAll(
                createStyledLabel("KHÁCH HÀNG (CUSTOMER)"),
                createStyledLabel("Kế toán trưởng (Chief Accountant)"),
                createStyledLabel("Chủ tài khoản (Account Holder)"),
                createStyledLabel("(Ký, họ tên/Signature & full name)")
        );

        VBox bankSignature = new VBox(5);
        bankSignature.setAlignment(Pos.CENTER);
        bankSignature.getChildren().addAll(
                createStyledLabel("NGÂN HÀNG (BANK SENDER)"),
                createStyledLabel("Giao dịch viên (Received by)"),
                createStyledLabel("Kiểm soát (Verified by)"),
                createStyledLabel("(Ký, họ tên/Signature & full name)")
        );

        footer.getChildren().addAll(customerSignature, bankSignature);

        mainContainer.getChildren().addAll(
                header,
                accountInfoGroup,
                bankInfoGroup,
                amountInfoGroup,
                footer
        );

        Scene scene = new Scene(mainContainer, 800, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private TitledPane createGroupPane(String title, String[][] fields) {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(10));
        pane.setStyle("-fx-border-color: #dcdcdc; -fx-border-width: 1px; -fx-background-color: #ffffff;");

        for (int i = 0; i < fields.length; i++) {
            pane.add(new Label(fields[i][0]), (i % 2) * 2, i / 2);
            pane.add(new TextField(fields[i][1]), (i % 2) * 2 + 1, i / 2);
        }

        TitledPane titledPane = new TitledPane(title, pane);
        titledPane.setCollapsible(false);
        titledPane.setStyle("-fx-font-weight: bold; -fx-text-fill: #333; -fx-font-size: 12px;");
        return titledPane;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333;");
        return label;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
