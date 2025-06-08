//esse servlet foi criado para listar os orçamentos de um usuário autenticado

package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import models.Orcamento;
import models.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/orcamentos")
public class ListarOrcamentosServlet extends HttpServlet {
    private static final int PAGE_SIZE = 10; // Tamanho padrão da página

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

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Orcamento> orcamentos = usuario.getOrcamentos();

        // Validação robusta dos parâmetros de paginação
        int page = 1;
        int pageSize = PAGE_SIZE;
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"erro\":\"Parâmetro 'page' deve ser maior ou igual a 1.\"}");
                    return;
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"erro\":\"Parâmetro 'page' inválido.\"}");
                return;
            }
        }

        if (pageSizeParam != null) {
            try {
                pageSize = Integer.parseInt(pageSizeParam);
                if (pageSize < 1 || pageSize > 100) { // Limite superior para evitar sobrecarga
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"erro\":\"Parâmetro 'pageSize' deve ser entre 1 e 100.\"}");
                    return;
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"erro\":\"Parâmetro 'pageSize' inválido.\"}");
                return;
            }
        }

        int totalOrcamentos = orcamentos.size();
        int totalPaginas = (int) Math.ceil((double) totalOrcamentos / pageSize);

        if (page > totalPaginas && totalOrcamentos > 0) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"erro\":\"Página solicitada não existe.\"}");
            return;
        }

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalOrcamentos);

        JSONArray lista = new JSONArray();
        for (int i = fromIndex; i < toIndex; i++) {
            Orcamento o = orcamentos.get(i);
            JSONObject obj = new JSONObject();
            obj.put("id", o.getId());
            obj.put("descricao", o.toString());
            double valorSeguro = (o.valor > 0) ? o.valor : 0;
            obj.put("valor", valorSeguro);
            obj.put("comMarcaDagua", o.isComMarcaDagua());
            obj.put("dataCriacao", o.getDataCriacao() != null ? o.getDataCriacao().toString() : "");
            obj.put("status", o.getStatus() != null ? o.getStatus() : "Indefinido");
            lista.put(obj);
        }

        JSONObject resultado = new JSONObject();
        resultado.put("orcamentos", lista);
        resultado.put("paginaAtual", page);
        resultado.put("totalPaginas", totalPaginas);
        resultado.put("totalOrcamentos", totalOrcamentos);

        response.getWriter().write(resultado.toString());
    }
}
