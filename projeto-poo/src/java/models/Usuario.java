package models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id; // ID único do usuário
    private String login; // Email do usuário
    private String senha; // Hash da senha
    private String nome; // Nome do usuário
    private List<Orcamento> orcamentos; // Lista de orçamentos associados ao usuário
    private String email;

    // Construtor padrão
    public Usuario() {
        this.id = java.util.UUID.randomUUID().toString();
        this.orcamentos = new ArrayList<>();
    }

    // Construtor com parâmetros
    public Usuario(String login, String senha, String nome) {
        this.id = java.util.UUID.randomUUID().toString(); // Gera um ID único
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.orcamentos = new ArrayList<>();
    }

    public Usuario(String usuário_Teste, String testexamplecom, String testexamplecom0, String hashedPassword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Orcamento> getOrcamentos() {
        return orcamentos;
    }

    public void setOrcamentos(List<Orcamento> orcamentos) {
        this.orcamentos = orcamentos;
    }

    // Adiciona um orçamento à lista de orçamentos do usuário
    public void adicionarOrcamento(Orcamento orcamento) {
        this.orcamentos.add(orcamento);
    }

    // Remove um orçamento da lista de orçamentos do usuário
    public void removerOrcamento(Orcamento orcamento) {
        this.orcamentos.remove(orcamento);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
