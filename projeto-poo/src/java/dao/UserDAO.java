package dao;

import models.Usuario; // Importa a classe de modelo Usuario
import utilities.DBConnection; // Importa sua classe DBConnection
import org.mindrot.jbcrypt.BCrypt; // Para hash de senhas

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserDAO {

    // Formato para parsear/formatar datas do banco de dados
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public UserDAO() {
        // Garante que a tabela é criada ou verificada quando o DAO é instanciado
        createTable();
    }

    // Método para criar a tabela 'users' se ela ainda não existir
    // ATENÇÃO: Nomes de coluna ajustados para 'id', 'name', 'email', 'password'
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "name TEXT," +
                     "email TEXT NOT NULL UNIQUE," +
                     "password TEXT NOT NULL," +
                     "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                     ");";
        try (Connection conn = DBConnection.conectar(); // Obtém a conexão do DBConnection
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'users' verificada/criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela 'users': " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para registrar um novo usuário
    public boolean registerUser(Usuario usuario) throws SQLException {
        // Gera um hash seguro da senha usando BCrypt
        String hashedPassword = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

        // SQL para inserir um novo usuário, usando os nomes de coluna 'name', 'email', 'password'
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.conectar(); // Obtém a conexão
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, hashedPassword);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            // Verifica se o erro é devido a uma violação de unicidade (email já existe)
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed: users.email")) {
                throw new SQLException("O email já está em uso.", e);
            }
            throw new SQLException("Erro ao registrar usuário: " + e.getMessage(), e);
        }
    }

    // Método para buscar um usuário por e-mail
    public Usuario getUserByEmail(String email) throws SQLException {
        // SQL para buscar um usuário, usando os nomes de coluna 'id', 'name', 'email', 'password', 'created_at'
        String sql = "SELECT id, name, email, password, created_at FROM users WHERE email = ?";
        Usuario usuario = null;
        try (Connection conn = DBConnection.conectar(); // Obtém a conexão
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id")); // Mapeia para o ID
                    usuario.setNome(rs.getString("name")); // Mapeia para o nome
                    usuario.setEmail(rs.getString("email")); // Mapeia para o email
                    usuario.setSenha(rs.getString("password")); // Mapeia para a senha hasheada

                    String createdAtStr = rs.getString("created_at");
                    if (createdAtStr != null) {
                        try {
                           usuario.setCreatedAt(LocalDateTime.parse(createdAtStr, FORMATTER));
                        } catch (java.time.format.DateTimeParseException dtpe) {
                            // Tenta um formato alternativo se o primeiro falhar
                            System.err.println("Erro ao parsear created_at: " + createdAtStr + " -> " + dtpe.getMessage());
                            usuario.setCreatedAt(LocalDateTime.parse(createdAtStr.replace(" ", "T")));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar usuário por email: " + e.getMessage(), e);
        }
        return usuario;
    }

    // Método para verificar a senha (texto puro vs. hash)
    public boolean checkPassword(String plainPassword, String hashedPasswordFromDB) {
        return BCrypt.checkpw(plainPassword, hashedPasswordFromDB);
    }
}