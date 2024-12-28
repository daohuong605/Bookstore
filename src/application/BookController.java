package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dto.book;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BookController {
    @FXML
    TextField txt_book_id;
    @FXML
    ComboBox<String> cbo_genre_id;
    @FXML
    TextField txt_author;
    @FXML
    TextField txt_title;
    @FXML
    TextField txt_publisher;
    @FXML
    TextField txt_price;
    @FXML
    TextField txt_stock;
    @FXML
    TableView<book> tb_view;
    @FXML
    ImageView img;

    Connection conn = null;

    @FXML
    private void click_tb_view(MouseEvent event) {
        if (event.getClickCount() == 2) { // Checking double click
            int row = tb_view.getFocusModel().getFocusedCell().getRow();
            Object st = tb_view.getItems().get(row);
            book k = (book) st;

            txt_book_id.setText(k.getBookid());
            cbo_genre_id.setValue(k.getGenreid());
            txt_author.setText(k.getAuthor());
            txt_title.setText(k.getTitle());
            txt_publisher.setText(k.getPublisher());
            txt_price.setText(k.getPrice());
            txt_stock.setText(k.getStock());

            // Hiển thị ảnh
            if (k.getImg() != null && !k.getImg().isEmpty()) {
                Image imgs = new Image("file:" + k.getImg());
                img.setImage(imgs);
            } else {
                img.setImage(null); // Không có ảnh
            }
        }
    }

    @FXML
    private void click_logout_btn(MouseEvent env) throws IOException {
        // Implement logout functionality here
    }

    @FXML
    private void click_add_btn(MouseEvent env) throws IOException, SQLException {
        try (Statement m_Statement = conn.createStatement()) {
            String imgPath = "null"; // Default if no image
            if (img.getImage() != null) {
                File file = new File(img.getImage().getUrl().replace("file:/", ""));
                imgPath = "images/" + file.getName(); // Save only the file name or relative path
            }

            String query = "INSERT INTO Book (GenreID, Author, Title, Publisher, Price, Stock, Image) VALUES ('" 
                    + cbo_genre_id.getValue() + "', N'" 
                    + txt_author.getText() + "', N'" 
                    + txt_title.getText() + "', N'" 
                    + txt_publisher.getText() + "', " 
                    + txt_price.getText() + ", " 
                    + txt_stock.getText() + ", N'" 
                    + imgPath + "')";
            m_Statement.executeUpdate(query);
            Display();
        }
    }

    @FXML
    private void click_update_btn(MouseEvent env) throws IOException, SQLException {
        try (Statement m_Statement = conn.createStatement()) {
        	String query = "UPDATE Book SET GenreID = '" + cbo_genre_id.getValue() 
        	+ "', Author = N'" + txt_author.getText() 
        	+ "', Title = N'" + txt_title.getText() 
        	+ "', Publisher = N'" + txt_publisher.getText() 
        	+ "', Price = " + txt_price.getText() 
        	+ ", Stock = " + txt_stock.getText() 
        	+ " WHERE BookID = " + txt_book_id.getText();
            m_Statement.executeUpdate(query);
            Display();
        }
    }

    @FXML
    private void click_delete_btn(MouseEvent env) throws IOException, SQLException {
        try (Statement m_Statement = conn.createStatement()) {
            String query = "DELETE FROM Book WHERE BookID='" + txt_book_id.getText() + "'";
            m_Statement.executeUpdate(query);
            Display();
        }
    }

    @FXML
    private void click_clear_btn(MouseEvent env) throws IOException {
        txt_book_id.clear();
        cbo_genre_id.setValue(null);
        txt_author.clear();
        txt_title.clear();
        txt_publisher.clear();
        txt_price.clear();
        txt_stock.clear();
    }

    void Display() throws SQLException {
        tb_view.getItems().clear();
        try (Statement m_Statement = conn.createStatement()) {
            String query = "SELECT * FROM Book";
            ResultSet m_ResultSet = m_Statement.executeQuery(query);
            while (m_ResultSet.next()) {
                book k = new book(
                    m_ResultSet.getString("BookID"),
                    m_ResultSet.getString("GenreID"),
                    m_ResultSet.getString("Author"),
                    m_ResultSet.getString("Title"),
                    m_ResultSet.getString("Publisher"),
                    m_ResultSet.getString("Price"),
                    m_ResultSet.getString("Stock"),
                    m_ResultSet.getString("Image") // Read Img field (relative path)
                );
                tb_view.getItems().add(k);
            }
        }
    }

    public void initialize() {
        try {
            // Set up the database connection
            String url = "jdbc:sqlserver://localhost;DatabaseName=TienThoBookk;user=sa;password=12345678;encrypt=false;";
            conn = DriverManager.getConnection(url);

            if (conn != null) {
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

            // Load genres into the ComboBox
            List<String> genres = new ArrayList<>();
            try (Statement statement = conn.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT GenreID FROM Genre")) {
                while (rs.next()) {
                    genres.add(rs.getString("GenreID"));
                }
                cbo_genre_id.getItems().addAll(genres);
            }

            // Set up the TableView columns
            tb_view.setEditable(true);
            tb_view.getColumns().clear();

            TableColumn<book, String> ID = new TableColumn<>("ID");
            ID.setCellValueFactory(new PropertyValueFactory<>("bookid"));

            TableColumn<book, String> Genre = new TableColumn<>("Genre");
            Genre.setCellValueFactory(new PropertyValueFactory<>("genreid"));

            TableColumn<book, String> Author = new TableColumn<>("Author");
            Author.setCellValueFactory(new PropertyValueFactory<>("author"));

            TableColumn<book, String> Title = new TableColumn<>("Title");
            Title.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<book, String> Publisher = new TableColumn<>("Publisher");
            Publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));

            TableColumn<book, String> Price = new TableColumn<>("Price");
            Price.setCellValueFactory(new PropertyValueFactory<>("price"));

            TableColumn<book, String> Stock = new TableColumn<>("Stock");
            Stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

            TableColumn<book, String> Img = new TableColumn<>("Image");
            Img.setCellValueFactory(new PropertyValueFactory<>("img"));

            tb_view.getColumns().addAll(ID, Title, Author, Genre, Publisher, Price, Stock, Img);

            // Load data from the database into the TableView
            Display();  // Call Display method to populate the TableView with data

        } catch (SQLException ex) {
            System.out.println("Error connecting: " + ex.getMessage());
        }
    }

    @FXML
    private void click_upimage_btn(MouseEvent env) throws IOException {
        try {
            Stage stage = new Stage();
            FileChooser filechooser = new FileChooser();
            filechooser.setTitle("Open Resource File");
            File file = filechooser.showOpenDialog(stage);
            if(file != null) {
                Image imgs = new Image("file:" + file.getPath());
                img.setImage(imgs);    
            }    
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}
