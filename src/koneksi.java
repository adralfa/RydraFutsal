

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Adra Zulfi A
 */
public class koneksi {
    Connection kon;
    public koneksi() throws InstantiationException, IllegalAccessException, SQLException {
        String id, pass, driver, url;
        id = "root";
        pass = "";
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/futsal";
        
        try {
            Class.forName(driver).newInstance();
            kon = DriverManager.getConnection(url, id, pass);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println("" + e.getLocalizedMessage());
        }
    }
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, SQLException {
        koneksi k = new koneksi();
    }
}
