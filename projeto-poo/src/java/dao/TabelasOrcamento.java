
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TabelasOrcamento {
    
    public static void creatTableProduto(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS orcamento_produto (\n" +
"                id TEXT PRIMARY KEY,\n" +
"                nome_produto TEXT NOT NULL,\n" +
"                materiais TEXT NOT NULL,\n" +
"                custo_producao REAL NOT NULL,\n" +
"                margem_lucro REAL NOT NULL,\n" +
"                valor_final REAL NOT NULL,\n" +
"                data_criacao TEXT DEFAULT CURRENT_TIMESTAMP,\n" +
"                com_marca_dagua INTEGER DEFAULT 1\n" +
"            )"
            ;
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela orcamento_produto criada");
        }
    }
    
    public static void crratTableServico(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS orcamento_servico (\n" +
"                id TEXT PRIMARY KEY,\n" +
"                descricao_servico TEXT NOT NULL,\n" +
"                horas_estimadas REAL NOT NULL,\n" +
"                valor_hora REAL NOT NULL,\n" +
"                custos_extras REAL DEFAULT 0,\n" +
"                valor_final REAL NOT NULL,\n" +
"                data_criacao TEXT DEFAULT CURRENT_TIMESTAMP,\n" +
"                com_marca_dagua INTEGER DEFAULT 1\n" +
"            )"
            
         ;
        
        try ( Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela orcamento_servico criada");
        }
    }
    
}
