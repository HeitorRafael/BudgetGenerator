/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.UUID;

public class orcamento {
    private String id;
    private String tipo; // "produto" ou "servico"
    // Campos para produto
    private String nomeProduto;
    private String materiais;
    private double custo;
    private double lucro; // em porcentagem

    // Campos para serviço
    private String descricaoServico;
    private double horas;
    private double valorHora;
    private double custosExtras;

    // Comum
    private double valorFinal; // valor calculado
    private String respostaIA; // texto gerado pela IA
    private boolean comMarcaDagua;

    public orcamento(String tipo) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.comMarcaDagua = true;
    }

    // Getters e setters

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public boolean isComMarcaDagua() { return comMarcaDagua; }
    public void removerMarcaDagua() { this.comMarcaDagua = false; }

    // Produto
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public String getMateriais() { return materiais; }
    public void setMateriais(String materiais) { this.materiais = materiais; }
    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }
    public double getLucro() { return lucro; }
    public void setLucro(double lucro) { this.lucro = lucro; }

    // Serviço
    public String getDescricaoServico() { return descricaoServico; }
    public void setDescricaoServico(String descricaoServico) { this.descricaoServico = descricaoServico; }
    public double getHoras() { return horas; }
    public void setHoras(double horas) { this.horas = horas; }
    public double getValorHora() { return valorHora; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }
    public double getCustosExtras() { return custosExtras; }
    public void setCustosExtras(double custosExtras) { this.custosExtras = custosExtras; }

    // Comum
    public double getValorFinal() { return valorFinal; }
    public void setValorFinal(double valorFinal) { this.valorFinal = valorFinal; }
    public String getRespostaIA() { return respostaIA; }
    public void setRespostaIA(String respostaIA) { this.respostaIA = respostaIA; }

    @Override
    public String toString() {
        if ("produto".equals(tipo)) {
            return "Produto: " + nomeProduto + "\nMateriais: " + materiais + "\nCusto: R$" + custo +
                   "\nLucro: " + lucro + "%\nValor Final: R$" + valorFinal +
                   "\n" + (comMarcaDagua ? "[COM MARCA D'ÁGUA]" : "[LIVRE]") +
                   "\nOrçamento IA:\n" + respostaIA;
        } else {
            return "Serviço: " + descricaoServico + "\nHoras: " + horas + "\nValor/Hora: R$" + valorHora +
                   "\nCustos Extras: R$" + custosExtras + "\nValor Final: R$" + valorFinal +
                   "\n" + (comMarcaDagua ? "[COM MARCA D'ÁGUA]" : "[LIVRE]") +
                   "\nOrçamento IA:\n" + respostaIA;
        }
    }
}
