package dao;

import models.Usuario;
import utilities.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Insere um novo usuário no banco
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id, login, senha) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
        }
    }

    // Busca usuário pelo login
    public static Usuario buscarPorLogin(String login) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(rs.getString("login"), rs.getString("senha"));
                u.setId(rs.getString("id"));
                return u;
            }
        }
        return null;
    }

    public List<String> listarOrcamentosPorUsuario(int usuarioId) throws SQLException {
        List<String> orcamentos = new ArrayList<>();
        String sql = "SELECT conteudo FROM orcamentos WHERE usuario_id = ? ORDER BY data_criacao DESC";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orcamentos.add(rs.getString("conteudo"));
            }
        }
        return orcamentos;
    }

    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(rs.getString("login"), rs.getString("senha"));
                u.setId(rs.getString("id"));
                return u;
            }
        }
        return null;
    }

    // Método auxiliar para obter conexão, caso não exista
    private Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
}
