package api;

import dao.OrcamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Orcamento;
import models.Usuario;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/RemoverMarcaDagua")
public class RemoverMarcaDaguaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orcamentoId = request.getParameter("id");
        HttpSession session = request.getSession(false);

        // Verifica se o usuário está autenticado
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }

        // Valida o parâmetro "id"
        if (orcamentoId == null || orcamentoId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"ID do orçamento é obrigatório\"}");
            return;
        }

        // Validação robusta: verifica se o ID é numérico positivo
        int orcamentoIdInt;
        try {
            orcamentoIdInt = Integer.parseInt(orcamentoId.trim());
            if (orcamentoIdInt <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json");
                response.getWriter().write("{\"erro\":\"ID do orçamento deve ser um número positivo\"}");
                return;
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"ID do orçamento inválido\"}");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        OrcamentoDAO dao = new OrcamentoDAO();

        try {
            // Busca o orçamento diretamente no banco de dados
            Orcamento orcamento = dao.buscarPorId(Integer.parseInt(orcamentoId), usuario.getId());
            if (orcamento == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"erro\":\"Orçamento não encontrado ou não pertence ao usuário\"}");
                return;
            }

            // Remove a marca d'água
            dao.removerMarcaDagua(orcamento.getId());
            response.setContentType("application/json");
            response.getWriter().write("{\"sucesso\":true}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"Erro ao acessar o banco de dados: " + e.getMessage().replace("\"", "\\\"") + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"erro\":\"Erro inesperado: " + e.getMessage().replace("\"", "\\\"") + "\"}");
        }
    }
}
