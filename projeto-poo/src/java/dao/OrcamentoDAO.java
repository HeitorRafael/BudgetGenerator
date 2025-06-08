package dao;

import models.Orcamento;
import utilities.DBConnection;
import java.sql.*;
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
}
