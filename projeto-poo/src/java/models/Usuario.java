package models;

import java.time.LocalDateTime;

public class Usuario {
    private int id;
    private String nome; // Corresponde à coluna 'name' no DB
    private String email; // Corresponde à coluna 'email' no DB
    private String senha; // Corresponde à coluna 'password' no DB
    private LocalDateTime createdAt; // Corresponde à coluna 'created_at' no DB

    // Construtor para registro (o ID e created_at serão gerados pelo DB)
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Construtor vazio (útil para quando carregar dados do banco de dados)
    public Usuario() {
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Usuario{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               ", senha='" + senha + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}