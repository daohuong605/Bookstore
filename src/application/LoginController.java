package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	TextField txt_username;
	@FXML
	TextField txt_password;
	@FXML
	ImageView img_logo;
	
	@FXML
	private void click_login_btn(MouseEvent env) throws IOException {
	    String user = txt_username.getText();
	    String pass = txt_password.getText();

	    if (user.equals("1") && pass.equals("1")) {
	        // Tải giao diện quản lý sách
	        Parent root = FXMLLoader.load(getClass().getResource("/UI/layout.fxml"));
	        Scene scene = new Scene(root);

	        // Tạo và hiển thị cửa sổ quản lý
	        Stage primaryStage = new Stage();
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Home");
	        primaryStage.show();
	    } else {
	        // Hiển thị thông báo lỗi
	        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
	        alert.setTitle("Login Error");
	        alert.setHeaderText("Invalid Username or Password");
	        alert.setContentText("Please check your credentials and try again.");
	        alert.showAndWait();
	    }
	}
	
	public void initialize() {
	    // Using relative path to load image from project folder
	    Image image_logo = new Image("file:E:/Java/New_project/src/images/logo.png");
	    img_logo.setImage(image_logo);
	}



}
	

