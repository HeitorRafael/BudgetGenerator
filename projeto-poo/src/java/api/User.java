package api; // Ajuste o pacote conforme sua estrutura

import java.time.LocalDateTime;

public class User {
    private int id;
    private String name;
    private String email;
    private String password; // Lembre-se: será o hash da senha
    private LocalDateTime createdAt;
    // Você pode adicionar um campo para fotoPerfil se for usar o do JSP
    // private String fotoPerfil;

    // Construtor vazio (útil para frameworks ou para instanciar e setar campos depois)
    public User() {
    }

    // Construtor com todos os campos (útil para criar objetos User completos)
    public User(int id, String name, String email, String password, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    // Construtor para criar um novo usuário (sem ID e created_at, pois o DB gera)
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /*
    // Exemplo de getter/setter para fotoPerfil, se você decidir adicioná-lo
    public String getFotoPerfil() {
        // Por enquanto, retorna um placeholder se não houver foto
        return fotoPerfil != null ? fotoPerfil : "https://via.placeholder.com/200x250";
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
    */

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", password='[PROTECTED]'" + // Nunca imprima a senha real em logs!
               ", createdAt=" + createdAt +
               '}';
    }

    public static class java {

        public java() {
        }
    }
}