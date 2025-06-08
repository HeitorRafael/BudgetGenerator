package api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.Orcamento;
import models.Usuario;
/*import utilities.PDFUtil;*/
import dao.OrcamentoDAO;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/DownloadOrcamento")
public class DownloadOrcamentoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orcamentoId = req.getParameter("id");
        if (orcamentoId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do orçamento não informado.");
            return;
        }

        // Verifica a sessão e o usuário autenticado
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuário não autenticado.");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Valida o ID do orçamento
        int id;
        try {
            id = Integer.parseInt(orcamentoId);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do orçamento inválido.");
            return;
        }

        // Busca o orçamento no banco de dados
        Orcamento orcamento;
        try {
            OrcamentoDAO dao = new OrcamentoDAO();
            orcamento = dao.buscarPorId(id,  usuario.getId());
        } catch (SQLException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao acessar o banco de dados.");
            return;
        }

        if (orcamento == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Orçamento não encontrado ou não pertence ao usuário.");
            return;
        }

        // Gera o PDF do orçamento
        boolean comMarcaDagua = orcamento.isComMarcaDagua();
        /*
        try {
            byte[] pdf = PDFUtil.gerarPDF(orcamento, comMarcaDagua);
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=orcamento_" + orcamentoId + ".pdf");
            resp.setContentLength(pdf.length);
            OutputStream os = resp.getOutputStream();
            os.write(pdf);
            os.flush();
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar PDF.");
        }
        */
    }
}
