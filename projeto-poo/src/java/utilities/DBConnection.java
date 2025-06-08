package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Caminho do banco de dados SQLite
    private static final String URL = "db/BudgetGenerator.db-journal";

    public static Connection conectar() throws SQLException {
        try {
            // Esta linha força o carregamento do driver JDBC do SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite não encontrado.", e);
        }

        // Conexão com o banco de dados
        return DriverManager.getConnection(URL);
    }

    public static Connection getConnection() throws SQLException {
        return conectar(); // Reutiliza o método conectar para obter a conexão
    }
}