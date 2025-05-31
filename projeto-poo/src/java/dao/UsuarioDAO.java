package dao;

import models.Usuario;
import utilities.DBConnection;

import java.sql.*;

public class UsuarioDAO {

    // Insere um novo usuário no banco
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id, login, senha) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
        }
    }

    // Busca usuário pelo login
    public Usuario buscarPorLogin(String login) throws SQLException {
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
}
