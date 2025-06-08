package models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String id; // Identificador único do usuário
    private String login; // Nome de usuário ou email
    private String senha; // Senha do usuário (armazenada como hash)
    private List<Orcamento> orcamentos; // Lista de orçamentos associados ao usuário

    // Construtor padrão
    public Usuario() {
        this.orcamentos = new ArrayList<>();
    }

    // Construtor com parâmetros
    public Usuario(String login, String senha) {
        this.login = login;
        this.senha = senha;
        this.orcamentos = new ArrayList<>();
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
}
