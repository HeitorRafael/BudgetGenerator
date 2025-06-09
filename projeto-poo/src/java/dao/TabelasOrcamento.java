
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import utilities.DBConnection;

// public class TabelasOrcamento {
//     public static void main(String[] args) {
        
//         //Criação das tabelas de orçamento de produto e serviço
//         try (Connection conn = DBConnection.conectar();
//              Statement stmt = conn.createStatement()) {

//             String sqlProduto = "CREATE TABLE IF NOT EXISTS produtos (" +
//                                 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                                 "nome TEXT," +
//                                 "materiais TEXT," +
//                                 "custo REAL," +
//                                 "lucro REAL," +
//                                 "resposta TEXT)";

//             String sqlServico = "CREATE TABLE IF NOT EXISTS servicos (" +
//                                 "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                                 "descricao TEXT," +
//                                 "horas TEXT," +
//                                 "valor_hora REAL," +
//                                 "custos_extras REAL," +
//                                 "resposta TEXT)";

//             stmt.execute(sqlProduto);
//             stmt.execute(sqlServico);

//             System.out.println("Tabelas criadas!");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
