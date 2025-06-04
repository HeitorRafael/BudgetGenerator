/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

        //Pega o tipo do formulário
        String tipo = request.getParameter("tipo");
        
        try{
            //Criação de um objeto json com os dados recebidos
             JSONObject json = new JSONObject();
             
             //Monta o prompt especifico para o tipo do formulario
             String prompt;
             if("produto".equals(tipo)){
                String nome = request.getParameter("nome");
                String materiais = request.getParameter("materiais");
                String custo = request.getParameter("custo");
                String lucro = request.getParameter("lucro");
                 
                json.put("tipo", tipo);
                json.put("nome", nome);
                json.put("materiais", materiais);
                json.put("custo", custo);
                json.put("lucro", lucro);
                
                // Prompt para produtos
                prompt = 
                "Você é um assistente que cria orçamentos para PRODUTOS. Gere um orçamento com:\n\n" +
                "Nome do produto: " + nome + "\n" +
                "Materiais utilizados: " + materiais + "\n" +
                "Custo de produção: R$" + custo + "\n" +
                "Margem de lucro desejada: " + lucro + "%\n\n" +
                "Calcule o preço final de venda e explique o cálculo.";
              
             }else{
                 
                // Obtém parâmetros específicos de serviço
                String descricao = request.getParameter("descricao");
                String horas = request.getParameter("horas");
                String valorHora = request.getParameter("valor_hora");
                String custosExtras = request.getParameter("custos_extras");

                json.put("tipo", tipo);
                json.put("descricao", descricao);
                json.put("horas", horas);
                json.put("valor_hora", valorHora);
                json.put("custos_extras", custosExtras);

                // Prompt para serviços
                prompt = 
                "Você é um assistente que cria orçamentos para SERVIÇOS. Gere um orçamento com:\n\n" +
                "Descrição do serviço: " + descricao + "\n" +
                "Tempo estimado: " + horas + " horas\n" +
                "Valor por hora: R$" + valorHora + "\n" +
                "Custos extras: R$" + custosExtras + "\n\n" +
                "Calcule o valor total e explique o cálculo.";
      
             }
             
                String respostaIA = GeminiAPIClient.enviarPrompt(prompt);

                //Cria o JSON de resposta final
                JSONObject resposta = new JSONObject();
                resposta.put("resposta", respostaIA);

                //Configuração http e envia resposta
                response.getWriter().write(resposta.toString());
                
                // Após gerar o orçamento com a IA:
                String orcamentoGerado = GeminiAPIClient.enviarPrompt(prompt);

                // Pegue o ID do usuário autenticado (usando sessão)
                HttpSession session = request.getSession(false);
                Integer usuarioId = (Integer) session.getAttribute("usuarioId");

                // Salve no banco
                OrcamentoDAO dao = new OrcamentoDAO();
                dao.salvarOrcamento(usuarioId, orcamentoGerado);

                // ...retorne o orçamento para o frontend...
        }catch(Exception e){
            
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
