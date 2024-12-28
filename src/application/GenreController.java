package application;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;

import dto.genre;

import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class GenreController {
	
	@FXML
	Label txtTitle;
	
	@FXML
	TextField txtID;
	
	@FXML
	TextField txtName;
	
	@FXML
	TableView<genre> tbaView;
	
	@FXML
	Button btnClose;

	@FXML
	private void ClickClose(MouseEvent env) throws SQLException
	{
		conn.close();
		Stage primaryStage = new Stage();
		primaryStage = (Stage)btnClose.getScene().getWindow();
		primaryStage.close();
	}
	
	@FXML
	private void ClickInsert(MouseEvent env) throws SQLException
	{
		Statement m_Statement = conn.createStatement();
		String query = "INSERT INTO Genre VALUES ('" +txtName.getText()+ "')";
		m_Statement.executeUpdate(query);
		
		Display();
	}
	

	@FXML
	private void ClickUpdate(MouseEvent env) throws SQLException 
	{
		Statement m_Statement = conn.createStatement();
		 {
	    		Dialog<ButtonType> dialog = new Dialog<>();
	    		dialog.setContentText("You sure?");
	    		dialog.getDialogPane().getButtonTypes().add(ButtonType.YES);
	    		dialog.getDialogPane().getButtonTypes().add(ButtonType.NO);

	    		Optional<ButtonType> results = dialog.showAndWait();
	    		if (results.isPresent() && results.get()==ButtonType.YES)
	    			System.out.println("YES");
	    		else
	    			System.out.println("NO");
	    	}
		String query ="UPDATE Genre SET GenreName=N'" + txtName.getText()+ "' WHERE GenreID='" +txtID.getText()+"'";
		m_Statement.executeUpdate(query);
		Display();

	}
	
	@FXML
	private void ClickDelete(MouseEvent env) throws SQLException
	{
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
		 
		String query = "DELETE FROM Genre WHERE GenreID='"+txtID.getText()+"'";
		m_Statement.executeUpdate(query);
		Display();
		
		 
	}
	
	@FXML
    private void ClickClear(MouseEvent env) throws IOException {
        txtID.clear();
        txtName.clear();
	}
	
	Connection conn = null;

	
	void Display() throws SQLException {
		
	    tbaView.getItems().clear();
	    
	    
	    Statement m_Statement = conn.createStatement();
	    String query = "SELECT * FROM Genre";
	    ResultSet m_ResultSet = m_Statement.executeQuery(query);
	    while (m_ResultSet.next()) {
	        System.out.println(m_ResultSet.getString(1) + ", " 
	            + m_ResultSet.getString(2));
	        
	        genre k = new genre(m_ResultSet.getString(1), 
	            m_ResultSet.getString(2));
	        tbaView.getItems().add(k);
	    }
	}
	
public void initialize() {
		
		tbaView.setEditable(true);
	    tbaView.getColumns().clear();

	    TableColumn<genre, String> GenreID = new TableColumn<>("GenreID");
	    GenreID.setCellValueFactory(new PropertyValueFactory<>("genreID"));

	    TableColumn<genre, String> GenreName = new TableColumn<>("GenreName");
	    GenreName.setCellValueFactory(new PropertyValueFactory<>("genreName"));

	    tbaView.getColumns().addAll(GenreID, GenreName);
	    
	    try {
	    	String url = "jdbc:sqlserver://localhost;DatabaseName=TienThoBookk; username=sa;password=12345678; encrypt=false;";
			conn = DriverManager.getConnection(url);
			if (conn != null) {
				 DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				 System.out.println("Driver name: " + dm.getDriverName());
				 System.out.println("Driver version: " + dm.getDriverVersion());
				 System.out.println("Product name: " + dm.getDatabaseProductName());
				 System.out.println("Product version: " + dm.getDatabaseProductVersion());
				 
				 Display();
			 }
		 }
		 catch (SQLException ex) {
		 System.out.println("error connections " + ex);
		 //ex.printStackTrace();	 
		 
		 }
	}

@FXML
private void ClickAddInfor(MouseEvent mnv) {
    genre k = new genre(txtID.getText(), txtName.getText());
    tbaView.getItems().add(k);
}

@FXML
private void ClickTableView(MouseEvent event) {
    if (event.getClickCount() == 2)  // Checking double click
    {
        int row = tbaView.getFocusModel().getFocusedCell().getRow();
        
        Object st= tbaView.getItems().get(row);
        genre k = (genre) st;
        
        txtID.setText(k.getGenreID());
        txtName.setText(k.getGenreName());
    }
}
}

