package dao;

import models.Usuario; // Use o modelo Usuario consistentemente
import org.mindrot.jbcrypt.BCrypt; // Importa a biblioteca BCrypt para hashing de senhas

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // Para formatar LocalDateTime para String e vice-versa no SQLite

public class UserDAO {

    // Caminho para o arquivo do banco de dados SQLite
    // Usando o caminho absoluto que você tinha para consistência, mas REVEJA ISSO EM PRODUÇÃO.
    private static final String DB_URL = "jdbc:sqlite:C:/Users/Kauan/Downloads/BudgetGenerator.db"; //
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public UserDAO() {
        // O construtor é o local para carregar o driver e garantir a criação da tabela
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver JDBC para SQLite carregado com sucesso.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro: Driver JDBC para SQLite não encontrado! " + e.getMessage());
            e.printStackTrace();
            // Lançar uma RuntimeException aqui impediria a aplicação de continuar sem o driver
            throw new RuntimeException("Falha ao carregar o driver SQLite JDBC", e);
        }
        createTable(); // Garante que a tabela é criada/verificada
    }

    // Método para obter uma conexão com o banco de dados
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Método para criar a tabela 'users' se ela ainda não existir
    // Usando os nomes de coluna do seu DB (nm_...) e adicionando created_at
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT," + //
                     "nm_email_usuario TEXT NOT NULL UNIQUE," +       //
                     "nm_usuario TEXT," +                             //
                     "nm_senha_usuario TEXT NOT NULL," +              //
                     "created_at DATETIME DEFAULT CURRENT_TIMESTAMP" + // Adicionado created_at
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

    // Método para registrar um novo usuário (usando models.Usuario)
    public boolean registerUser(Usuario usuario) throws SQLException { // Lança SQLException
        // Hash da senha antes de salvar
        String hashedPassword = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
        // Não precisamos setar a senha hasheada no objeto Usuario se ele não for usado depois
        // usuario.setSenha(hashedPassword); // Descomente se precisar da senha hasheada no objeto

        // SQL para inserir um novo usuário
        String sql = "INSERT INTO users (nm_usuario, nm_email_usuario, nm_senha_usuario) VALUES (?, ?, ?)"; // Usando nomes de coluna do seu DB
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, hashedPassword); // Senha já hasheada

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            // Pode haver erro de UNIQUE constraint se o email já existe
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed: users.nm_email_usuario")) { // Ajuste o nome da coluna conforme seu DB
                throw new SQLException("O email já está em uso.", e);
            }
            throw new SQLException("Erro ao registrar usuário: " + e.getMessage(), e);
        }
    }

    // Método para buscar um usuário por e-mail (usando models.Usuario)
    public Usuario getUserByEmail(String email) throws SQLException { // Lança SQLException
        String sql = "SELECT id_usuario, nm_usuario, nm_email_usuario, nm_senha_usuario, created_at FROM users WHERE nm_email_usuario = ?"; // Usando nomes de coluna do seu DB
        Usuario usuario = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setId(rs.getInt("id_usuario")); //
                    usuario.setNome(rs.getString("nm_usuario")); //
                    usuario.setEmail(rs.getString("nm_email_usuario")); //
                    usuario.setSenha(rs.getString("nm_senha_usuario")); // É o hash!

                    String createdAtStr = rs.getString("created_at");
                    if (createdAtStr != null) {
                        // O formato padrão do LocalDateTime.parse geralmente funciona para ISO 8601
                        // Se o SQLite não retornar em ISO 8601 (yyyy-MM-ddTHH:MM:SS), ajuste o parse aqui
                        try {
                           usuario.setCreatedAt(LocalDateTime.parse(createdAtStr, FORMATTER)); // Use o formatador definido
                        } catch (java.time.format.DateTimeParseException dtpe) {
                            System.err.println("Erro ao parsear created_at: " + createdAtStr + " -> " + dtpe.getMessage());
                            // Tente um formato mais simples se o padrão falhar
                            usuario.setCreatedAt(LocalDateTime.parse(createdAtStr.replace(" ", "T")));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar usuário por email: " + e.getMessage(), e);
        }
        return usuario; // Retorna o objeto Usuario ou null se não encontrado
    }

    // Método para verificar a senha (chamado após getUserByEmail)
    public boolean checkPassword(String plainPassword, String hashedPasswordFromDB) {
        // Retorna true se a senha em texto plano corresponder ao hash
        return BCrypt.checkpw(plainPassword, hashedPasswordFromDB);
    }

    // Removendo métodos gerados/duplicados que não são mais necessários
    // public void inserir(User novoUsuario) {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }
    // public User buscarPorLogin(String login) {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }
    // public boolean registerUser(Usuario newUser) {
    //     throw new UnsupportedOperationException("Not supported yet.");
    // }
}