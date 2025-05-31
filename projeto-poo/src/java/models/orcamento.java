/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.util.UUID;
/**
 *
 * @author raffinoh
 */


public class orcamento {
    private String id;
    private String descricao;
    private double valor;
    private boolean comMarcaDagua;

    public orcamento(String descricao, double valor) {
        this.id = UUID.randomUUID().toString();
        this.descricao = descricao;
        this.valor = valor;
        this.comMarcaDagua = true; // padrão vem com marca d'água
    }

    public void removerMarcaDagua() {
        this.comMarcaDagua = false;
    }

    public String getId() {
        return id;
    }

    public boolean isComMarcaDagua() {
        return comMarcaDagua;
    }

    @Override
    public String toString() {
        return "Descrição: " + descricao + "\n" +
               "Valor: R$ " + valor + "\n" +
               (comMarcaDagua ? "[ORÇAMENTO COM MARCA D'ÁGUA]" : "[ORÇAMENTO LIVRE]");
    }
}
