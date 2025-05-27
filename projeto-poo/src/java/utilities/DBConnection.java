//conexão com o DB

package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Retorna uma conexão pronta para uso
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.URL);
    }
}
