package api;

import dao.OrcamentoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Usuario;
import models.orcamento;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

@WebServlet("/HistoricoOrcamentos")
public class HistoricoOrcamentosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
        if (usuario == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }
        try {
            List<orcamento> lista = new OrcamentoDAO().listarPorUsuario(usuario.getId());
            JSONArray arr = new JSONArray();
            for (orcamento o : lista) {
                JSONObject obj = new JSONObject();
                obj.put("id", o.getId());
                obj.put("descricao", o.getDescricao());
                obj.put("valor", o.getValor());
                obj.put("comMarcaDagua", o.isComMarcaDagua());
                // Adicione outros campos conforme necessário
                arr.put(obj);
            }
            response.getWriter().write(arr.toString());
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"erro\":\"Erro ao buscar histórico\"}");
        }
    }
}