package api;

import dao.OrcamentoDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Usuario;
import models.Orcamento;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utilities.DBConnection;

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
            JSONArray arr = new JSONArray();
            // Consulta produtos
            try (Connection conn = DBConnection.conectar()) {
                String sqlProdutos = "SELECT nome, materiais, custo, lucro FROM produtos";
                try (PreparedStatement ps = conn.prepareStatement(sqlProdutos); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        JSONObject obj = new JSONObject();
                        obj.put("tipo", "produto");
                        obj.put("nome", rs.getString("nome"));
                        obj.put("materiais", rs.getString("materiais"));
                        obj.put("custo", rs.getDouble("custo"));
                        obj.put("lucro", rs.getDouble("lucro"));
                        arr.put(obj);
                    }
                }
                // Consulta serviços
                String sqlServicos = "SELECT descricao, horas, valor_hora, custos_extras FROM servicos";
                try (PreparedStatement ps = conn.prepareStatement(sqlServicos); ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        JSONObject obj = new JSONObject();
                        obj.put("tipo", "servico");
                        obj.put("descricao", rs.getString("descricao"));
                        obj.put("horas", rs.getString("horas"));
                        obj.put("valor_hora", rs.getDouble("valor_hora"));
                        obj.put("custos_extras", rs.getDouble("custos_extras"));
                        arr.put(obj);
                    }
                }
            }
            response.getWriter().write(arr.toString());
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("{\"erro\":\"Erro ao buscar histórico\"}");
        }
    }
}