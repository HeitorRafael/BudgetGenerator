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


public class Orcamento {
    private String id;
    private String descricao;
    public double valor;
    private boolean comMarcaDagua;

    public Orcamento(String descricao, double valor) {
        this.id = UUID.randomUUID().toString();
        this.descricao = descricao;
        this.valor = valor;
        this.comMarcaDagua = true; // padrão vem com marca d'água
    }

    public Orcamento(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    public void setId(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setNomeProduto(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setValorFinal(double aDouble) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setRespostaIA(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setComMarcaDagua(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getValorFinal() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
