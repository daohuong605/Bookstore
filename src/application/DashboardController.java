package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.dashboard;

public class DashboardController {
	
	@FXML
	private TextField text1;
	
	@FXML
	private TextField text2;
	
	@FXML
	private TextField text3;

    @FXML
    private BarChart<String, Number> barChart1; 

    @FXML
    private BarChart<String, Number> barChart2; 

    @FXML
    private TableView<dashboard> tableView; 

    @FXML
    private TableColumn<dashboard, String> column1; 

    @FXML
    private TableColumn<dashboard, Integer> column2; 

    @FXML
    private TableColumn<dashboard, Integer> column3;

    @FXML
    private TableColumn<dashboard, Integer> column4; 


    private static final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=TienThoBookk;encrypt=false;";
    private static final String DATABASE_USER = "sa";
    private static final String DATABASE_PASSWORD = "12345678";

    
    public void initialize() {
    	loadTextField1();
    	loadTextField3();
    	loadTextField2();
        loadBarChartData1();
        loadBarChartData2();
        loadDataTableView();
    }

    //Tổng doanh thu
    private void loadTextField1() {
        String query = "SELECT SUM(TotalAmount) AS TotalRevenue FROM Orders";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                double totalRevenue = resultSet.getDouble("TotalRevenue");

                text1.setText(String.format("%.2f", totalRevenue)); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Tổng số khách hàng đặt hàng thành công
    private void loadTextField2() {
        String query = "SELECT SUM(CustomerID) AS Customer FROM Orders WHERE Status = 'Completed'";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                double totalCustomer = resultSet.getDouble("Customer");

                text2.setText(String.format("%.1f", totalCustomer)); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Tổng số sách bán được
    private void loadTextField3() {
        String query = "SELECT SUM(BookID) AS Books FROM OrderDetail od JOIN Orders o ON od.OrderID = o.OrderID WHERE o.Status = 'Completed'";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                double totalBook = resultSet.getDouble("Books");

                text3.setText(String.format("%.1f", totalBook)); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Tải dữ liệu cho BarChart1
    private void loadBarChartData1() {
        String query = "SELECT GenreName, TotalStock FROM StockByGenre";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Stock By Genre");

            while (resultSet.next()) {
                String genreName = resultSet.getString("GenreName");
                int totalStock = resultSet.getInt("TotalStock");

                series.getData().add(new XYChart.Data<>(genreName, totalStock));
            }

            barChart1.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tải dữ liệu cho BarChart2
    private void loadBarChartData2() {
        String query = "SELECT Customer_Name, TotalBooksSold FROM BooksSoldPerCustomerPerDay";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Books Sold Per Customer");

            while (resultSet.next()) {
                String customerName = resultSet.getString("Customer_Name");
                int totalBooksSold = resultSet.getInt("TotalBooksSold");

                series.getData().add(new XYChart.Data<>(customerName, totalBooksSold));
            }

            barChart2.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tải dữ liệu cho TableView
    private void loadDataTableView() {
        String query = "SELECT GenreName, TotalSold, TotalRevenue, TotalStock FROM v_BooksSalesInfo";
        ObservableList<dashboard> dashboardData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String genreName = resultSet.getString("GenreName");
                int totalSold = resultSet.getInt("TotalSold");
                int totalRevenue = resultSet.getInt("TotalRevenue");
                int totalStock = resultSet.getInt("TotalStock");

                dashboard dashboardItem = new dashboard(genreName, totalSold, totalRevenue, totalStock);
                dashboardData.add(dashboardItem);
            }

            tableView.setItems(dashboardData);

            // Ánh xạ các cột với thuộc tính của đối tượng dashboard
            column1.setCellValueFactory(new PropertyValueFactory<>("Genrename"));
            column2.setCellValueFactory(new PropertyValueFactory<>("Totalsold"));
            column3.setCellValueFactory(new PropertyValueFactory<>("Totalrevenue"));
            column4.setCellValueFactory(new PropertyValueFactory<>("Totalstock"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
