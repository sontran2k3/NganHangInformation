package com.example.nganhanginformation;

import Entity.EntityKhachHang;
import BusinessLogicLayer.BLLKhachHang;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.sql.Date;

public class KhachHangPane extends AnchorPane {

    private TableView<EntityKhachHang> tableView;
    private ObservableList<EntityKhachHang> dataList;
    private BLLKhachHang bllKhachHang = new BLLKhachHang();

    private ImageView profileImageView;
    private ImageView signImageView;
    private ImageView profileIcon;
    private ImageView signIcon;

    public KhachHangPane(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("Styles/table.css").toExternalForm());

        Label welcomeLabel = new Label("WELCOME, Trần K. Sơn.  Quản lý khách hàng");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        this.getChildren().add(welcomeLabel);
        AnchorPane.setTopAnchor(welcomeLabel, 20.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 20.0);

        VBox container = new VBox();
        container.setSpacing(10);
        container.setPadding(new Insets(10));

        VBox inputFieldsBox = new VBox();
        inputFieldsBox.setPadding(new Insets(2));
        inputFieldsBox.setStyle("-fx-border-color: #3b8677; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-background-color: #f5f5f5;");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(20);
        grid.setPadding(new Insets(10));

        TextField fullNameField = new TextField();
        TextField cccdField = new TextField();
        DatePicker birthdayPicker = new DatePicker();
        TextField addressField = new TextField();

        grid.add(new Label("Full Name:"), 0, 0);
        grid.add(fullNameField, 1, 0);
        grid.add(new Label("CCCD:"), 0, 1);
        grid.add(cccdField, 1, 1);
        grid.add(new Label("Birthday:"), 0, 2);
        grid.add(birthdayPicker, 1, 2);
        grid.add(new Label("Address:"), 0, 3);
        grid.add(addressField, 1, 3);

        TextField phoneField = new TextField();
        TextField emailField = new TextField();
        TextField occupationField = new TextField();

        grid.add(new Label("Phone:"), 2, 0);
        grid.add(phoneField, 3, 0);
        grid.add(new Label("Email:"), 2, 1);
        grid.add(emailField, 3, 1);
        grid.add(new Label("Occupation:"), 2, 2);
        grid.add(occupationField, 3, 2);

        ToggleGroup genderGroup = new ToggleGroup();
        RadioButton maleRadio = new RadioButton("Nam");
        RadioButton femaleRadio = new RadioButton("Nữ");

        maleRadio.setToggleGroup(genderGroup);
        femaleRadio.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(50);
        genderBox.getChildren().addAll(maleRadio, femaleRadio);

        maleRadio.setSelected(true);

        grid.add(new Label("Gender:"), 2, 3);
        grid.add(genderBox, 3, 3);

        Button chooseProfileImageButton = new Button("Ảnh đại diện");
        chooseProfileImageButton.setOnAction(e -> chooseImage("Profile Picture"));
        profileImageView = new ImageView();
        profileImageView.setPreserveRatio(true);
        profileImageView.setFitHeight(100);
        profileImageView.setFitWidth(100);

        profileIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/image.png")));
        profileIcon.setFitHeight(30);
        profileIcon.setFitWidth(30);

        StackPane profileImageContainer = new StackPane();
        profileImageContainer.setPrefSize(100, 100);
        profileImageContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        profileImageContainer.getChildren().addAll(profileImageView, profileIcon);
        grid.add(chooseProfileImageButton, 4, 0);
        grid.add(profileImageContainer, 5, 0);

        Button chooseSignImageButton = new Button("Chữ ký");
        chooseSignImageButton.setOnAction(e -> chooseImage("Signature"));
        signImageView = new ImageView();
        signImageView.setPreserveRatio(true);
        signImageView.setFitHeight(100);
        signImageView.setFitWidth(100);

        signIcon = new ImageView(new Image(getClass().getResourceAsStream("/com/example/nganhanginformation/Image/image.png")));
        signIcon.setFitHeight(30);
        signIcon.setFitWidth(30);

        StackPane signImageContainer = new StackPane();
        signImageContainer.setPrefSize(100, 100);
        signImageContainer.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        signImageContainer.getChildren().addAll(signImageView, signIcon);
        grid.add(chooseSignImageButton, 4, 1);
        grid.add(signImageContainer, 5, 1);

        tableView = new TableView<>();
        TableColumn<EntityKhachHang, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        idCol.setMinWidth(100);

        TableColumn<EntityKhachHang, String> fullnameCol = new TableColumn<>("Họ Tên");
        fullnameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        fullnameCol.setMinWidth(150);

        TableColumn<EntityKhachHang, String> cccdCol = new TableColumn<>("CMND/CCCD");
        cccdCol.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        cccdCol.setMinWidth(125);

        TableColumn<EntityKhachHang, String> phoneCol = new TableColumn<>("Số Điện Thoại");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setMinWidth(100);

        TableColumn<EntityKhachHang, String> birthdayCol = new TableColumn<>("Ngày Sinh");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        birthdayCol.setMinWidth(100);

        TableColumn<EntityKhachHang, String> addressCol = new TableColumn<>("Địa Chỉ");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setMinWidth(175);

        TableColumn<EntityKhachHang, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setMinWidth(150);

        TableColumn<EntityKhachHang, String> occupationCol = new TableColumn<>("Nghề Nghiệp");
        occupationCol.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        occupationCol.setMinWidth(100);

        tableView.getColumns().addAll(idCol, fullnameCol, cccdCol, phoneCol, birthdayCol, addressCol, emailCol, occupationCol);

        dataList = FXCollections.observableArrayList(bllKhachHang.getAllCustomers());
        tableView.setItems(dataList);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));

        Button addCustomerButton = new Button("Thêm Khách Hàng");
        Button UpdateCustomerButton = new Button("Cập Nhật Khách Hàng");
        Button DeleteCustomerButton = new Button("Xóa Khách Hàng");



        addCustomerButton.setOnAction(e -> {
            int customerId = generateRandomIdKhachHang();
            String fullname = fullNameField.getText();
            String cccd = cccdField.getText();
            Date birthday = birthdayPicker.getValue() != null ? Date.valueOf(birthdayPicker.getValue()) : null;
            String address = addressField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String occupation = occupationField.getText();
            String gender = maleRadio.isSelected() ? "Nam" : "Nữ";

            byte[] profilePicture = profileImageView.getImage() != null ?
                    getImageBytes(profileImageView.getImage()) : null;
            byte[] signSample = signImageView.getImage() != null ?
                    getImageBytes(signImageView.getImage()) : null;

            if (fullname.isEmpty() || cccd.isEmpty() || birthday == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Vui lòng điền đầy đủ thông tin bắt buộc (Họ tên, CCCD, Ngày sinh)!");
                alert.show();
                return;
            }

            EntityKhachHang newCustomer = new EntityKhachHang();
            newCustomer.setCustomerId(customerId);
            newCustomer.setFullname(fullname);
            newCustomer.setCccd(cccd);
            newCustomer.setBirthday(birthday);
            newCustomer.setAddress(address);
            newCustomer.setPhone(phone);
            newCustomer.setEmail(email);
            newCustomer.setOccupation(occupation);
            newCustomer.setGender(gender);
            newCustomer.setProfilePicture(profilePicture);
            newCustomer.setSignSample(signSample);

            boolean isAdded = bllKhachHang.addCustomer(newCustomer);
            if (isAdded) {
                dataList.add(newCustomer); // Cập nhật bảng dữ liệu
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Thêm khách hàng thành công!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("CCCD đã tồn tại hoặc xảy ra lỗi khi thêm khách hàng!");
                alert.show();
            }
        });
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Gán giá trị từ EntityKhachHang vào các trường
                fullNameField.setText(newValue.getFullname());
                cccdField.setText(newValue.getCccd());
                birthdayPicker.setValue(newValue.getBirthday() != null ? newValue.getBirthday().toLocalDate() : null);
                addressField.setText(newValue.getAddress());
                phoneField.setText(newValue.getPhone());
                emailField.setText(newValue.getEmail());
                occupationField.setText(newValue.getOccupation());

                if ("Nam".equals(newValue.getGender())) {
                    maleRadio.setSelected(true);
                } else if ("Nữ".equals(newValue.getGender())) {
                    femaleRadio.setSelected(true);
                }
                if (newValue.getProfilePicture() != null) {
                    profileImageView.setImage(new Image(new java.io.ByteArrayInputStream(newValue.getProfilePicture())));
                    profileIcon.setVisible(false);
                } else {
                    profileImageView.setImage(null);
                    profileIcon.setVisible(true);
                }
                if (newValue.getSignSample() != null) {
                    signImageView.setImage(new Image(new java.io.ByteArrayInputStream(newValue.getSignSample())));
                    signIcon.setVisible(false);
                } else {
                    signImageView.setImage(null);
                    signIcon.setVisible(true);
                }
            }
        });

        UpdateCustomerButton.setOnAction(e -> {
            EntityKhachHang selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                String fullname = fullNameField.getText();
                String cccd = cccdField.getText();
                Date birthday = birthdayPicker.getValue() != null ? Date.valueOf(birthdayPicker.getValue()) : null;
                String address = addressField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String occupation = occupationField.getText();
                String gender = maleRadio.isSelected() ? "Nam" : "Nữ";

                byte[] profilePicture = profileImageView.getImage() != null ?
                        getImageBytes(profileImageView.getImage()) : null;
                byte[] signSample = signImageView.getImage() != null ?
                        getImageBytes(signImageView.getImage()) : null;

                if (fullname.isEmpty() || cccd.isEmpty() || birthday == null) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Vui lòng điền đầy đủ thông tin bắt buộc (Họ tên, CCCD, Ngày sinh)!");
                    alert.show();
                    return;
                }
                selectedCustomer.setFullname(fullname);
                selectedCustomer.setCccd(cccd);
                selectedCustomer.setBirthday(birthday);
                selectedCustomer.setAddress(address);
                selectedCustomer.setPhone(phone);
                selectedCustomer.setEmail(email);
                selectedCustomer.setOccupation(occupation);
                selectedCustomer.setGender(gender);
                selectedCustomer.setProfilePicture(profilePicture);
                selectedCustomer.setSignSample(signSample);

                boolean isUpdated = bllKhachHang.updateCustomer(selectedCustomer);
                if (isUpdated) {
                    tableView.refresh();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Cập nhật khách hàng thành công!");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Cập nhật thất bại. Vui lòng kiểm tra lại thông tin.");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Vui lòng chọn khách hàng để cập nhật!");
                alert.show();
            }
        });

        DeleteCustomerButton.setOnAction(e -> {
            EntityKhachHang selectedCustomer = tableView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                // Hiển thị hộp thoại xác nhận xóa
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Xóa Khách Hàng");
                alert.setHeaderText("Bạn có chắc chắn muốn xóa khách hàng này?");
                alert.setContentText("Họ Tên: " + selectedCustomer.getFullname());

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        boolean isDeleted = bllKhachHang.deleteCustomer(selectedCustomer.getCustomerId());
                        if (isDeleted) {
                            dataList.remove(selectedCustomer);
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setContentText("Khách hàng đã được xóa thành công!");
                            successAlert.show();
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setContentText("Có lỗi xảy ra khi xóa khách hàng.");
                            errorAlert.show();
                        }
                    }
                });
            } else {
                // Thông báo nếu chưa chọn khách hàng
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Vui lòng chọn khách hàng để xóa.");
                alert.show();
            }
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addCustomerButton, UpdateCustomerButton, DeleteCustomerButton); // Thêm button vào HBox
        buttonBox.setAlignment(Pos.BASELINE_LEFT);

        container.getChildren().addAll(inputFieldsBox, searchField, buttonBox, tableView);

        inputFieldsBox.getChildren().add(grid);

        this.getChildren().add(container);
        AnchorPane.setTopAnchor(container, 70.0);
        AnchorPane.setLeftAnchor(container, 20.0);

        this.setPrefWidth(800);
        this.setPrefHeight(600);
    }

    private void chooseImage(String imageType) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));

        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            if (imageType.equals("Profile Picture")) {
                profileImageView.setImage(image);
                profileIcon.setVisible(false); // Ẩn icon khi có ảnh
            } else {
                signImageView.setImage(image);
                signIcon.setVisible(false); // Ẩn icon khi có ảnh
            }
        }
    }
    private byte[] getImageBytes(Image image) {
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.awt.image.BufferedImage bufferedImage = javafx.embed.swing.SwingFXUtils.fromFXImage(image, null);
            javax.imageio.ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void filterData(String searchText) {
        ObservableList<EntityKhachHang> filteredData = FXCollections.observableArrayList();
        for (EntityKhachHang customer : dataList) {
            if (customer.getFullname().toLowerCase().contains(searchText.toLowerCase()) ||
                    customer.getCccd().toLowerCase().contains(searchText.toLowerCase())) {
                filteredData.add(customer);
            }
        }
        tableView.setItems(filteredData);
    }

    private int generateRandomIdKhachHang() {
        return (int) (Math.random() * 900000) + 100000;
    }
}