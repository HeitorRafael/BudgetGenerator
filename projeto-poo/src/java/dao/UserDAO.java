package dao; // Ajuste o pacote conforme sua estrutura


import org.mindrot.jbcrypt.BCrypt; // Importa a biblioteca BCrypt para hashing de senhas

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Para formatar LocalDateTime para String e vice-versa no SQLite
import api.User;

public class UserDAO {
    // Caminho para o arquivo do banco de dados SQLite
    // ATENÇÃO: Em uma aplicação web, o ideal é que esse arquivo .db esteja em um local seguro
    // fora da raiz da aplicação web, ou dentro de WEB-INF para não ser acessado diretamente.
    // Para simplificar, vou usar um caminho relativo que você pode ajustar.
    // CONSIDERE um caminho absoluto para produção ou um local configurável!
    private static final String JDBC_URL = "jdbc:sqlite:./login_app.db"; // Onde login_app.db será criado ou lido

    public UserDAO() {
        // O construtor pode ser usado para garantir que o driver seja carregado
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC para SQLite não encontrado!");
            e.printStackTrace();
        }
        // Chamamos o método para criar a tabela se ela não existir
        createTable();
    }

    // Método para obter uma conexão com o banco de dados
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }

    // Método para criar a tabela 'users' se ela ainda não existir
    // Isso é útil para inicializar o DB na primeira execução, se você não fez via SQLite Studio
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "name TEXT NOT NULL," +
                     "email TEXT NOT NULL UNIQUE," +
                     "password TEXT NOT NULL," +
                     "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" +
                     ");";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela 'users' verificada/criada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela 'users': " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para registrar um novo usuário
    public boolean registerUser(User user) {
        // Hash da senha antes de salvar
        String hashedPassword = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt());
        user.setSenha(hashedPassword); // Atualiza o objeto user com a senha hasheada

        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getSenha()); // Senha já hasheada

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Se o email já existe (UNIQUE constraint violation)
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed: users.email")) {
                System.err.println("Erro: Email já cadastrado.");
                return false; // Retorna false se o email já estiver em uso
            }
            System.err.println("Erro ao registrar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para verificar as credenciais de login
    public User getUserByEmail(String email) {
        String sql = "SELECT id, name, email, password, created_at FROM users WHERE email = ?";
        User user = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setSenha(rs.getString("password")); // É o hash, não a senha em texto puro!

                // Convertendo String para LocalDateTime (SQLite armazena DATETIME como TEXT)
                String createdAtStr = rs.getString("created_at");
                if (createdAtStr != null) {
                    // SQLite geralmente armazena DATETIME no formato "YYYY-MM-DD HH:MM:SS"
                    // Ou "YYYY-MM-DD HH:MM:SS.SSS"
                    // O formato padrão do LocalDateTime.parse geralmente funciona para ISO 8601
                    user.setCreatedAt(LocalDateTime.parse(createdAtStr.replace(" ", "T")));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }

    // Método para verificar a senha (chamado após getUserByEmail)
    public boolean checkPassword(String plainPassword, String hashedPasswordFromDB) {
        return BCrypt.checkpw(plainPassword, hashedPasswordFromDB);
    }

    public void inserir(User novoUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public User buscarPorLogin(String login) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}