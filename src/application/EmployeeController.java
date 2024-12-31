package application;

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

import dto.employee; // Correct class name with capital 'E'

public class EmployeeController {

    @FXML
    private TextField EmID, EmName, EmSex, EmDOB, EmCitizenID, EmPosition, EmPhoneNumber;
    @FXML
    private TableView<employee> tbaView; 
    @FXML
    private Button btnClose;

    private Connection conn;

    @FXML
    public void onClose(MouseEvent event) {
        try {
            // Load giao diện layout.fxml
            FXMLLoader layoutLoader = new FXMLLoader(getClass().getResource("/ui/layout.fxml"));
            Parent layoutRoot = layoutLoader.load();

            // Lấy controller của layout.fxml
            MainController layoutController = layoutLoader.getController();

            // Hiển thị layout.fxml trong stage hiện tại
            Stage stage = (Stage) btnClose.getScene().getWindow();
            Scene scene = new Scene(layoutRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Do not load file layout.fxml");
        }
    }

    @FXML
    private void ClickInsert(MouseEvent env) {
        String query = "INSERT INTO Employee (Employee_Name, Sex, DOB, Citizen_ID, Position, Phone_Number) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, EmName.getText());
            pstmt.setString(2, EmSex.getText());
            pstmt.setString(3, EmDOB.getText());
            pstmt.setString(4, EmCitizenID.getText());
            pstmt.setString(5, EmPosition.getText());
            pstmt.setString(6, EmPhoneNumber.getText());
            pstmt.executeUpdate();
            System.out.println("Employee inserted successfully!");
            Display();
        } catch (SQLException e) {
            System.out.println("Error inserting employee: " + e.getMessage());
        }
    }

    @FXML
    private void ClickUpdate(MouseEvent env) {
        if (EmID.getText().isEmpty()) {
            System.out.println("Employee ID cannot be empty for update.");
            return;
        }

        String query = "UPDATE Employee SET Employee_Name = ?, Sex = ?, DOB = ?, Citizen_ID = ?, Position = ?, Phone_Number = ? WHERE EmployeeID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, EmName.getText());
            pstmt.setString(2, EmSex.getText());
            pstmt.setString(3, EmDOB.getText());
            pstmt.setString(4, EmCitizenID.getText());
            pstmt.setString(5, EmPosition.getText());
            pstmt.setString(6, EmPhoneNumber.getText());
            pstmt.setInt(7, Integer.parseInt(EmID.getText())); // Lấy EmployeeID từ TextField

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully!");
                Display(); // Refresh the table view
            }
        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }


    @FXML
    private void ClickDelete(MouseEvent env) {
        if (EmID.getText().isEmpty()) {
            System.out.println("Employee ID cannot be empty for deletion.");
            return;
        }

        String query = "DELETE FROM Employee WHERE EmployeeID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(EmID.getText())); 
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully!");
                Display(); // Refresh the table view
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    private void Display() {
        tbaView.getItems().clear();
        String query = "SELECT * FROM Employee";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                employee employee = new employee(
                        rs.getInt("EmployeeID"),
                        rs.getString("Employee_Name"),
                        rs.getString("Sex"),
                        rs.getString("DOB"),
                        rs.getString("Citizen_ID"),
                        rs.getString("Position"),
                        rs.getString("Phone_Number")
                );
                tbaView.getItems().add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying employees: " + e.getMessage());
        }
    }

    public void initialize() {
        tbaView.setEditable(true);
        tbaView.getColumns().clear();

        TableColumn<employee, Integer> employeeIDCol = new TableColumn<>("Employee ID");
        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        TableColumn<employee, String> employeeNameCol = new TableColumn<>("Employee Name");
        employeeNameCol.setCellValueFactory(new PropertyValueFactory<>("employeeName"));

        TableColumn<employee, String> sexCol = new TableColumn<>("Sex");
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));

        TableColumn<employee, String> dobCol = new TableColumn<>("DOB");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));

        TableColumn<employee, String> citizenIDCol = new TableColumn<>("Citizen ID");
        citizenIDCol.setCellValueFactory(new PropertyValueFactory<>("citizenId"));

        TableColumn<employee, String> positionCol = new TableColumn<>("Position");
        positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<employee, String> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        tbaView.getColumns().addAll(employeeIDCol, employeeNameCol, sexCol, dobCol, citizenIDCol, positionCol, phoneNumberCol);

        try {
            String url = "jdbc:sqlserver://localhost;DatabaseName=TienThoBookk;username=sa;password=12345678;encrypt=false;";
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                Display();
            }
        } catch (SQLException ex) {
            System.out.println("Error connecting to the database: " + ex.getMessage());
        }
    }

    @FXML
    private void ClickTableView(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double click
            employee selectedEmployee = tbaView.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                
                EmID.setText(String.valueOf(selectedEmployee.getEmployeeId()));
                EmName.setText(selectedEmployee.getEmployeeName());
                EmSex.setText(selectedEmployee.getSex());
                EmDOB.setText(selectedEmployee.getDob()); 
                EmCitizenID.setText(selectedEmployee.getCitizenId());
                EmPosition.setText(selectedEmployee.getPosition());
                EmPhoneNumber.setText(selectedEmployee.getPhoneNumber());
            }
        }
    }
}
