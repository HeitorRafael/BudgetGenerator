package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/seubanco"; // Substitua 'seubanco'
    private static final String USER = "seu_usuario"; // Substitua 'seu_usuario'
    private static final String PASSWORD = "sua_senha"; // Substitua 'sua_senha'

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carrega o driver MySQL
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do MySQL não encontrado: " + e.getMessage());
            throw new SQLException("Driver JDBC não encontrado", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}