
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class InsercaoDadosOrcamento {
    
    public static void insertProduto(Connection conn, String nome, String materiais,
                            double custo, double margem) throws SQLException{
        
        String sql = "INSERT INTO orcamento_produto (\n" +
"                id, nome_produto, materiais, \n" +
"                custo_producao, margem_lucro, valor_final\n" +
"            ) VALUES (?, ?, ?, ?, ?, ?)"
            ;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String id = UUID.randomUUID().toString();
            double valorFinal = custo * (1 + margem/100);

            pstmt.setString(1, id);
            pstmt.setString(2, nome);
            pstmt.setString(3, materiais);
            pstmt.setDouble(4, custo);
            pstmt.setDouble(5, margem);
            pstmt.setDouble(6, valorFinal);

            pstmt.executeUpdate();
            System.out.println("Orçamento de produto inserido! ID: " + id);
        }
    }
    
        public static void insertServico(Connection conn, String descricao, double horas,
                                double valorHora, double custosExtras) throws SQLException {

            String sql = "INSERT INTO orcamento_servico (\n" +
"                    id, descricao_servico, horas_estimadas,\n" +
"                    valor_hora, custos_extras, valor_final\n" +
"                ) VALUES (?, ?, ?, ?, ?, ?)"
                ;

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                String id = UUID.randomUUID().toString();
                double valorFinal = (horas * valorHora) + custosExtras;

                pstmt.setString(1, id);
                pstmt.setString(2, descricao);
                pstmt.setDouble(3, horas);
                pstmt.setDouble(4, valorHora);
                pstmt.setDouble(5, custosExtras);
                pstmt.setDouble(6, valorFinal);

                pstmt.executeUpdate();
                System.out.println("Orçamento de serviço inserido! ID: " + id);
            }
        }
    
    
}
