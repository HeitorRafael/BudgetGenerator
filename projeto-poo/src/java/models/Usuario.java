package models;

// import java.time.LocalDateTime; // Se você tiver campo created_at

import java.time.LocalDateTime;


public class Usuario {
    private int id; // Corresponde a id_usuario INTEGER
    private String nome; // Corresponde a nm_usuario TEXT
    private String email; // Corresponde a nm_email_usuario TEXT
    private String senha; // Corresponde a nm_senha_usuario TEXT

    // Construtores
    public Usuario() {}

    // Construtor para registro (assumindo que ID é AUTOINCREMENT no DB)
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    // Opcional: toString() para depuração
    @Override
    public String toString() {
        return "Usuario{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               ", senha='[PROTECTED]'" + // Nunca imprima a senha real!
               '}';
    }

    public void setCreatedAt(LocalDateTime parse) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}