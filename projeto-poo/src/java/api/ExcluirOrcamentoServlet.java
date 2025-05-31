package api;

import dao.OrcamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Usuario;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ExcluirOrcamento")
public class ExcluirOrcamentoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orcamentoId = request.getParameter("id");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        OrcamentoDAO dao = new OrcamentoDAO();
        try {
            // (Opcional) Verifique se o orçamento pertence ao usuário antes de excluir
            dao.excluir(orcamentoId);
            response.setContentType("application/json");
            response.getWriter().write("{\"sucesso\":true}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\":\"Erro ao excluir orçamento\"}");
        }
    }
}
