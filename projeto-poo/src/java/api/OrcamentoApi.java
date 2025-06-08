/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import dao.OrcamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.orcamento;
import org.json.JSONObject;
import utilities.DBConnection;

import java.io.IOException;

@WebServlet(name = "OrcamentoApi", urlPatterns = {"/OrcamentoApi"})
public class OrcamentoApi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String tipo = request.getParameter("tipo");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }

        String usuarioId = ((models.Usuario) session.getAttribute("usuario")).getId();
        OrcamentoDAO dao = new OrcamentoDAO(DBConnection.getConnection());

        try {
            if ("produto".equals(tipo)) {
                // Processar formulário de produto
                String nome = request.getParameter("nome");
                String materiais = request.getParameter("materiais");
                double custo = Double.parseDouble(request.getParameter("custo"));
                double lucro = Double.parseDouble(request.getParameter("lucro"));

                orcamento o = new orcamento(nome, custo + (custo * lucro / 100));
                dao.inserir(o, usuarioId);

                JSONObject resposta = new JSONObject();
                resposta.put("mensagem", "Orçamento de produto salvo com sucesso!");
                response.getWriter().write(resposta.toString());

            } else if ("servico".equals(tipo)) {
                // Processar formulário de serviço
                String descricao = request.getParameter("descricao");
                double horas = Double.parseDouble(request.getParameter("horas"));
                double valorHora = Double.parseDouble(request.getParameter("valor_hora"));
                double custosExtras = Double.parseDouble(request.getParameter("custos_extras"));

                dao.inserirServico(descricao, horas, valorHora, custosExtras, usuarioId);

                JSONObject resposta = new JSONObject();
                resposta.put("mensagem", "Orçamento de serviço salvo com sucesso!");
                response.getWriter().write(resposta.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"erro\":\"Tipo de orçamento inválido\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\":\"Erro ao processar orçamento\"}");
        }
    }
}
