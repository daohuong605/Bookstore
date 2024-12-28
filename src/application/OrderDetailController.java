package application;

import dto.OrderDetail;
import dto.order;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

public class OrderDetailController {

    @FXML
    private ComboBox<String> cboCustomer, cboEmployee, cboBook,cboStatus;
    @FXML
    private Button btnCancel,btnSave;

    @FXML
    private DatePicker dpOrderDate;

    @FXML
    private TextField txtQuantity, txtTotalAmount;

    @FXML
    private TableView<OrderDetail> tbaDetail;

    @FXML
    private TableColumn<OrderDetail, String> colBookTitle;

    @FXML
    private TableColumn<OrderDetail, Integer> colQuantity;

    @FXML
    private TableColumn<OrderDetail, Double> colPrice, colSubtotal;

    private ObservableList<OrderDetail> orderDetails = FXCollections.observableArrayList();
    private Connection connection;

    public void initialize() {
        connectDatabase();
        loadComboBoxes();
        setupTableView();
    }

    private void connectDatabase() {
        try {
        	String url = "jdbc:sqlserver://localhost:1433;databaseName=TienThoBookk;user=sa;password=12345678;encrypt=true;trustServerCertificate=true;";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void loadComboBoxes() {
        loadCustomerData("Customer", cboCustomer);
        loadCustomerData("Employee", cboEmployee);
        loadCustomerData("Book", cboBook);
        cboStatus.getItems().addAll("Pending", "Completed", "Cancelled");
        cboStatus.setValue("Pending"); 
    }

    private void loadCustomerData(String table, ComboBox<String> comboBox) {
        try {
            String query = "SELECT * FROM " + table;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                comboBox.getItems().add(rs.getString(2)); // Assuming name is in second column
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTableView() {
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        tbaDetail.setItems(orderDetails);
    }

    @FXML
    public void onAddBook(MouseEvent event) {
        try {
            String bookTitle = cboBook.getValue();
            int quantity = Integer.parseInt(txtQuantity.getText());
            String query = "SELECT Price FROM Book WHERE Title = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, bookTitle);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble(1);
                orderDetails.add(new OrderDetail(bookTitle, price, quantity));
                updateTotalAmount();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTotalAmount() {
        double total = orderDetails.stream().mapToDouble(OrderDetail::getSubtotal).sum();
        txtTotalAmount.setText(String.format("%.2f", total));
    }
    
    @FXML
    public void onUpdateBook(MouseEvent event) {
        try {
            // Example: Update logic for the selected book in the table
            OrderDetail selectedDetail = tbaDetail.getSelectionModel().getSelectedItem();
            if (selectedDetail != null) {
                // Example: Update quantity and subtotal for the selected book
                int newQuantity = Integer.parseInt(txtQuantity.getText());
                double newSubtotal = newQuantity * selectedDetail.getPrice();
                selectedDetail.setQuantity(newQuantity);
                selectedDetail.setSubtotal(newSubtotal);

                // Refresh the table view
                tbaDetail.refresh();
                updateTotalAmount(); // Update total amount after the change
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    private int getIdFromName(String table, String columnName, String name) throws SQLException {
        String query = "SELECT " + table + "ID FROM " + table + " WHERE " + columnName + " = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }
    
    @FXML
    public void onSaveOrder(MouseEvent event) {
        try {
            // Assuming you want to save the order details here
            String status = cboStatus.getValue();
            String customerName = cboCustomer.getValue();
            String employeeName = cboEmployee.getValue();
            Date orderDate = Date.valueOf(dpOrderDate.getValue());

            // Get IDs for the selected customer and employee
            int customerId = getIdFromName("Customer", "Customer_Name", customerName);
            int employeeId = getIdFromName("Employee", "Employee_Name", employeeName);

            // Calculate TotalAmount from orderDetails
            double totalAmount = orderDetails.stream().mapToDouble(OrderDetail::getSubtotal).sum();

            // Save the order (including the status, date, and total amount)
            String insertOrderQuery = "INSERT INTO Orders (CustomerID, EmployeeID, OrderDate, Status, TotalAmount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);  // Use RETURN_GENERATED_KEYS to fetch the generated keys
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, employeeId);
            pstmt.setDate(3, orderDate);
            pstmt.setString(4, status);
            pstmt.setDouble(5, totalAmount);

            // Execute the update
            pstmt.executeUpdate();

            // Now get the generated OrderID
            ResultSet rs = pstmt.getGeneratedKeys();
            int orderId = -1;
            if (rs.next()) {
                orderId = rs.getInt(1);  // Get the generated OrderID
            }

            // Save the order details (BookID, Quantity, SubTotal) if orderId is valid
            if (orderId != -1) {
                saveOrderDetails(orderId);
            }

            // Show success alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Order has been saved successfully!");
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void saveOrderDetails(int orderId) throws SQLException {
        String insertOrderDetail = "INSERT INTO OrderDetail (OrderID, BookID, Quantity, SubTotal) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = connection.prepareStatement(insertOrderDetail);

        for (OrderDetail detail : orderDetails) {
            int bookId = getIdFromName("Book", "Title", detail.getBookTitle());
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, detail.getQuantity());
            pstmt.setDouble(4, detail.getSubtotal());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
    }

    @FXML
    public void onClose(MouseEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

	public void setOrderData(order order) {
		// TODO Auto-generated method stub
		
	}
}
