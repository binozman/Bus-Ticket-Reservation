package sample.connectivity;
import java.sql.*;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConnectionClass {
public Connection connection;
    Statement std;
    public Connection getConnection()
    {
        String dbName ="bus";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,username,password);
            std=connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void setSQL(String sql){
        try{
           getConnection();
           int r=std.executeUpdate(sql);
           JOptionPane.showMessageDialog(null, r==1?"operation success":"failed");
           connection.close();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}