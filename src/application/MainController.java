package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private AnchorPane placeholderPane; 

    @FXML
    public void initialize() {
        loadHomePage(); 
    }

    @FXML
    private void home() {
        loadHomePage();
    }

    @FXML
    private void book() {
        loadBooksPage();
    }

    @FXML
    private void employee() {
        loadEmployeesPage();
    }

    @FXML
    private void customer() {
        loadCustomerPage();
    }

    @FXML
    private void genre() {
        loadGenrePage();
    }

    @FXML
    private void order() {
        loadOrderPage();
    }

    @FXML
    private void detailedorder() {
        loadOrderDetailPage();
    }

    @FXML
    private void setting() {
        loadEmployeesPage();
    }

    private void loadHomePage() {
        AnchorPane homePage = createPageFromFXML("/ui/dashboard.fxml");
        placeholderPane.getChildren().setAll(homePage); 
    }

    private void loadBooksPage() {
        AnchorPane booksPage = createPageFromFXML("/ui/books.fxml");
        placeholderPane.getChildren().setAll(booksPage); 
    }

    private void loadEmployeesPage() {
        AnchorPane employeesPage = createPageFromFXML("/ui/employees.fxml");
        placeholderPane.getChildren().setAll(employeesPage); 
    }
    
    private void loadCustomerPage() {
        AnchorPane customerPage = createPageFromFXML("/ui/customer.fxml");
        placeholderPane.getChildren().setAll(customerPage); 
    }
    
    private void loadGenrePage() {
        AnchorPane genrePage = createPageFromFXML("/ui/genre.fxml");
        placeholderPane.getChildren().setAll(genrePage); 
    }
    
    private void loadOrderPage() {
        AnchorPane genrePage = createPageFromFXML("/ui/order.fxml");
        placeholderPane.getChildren().setAll(genrePage); 
    }
    
    private void loadOrderDetailPage() {
        AnchorPane genrePage = createPageFromFXML("/ui/details.fxml");
        placeholderPane.getChildren().setAll(genrePage); 
    }

    
    @FXML
    private void logout() {
        try {
            // Đóng cửa sổ hiện tại
            placeholderPane.getScene().getWindow().hide();

            // Tải giao diện login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/login.fxml"));
            AnchorPane loginPage = loader.load();

            // Tạo một stage mới cho trang login
            Stage loginStage = new Stage();
            Scene loginScene = new Scene(loginPage);
            loginStage.setScene(loginScene);
            loginStage.setTitle("Login");
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AnchorPane createPageFromFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane page = loader.load(); 
            page.setPrefSize(941, 736); 
            page.setStyle("-fx-background-color: #f5f5f5;");
            return page;
        } catch (IOException e) {
            e.printStackTrace();
            return new AnchorPane(); 
        }
    }
}
