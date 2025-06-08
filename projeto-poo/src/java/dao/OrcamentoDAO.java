package dao;

import models.Orcamento;
import models.orcamento;

import org.json.JSONArray;
import org.json.JSONObject;
import utilities.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDAO {
    // Insere um produto
    public void inserirProduto(Orcamento o) throws SQLException {
        String sql = "INSERT INTO produtos (nome, materiais, custo, lucro) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, o.getNome());
            stmt.setString(2, o.getMateriais());
            stmt.setDouble(3, o.getCusto());
            stmt.setDouble(4, o.getLucro());
            stmt.executeUpdate();
        }
    }

    // Insere um serviço
    public void inserirServico(Orcamento o) throws SQLException {
        String sql = "INSERT INTO servicos (descricao, horas, valor_hora, custos_extras) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, o.getDescricao());
            stmt.setString(2, o.getHoras());
            stmt.setDouble(3, o.getValorHora());
            stmt.setDouble(4, o.getCustosExtras());
            stmt.executeUpdate();
        }
    }

    // Lista todos os produtos
    public List<Orcamento> listarProdutos() throws SQLException {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Orcamento o = new Orcamento();
                o.setTipo("produto");
                o.setId(rs.getInt("id"));
                o.setNome(rs.getString("nome"));
                o.setMateriais(rs.getString("materiais"));
                o.setCusto(rs.getDouble("custo"));
                o.setLucro(rs.getDouble("lucro"));
                lista.add(o);
            }
        }
        return lista;
    }

    // Lista todos os serviços
    public List<Orcamento> listarServicos() throws SQLException {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Orcamento o = new Orcamento();
                o.setTipo("servico");
                o.setId(rs.getInt("id"));
                o.setDescricao(rs.getString("descricao"));
                o.setHoras(rs.getString("horas"));
                o.setValorHora(rs.getDouble("valor_hora"));
                o.setCustosExtras(rs.getDouble("custos_extras"));
                lista.add(o);
            }
        }
        return lista;
    }

    // Lista todos os orçamentos (produtos e serviços)
    public List<Orcamento> listarTodos() throws SQLException {
        List<Orcamento> lista = new ArrayList<>();
        lista.addAll(listarProdutos());
        lista.addAll(listarServicos());
        return lista;
    }

    // Lista o histórico completo (orçamentos, produtos e serviços) de um usuário
    public JSONArray listarHistoricoCompleto(String usuarioId) throws Exception {
        JSONArray historico = new JSONArray();

        // Busca orçamentos do usuário
        try (Connection conn = DBConnection.conectar()) {
            String sqlOrcamentos = "SELECT id, descricao, valor, comMarcaDagua FROM orcamentos WHERE usuario_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlOrcamentos)) {
                ps.setString(1, usuarioId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        JSONObject obj = new JSONObject();
                        obj.put("tipo", "orcamento");
                        obj.put("id", rs.getInt("id"));
                        obj.put("descricao", rs.getString("descricao"));
                        obj.put("valor", rs.getDouble("valor"));
                        obj.put("comMarcaDagua", rs.getBoolean("comMarcaDagua"));
                        historico.put(obj);
                    }
                }
            }

            // Busca produtos
            String sqlProdutos = "SELECT nome, materiais, custo, lucro FROM produtos";
            try (PreparedStatement ps = conn.prepareStatement(sqlProdutos); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("tipo", "produto");
                    obj.put("nome", rs.getString("nome"));
                    obj.put("materiais", rs.getString("materiais"));
                    obj.put("custo", rs.getDouble("custo"));
                    obj.put("lucro", rs.getDouble("lucro"));
                    historico.put(obj);
                }
            }

            // Busca serviços
            String sqlServicos = "SELECT descricao, horas, valor_hora, custos_extras FROM servicos";
            try (PreparedStatement ps = conn.prepareStatement(sqlServicos); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("tipo", "servico");
                    obj.put("descricao", rs.getString("descricao"));
                    obj.put("horas", rs.getInt("horas"));
                    obj.put("valorHora", rs.getDouble("valor_hora"));
                    obj.put("custosExtras", rs.getDouble("custos_extras"));
                    historico.put(obj);
                }
            }
        }

        return historico;
    }

    /**
     * Método genérico para buscar orçamento por ID, podendo opcionalmente filtrar por usuário.
     * Se usuarioId for null, busca apenas pelo ID. Se não for null, busca pelo ID e usuário.
     */
    public Orcamento buscarPorId(int orcamentoId, String usuarioId) throws SQLException {
        String sql = "SELECT * FROM orcamentos WHERE id = ? AND usuario_id = ?";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orcamentoId);
            stmt.setString(2, usuarioId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Orcamento o = new Orcamento();
                    o.setId(rs.getInt("id"));
                    o.setDescricao(rs.getString("descricao"));
                    o.setValorHora(rs.getDouble("valor"));
                    o.setComMarcaDagua(rs.getBoolean("comMarcaDagua"));
                    return o;
                }
            }
        }
        return null; // Retorna null se o orçamento não for encontrado
    }
    // Implementação do método excluir
    public void excluir(int orcamentoId) throws SQLException {
        String sql = "DELETE FROM orcamentos WHERE id = ?";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orcamentoId);
            stmt.executeUpdate();
        }
    }

    // Implementação do método removerMarcaDagua
    public void removerMarcaDagua(int orcamentoId) throws SQLException {
        String sql = "UPDATE orcamentos SET comMarcaDagua = false WHERE id = ?";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orcamentoId);
            stmt.executeUpdate();
        }
    }
}
