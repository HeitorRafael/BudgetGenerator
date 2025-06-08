package api;

import dao.OrcamentoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Usuario;
import models.Orcamento;

import java.io.IOException;

@WebServlet("/ExcluirOrcamento")
public class ExcluirOrcamentoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orcamentoId = request.getParameter("id");
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
        if (usuario == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }
        try {
            OrcamentoDAO dao = new OrcamentoDAO();
            Orcamento orcamento = dao.buscarPorId(Integer.parseInt(orcamentoId), usuario.getId());
            if (orcamento == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"erro\":\"Orçamento não encontrado ou não pertence ao usuário\"}");
                return;
            }

            // Exclui o orçamento
            dao.excluir(orcamento.getId());
            response.setContentType("application/json");
            response.getWriter().write("{\"sucesso\":true}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"erro\":\"Erro ao excluir orçamento\"}");
        }
    }
}