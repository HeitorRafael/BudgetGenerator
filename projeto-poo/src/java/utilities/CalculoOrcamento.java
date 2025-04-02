/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

/**
 *
 * @author alexo
 */
public class CalculoOrcamento {
    
    public String gerarOrcamento (String produtoServico, String materiais, String tempo, String maoDeObra, String lucro, String custos){
        
        String promptParaIA = "Um usuário deseja gerar um orçamento para um serviço ou produto chamado " + produtoServico +
                        ". Ele utilizará os seguintes materiais: " + materiais +
                        ". O tempo estimado para o serviço é de " + tempo +
                        ". A mão de obra necessária é " + maoDeObra +
                        "Além disso, os custos fixos são de " + custos +
                        ". Considerando uma margem de lucro de " + lucro + "%, gere um orçamento detalhado, incluindo o custo total,"
                + "      a distribuição de despesas e uma sugestão de preço final para o cliente.";  
        return promptParaIA;
    }
    
}
