package api;

import dao.OrcamentoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Orcamento;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/RemoverMarcaDagua")
public class RemoverMarcaDaguaServlet extends HttpServlet {
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
        User usuario = (User) session.getAttribute("usuario");

        OrcamentoDAO dao = new OrcamentoDAO();
        try {
            // Busca o orçamento do usuário
            List<Orcamento> orcamentos = dao.listarPorUsuario(usuario.getId());
            Orcamento encontrado = null;
            for (Orcamento o : orcamentos) {
                if (o.getId().equals(orcamentoId)) {
                    encontrado = o;
                    break;
                }
            }
            if (encontrado == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"erro\":\"Orçamento não encontrado\"}");
                return;
            }

            // Atualiza no banco para remover a marca d'água
            dao.removerMarcaDagua(orcamentoId);

            response.setContentType("application/json");
            response.getWriter().write("{\"sucesso\":true}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"erro\":\"Erro ao atualizar orçamento\"}");
        }
    }
}
