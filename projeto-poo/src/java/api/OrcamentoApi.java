/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import org.json.JSONObject;

/**
 *
 * @author alexo
 */
@WebServlet(name = "OrcamentoApi", urlPatterns = {"/OrcamentoApi"})
public class OrcamentoApi extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Recebe os dados do formulário
        String produto = request.getParameter("produto_servico");
        String materiais = request.getParameter("materiais");
        String tempo = request.getParameter("tempo");
        String maoDeObra = request.getParameter("mao_de_obra");
        String lucro = request.getParameter("lucro");
        String custos = request.getParameter("custos");
        
        //Criando um objeto JSON
        JSONObject json = new JSONObject();
        
        //Colocando os inputs dentro do JSON
        json.put("produto", produto);
        json.put("materiais", materiais);
        json.put("tempo", tempo);
        json.put("maoDeObra", maoDeObra);
        json.put("lucro", lucro);
        json.put("custos", custos);
        
        //Prompt para a IA
        String prompt = "Gere um orçamento detalhado com base nestes dados:\n" +
                "Produto/Serviço: " + produto + "\n" +
                "Materiais necessários: " + materiais + "\n" +
                "Tempo estimado: " + tempo + " horas\n" +
                "Valor da mão de obra: R$" + maoDeObra + "\n" +
                "Margem de lucro: " + lucro + "%\n" +
                "Custos adicionais: R$" + custos + "\n\n" +
                "Por favor, calcule o valor total incluindo todos estes custos " +
                "e acrescentando a margem de lucro informada.";

        //Respostas para a IA
        JSONObject resposta = new JSONObject();
        resposta.put("dados", json);
        resposta.put("prompt", prompt);
        
        System.out.println("Dados recebidos");
        
        //Configuração http
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resposta.toString());
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
