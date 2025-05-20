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
    
        // Configura encoding e headers CORS
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");

        //Recebe os dados do formulário
        String produto = request.getParameter("produto_servico");
        String materiais = request.getParameter("materiais");
        String tempo = request.getParameter("tempo");
        String maoDeObra = request.getParameter("mao_de_obra");
        String lucro = request.getParameter("lucro");
        String custos = request.getParameter("custos");

        //Criando um objeto JSON (opcional - você pode remover se não estiver usando)
        JSONObject json = new JSONObject();
        json.put("produto", produto);
        json.put("materiais", materiais);
        json.put("tempo", tempo);
        json.put("maoDeObra", maoDeObra);
        json.put("lucro", lucro);
        json.put("custos", custos);

        //Prompt para a IA
        String prompt = "Você é um assistente que cria orçamentos para pequenos negócios. Gere um orçamento claro e profissional com os dados abaixo:\n\n" +
        "Produto ou serviço: " + produto + "\n" +
        "Materiais: " + materiais + "\n" +
        "Tempo estimado de trabalho: " + tempo + " horas\n" +
        "Valor da mão de obra: R$" + maoDeObra + "\n" +
        "Margem de lucro desejada: " + lucro + "%\n" +
        "Custos adicionais: R$" + custos + "\n\n" +
        "Calcule o valor total final do orçamento e explique brevemente como ele foi calculado.";

        //Chamada da IA
        try{
            String respostaIA = GeminiAPIClient.enviarPrompt(prompt);

            //Cria o JSON de resposta final
            JSONObject resposta = new JSONObject();
            resposta.put("resposta", respostaIA);

            //Configuração http e envia resposta
            response.getWriter().write(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();

            //Cria JSON de erro
            JSONObject error = new JSONObject();
            error.put("erro", "Ocorreu um erro ao gerar o orçamento: " + e.getMessage());

            //Configura status de erro e envia resposta
            response.setStatus(500);
            response.getWriter().write(error.toString());
        }
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
