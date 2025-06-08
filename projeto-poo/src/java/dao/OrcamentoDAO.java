package dao;

import models.Orcamento;
import utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDAO {

    // Salva um novo orçamento
    public void inserir(Orcamento o, String usuarioId) throws SQLException {
        String sql = "INSERT INTO orcamentos (id, usuario_id, descricao, valor, com_marca_dagua) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, o.getId());
            stmt.setString(2, usuarioId);
            stmt.setString(3, o.toString()); // ou o.getDescricao()
            stmt.setDouble(4, o.valor);
            stmt.setInt(5, o.isComMarcaDagua() ? 1 : 0);
            stmt.executeUpdate();
        }
    }

    // Lista todos os orçamentos de um usuário
    public List<Orcamento> listarPorUsuario(String usuarioId) throws SQLException {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM orcamentos WHERE usuario_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Orcamento o = new Orcamento(rs.getString("descricao"), rs.getDouble("valor"));
                // Ajuste o construtor se necessário
                lista.add(o);
            }
        }
        return lista;
    }

    // Remove a marca d'água (define como false)
    public void removerMarcaDagua(String orcamentoId) throws SQLException {
        String sql = "UPDATE orcamentos SET com_marca_dagua = 0 WHERE id = ?";
        try (Connection conn = utilities.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orcamentoId);
            stmt.executeUpdate();
        }
    }

    public static Orcamento buscarPorId(String id) throws SQLException {
        String sql = "SELECT * FROM orcamentos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Orcamento o = new Orcamento(rs.getString("tipo"));
                // Preencha os campos conforme seu modelo
                o.setId(rs.getString("id"));
                o.setNomeProduto(rs.getString("nome_produto"));
                // ... outros campos ...
                o.setValorFinal(rs.getDouble("valor_final"));
                o.setRespostaIA(rs.getString("resposta_ia"));
                o.setComMarcaDagua(rs.getInt("comMarcaDagua") == 1);
                return o;
            }
        }
        return null;
    }

    // Exclui um orçamento pelo ID
    public void excluir(String orcamentoId) throws SQLException {
        String sql = "DELETE FROM orcamentos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, orcamentoId);
            stmt.executeUpdate();
        }
    }
}
