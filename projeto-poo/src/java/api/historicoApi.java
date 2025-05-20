package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/api/historicoApi")
public class historicoApi extends HttpServlet {

    private static List<JSONObject> historicoDeOrcamentos = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        JSONArray jsonArray = new JSONArray(historicoDeOrcamentos);
        response.getWriter().write(jsonArray.toString());
    }

    // Este método será chamado pela OrcamentoApi para adicionar um novo orçamento ao histórico
    public static void adicionarOrcamentoAoHistorico(JSONObject orcamento) {
        historicoDeOrcamentos.add(orcamento);
    }
}