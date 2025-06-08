package models;

import java.time.LocalDate;

public class Orcamento {
    private int id;
    private String tipo; // "produto" ou "servico"
    private LocalDate dataCriacao;
    private String status; // e.g., "pendente", "aprovado"
    private boolean comMarcaDagua;

    // Produto
    private String nome;
    private String materiais;
    private Double custo;
    private Double lucro;

    // Serviço
    private String descricao;
    private String horas;
    private Double valorHora;
    private Double custosExtras;
    public int valor;

    public Orcamento() {
        this.dataCriacao = LocalDate.now();
        this.status = "pendente";
        this.comMarcaDagua = true; // Padrão: com marca d'água
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isComMarcaDagua() { return comMarcaDagua; }
    public void setComMarcaDagua(boolean comMarcaDagua) { this.comMarcaDagua = comMarcaDagua; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getMateriais() { return materiais; }
    public void setMateriais(String materiais) { this.materiais = materiais; }

    public Double getCusto() { return custo; }
    public void setCusto(Double custo) { this.custo = custo; }

    public Double getLucro() { return lucro; }
    public void setLucro(Double lucro) { this.lucro = lucro; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getHoras() { return horas; }
    public void setHoras(String horas) { this.horas = horas; }

    public Double getValorHora() { return valorHora; }
    public void setValorHora(Double valorHora) { this.valorHora = valorHora; }

    public Double getCustosExtras() { return custosExtras; }
    public void setCustosExtras(Double custosExtras) { this.custosExtras = custosExtras; }

    // Métodos de Negócio
    public Double calcularPrecoFinal() {
        if ("produto".equals(tipo)) {
            return custo + (custo * (lucro / 100));
        } else if ("servico".equals(tipo)) {
            return (Double.parseDouble(horas) * valorHora) + custosExtras;
        }
        return 0.0;
    }
}
