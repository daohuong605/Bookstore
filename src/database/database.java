package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // URL kết nối JDBC với SSL được bỏ qua
            String url = "jdbc:sqlserver://localhost:1433;databaseName=TienThoBookk;encrypt=true;trustServerCertificate=true;";
            String username = "sa";
            String password = "12345678";

            // Kết nối cơ sở dữ liệu
            conn = DriverManager.getConnection(url, username, password);

            // Nếu kết nối thành công, in ra dòng "Kết nối thành công"
            if (conn != null) {
                System.out.println("Kết nối thành công!");

                // Lấy thông tin về cơ sở dữ liệu
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());

                // Tạo Statement để thực thi câu lệnh SQL
                stmt = conn.createStatement();

                // Thực thi câu lệnh SELECT *
                String query = "SELECT * FROM Genre";  // Thay YourTableName bằng tên bảng của bạn
                rs = stmt.executeQuery(query);

                // In kết quả từ ResultSet
                while (rs.next()) {
                    // In ra các cột trong bảng (thay "Column1", "Column2" bằng tên cột thực tế trong bảng của bạn)
                    System.out.println("Column1: " + rs.getString("Column1"));
                    System.out.println("Column2: " + rs.getString("Column2"));
                    // Thêm các cột khác nếu cần
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error connecting to database: " + ex.getMessage());
        } finally {
            // Đảm bảo luôn đóng kết nối và các tài nguyên
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }
    }
}
