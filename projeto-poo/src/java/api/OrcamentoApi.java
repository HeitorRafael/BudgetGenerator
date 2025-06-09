
package api;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import utilities.DBConnection;

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
             
             
             //string que vai ser montada com o prompt
             String respostaIA = "";
             
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
                "Você é um assistente especializado em orçamentos de PRODUTOS. Com base nas informações a seguir, gere um orçamento detalhado, estruturado em tópicos:\n\n" +
                "1. Nome do produto: " + nome + "\n" +
                "2. Materiais utilizados: " + materiais + "\n" +
                "   - Pesquise o preço médio de mercado de cada material listado.\n" +
                "3. Custo de produção informado: R$" + custo + "\n" +
                "4. Margem de lucro desejada: " + lucro + "%\n\n" +
                "Objetivo:\n" +
                "- Liste cada material com seu preço médio estimado.\n" +
                "- Calcule o custo total real considerando os materiais.\n" +
                "- Aplique a margem de lucro sobre o custo total e forneça o preço final sugerido de venda.\n\n" +
                "Formato da resposta: utilize listas e subtítulos, evite parágrafos longos, e explique brevemente o cálculo final.";
                
                respostaIA = GeminiAPIClient.enviarPrompt(prompt);
                
                //Conexão com o banco SQlite
                try (Connection conn = DBConnection.conectar();
                    Statement stmt = conn.createStatement()) {

                    //Inserção dos dados do formulario de Produto
                    String sql = "INSERT INTO produtos (nome, materiais, custo, lucro, resposta) VALUES (" +
                                "'" + nome + "', '" + materiais + "', " + custo + ", " + lucro + ", '" + respostaIA.replace("'", "''") + "')";
                    stmt.executeUpdate(sql);
                } catch (Exception ex) {
                    ex.printStackTrace(); // só para garantir que erros no banco não travem a API
}
              
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
                "Você é um assistente que cria orçamentos para SERVIÇOS. Gere um orçamento com base nas seguintes informações:\n\n" +
                "Descrição do serviço: " + descricao + "\n" +
                "Tempo estimado: " + horas + " horas\n" +
                "Valor por hora: R$" + valorHora + "\n" +
                "Custos extras: R$" + custosExtras + "\n\n" +
                "Se aplicável, considere custos indiretos típicos (como transporte, ferramentas, equipamentos, etc) para o tipo de serviço descrito, com valores aproximados de mercado.\n" +
                "Calcule o valor total do serviço considerando todos os custos e explique brevemente como chegou ao valor.";

                respostaIA = GeminiAPIClient.enviarPrompt(prompt);
                
                //Conexão com o banco SQlite
                try (Connection conn = DBConnection.conectar();
                    Statement stmt = conn.createStatement()) {

                    //Inserção dos dados do formulario de Serviços
                    String sql = "INSERT INTO servicos (descricao, horas, valor_hora, custos_extras, resposta) VALUES (" +
                                "'" + descricao + "', '" + horas + "', " + valorHora + ", " + custosExtras + ", '" + respostaIA.replace("'", "''") + "')";
                    stmt.executeUpdate(sql);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
      
                }
             

                //Cria o JSON de resposta final
                JSONObject resposta = new JSONObject();
                resposta.put("resposta", respostaIA);

                //Configuração http e envia resposta
                response.getWriter().write(resposta.toString());
                
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
