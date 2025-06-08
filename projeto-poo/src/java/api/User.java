/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import models.Orcamento;

/**
 *
 * @author raffinoh
 */
public class User {
    
    private String id;
    private String usuario;
    private String senha;
    private String email;
    private String fotoPerfil;
    private List<Orcamento> orcamentos;
    private boolean logado;
    private String nome; // Exemplo de campo para armazenar o nome    

    User(String name, String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public User() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getName() {
        return nome;
    }

    public void setName(String nome) { // Se você não tiver um setter, adicione-o também
        this.nome = nome;
    }


    public User(String nome, String email, String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
        this.email = email;
        this.id = UUID.randomUUID().toString();
        this.orcamentos = new ArrayList<>();
        this.logado = true;
    }



    public User(String usuario, String senha) {
        this.id = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.senha = senha;
        this.orcamentos = new ArrayList<>();
        this.logado = true;
    }

    // Foto de perfil
    public void setFotoPerfil(String caminhoArquivo) {
        this.fotoPerfil = caminhoArquivo;
    }

    public String getFotoPerfil() {
        return this.fotoPerfil;
    }

    // Alterar senha
    public boolean alterarSenha(String senhaAntiga, String novaSenha) {
        if (this.senha.equals(senhaAntiga)) {
            this.senha = novaSenha;
            return true;
        }
        return false;
    }

    // Gerar orçamento com marca d'água
    public Orcamento gerarOrcamento(String descricao, double valor) {
        Orcamento o = new Orcamento(descricao, valor);
        orcamentos.add(o);
        return o;
    }

    // Ver orçamentos
    public List<Orcamento> getOrcamentos() {
        return orcamentos;
    }

    // Excluir orçamento
    public boolean excluirOrcamento(String id) {
        return orcamentos.removeIf(o -> o.getId().equals(id));
    }

    // Imprimir orçamento (simulação de impressão)
    public void imprimirOrcamento(String id) {
        Orcamento o = buscarOrcamentoPorId(id);
        if (o != null) {
            System.out.println("=== ORÇAMENTO ===");
            System.out.println(o);
        } else {
            System.out.println("Orçamento não encontrado.");
        }
    }

    // Tirar marca d'água após pagamento (simulação)
    public boolean pagarParaTirarMarcaDagua(String id) {
        Orcamento o = buscarOrcamentoPorId(id);
        if (o != null) {
            o.removerMarcaDagua();
            return true;
        }
        return false;
    }

    // Encerrar sessão
    public void sair() {
        this.logado = false;
    }

    // Buscar orçamento
    private Orcamento buscarOrcamentoPorId(String id) {
        for (Orcamento o : orcamentos) {
            if (o.getId().equals(id)) {
                return o;
            }
        }
        return null;
    }

    public boolean isLogado() {
        return logado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public void setCreatedAt(LocalDateTime parse) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
