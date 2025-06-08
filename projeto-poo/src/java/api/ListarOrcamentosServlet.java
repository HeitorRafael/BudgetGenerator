//esse servlet foi criado para listar os orçamentos de um usuário autenticado

package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.Orcamento;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/orcamentos")
public class ListarOrcamentosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"erro\":\"Usuário não autenticado\"}");
            return;
        }

        User usuario = (User) session.getAttribute("usuario");
        List<Orcamento> orcamentos = usuario.getOrcamentos();

        JSONArray lista = new JSONArray();
        for (Orcamento o : orcamentos) {
            JSONObject obj = new JSONObject();
            obj.put("id", o.getId());
            obj.put("descricao", o.toString());
            obj.put("valor", o.valor); // ajuste se o campo for privado
            obj.put("comMarcaDagua", o.isComMarcaDagua());
            lista.put(obj);
        }

        response.getWriter().write(lista.toString());
    }
}
