package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import dao.TabelasOrcamento;

public class DBConfig {
    public static final String URL = "jdbc:sqlite:/caminho/para/budgetdb.sqlite"; // Altere o caminho conforme seu ambiente
}
