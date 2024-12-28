package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import dto.customer;

public class CustomerController {

    @FXML
    Label txtTitle;

    @FXML
    TextField txtID;

    @FXML
    TextField txtName;

    @FXML
    TextField txtEmail;

    @FXML
    TextField txtPhone;

    @FXML
    ComboBox<String> cboAddress;

    @FXML
    TableView<customer> tbaView;

    @FXML
    Button btnClose;


    @FXML
    private void ClickClose(MouseEvent env) throws SQLException {
        conn.close();
        Stage primaryStage = (Stage) btnClose.getScene().getWindow();
        primaryStage.close();
    }

    @FXML
    private void ClickInsert(MouseEvent env) throws SQLException {
        Statement m_Statement = conn.createStatement();
        String query = "INSERT INTO Customer (Customer_Name, Email, Phone, Address) VALUES (N'" 
                + txtName.getText() + "', N'" 
                + txtEmail.getText() + "', N'" 
                + txtPhone.getText() + "', N'" 
                + cboAddress.getValue() + "')";
        m_Statement.executeUpdate(query);
        Display();
    }

    @FXML
    private void ClickUpdate(MouseEvent env) throws SQLException {
        Statement m_Statement = conn.createStatement();
        String query = "UPDATE Customer SET Customer_Name=N'" +
                txtName.getText() + "', Email=N'" +
                txtEmail.getText() + "', Phone=N'" +
                txtPhone.getText() + "', Address=N'" +
                cboAddress.getValue() + "' WHERE CustomerID='" + txtID.getText() + "'";
        m_Statement.executeUpdate(query);
        Display();
    }

    @FXML
    private void ClickDelete(MouseEvent env) throws SQLException {
        Statement m_Statement = conn.createStatement();
        {
    		Dialog<ButtonType> dialog = new Dialog<>();
    		dialog.setContentText("You want to delete it?");
    		dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
    		dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);

    		Optional<ButtonType> results = dialog.showAndWait();
    		if (results.isPresent() && results.get()==ButtonType.YES)
    			System.out.println("YES");
    		else
    			System.out.println("NO");
    	}
        String query = "DELETE FROM Customer WHERE CustomerID='" + txtID.getText() + "'";
        m_Statement.executeUpdate(query);
        Display();
        
       
    }
    
    @FXML
    private void ClickClear(MouseEvent env) throws IOException {
        txtID.clear();
        cboAddress.setValue(null);
        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
    }
    
	Connection conn = null;

    void Display() throws SQLException {
        tbaView.getItems().clear();

        Statement m_Statement = conn.createStatement();
        String query = "SELECT * FROM Customer";
        ResultSet m_ResultSet = m_Statement.executeQuery(query);

        while (m_ResultSet.next()) {
            customer customer = new customer(
                    m_ResultSet.getString(1),
                    m_ResultSet.getString(2),
                    m_ResultSet.getString(3),
                    m_ResultSet.getString(4),
                    m_ResultSet.getString(5)
            );

            tbaView.getItems().add(customer);
        }
    }

    public void initialize() {
        tbaView.setEditable(true);
        tbaView.getColumns().clear();

        TableColumn<customer, String> customerIDCol = new TableColumn<>("Customer ID");
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        TableColumn<customer, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        TableColumn<customer, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn<customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        tbaView.getColumns().addAll(customerIDCol, customerNameCol, emailCol, phoneCol, addressCol);

        String[] add = {" Cau Giay District - Ha Noi ", " Bac Tu Liem District - Ha Noi ", " Lac Long Quan Street - Ha Noi ", " Nam Tu Liem District - Ha Noi "," Hai Ba Trung District - Ha Noi ", " Thai Binh ", " Nam Dinh ", " Hoa Binh ", " Hung Yen ", " Ninh Binh " };
        cboAddress.getItems().addAll(add);

        try {
            String url = "jdbc:sqlserver://localhost;DatabaseName=TienThoBookk; username=sa;password=12345678; encrypt=false;";
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                Display();
            }
        } catch (SQLException ex) {
            System.out.println("Error connecting: " + ex);
        }
    }

    @FXML
    private void ClickAddInfor(MouseEvent mnv) {
        customer customer = new customer(
                txtID.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                cboAddress.getValue()
        );
        tbaView.getItems().add(customer);
    }

    @FXML
    private void ClickTableView(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double click
            customer selectedCustomer = tbaView.getSelectionModel().getSelectedItem();
            if (selectedCustomer != null) {
                txtID.setText(selectedCustomer.getCustomerID());
                txtName.setText(selectedCustomer.getCustomerName());
                txtEmail.setText(selectedCustomer.getEmail());
                txtPhone.setText(selectedCustomer.getPhone());
                cboAddress.setValue(selectedCustomer.getAddress());
            }
        }
    }
}
