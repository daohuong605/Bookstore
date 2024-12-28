package application;

import dto.order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class OrderController {
    @FXML
    private TableView<order> tbaOrder;
    @FXML
    private TableColumn<order, Integer> orderIdColumn;
    @FXML
    private TableColumn<order, String> customerNameColumn;
    @FXML
    private TableColumn<order, String> employeeNameColumn;
    @FXML
    private TableColumn<order, String> orderDateColumn;
    @FXML
    private TableColumn<order, Double> totalAmountColumn;
    @FXML
    private TableColumn<order, String> statusColumn;
     
    @FXML
    private Button btnInsert, btnUpdate, btnDelete, btnClose;

    private ObservableList<order> orderList = FXCollections.observableArrayList();

    // Thông tin kết nối cơ sở dữ liệu
    private final String DB_URL = "jdbc:sqlserver://localhost;DatabaseName=TienThoBookk;encrypt=false;";
    private final String DB_USER = "sa";
    private final String DB_PASSWORD = "12345678";

    @FXML
    private void initialize() {
        // Set up table columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Load orders from the database
        loadOrdersFromDatabase();
    }

    @FXML
    private void ClickInsert(MouseEvent event) {
        openOrderDetailWindow(null); // Mở cửa sổ thêm đơn hàng mới
    }

    @FXML
    private void ClickDelete(MouseEvent event) {
        deleteOrder(); // Xóa đơn hàng được chọn
    }

    @FXML
    private void ClickClose(MouseEvent event) {
        closeOrderWindow(); // Đóng cửa sổ hiện tại
    }


    private order getSelectedOrder() {
        return tbaOrder.getSelectionModel().getSelectedItem();
    }

    public void openOrderDetailWindow(order order) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/details.fxml"));
            Parent root = loader.load();
            OrderDetailController controller = loader.getController();
            controller.setOrderData(order);  // Truyền Order vào controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void ClickUpdate(MouseEvent event) {
        order selectedOrder = getSelectedOrder();
        if (selectedOrder != null) {
            // Open a prompt to allow the user to select the new status
            TextInputDialog statusDialog = new TextInputDialog(selectedOrder.getStatus());
            statusDialog.setTitle("Update Order Status");
            statusDialog.setHeaderText("Enter new status for Order ID " + selectedOrder.getOrderId());
            statusDialog.setContentText("Status:");

            statusDialog.showAndWait().ifPresent(newStatus -> {
                if (!newStatus.isEmpty()) {
                    // Update the status in the database
                    updateOrderStatusInDatabase(selectedOrder.getOrderId(), newStatus);
                    // Update the status in the selected order in the list
                    selectedOrder.setStatus(newStatus);
                    tbaOrder.refresh(); // Refresh the table to reflect changes
                } else {
                    // Handle empty input, if necessary
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Status cannot be empty!", ButtonType.OK);
                    alert.showAndWait();
                }
            });
        } else {
            // Handle the case where no order is selected
            Alert alert = new Alert(Alert.AlertType.WARNING, "No order selected for update!", ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    private void updateOrderStatusInDatabase(int orderId, String newStatus) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Prepare SQL query to update the order status
            String updateQuery = "UPDATE Orders SET Status = ? WHERE OrderID = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Order status updated successfully!");
            } else {
                System.out.println("Failed to update order status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void deleteOrder() {
        order selectedOrder = getSelectedOrder();
        if (selectedOrder != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Order");
            alert.setHeaderText("Are you sure you want to delete this order?");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                        // Delete order details first
                        String deleteOrderDetailQuery = "DELETE FROM OrderDetail WHERE OrderID = ?";
                        PreparedStatement stmtDetail = conn.prepareStatement(deleteOrderDetailQuery);
                        stmtDetail.setInt(1, selectedOrder.getOrderId());
                        stmtDetail.executeUpdate();
                        
                        // Then delete the order
                        String deleteOrderQuery = "DELETE FROM Orders WHERE OrderID = ?";
                        PreparedStatement stmtOrder = conn.prepareStatement(deleteOrderQuery);
                        stmtOrder.setInt(1, selectedOrder.getOrderId());
                        stmtOrder.executeUpdate();
                        
                        // Remove order from the list
                        orderList.remove(selectedOrder);
                        System.out.println("Order deleted successfully!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            System.out.println("No order selected for deletion.");
        }
    }

    private void closeOrderWindow() {
        try {
            // Load the OrderPage FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/layout.fxml"));
            Parent root = loader.load();

            // Get the current stage and set the scene to the new order page
            Stage stage = (Stage) btnClose.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            
            // Optionally, set the stage title if needed
            stage.setTitle("Order Page");

            // Show the new scene
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadOrdersFromDatabase() {
        orderList.clear();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT o.OrderID, o.OrderDate, o.TotalAmount, o.Status, c.Customer_Name, e.Employee_Name ,o.Status "+
                           "FROM Orders o " +
                           "JOIN Customer c ON o.CustomerID = c.CustomerID " +
                           "JOIN Employee e ON o.EmployeeID = e.EmployeeID";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                order newOrder = new order(
                    rs.getInt("OrderID"),
                    rs.getString("Customer_Name"),
                    rs.getString("OrderDate"),
                    rs.getDouble("TotalAmount"),
                    rs.getString("Status")
                );
                newOrder.setEmployeeName(rs.getString("Employee_Name")); // Ensure this method is available in order class
                orderList.add(newOrder);
            }
            tbaOrder.setItems(orderList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
