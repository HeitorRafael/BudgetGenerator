package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Caminho do banco de dados SQLite
    private static final String URL = "jdbc:sqlite:C:\\Usuários\\Kauan\\Documents\\BudgetGenerator\\banco de dados\\BudgetGenerator.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
