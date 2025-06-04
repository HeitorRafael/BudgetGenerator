package api;

import dao.OrcamentoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Usuario;

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
            // Opcional: verifique se o orçamento pertence ao usuário
            dao.excluir(orcamentoId);
            response.getWriter().write("{\"sucesso\":true}");
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"erro\":\"Erro ao excluir orçamento\"}");
        }
    }
}